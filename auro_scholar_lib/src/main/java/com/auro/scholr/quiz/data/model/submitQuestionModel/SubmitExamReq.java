package com.auro.scholr.quiz.data.model.submitQuestionModel;

public class SubmitExamReq{
	private ObjSubmitExamApiInput objSubmitExamApiInput;

	public void setObjSubmitExamApiInput(ObjSubmitExamApiInput objSubmitExamApiInput){
		this.objSubmitExamApiInput = objSubmitExamApiInput;
	}

	public ObjSubmitExamApiInput getObjSubmitExamApiInput(){
		return objSubmitExamApiInput;
	}

	@Override
 	public String toString(){
		return 
			"SubmitExamReq{" + 
			"objSubmitExamApiInput = '" + objSubmitExamApiInput + '\'' + 
			"}";
		}
}
