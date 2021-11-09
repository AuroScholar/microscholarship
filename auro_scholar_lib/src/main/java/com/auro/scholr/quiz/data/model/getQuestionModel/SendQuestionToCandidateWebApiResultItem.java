package com.auro.scholr.quiz.data.model.getQuestionModel;

public class SendQuestionToCandidateWebApiResultItem{
	private String imageName;
	private int studentExamQuestionsID;
	private int questionID;
	private String question;
	private String optionDetails;

	public void setImageName(String imageName){
		this.imageName = imageName;
	}

	public String getImageName(){
		return imageName;
	}

	public void setStudentExamQuestionsID(int studentExamQuestionsID){
		this.studentExamQuestionsID = studentExamQuestionsID;
	}

	public int getStudentExamQuestionsID(){
		return studentExamQuestionsID;
	}

	public void setQuestionID(int questionID){
		this.questionID = questionID;
	}

	public int getQuestionID(){
		return questionID;
	}

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return question;
	}

	public void setOptionDetails(String optionDetails){
		this.optionDetails = optionDetails;
	}

	public String getOptionDetails(){
		return optionDetails;
	}

	@Override
 	public String toString(){
		return 
			"SendQuestionToCandidateWebApiResultItem{" + 
			"imageName = '" + imageName + '\'' + 
			",studentExamQuestionsID = '" + studentExamQuestionsID + '\'' + 
			",questionID = '" + questionID + '\'' + 
			",question = '" + question + '\'' + 
			",optionDetails = '" + optionDetails + '\'' + 
			"}";
		}
}
