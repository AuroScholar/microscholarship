package com.auro.scholr.util;

import android.content.Context;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;


import com.auro.scholr.core.application.AuroApp;
import com.google.android.gms.maps.model.LatLng;
import com.auro.scholr.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtil {

    public static float getDistanceBetweenCoordinates(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {

        float[] result = new float[1];

        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, result);

        return result[0];
    }

    public static LatLng getLatlng(String latitude, String longitude) {
        Double latitudeValue = ConversionUtil.INSTANCE.convertStringToDouble(latitude);
        Double longitudeValue = ConversionUtil.INSTANCE.convertStringToDouble(longitude);
        return new LatLng(latitudeValue, longitudeValue);
    }


    public static String getLocaleLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else { //noinspection deprecation
            locale = Resources.getSystem().getConfiguration().locale;
        }

        String language = locale.getLanguage();

        if (!language.equalsIgnoreCase("ar")) {
            language = "en";
        }

        return language;
    }


    public static boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public static String getLocale() {


        return Locale.getDefault().getLanguage().toUpperCase();
        // return "AR";

    }


    public static String getAddressFromLatLng(double latitude, double longitude,
                                              boolean custom) {
        if (latitude != -1D && longitude != -1D) {
            Locale.setDefault(new Locale(AppUtil.getLocale()));
            Geocoder geocoder;
            List<Address> addresses;
            Locale.setDefault(new Locale(AppUtil.getLocale()));
            geocoder = new Geocoder(AuroApp.getAppContext(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);

                    if (custom) {
                        return getAddressDetail(address);
                    } else {
                        return getFullAddress(address);
                    }
                } else {
                    return "address_not_found";
                }

            } catch (IOException e) {
                return "address_not_found";
            }
        }
        return "address_not_found";
    }


    public static String getFullAddress(Address address) {
        StringBuilder sb = new StringBuilder();
        String fullAddress = address.getAddressLine(0);
        if (fullAddress != null && !fullAddress.isEmpty()) {
            sb.append(address.getAddressLine(0));
            return sb.toString();
        } else {
            sb.append(address.getLocality()).append(",");
            sb.append(address.getPostalCode()).append(",");
            sb.append(address.getCountryName());
            if (sb.toString() != null && !sb.toString().isEmpty()) {
                return sb.toString();
            } else {
                return "address_not_found";

            }
        }
    }


    public static String getAddressDetail(Address address) {
        StringBuilder addressCurrent = new StringBuilder();
        if (address != null && address.getSubLocality() != null && !address.getSubLocality().isEmpty()) {
            addressCurrent.append(address.getSubLocality()).toString();
        }

        if (!addressCurrent.toString().isEmpty()) {
            if (address != null && address.getLocality() != null && !address.getLocality().isEmpty()) {
                return addressCurrent.append(", " + address.getLocality()).toString();
            }
        } else {
            if (address != null && address.getLocality() != null && !address.getLocality().isEmpty()) {
                return addressCurrent.append(address.getLocality()).toString();
            }

        }
        return "address_not_found";

    }


}
