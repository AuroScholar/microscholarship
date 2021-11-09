package com.auro.scholr.quiz.data.model.getQuestionModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchQuizResModel {

	@SerializedName("status")
	@Expose
	private String status;

	@SerializedName("registration_id")
	@Expose
	private String registration_id;

	@SerializedName("error")
	@Expose
	private Boolean error;
	@SerializedName("StudentID")
	@Expose
	private String studentID;
	@SerializedName("Subject")
	@Expose
	private String subject;
	@SerializedName("exam_name")
	@Expose
	private String examName;
	@SerializedName("quiz_attempt")
	@Expose
	private String quizAttempt;
	@SerializedName("examlang")
	@Expose
	private String examlang;
	@SerializedName("ExamAssignmentID")
	@Expose
	private String examAssignmentID;
	@SerializedName("quiz_status")
	@Expose
	private String quizStatus;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("start_timestamp")
	@Expose
	private String startTimestamp;
	@SerializedName("end_timestamp")
	@Expose
	private String endTimestamp;
	@SerializedName("question_attemped")
	@Expose
	private Integer questionAttemped;
	@SerializedName("SendQuestionToCandidateWebApiResult")
	@Expose
	private List<QuestionResModel> questionResModelList = null;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getQuizAttempt() {
		return quizAttempt;
	}

	public void setQuizAttempt(String quizAttempt) {
		this.quizAttempt = quizAttempt;
	}

	public String getExamlang() {
		return examlang;
	}

	public void setExamlang(String examlang) {
		this.examlang = examlang;
	}

	public String getExamAssignmentID() {
		return examAssignmentID;
	}

	public void setExamAssignmentID(String examAssignmentID) {
		this.examAssignmentID = examAssignmentID;
	}

	public String getQuizStatus() {
		return quizStatus;
	}

	public void setQuizStatus(String quizStatus) {
		this.quizStatus = quizStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(String startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public String getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(String endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public Integer getQuestionAttemped() {
		return questionAttemped;
	}

	public void setQuestionAttemped(Integer questionAttemped) {
		this.questionAttemped = questionAttemped;
	}

	public List<QuestionResModel> getQuestionResModelList() {
		return questionResModelList;
	}

	public void setQuestionResModelList(List<QuestionResModel> questionResModelList) {
		this.questionResModelList = questionResModelList;
	}

	public String getRegistration_id() {
		return registration_id;
	}

	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}
}