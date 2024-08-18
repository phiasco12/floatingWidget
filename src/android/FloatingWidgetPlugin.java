package com.example.floatingwidget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

public class FloatingWidgetPlugin extends CordovaPlugin {
    private WindowManager windowManager;
    private View floatingView;
    private WindowManager.LayoutParams params;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("showFloatingWidget")) {
            showFloatingWidget(callbackContext);
            return true;
        }
        return false;
    }

private void showFloatingWidget(CallbackContext callbackContext) {
    Context context = cordova.getActivity().getApplicationContext();
    windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

    // Check for overlay permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(cordova.getActivity())) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + cordova.getActivity().getPackageName()));
        cordova.getActivity().startActivityForResult(intent, 0);
        callbackContext.error("Overlay permission is required. Please grant it in the settings.");
        return;
    }

    // Inflate the floating widget layout
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    int layoutId = context.getResources().getIdentifier("floating_widget_layout", "layout", context.getPackageName());
    if (layoutId == 0) {
        callbackContext.error("Floating widget layout not found.");
        return;
    }

    floatingView = inflater.inflate(layoutId, null);

    // Set up layout parameters for the floating widget
    params = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        android.graphics.PixelFormat.TRANSLUCENT
    );
    params.gravity = Gravity.TOP | Gravity.LEFT;
    params.x = 0;
    params.y = 100;

    // Add the view to the window
    windowManager.addView(floatingView, params);

    // Make the floating widget draggable
    floatingView.setOnTouchListener(new View.OnTouchListener() {
        private int xOffset;
        private int yOffset;
        private float xStart;
        private float yStart;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xStart = event.getRawX();
                    yStart = event.getRawY();
                    xOffset = params.x;
                    yOffset = params.y;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    params.x = xOffset + (int) (event.getRawX() - xStart);
                    params.y = yOffset + (int) (event.getRawY() - yStart);
                    windowManager.updateViewLayout(floatingView, params);
                    return true;
            }
            return false;
        }
    });

    // Set up OnClickListener to open the app
    floatingView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("FloatingWidgetPlugin", "Floating widget clicked");

            // Create an intent to open the main activity of the app
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            if (launchIntent != null) {
                context.startActivity(launchIntent);
            } else {
                Log.e("FloatingWidgetPlugin", "Unable to open the app.");
                callbackContext.error("Unable to open the app.");
            }
        }
    });

    callbackContext.success();
}

}
