package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.databinding.QuizItemLayout2Binding;


public class QuizItemNewAdapter extends RecyclerView.Adapter<QuizItemNewAdapter.ViewHolder> {


    Context mContext;

    public QuizItemNewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public QuizItemNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        QuizItemLayout2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.quiz_item_layout_2, viewGroup, false);
        return new QuizItemNewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizItemNewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        QuizItemLayout2Binding binding;

        public ViewHolder(QuizItemLayout2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
