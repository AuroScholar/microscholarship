package com.auro.scholr.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearByFriendList {

    @Expose
    @SerializedName("student")
    private List<Student> student;
    @Expose
    @SerializedName("error")
    private boolean error;
    @Expose
    @SerializedName("message")
    private String message;

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Student {
        @Expose
        @SerializedName("longitude")
        private String longitude;
        @Expose
        @SerializedName("latitude")
        private String latitude;
        @Expose
        @SerializedName("student_class")
        private String student_class;
        @Expose
        @SerializedName("email_id")
        private String email_id;
        @Expose
        @SerializedName("student_name")
        private String student_name;
        @Expose
        @SerializedName("mobile_no")
        private String mobile_no;
        @Expose
        @SerializedName("registration_id")
        private int registration_id;
        @Expose
        @SerializedName("distance")
        private String distance;
        @Expose
        @SerializedName("student_photo")
        private String student_photo;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLattitude() {
            return latitude;
        }

        public void setLattitude(String lattitude) {
            this.latitude = lattitude;
        }

        public String getStudent_class() {
            return student_class;
        }

        public void setStudent_class(String student_class) {
            this.student_class = student_class;
        }

        public String getEmail_id() {
            return email_id;
        }

        public void setEmail_id(String email_id) {
            this.email_id = email_id;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public int getRegistration_id() {
            return registration_id;
        }

        public void setRegistration_id(int registration_id) {
            this.registration_id = registration_id;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getStudent_photo() {
            return student_photo;
        }

        public void setStudent_photo(String student_photo) {
            this.student_photo = student_photo;
        }
    }
}
