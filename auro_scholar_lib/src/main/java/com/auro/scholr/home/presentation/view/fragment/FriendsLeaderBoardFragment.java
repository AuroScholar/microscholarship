package com.auro.scholr.home.presentation.view.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.databinding.FriendsLeoboardLayoutBinding;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.adapter.LeaderBoardAdapter;
import com.auro.scholr.home.presentation.view.adapter.QuizItemAdapter;
import com.auro.scholr.home.presentation.viewmodel.DemographicViewModel;
import com.auro.scholr.home.presentation.viewmodel.FriendsLeaderShipViewModel;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
import static com.auro.scholr.core.common.Status.DEMOGRAPHIC_API;


public class FriendsLeaderBoardFragment extends BaseFragment implements View.OnClickListener,InviteBoxDialog.Listener {

    @Inject
    @Named("FriendsLeaderBoardFragment")
    ViewModelFactory viewModelFactory;

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;
    private static final String TAG = FriendsLeaderBoardFragment.class.getSimpleName();


    FriendsLeoboardLayoutBinding binding;
    FriendsLeaderShipViewModel viewModel;
    InviteBoxDialog mInviteBoxDialog;

    LeaderBoardAdapter leaderBoardAdapter;

    InviteBoxDialog.Listener mlistener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FriendsLeaderShipViewModel.class);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }


    @Override
    protected void init() {

        setListener();
        setLeaderBoard();
    }


    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.headerParent.cambridgeHeading.setVisibility(View.GONE);
        binding.toolbarLayout.backArrow.setOnClickListener(this);
        binding.fbInviteButton.setOnClickListener(this);
    }


    @Override
    protected int getLayout() {
        return R.layout.friends_leoboard_layout;
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

    private void setLeaderBoard() {
        binding.friendsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.friendsList.setHasFixedSize(true);
        leaderBoardAdapter = new LeaderBoardAdapter(viewModel.homeUseCase.makeListForFriendsLeaderBoard(true));
        binding.friendsList.setAdapter(leaderBoardAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_arrow) {
            getActivity().getSupportFragmentManager().popBackStack();
        } else if (v.getId() == R.id.fb_invite_button) {
            // openFragment(new FriendsInviteBoardFragment());
            //or
            mlistener = this;
            mInviteBoxDialog = new InviteBoxDialog(getContext(), mlistener);
            mInviteBoxDialog.show();

        }
    }

    private void openFragment(Fragment fragment) {
        ((AppCompatActivity) (this.getContext())).getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, FriendsLeaderBoardFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();


    }
    private void askPermission() {
        String rationale = getString(R.string.permission_error_msg);
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mContactPermission, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                ViewUtil.showSnackBar(binding.getRoot(), rationale);
            }
        });
    }

    @Override
    public void performAddButton() {
        askPermission();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();
            retrieveContactNumber();
        }
    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getActivity().getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.e(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();


        Log.e(TAG, "Contact Phone Number: " + contactNumber);
        mInviteBoxDialog.mContactEdit.setText(contactNumber);
    }
}
