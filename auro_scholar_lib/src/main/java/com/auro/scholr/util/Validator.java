package com.auro.scholr.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class Validator {

    private static final String TAG = "Validator";

    public Validator(){

    }
    public static boolean isPhoneNumberValid(String pStrPhoneNum, @Nullable String countryCode){
        boolean result =false;
        try {

            countryCode = (countryCode != null && !countryCode.isEmpty())? countryCode :"+91";
            if (countryCode.length() > 0 && pStrPhoneNum.length() > 0 && isValidPhoneNumber(pStrPhoneNum)) {
                    boolean status = validateCountryPhoneNumber(countryCode, pStrPhoneNum);
                    if (status) {
                        result = true;
                    }

            }
        }
        catch (Exception e){
            AppLogger.e(TAG, e.getMessage());
        }
        return result;
    }

    private static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    public static boolean isNumberValid(String phNumber){

        Phonenumber.PhoneNumber numberProto = null;
        String countryCode = null;
        String mobileNumber = null;

        if(phNumber.contains("+")) {

            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                // phone must begin with '+'
                //phoneUtil.format(phNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                numberProto = phoneUtil.parse(phNumber, "");
                countryCode = String.valueOf(numberProto.getCountryCode());
                mobileNumber = String.valueOf(numberProto.getNationalNumber());
            } catch (NumberParseException e) {
                System.err.println("NumberParseException was thrown: " + e.toString());
                return false;
            }

            return validateCountryPhoneNumber(countryCode, mobileNumber);
        }

        return false;
    }

    private static boolean validateCountryPhoneNumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {

            AppLogger.e("NumberParseException", e.getMessage());
        }


        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            if(phoneNumber != null) {
                String number = "" + phoneNumber.getNationalNumber();
                return phNumber.equals(number);
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkLocationPermissions(Context context) {
        int lIntAccessCoarseLocation = ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION);
        int lIntAccessFineLocation = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION);

        return lIntAccessCoarseLocation == PackageManager.PERMISSION_GRANTED
                && lIntAccessFineLocation  == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermissions(AppCompatActivity activity, int PERMISSION_ACCEPT_CODE) {
        ActivityCompat.requestPermissions(activity, new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, PERMISSION_ACCEPT_CODE);
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;

        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF;

    }

    public static boolean validateAlpha(String matchString, String locale){

        //String matchPattern = "[\\u0600-\\u06FF]+";

        String matchPattern="";

        switch (locale){

            case "AR":
                matchPattern = "^[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z]+[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z-_]*$";
                break;

            case "EN":
                matchPattern = "^[a-zA-Z ]+$";
                break;

                default:
                    break;
        }

        return matchString != null && !matchString.isEmpty() && matchString.matches(matchPattern);

        //String matchPattern = "^[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z]+[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z-_]*$";
        //return matchString != null && !matchString.isEmpty() && matchString.matches(matchPattern);

        /// open for the ltr EN support lanaguge
//        String matchPattern = "^[a-zA-Z ]+$";
//        return matchString != null && !matchString.isEmpty() && matchString.matches(matchPattern);
    }

    public static boolean validateAlphaNumeric(String matchString){
        String matchPattern = "^[0-9a-zA-Z ]+$";
        return matchString != null && !matchString.isEmpty() && matchString.matches(matchPattern);
    }

    public static boolean validateEmail(String email){

        return email != null && !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isThisDateValid(String dateToValidate, String dateFormat){

        if(dateToValidate == null){
            return false;
        }

        DateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            sdf.parse(dateToValidate);

        } catch (ParseException e) {

            AppLogger.e(TAG, e.getMessage());
            return false;
        }

        return true;
    }

    public static int getDiffYears(String first, String last, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            Date firstDate = sdf.parse(first);
            Date lastDate = null;
            if(last == null) {
                lastDate = sdf.parse(sdf.format(new Date()));
            }else {
                lastDate = sdf.parse(last);
            }
            Calendar a = getCalendar(firstDate);
            Calendar b = getCalendar(lastDate);
            int diff = b.get(YEAR) - a.get(YEAR);
            if (a.get(MONTH) > b.get(MONTH) ||
                    (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
                diff--;
            }
            return diff;
        } catch (ParseException e) {

            AppLogger.e(TAG, e.getMessage());
        }
        return 0;
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static boolean isStartWithZero(String mobileNum){

        return String.valueOf(mobileNum.charAt(0)).equalsIgnoreCase("0");
    }

}
