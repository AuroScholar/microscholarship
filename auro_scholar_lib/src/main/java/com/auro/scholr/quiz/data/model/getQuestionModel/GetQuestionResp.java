package com.auro.scholr.quiz.data.model.getQuestionModel;

import java.util.List;

public class GetQuestionResp{
	private List<SendQuestionToCandidateWebApiResultItem> sendQuestionToCandidateWebApiResult;

	public void setSendQuestionToCandidateWebApiResult(List<SendQuestionToCandidateWebApiResultItem> sendQuestionToCandidateWebApiResult){
		this.sendQuestionToCandidateWebApiResult = sendQuestionToCandidateWebApiResult;
	}

	public List<SendQuestionToCandidateWebApiResultItem> getSendQuestionToCandidateWebApiResult(){
		return sendQuestionToCandidateWebApiResult;
	}

	@Override
 	public String toString(){
		return 
			"GetQuestionResp{" + 
			"sendQuestionToCandidateWebApiResult = '" + sendQuestionToCandidateWebApiResult + '\'' + 
			"}";
		}
}