package com.auro.scholr.quiz.data.model.submitQuestionModel;

public class SubmitExamResultRes{
	private SubmitExamAPIResult submitExamAPIResult;

	public void setSubmitExamAPIResult(SubmitExamAPIResult submitExamAPIResult){
		this.submitExamAPIResult = submitExamAPIResult;
	}

	public SubmitExamAPIResult getSubmitExamAPIResult(){
		return submitExamAPIResult;
	}

	@Override
 	public String toString(){
		return 
			"SubmitExamResultRes{" + 
			"submitExamAPIResult = '" + submitExamAPIResult + '\'' + 
			"}";
		}
}
