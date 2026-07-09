package com.nasheebah.reportcard;

import android.content.Context;
import android.provider.Settings;
import android.os.Build;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class DeviceUtils {
    public static String getDeviceId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String serial = Build.getSerial();
        String model = Build.MODEL;
        return androidId + "|" + serial + "|" + model;
    }

    public static String generateActivationCode(String deviceId, String salt) {
        try {
            String combined = deviceId + salt;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combined.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.substring(0, 12).toUpperCase();
        } catch (Exception e) {
            return "ERROR";
        }
    }
                                 }
