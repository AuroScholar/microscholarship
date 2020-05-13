package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AzureResModel implements Parcelable {

	@SerializedName("face_img")
	@Expose
	private List<FaceImgItemResModel> faceImg;

	@SerializedName("ExamAssignmentID")
	@Expose
	private String examAssignmentID;

	@SerializedName("error")
	@Expose
	private boolean error;

	@SerializedName("exam_name")
	@Expose
	private String examName;

	@SerializedName("status")
	@Expose
	private String status;

	@SerializedName("StudentID")
	@Expose
	private String studentID;

	@SerializedName("quiz_attempt")
	@Expose
	private String quizAttempt;

	protected AzureResModel(Parcel in) {
		faceImg = in.createTypedArrayList(FaceImgItemResModel.CREATOR);
		examAssignmentID = in.readString();
		error = in.readByte() != 0;
		examName = in.readString();
		status = in.readString();
		studentID = in.readString();
		quizAttempt = in.readString();
	}

	public static final Creator<AzureResModel> CREATOR = new Creator<AzureResModel>() {
		@Override
		public AzureResModel createFromParcel(Parcel in) {
			return new AzureResModel(in);
		}

		@Override
		public AzureResModel[] newArray(int size) {
			return new AzureResModel[size];
		}
	};

	public void setFaceImg(List<FaceImgItemResModel> faceImg){
		this.faceImg = faceImg;
	}

	public List<FaceImgItemResModel> getFaceImg(){
		return faceImg;
	}

	public void setExamAssignmentID(String examAssignmentID){
		this.examAssignmentID = examAssignmentID;
	}

	public String getExamAssignmentID(){
		return examAssignmentID;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setExamName(String examName){
		this.examName = examName;
	}

	public String getExamName(){
		return examName;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setStudentID(String studentID){
		this.studentID = studentID;
	}

	public String getStudentID(){
		return studentID;
	}

	public void setQuizAttempt(String quizAttempt){
		this.quizAttempt = quizAttempt;
	}

	public String getQuizAttempt(){
		return quizAttempt;
	}

	@Override
 	public String toString(){
		return 
			"AzureResModel{" + 
			"face_img = '" + faceImg + '\'' + 
			",examAssignmentID = '" + examAssignmentID + '\'' + 
			",error = '" + error + '\'' + 
			",exam_name = '" + examName + '\'' + 
			",status = '" + status + '\'' + 
			",studentID = '" + studentID + '\'' + 
			",quiz_attempt = '" + quizAttempt + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeTypedList(faceImg);
		parcel.writeString(examAssignmentID);
		parcel.writeByte((byte) (error ? 1 : 0));
		parcel.writeString(examName);
		parcel.writeString(status);
		parcel.writeString(studentID);
		parcel.writeString(quizAttempt);
	}
}