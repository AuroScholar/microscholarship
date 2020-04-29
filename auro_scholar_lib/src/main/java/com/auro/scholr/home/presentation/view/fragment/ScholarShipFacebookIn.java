package com.auro.scholr.home.presentation.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.auro.scholr.BuildConfig;
import com.auro.scholr.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScholarShipFacebookIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScholarShipFacebookIn extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EMAIL = "email";
    LoginButton loginButton;
    ProgressBar mPb;
    // Your Facebook APP ID
    TextView mFirstName, LastName, txtToken, emailTv, nameTv;
    ConstraintLayout fb;


    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken access_token;
    GraphRequest request;
    ProfileTracker profileTracker;
    private String email, facebook_uid, first_name, last_name, social_id, name, picture;


    public ScholarShipFacebookIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScholarShipFacebookIn.
     */
    // TODO: Rename and change types and number of parameters
    public static ScholarShipFacebookIn newInstance(String param1, String param2) {
        ScholarShipFacebookIn fragment = new ScholarShipFacebookIn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mview = inflater.inflate(R.layout.fragment_scholar_ship_facebook_in, container, false);
        loginButton = mview.findViewById(R.id.login_button);
        mFirstName = mview.findViewById(R.id.txtFirstName);
        LastName = mview.findViewById(R.id.txtLastName);
        txtToken = mview.findViewById(R.id.txtToken);
        fb = mview.findViewById(R.id.fb_invite_button);

        fb.setOnClickListener(this);

        return mview;
    }


    private void facebookLoginSignup() {
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        //   LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("basic_info, user_friends"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions((this), Arrays.asList("email", "public_profile", "user_friends","read_friendlists"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("response Success", "Login");
                        access_token = loginResult.getAccessToken();
                        Log.d("response access_token", access_token.toString());
                        fetchFriendsList();
                        request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {


                                JSONObject json = response.getJSONObject();
                                try {
                                    if (json != null) {
                                        Log.d("response", json.toString());
                                        try {
                                            email = json.getString("email");
                                            emailTv.setText(email + "");

                                           /* //
                                            JSONArray jsonArrayFriends = json.getJSONObject("friendlist").getJSONArray("data");
                                            JSONObject friendlistObject = jsonArrayFriends.getJSONObject(0);
                                            String friendListID = friendlistObject.getString("id");
                                            Toast.makeText(getContext(),"Friend List: "+friendListID,Toast.LENGTH_LONG).show();
                                            //*/

                                        } catch (Exception e) {
                                            Toast.makeText(getContext(), "Sorry!!! Your email is not verified on facebook.", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        facebook_uid = json.getString("id");
                                        social_id = json.getString("id");
                                        first_name = json.getString("first_name");
                                        last_name = json.getString("last_name");
                                        name = json.getString("name");
                                        nameTv.setText(name + "");

                                        picture = "https://graph.facebook.com/" + facebook_uid + "/picture?type=large";
                                        Log.d("response", " picture" + picture);
                                        // Picasso.with(context).load(picture).placeholder(R.mipmap.ic_launcher).into(userIv);
                                        mPb.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("response problem", "problem" + e.getMessage());
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,first_name,last_name,link,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getContext(), "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void fetchFriendsList() {
        GraphRequest request_getFriends = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + AccessToken.getCurrentAccessToken().getUserId() + "/friends?",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {

                    @Override
                    public void onCompleted(GraphResponse response) {

                        JSONObject object = response.getJSONObject();
                        try {
                            if (object != null) {
                                JSONArray data = object.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    String id = data.getJSONObject(i).getString("id");
                                    String name = data.getJSONObject(i).getString("name");

                                }
                            }
                        } catch (JSONException e) {

                        }
                    }
                }
        );

        request_getFriends.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        facebookLoginSignup();
        // mPb.setVisibility(View.VISIBLE);
    }
}
