package com.auro.scholr.teacher.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * User: dharmaraj
 * Date: 29/7/20
 * Time: 11:59 AM
 */
public class TeacherProgressModel {
    @SerializedName("progress")
    @Expose
    public ArrayList<TeacherProgressStatusModel> teacherProgressStatusModels = null;


}





