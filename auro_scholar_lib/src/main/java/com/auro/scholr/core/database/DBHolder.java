package com.auro.scholr.core.database;


public final class DBHolder {

    private DBHolder() {

    }

    private static AppDatabase appDatabase;



    public static AppDatabase getDB() {
        appDatabase = AppDatabase.getAppDatabase();
        return appDatabase;
    }

    public static void destroyDBInstance() {
        if (appDatabase != null) {
            AppDatabase.destroyInstance();
            appDatabase = null;
        }
    }
}
