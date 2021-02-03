package com.auro.scholr.teacher.presentation.viewmodel;

import android.view.View;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.TeacherProgressItemLayoutBinding;
import com.auro.scholr.teacher.data.model.response.TeacherProgressStatusModel;
import com.auro.scholr.util.AppUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherProgressViewHolder extends RecyclerView.ViewHolder {

    TeacherProgressItemLayoutBinding binding;


    public TeacherProgressViewHolder(@NonNull TeacherProgressItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindUser(List<TeacherProgressStatusModel> model, int position, CommonCallBackListner commonCallBackListner) {

          if(((position+1)%2)==0 ){
              binding.tvProgressNoOdd.setVisibility(View.INVISIBLE);
              binding.tvProgressNoEven.setVisibility(View.VISIBLE);
              binding.tvProgressNoEven.setText(String.valueOf(position + 1));
          }else{
              binding.tvProgressNoOdd.setVisibility(View.VISIBLE);
              binding.tvProgressNoEven.setVisibility(View.INVISIBLE);
              binding.tvProgressNoOdd.setText(String.valueOf(position + 1));
          }

          binding.tvProgressTitle.setText(model.get(position).getTitle());
          if(model.get(position).getStatus()){
              binding.tvProgressNoOdd.setAlpha(1);
              binding.tvProgressNoEven.setAlpha(1);

             // binding.ivLowerLine.setBackgroundColor(AuroApp.getAppContext().getResources().getColor(R.color.colorPrimary));
              binding.ivUpperLine.setImageResource(R.drawable.teacher_progress_color);
          }
          else{
              binding.tvProgressNoOdd.setAlpha(0.5f);
              binding.tvProgressNoEven.setAlpha(0.5f);
          }

        if (position == model.size()-1) {
            binding.ivCircle.setBackgroundDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.progress_bottom));
        }


        int nextPosition = position+1 < model.size() ? (position + 1) : 0;

        if(model.get(position).getStatus() && model.get(nextPosition).getStatus()) {
            binding.ivCircle.setImageResource(R.drawable.is_active);
        }

        binding.viewLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.get(position).getCanaddwebinar() != null && model.get(position).getCanaddwebinar().booleanValue()){
                    if (commonCallBackListner != null) {
                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.WEBINAR_CLICK, model));
                    }
                }
            }
        });
        if (model.get(position).getCanaddwebinar() != null && model.get(position).getCanaddwebinar().booleanValue() )  {
           binding.viewBook.setVisibility(View.GONE);
        }else{
            binding.viewBook.setVisibility(View.GONE);
        }

//        else if (position == model.size() - 1)
 //       }{
//
//          //  binding.ivLowerLine.setVisibility(View.INVISIBLE);
//
//        }

//        binding.txtDocumentName.setText(model.getDocumentName());
//        if (!model.getDocumentFileName().equalsIgnoreCase(AuroApp.getAppContext().getString(R.string.no_file_chosen))) {
//            binding.txtFileName.setVisibility(View.VISIBLE);
//            binding.txtFileName.setText(model.getDocumentFileName());
//            binding.docImg.setOnClickListener(null);
//        } else {
//            binding.txtFileName.setVisibility(View.GONE);
//        }
//        if (model.isModify()) {
//            binding.docImg.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_auro_check));
//        } else {
//            binding.docImg.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_auro_upload));
//            binding.docImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (commonCallBackListner != null) {
//                        commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(position, Status.DOCUMENT_CLICK, model));
//                    }
//
//                }
//            });
//        }


    }
}
