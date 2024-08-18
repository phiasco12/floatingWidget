package com.example.floatingwidget;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import org.apache.cordova.CordovaActivity;

public class MyCordovaActivity extends CordovaActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUrl(launchUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Settings.canDrawOverlays(this)) {
                // Permission granted
                // Optionally, you might want to re-trigger showing the floating widget
            } else {
                // Permission denied
                // Notify user or handle accordingly
            }
        }
    }
}
