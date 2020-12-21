package com.auro.scholr.home.presentation.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FriendsLeoboardAddLayoutBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.NearByFriendList;
import com.auro.scholr.home.presentation.viewmodel.FriendsLeaderShipViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ImageUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.firebase.FirebaseEventUtil;
import com.auro.scholr.util.permission.LocationHandler;
import com.auro.scholr.util.permission.LocationModel;
import com.auro.scholr.util.permission.LocationUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import static com.auro.scholr.core.common.Status.FIND_FRIEND_DATA;
import static com.auro.scholr.core.common.Status.SEND_FRIENDS_REQUEST;
import static com.auro.scholr.util.permission.LocationHandler.REQUEST_CHECK_SETTINGS_GPS;

public class FriendsLeaderBoardAddFragment extends BaseFragment implements View.OnClickListener, CommonCallBackListner, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveListener {

    @Inject
    @Named("FriendsLeaderBoardAddFragment")
    ViewModelFactory viewModelFactory;

    private static final String TAG = FriendsLeaderBoardAddFragment.class.getSimpleName();
    FriendsLeoboardAddLayoutBinding binding;
    FriendsLeaderShipViewModel viewModel;

    NearByFriendList resModel;

    boolean isFriendList = true;
    Resources resources;
    boolean isStateRestore;
    FirebaseEventUtil firebaseEventUtil;
    Map<String, String> logparam;
    int itemPos;

