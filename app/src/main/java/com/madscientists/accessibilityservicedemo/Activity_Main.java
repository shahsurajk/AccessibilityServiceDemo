package com.madscientists.accessibilityservicedemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.madscientists.accessibilityservicedemo.utils.Util;

public class Activity_Main extends AppCompatActivity {

    private AppCompatButton button;
    private AppCompatButton openChromeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_accessibility_settings);
        openChromeButton = findViewById(R.id.btn_open_browser);
    }

    @Override
    protected void onStart() {
        super.onStart();

        openChromeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chromePackage = "com.android.chrome";
                Intent intent;
                if (Util.isPackageInstalled(chromePackage, getPackageManager())){
                    intent = new Intent(getPackageManager().getLaunchIntentForPackage(chromePackage));
                }else{
                    intent = Util.getOpenAppOrOpenPlayStoreIntent(chromePackage);
                }
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });
    }
}
