package com.auro.scholr.util.disclaimer;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.auro.scholr.R;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.AmParentDialogBinding;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.util.MySpannable;
import com.auro.scholr.util.ViewUtil;


public class DisclaimerKycDialog extends Dialog {

    public Activity activity;
    private AmParentDialogBinding binding;
    PrefModel prefModel;

    public DisclaimerKycDialog(Activity context) {
        super(context);
        this.activity = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.am_parent_dialog, null, false);
        setContentView(binding.getRoot());
        prefModel = AppPref.INSTANCE.getModelInstance();
        binding.parentCheckRow.setVisibility(View.GONE);
        binding.tvMessageKyc.setText("I/We hereby submit voluntarily at my/our own discretion, the copy of Aadhaar card/physical e-Aadhaar / masked Aadhaar /offline electronic Aadhaar XML as issued by UIDAI (Aadhaar),to Auro Scholar for the purpose of establishing my/our identity/address proof and; hereby consent to Auro Scholar for verification of my/our Aadhaar to establish its genuineness through Quick Response (QR) code embedded in the Aadhaar card or through such other acceptable manner as per UIDAI or under any Act or law from time to time.\n");
        makeTextViewResizable(binding.tvMessageKyc, 3, "...See More", true);
        binding.tvParent.setVisibility(View.VISIBLE);
        binding.RlKycCheck.setVisibility(View.VISIBLE);
        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prefModel.isPreKycDisclaimer()) {
                    dismiss();
                    ((StudentMainDashboardActivity) activity).onBackPressed();
                }
            }
        });

        binding.RPAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( binding.checkIsKyc.isChecked()) {
                    dismiss();
                    prefModel.setPreKycDisclaimer(false);
                    AppPref.INSTANCE.setPref(prefModel);
                } else {
                    ViewUtil.showSnackBar(binding.getRoot(), "Please select checkbox");
                }


            }
        });
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, "...See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }
}