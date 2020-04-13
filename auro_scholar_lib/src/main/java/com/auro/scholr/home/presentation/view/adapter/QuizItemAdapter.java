package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;




import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.home.data.model.QuizDasboardDatamodel;


public class QuizItemAdapter extends RecyclerView.Adapter<QuizItemAdapter.ViewHolder> {

    ArrayList<QuizDasboardDatamodel> mValues;
    Context mContext;

    public QuizItemAdapter(Context context, ArrayList<QuizDasboardDatamodel> values) {

        mValues = values;
        mContext = context;
    }

    public void updateList(ArrayList<QuizDasboardDatamodel> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, ques_no;

        public ViewHolder(View v) {
            super(v);

           // textView = (TextView) v.findViewById(R.id.status_view);

        }

        public void setData(QuizDasboardDatamodel questionCompFunda, int position) {

        }

    }

    @Override
    public QuizItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.quiz_item_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position), position);

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }


}