    FusedLocationProviderClient mFusedLocationClient;
    GoogleMap mMap;
    Marker googleMarker;
    List<Marker> markersList = new ArrayList<>();
    float zoomLevel = 15.0f;
    LatLng mapPosotion;
    double radius = 5;
    double latitude = 0L, longitude = 0L;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initMap() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapfragment);

            mapFragment.getMapAsync(new FriendsLeaderBoardAddFragment.MapReady(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FriendsLeaderShipViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }

    @Override
    protected void init() {

        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && TextUtil.isEmpty(prefModel.getUserLanguage())) {
            ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
        }
        firebaseEventUtil = new FirebaseEventUtil(getContext());
        logparam = new HashMap<>();

        setListener();

        initMap();
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    //For ProgressBar
                    if (responseApi.apiTypeStatus == FIND_FRIEND_DATA) {
                        handleProgress(0, "");
                    }

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == FIND_FRIEND_DATA) {
                        resModel = (NearByFriendList) responseApi.data;
                        if (resModel.isError()) {
                            handleProgress(2, resModel.getStatus());
                        } else {
                            handleProgress(1, "");

                        }
                    }
                    if (responseApi.apiTypeStatus == SEND_FRIENDS_REQUEST) {
                        resModel = (NearByFriendList) responseApi.data;
                        showSnackbarError(resModel.getMessage());
                    }
                    break;

                case NO_INTERNET:
                case FAIL_400:
                    if (responseApi.apiTypeStatus == FIND_FRIEND_DATA) {
                        handleProgress(3, (String) responseApi.data);
                    }
                    showSnackbarError((String) responseApi.data);
                    break;

                default:
                    if (responseApi.apiTypeStatus == FIND_FRIEND_DATA) {
                        handleProgress(3, (String) responseApi.data);
                    }
                    showSnackbarError((String) responseApi.data);
                    break;
            }

        });
    }

    private void handleProgress(int i, String msg) {
        switch (i) {
            case 0:

                break;

            case 1:
                if (resModel.getStudent() != null && resModel.getStudent().size() < 11) {
                    radius += 5;
                } else {
                    radius = 5;
                }
                loadStudentonMaps();
                break;

            case 2:

                break;

            case 3:

                break;

        }

    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    @Override
    protected int getLayout() {
        return R.layout.friends_leoboard_add_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            // dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);

        }
        init();
        setToolbar();
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        setKeyListner();
        if (latitude == 0 && longitude == 0) {
            askPermission();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        itemPos = commonDataModel.getSource();

        switch (commonDataModel.getClickType()) {
            case SEND_INVITE_CLICK:

                break;

            case ACCEPT_INVITE_CLICK:

                break;
        }
    }

    private void setKeyListner() {
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
    }

    private double getMapVisibleRadius(VisibleRegion visibleRegion) {
        mapPosotion = visibleRegion.latLngBounds.getCenter();

        float[] distanceWidth = new float[1];
        float[] distanceHeight = new float[1];

        LatLng farRight = visibleRegion.farRight;
        LatLng farLeft = visibleRegion.farLeft;
        LatLng nearRight = visibleRegion.nearRight;
        LatLng nearLeft = visibleRegion.nearLeft;

        Location.distanceBetween(
                (farLeft.latitude + nearLeft.latitude) / 2,
                farLeft.longitude,
                (farRight.latitude + nearRight.latitude) / 2,
                farRight.longitude,
                distanceWidth
        );

        Location.distanceBetween(
                farRight.latitude,
                (farRight.longitude + farLeft.longitude) / 2,
                nearRight.latitude,
                (nearRight.longitude + nearLeft.longitude) / 2,
                distanceHeight
        );

        double radiusInMeters = Math.sqrt(Math.pow(distanceWidth[0], 2) + Math.pow(distanceHeight[0], 2)) / 2;

        return (radiusInMeters / 1609);
    }

    @Override
    public void onCameraIdle() {
        if (mMap != null) {
//            radius = getMapVisibleRadius(mMap.getProjection().getVisibleRegion());
            getMapVisibleRadius(mMap.getProjection().getVisibleRegion());
            radius = 5;
            viewModel.findFriendData(mapPosotion.latitude, mapPosotion.longitude, radius);
        }
    }

    @Override
    public void onCameraMove() {
        if (mMap != null) {
//            radius = getMapVisibleRadius(mMap.getProjection().getVisibleRegion());
//            mMap.clear();
//            mMap.addMarker(new MarkerOptions()
//                                   .position(mapPosotion)
//                                   .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location)));

        }
    }

    private class MapReady implements OnMapReadyCallback {

        Context mContext;
        Location mLocation;

        public MapReady(Context mContext) {
            this.mContext = mContext;
        }

        public MapReady(Context mContext, Location mLocation) {
            this.mContext = mContext;
            this.mLocation = mLocation;
        }

        @Override
        public void onMapReady(final GoogleMap googleMap) {
            mMap = googleMap;
            //           loadDoctorsonMaps();
            mMap.setOnCameraIdleListener(FriendsLeaderBoardAddFragment.this);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));
            if (!(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                // TODO: Consider calling
                mMap.setMyLocationEnabled(true);
            }


//            mMap.setOnCameraMoveStartedListener(DoctorSearchMapFragment.this);
//            mMap.setOnCameraMoveListener(DoctorSearchMapFragment.this);
//            mMap.setOnCameraMoveCanceledListener(DoctorSearchMapFragment.this);

            if (mMap != null && latitude != 0) {
                LatLng sydney = new LatLng(latitude, longitude);
                //View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
//                mMap.addMarker(new MarkerOptions()
//                                       .position(sydney)
//                                       .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));

                viewModel.findFriendData(latitude, longitude, radius);
            }

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (markersList != null && !markersList.isEmpty()) {

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markersList.get(0).getPosition(), zoomLevel));

                    }
                }
            });
        }
    }

    private void loadStudentonMaps() {
        LatLng studentLocation;

        if (getActivity() != null && isAdded()) {
            View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.auro_custom_marker_layout, null);

            int i = 0;
            markersList.clear();
            mMap.clear();

//            mMap.addMarker(new MarkerOptions()
//                                   .position(mapPosotion)
//                                   .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(mapPosotion));

            for (NearByFriendList.Student studentData : resModel.getStudent()) {
                if (!studentData.getLattitude().isEmpty() && !studentData.getLongitude().isEmpty()) {
                    TextView numTxt = marker.findViewById(R.id.num_txt);
                    numTxt.setText(studentData.getStudent_name());
                    studentLocation = new LatLng(Double.parseDouble(studentData.getLattitude()), Double.parseDouble(studentData.getLongitude()));
                    googleMarker = mMap.addMarker(new MarkerOptions()
                                                          .position(studentLocation)
                                                          .draggable(true)
                                                          .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker)))
                    );
                    googleMarker.setTag(i);

                    markersList.add(googleMarker);

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            binding.parentLayout.setVisibility(View.VISIBLE);
                            if (marker.getTag() == null || resModel.getStudent()==null || resModel.getStudent().size() ==0 ) {
                                return false;
                            }
                            NearByFriendList.Student studentDetails = resModel.getStudent().get((Integer) marker.getTag());
