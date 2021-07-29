package com.auro.scholr.home.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpResModel {

	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("error")
	@Expose
	private boolean error;
	@SerializedName("mobile_no")
	@Expose
	private String mobileNo;
	@SerializedName("sms_text")
	@Expose
	private String smsText;
	@SerializedName("otp")
	@Expose
	private int otp;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("is_log")
	@Expose
	private boolean isLog;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getIsLog() {
		return isLog;
	}

	public void setIsLog(boolean isLog) {
		this.isLog = isLog;
	}

}