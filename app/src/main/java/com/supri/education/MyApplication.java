package com.supri.education;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.multidex.MultiDexApplication;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.supri.education.Model.UserModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyApplication extends MultiDexApplication {

    public static String username;
    @Override
    public void onCreate() {
        super.onCreate();
        username = null;
    }


    public static int getDeviceWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    public static int getDeviceHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }

    public static int getScaledLength(Activity activity, int length) {
        int scaledLength = (int)(length * activity.getResources().getDisplayMetrics().density);
        return scaledLength;
    }

    public static String getFormattedDateTimeString(String format, long timestamp) {
        return getFormattedDateTimeString(format, new Date(timestamp * 1000));
    }

    public static String getFormattedDateTimeString(String format, Date date) {
        DateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static void showKeyboard(Context context, View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Activity activity){
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static String getAddressFromLatitudeAndLongitude(Context context, double latitude, double longitude) {
        String address = "";
        try {
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(context, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size()>0) {
                Address returnedAddress = addresses.get(0);
                for (int j = 0; j <= returnedAddress.getMaxAddressLineIndex(); j++) {
                    address = address + returnedAddress.getAddressLine(j);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Google Map error\n" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        return address;
    }


    public static String getPackageName(Context context) {
        String packageName = context.getPackageName();
        return packageName;
    }

    public static String getAppName(Context context) {
        Resources appR = context.getResources();
        String appName = appR.getText(appR.getIdentifier("app_name", "string", context.getPackageName())).toString();
        return appName;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static Drawable getRectDrawable(Activity activity, int solidColor, int strokeColor, int strokeWidth) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(solidColor);
        drawable.setStroke(getScaledLength(activity, strokeWidth), strokeColor);
        return drawable;
    }

    public static Drawable getRoundDrawable(Activity activity, int cornerRadius, int solidColor, int strokeColor, int strokeWidth) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(getScaledLength(activity, cornerRadius));
        drawable.setColor(solidColor);
        drawable.setStroke(getScaledLength(activity, strokeWidth), strokeColor);
        return drawable;
    }

    public static Drawable getRoundDrawable(Activity activity, int cornerRadius, int solidColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(getScaledLength(activity, cornerRadius));
        drawable.setColor(solidColor);
        return drawable;
    }

    public static Drawable getOvalDrawable(Activity activity, int solidColor, int strokeColor, int strokeWidth) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(solidColor);
        drawable.setStroke(getScaledLength(activity, strokeWidth), strokeColor);
        return drawable;
    }

    public static Drawable getOvalDrawable(Activity activity, int solidColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(solidColor);
        return drawable;
    }
}
