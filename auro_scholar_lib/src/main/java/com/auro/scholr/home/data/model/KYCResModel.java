package com.auro.scholr.home.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KYCResModel {

    @SerializedName("id_proof")
    @Expose
    private KYCResItemModel idProof;
    @SerializedName("id_proof_back")
    @Expose
    private KYCResItemModel idProofBack;
    @SerializedName("school_id_card")
    @Expose
    private KYCResItemModel schoolIdCard;
    @SerializedName("student_photo")
    @Expose
    private KYCResItemModel studentPhoto;

    public KYCResItemModel getIdProof() {
        return idProof;
    }

    public void setIdProof(KYCResItemModel idProof) {
        this.idProof = idProof;
    }

    public KYCResItemModel getIdProofBack() {
        return idProofBack;
    }

    public void setIdProofBack(KYCResItemModel idProofBack) {
        this.idProofBack = idProofBack;
    }

    public KYCResItemModel getSchoolIdCard() {
        return schoolIdCard;
    }

    public void setSchoolIdCard(KYCResItemModel schoolIdCard) {
        this.schoolIdCard = schoolIdCard;
    }

    public KYCResItemModel getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(KYCResItemModel studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

}