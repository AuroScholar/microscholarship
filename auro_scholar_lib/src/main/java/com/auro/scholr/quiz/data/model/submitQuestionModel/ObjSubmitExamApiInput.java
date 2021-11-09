package com.auro.scholr.quiz.data.model.submitQuestionModel;

public class ObjSubmitExamApiInput{
	private int examAssignmentID;

	public void setExamAssignmentID(int examAssignmentID){
		this.examAssignmentID = examAssignmentID;
	}

	public int getExamAssignmentID(){
		return examAssignmentID;
	}

	@Override
 	public String toString(){
		return 
			"ObjSubmitExamApiInput{" + 
			"examAssignmentID = '" + examAssignmentID + '\'' + 
			"}";
		}
}
