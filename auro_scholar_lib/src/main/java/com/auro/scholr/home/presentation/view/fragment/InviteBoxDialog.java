package com.auro.scholr.home.presentation.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.auro.scholr.R;

public class InviteBoxDialog extends Dialog  implements View.OnClickListener {

    public Context mcontext;
    TextView txtshareWithOther,txtInvite;
    ImageView close,icContact;
    Listener mlistener;
    EditText mContactEdit;

    public InviteBoxDialog(@NonNull Context context,Listener mlistener) {
        super(context);
        this.mcontext = context;
        this.mlistener = mlistener;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_invite_layout);


        txtInvite = findViewById(R.id.invite_text);
        txtshareWithOther = findViewById(R.id.txtShareWithOther);
        close = findViewById(R.id.icClose);
        icContact = findViewById(R.id.icContact);
        close.setOnClickListener(this);
        mContactEdit =findViewById(R.id.edit_contact);
        txtshareWithOther.setOnClickListener(this);
        icContact.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.icClose) {
            dismiss();
        } else if (id == R.id.txtShareWithOther) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            dismiss();
            mcontext.startActivity(shareIntent);
        } else if (id == R.id.icContact) {
            mlistener.performAddButton();

        }
    }
    public interface Listener {

        void performAddButton();
    }

}
