package com.auro.scholr.payment.data.model.response;

import com.google.gson.annotations.SerializedName;

public class PaytmResModel{

	@SerializedName("response")
	private String response;

	@SerializedName("error")
	private boolean error;

	@SerializedName("status")
	private String status;

	@SerializedName("message")
	private String message;




	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}