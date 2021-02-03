package com.auro.scholr.teacher.data.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyClassRoomResModel {


    @SerializedName("APImyclassroomteacher")
    @Expose
    private MyClassRoomTeacherResModel teacherResModel;


    public MyClassRoomTeacherResModel getTeacherResModel() {
        return teacherResModel;
    }

    public void setTeacherResModel(MyClassRoomTeacherResModel teacherResModel) {
        this.teacherResModel = teacherResModel;
    }
}