//                        String patLat = "", patLng = "";
//                        patLat = doctorDetails.getLatitude();
//                        patLng = doctorDetails.getLongitude();
//                        if (!patLat.isEmpty() && !patLng.isEmpty()) {
//                            LatLng docLocation = new LatLng(Double.parseDouble(patLat), Double.parseDouble(patLng));
//                            float zoomLevel = 15.0f;
//                            isBottomShowing = true;
//                            showBottom(isBottomShowing);
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(docLocation, zoomLevel));
//                            rv_DoctorsSearchList.smoothScrollToPosition((Integer) marker.getTag());
//                        }

                            ImageUtil.loadCircleImage(binding.profileImage, studentDetails.getStudent_photo());
                            binding.nameText.setText(studentDetails.getStudent_name());
                            binding.distance.setText(studentDetails.getDistance() + " KM");

                            binding.inviteButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
                                    DashboardResModel dashboardResModel = prefModel.getDashboardResModel();
                                    if (dashboardResModel != null) {
                                        viewModel.sendFriendRequestData(Integer.valueOf(dashboardResModel.getAuroid()), studentDetails.getRegistration_id(), dashboardResModel.getPhonenumber(), studentDetails.getMobile_no());
                                    }
                                    // viewModel.sendFriendRequestData(Pref,studentDetails.getRegistration_id());
                                }
                            });
                            return false;
                        }
                    });
                    i++;
                }
            }
//        if (markersList != null && !markersList.isEmpty()) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markersList.get(0).getPosition(), zoomLevel));
//            markersList.get(0).showInfoWindow();
//        }
        }

    }

    private void askPermission() {
        String rationale = "Please give location permission for provide you the better service.";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mLocationPermission, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                getCurrentLocation();
                AppLogger.e(TAG, "Location Permission Granted");
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                //getActivity().getSupportFragmentManager().popBackStack();
                AppLogger.e(TAG, "Location Permission Denied");

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS_GPS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    AppLogger.e(TAG, "GPS on Allow");
                    getCurrentLocation();
                    break;
                case Activity.RESULT_CANCELED:
                    AppLogger.e(TAG, "GPS on Denied");
                    break;
                default:
                    // do nothing here
                    break;

            }

        }
    }

    public void getCurrentLocation() {
        LocationHandler locationHandlerUpdate = new LocationHandler();
        locationHandlerUpdate.setUpGoogleClient(getActivity());
        if (LocationUtil.isGPSEnabled(getActivity())) {
            callServiceWhenLocationReceived();
        }
    }

    private void callServiceWhenLocationReceived() {
        LocationModel locationModel = LocationUtil.getLocationData();
        if (locationModel != null && locationModel.getLatitude() != null) {
            if (mMap!=null && !(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                // TODO: Consider calling
                mMap.setMyLocationEnabled(true);
            }
            latitude = Double.parseDouble(locationModel.getLatitude());
            longitude = Double.parseDouble(locationModel.getLongitude());

            LatLng sydney = new LatLng(latitude, longitude);

            View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.auro_custom_marker_layout, null);

            if (mMap != null) {
                mMap.addMarker(new MarkerOptions()
                                       .position(sydney)
                                       .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker))));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                viewModel.findFriendData(latitude, longitude, (long) (radius));
            }

        } else {
            Handler handler = new Handler();
            handler.postDelayed(this::callServiceWhenLocationReceived, 2000);
        }
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);

        return bitmap;
    }

}

