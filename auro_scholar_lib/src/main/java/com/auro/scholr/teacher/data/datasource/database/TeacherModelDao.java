package com.auro.scholr.teacher.data.datasource.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;

import java.util.List;

@Dao
public interface TeacherModelDao {


    @Insert
    Long[] insertStateListData(List<StateDataModel> list);


    @Query("SELECT * FROM state_table")
    List<StateDataModel> getAllStateData();



    @Insert
    Long[] insertDistrictListData(List<DistrictDataModel> list);


    @Query("SELECT * FROM district_table")
    List<DistrictDataModel> getAllDistrictData();

    @Query("SELECT * FROM district_table WHERE state_code=:stateCode")
    List<DistrictDataModel> getStateDistrictData(String stateCode);

/*
    @Query("UPDATE course_masters SET modifiedTime =:modifiedTime WHERE  locale=:locale")
    int updateModifiedTime(String modifiedTime, String locale);

    @Query("SELECT count(courseId) FROM course_masters WHERE courseId =:courseId AND locale=:locale ")
    int isCourseDataAvailable(int courseId, String locale);


    @Query("SELECT primaryId FROM course_masters WHERE  courseId =:courseId AND locale=:locale")
    Long getDbCourseID(int courseId, String locale);

    @Update
    int updateCourseModelData(CourseModel courseModel);

    @Query("DELETE FROM course_masters")
    void clearCourseTable();*/
}
