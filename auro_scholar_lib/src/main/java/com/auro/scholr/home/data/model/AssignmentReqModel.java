package com.auro.scholr.home.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentReqModel {

    public AssignmentReqModel() {

    }

    @SerializedName("objStudentExamInfo")
    @Expose
    private ObjStudentExamInfo objStudentExamInfo;

    public ObjStudentExamInfo getObjStudentExamInfo() {
        return objStudentExamInfo;
    }

    public void setObjStudentExamInfo(ObjStudentExamInfo objStudentExamInfo) {
        this.objStudentExamInfo = objStudentExamInfo;
    }

}
