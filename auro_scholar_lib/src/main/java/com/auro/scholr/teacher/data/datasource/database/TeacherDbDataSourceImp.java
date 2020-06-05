package com.auro.scholr.teacher.data.datasource.database;

import com.auro.scholr.home.data.repository.HomeRepo;
import com.auro.scholr.teacher.data.repository.TeacherRepo;

import io.reactivex.Single;

public class TeacherDbDataSourceImp implements TeacherRepo.TeacherDbData {

    @Override
    public Single<Integer> getTeacherDataCount() {
        return null;
    }
}
