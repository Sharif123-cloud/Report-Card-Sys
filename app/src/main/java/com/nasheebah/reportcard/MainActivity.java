package com.nasheebah.reportcard;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.webkit.WebSettings;

public class MainActivity extends AppCompatActivity {

    private static final String PASSCODE = "0 787816686";
    private static final String SALT = "NasheebahCoreSalt2026!@#";
    private static final String PREFS_NAME = "NasheebahPrefs";
    private static final String KEY_ACTIVATED = "activated";
    private static final String KEY_DEVICE_ID = "device_id";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Global crash handler to log and show error
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            String msg = "Crash: " + throwable.getMessage();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            android.util.Log.e("ReportCardCrash", msg, throwable);
            finish();
        });

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean activated = prefs.getBoolean(KEY_ACTIVATED, false);

        if (activated) {
            loadReportCard();
        } else {
            setContentView(R.layout.activity_passcode);
            EditText passInput = findViewById(R.id.passInput);
            Button passBtn = findViewById(R.id.passBtn);
            passBtn.setOnClickListener(v -> {
                String entered = passInput.getText().toString();
                if (entered.equals(PASSCODE)) {
                    checkActivation();
                } else {
                    Toast.makeText(this, "Wrong passcode!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkActivation() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String deviceId = DeviceUtils.getDeviceId(this);
        String storedDeviceId = prefs.getString(KEY_DEVICE_ID, "");

        if (storedDeviceId.isEmpty()) {
            showActivationDialog();
        } else if (storedDeviceId.equals(deviceId)) {
            prefs.edit().putBoolean(KEY_ACTIVATED, true).apply();
            loadReportCard();
        } else {
            Toast.makeText(this, "This app is not authorized on this device.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void showActivationDialog() {
        setContentView(R.layout.activity_activation);
        EditText codeInput = findViewById(R.id.codeInput);
        Button activateBtn = findViewById(R.id.activateBtn);
        activateBtn.setOnClickListener(v -> {
            String enteredCode = codeInput.getText().toString().trim();
            String deviceId = DeviceUtils.getDeviceId(this);
            String expectedCode = DeviceUtils.generateActivationCode(deviceId, SALT);
            if (enteredCode.equals(expectedCode)) {
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                prefs.edit().putBoolean(KEY_ACTIVATED, true)
                            .putString(KEY_DEVICE_ID, deviceId)
                            .apply();
                loadReportCard();
            } else {
                Toast.makeText(this, "Invalid activation code!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReportCard() {
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webView);

        // Compatibility settings for older Android versions
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setSaveFormData(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "WebView error: " + description, Toast.LENGTH_LONG).show();
            }
        });

        try {
            webView.loadUrl("file:///android_asset/index.html");
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load report card: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }
                           }        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean activated = prefs.getBoolean(KEY_ACTIVATED, false);

        if (activated) {
            loadReportCard();
        } else {
            setContentView(R.layout.activity_passcode);
            EditText passInput = findViewById(R.id.passInput);
            Button passBtn = findViewById(R.id.passBtn);
            passBtn.setOnClickListener(v -> {
                String entered = passInput.getText().toString();
                if (entered.equals(PASSCODE)) {
                    checkActivation();
                } else {
                    Toast.makeText(this, "Wrong passcode!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkActivation() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String deviceId = DeviceUtils.getDeviceId(this);
        String storedDeviceId = prefs.getString(KEY_DEVICE_ID, "");

        if (storedDeviceId.isEmpty()) {
            showActivationDialog();
        } else if (storedDeviceId.equals(deviceId)) {
            prefs.edit().putBoolean(KEY_ACTIVATED, true).apply();
            loadReportCard();
        } else {
            Toast.makeText(this, "This app is not authorized on this device.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void showActivationDialog() {
        setContentView(R.layout.activity_activation);
        EditText codeInput = findViewById(R.id.codeInput);
        Button activateBtn = findViewById(R.id.activateBtn);
        activateBtn.setOnClickListener(v -> {
            String enteredCode = codeInput.getText().toString().trim();
            String deviceId = DeviceUtils.getDeviceId(this);
            String expectedCode = DeviceUtils.generateActivationCode(deviceId, SALT);
            if (enteredCode.equals(expectedCode)) {
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                prefs.edit().putBoolean(KEY_ACTIVATED, true)
                            .putString(KEY_DEVICE_ID, deviceId)
                            .apply();
                loadReportCard();
            } else {
                Toast.makeText(this, "Invalid activation code!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReportCard() {
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webView);

        // Compatibility settings for older Android versions
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setSaveFormData(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "WebView error: " + description, Toast.LENGTH_LONG).show();
            }
        });

        try {
            webView.loadUrl("file:///android_asset/index.html");
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load report card: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }
                }                    checkActivation();
                } else {
                    Toast.makeText(this, "Wrong passcode!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkActivation() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String deviceId = DeviceUtils.getDeviceId(this);
        String storedDeviceId = prefs.getString(KEY_DEVICE_ID, "");

        if (storedDeviceId.isEmpty()) {
            showActivationDialog();
        } else if (storedDeviceId.equals(deviceId)) {
            prefs.edit().putBoolean(KEY_ACTIVATED, true).apply();
            loadReportCard();
        } else {
            Toast.makeText(this, "This app is not authorized on this device.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void showActivationDialog() {
        setContentView(R.layout.activity_activation);
        EditText codeInput = findViewById(R.id.codeInput);
        Button activateBtn = findViewById(R.id.activateBtn);
        activateBtn.setOnClickListener(v -> {
            String enteredCode = codeInput.getText().toString().trim();
            String deviceId = DeviceUtils.getDeviceId(this);
            String expectedCode = DeviceUtils.generateActivationCode(deviceId, SALT);
            if (enteredCode.equals(expectedCode)) {
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                prefs.edit().putBoolean(KEY_ACTIVATED, true)
                            .putString(KEY_DEVICE_ID, deviceId)
                            .apply();
                loadReportCard();
            } else {
                Toast.makeText(this, "Invalid activation code!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReportCard() {
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/index.html");
    }
        }
