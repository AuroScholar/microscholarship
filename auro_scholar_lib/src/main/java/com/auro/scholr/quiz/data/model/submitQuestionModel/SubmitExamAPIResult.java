package com.auro.scholr.quiz.data.model.submitQuestionModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitExamAPIResult{
	@SerializedName("SuccessFlag")
	@Expose
	private Boolean successFlag;


	@SerializedName("Score")
	@Expose
	private  int score;



	public Boolean getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(Boolean successFlag) {
		this.successFlag = successFlag;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
