package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceImgItemResModel implements Parcelable {

	@SerializedName("error")
	@Expose
	private boolean error;

	@SerializedName("message")
	@Expose
	private String message;

	@SerializedName("id_name")
	@Expose
	private String idName;

	@SerializedName("url")
	@Expose
	private String url;

	@SerializedName("status")
	@Expose
	private String status;

	protected FaceImgItemResModel(Parcel in) {
		error = in.readByte() != 0;
		message = in.readString();
		idName = in.readString();
		url = in.readString();
		status = in.readString();
	}

	public static final Creator<FaceImgItemResModel> CREATOR = new Creator<FaceImgItemResModel>() {
		@Override
		public FaceImgItemResModel createFromParcel(Parcel in) {
			return new FaceImgItemResModel(in);
		}

		@Override
		public FaceImgItemResModel[] newArray(int size) {
			return new FaceImgItemResModel[size];
		}
	};

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setIdName(String idName){
		this.idName = idName;
	}

	public String getIdName(){
		return idName;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"FaceImgItem{" + 
			"error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			",id_name = '" + idName + '\'' + 
			",url = '" + url + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeByte((byte) (error ? 1 : 0));
		parcel.writeString(message);
		parcel.writeString(idName);
		parcel.writeString(url);
		parcel.writeString(status);
	}
}