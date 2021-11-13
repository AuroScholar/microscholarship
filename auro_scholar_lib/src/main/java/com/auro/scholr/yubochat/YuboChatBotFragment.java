package com.auro.scholr.yubochat;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.auro.scholr.R;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.databinding.FragmentYuboChatBotBinding;

import com.auro.yubolibrary.Recycler;
import com.auro.yubolibrary.utils.ItemDecorationAlbumColumns;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings;
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy;




import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class YuboChatBotFragment extends BaseFragment implements Recycler.SetTypingAnimation, TextWatcher, View.OnClickListener {

    FragmentYuboChatBotBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context dashBoardActivity;
    boolean isTyping = false;
    private Timer timer;
    private int DELAY = 1500;
    Recycler recycler;
    String id;
    JSONObject typingJson;

    String chatApi = "bob_chatbot";
    YuboChatBotFragment setTypingAnimation;

    public YuboChatBotFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_yubo_chat_bot, container, false);
        init();
        checkInternetConnectivity();
        return binding.getRoot();
    }

    @Override
    protected void init() {
        setListener();

        id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        recycler = binding.rcTest;
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new ItemDecorationAlbumColumns(dashBoardActivity.getResources().getDimensionPixelSize(R.dimen._7sdp), 1));
        setTypingAnimation = this;
        recycler.setSetAnimationsRecycle(this);

        timer = new Timer();
        // milliseconds
        recycler.callApiFirstMsg(chatApi);


    }


    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

        binding.etSendMsg.addTextChangedListener(this);
        binding.imgSend.setOnClickListener(this);
        binding.closeBt.setOnClickListener(this);

    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_yubo_chat_bot;
    }

    @Override
    public void showAnimation(boolean b) {
        if (b) {
//            typingInd.visibility = View.VISIBLE
        } else {
//            typingInd.visibility = View.GONE
        }
    }

    @Override
    public void scrollPosition(int scrollToBottom, @Nullable Boolean typeOption) {
        binding.rcTest.scrollToPosition(scrollToBottom);
        if (typeOption) {
            binding.llSelfRpl.setVisibility(View.VISIBLE);
        } else {
            binding.llSelfRpl.setVisibility(View.GONE);

//            AppUtils.hideKeyboard(activity)
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dashBoardActivity = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            if (!isTyping) {
                        /*typingJson = JSONObject()
                        typingJson.put("typing", "1")
                        typingJson.put("userId", chat_user_id)
                        perticularChatViewModel.emitSocket(typingJson)*/
                isTyping = true;
            }
            timer.cancel();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isTyping = false;
                    typingJson = new JSONObject();
                }
            }, DELAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.close_bt) {
            getActivity().onBackPressed();
        } else if (vId == R.id.et_sendMsg || vId == R.id.imgSend) {
            sendMessage();
        }

    }

    void sendMessage() {
        if (binding.etSendMsg.getText().toString().trim().length() > 0) {
            recycler.callBotApi(
                    chatApi,
                    binding.etSendMsg.getText().toString(),
                    id
            );
            binding.etSendMsg.setText("");
        } else {
            Toast.makeText(getActivity(), "Please enter the your message", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void checkInternetConnectivity() {
        InternetObservingSettings settings = InternetObservingSettings.builder()
                .host("8.8.8.8")
                .port(53)
                .strategy(new SocketInternetObservingStrategy())
                .build();
        ReactiveNetwork.observeInternetConnectivity(settings)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) throws Exception {
                        if (isConnectedToInternet) {
                            binding.connectionStatus.setText("Online");
                            binding.onlineCircle.setColorFilter(getResources().getColor(R.color.accepted));
                        } else {
                            binding.connectionStatus.setText("Offline");
                            binding.onlineCircle.setColorFilter(getResources().getColor(R.color.red));
                        }

                        // do something with isConnectedToInternet value
                        // ViewUtil.showToast("isConnectedToInternet--" + isConnectedToInternet);
                    }
                });
    }

}