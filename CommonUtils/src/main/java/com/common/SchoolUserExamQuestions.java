package com.common;

import java.io.Serializable;
import java.util.Date;

public class SchoolUserExamQuestions implements Serializable {
    private static final long serialVersionUID = -7025627672294592226L;
    private String id;
    private String userId;
    private String schoolSubjectId;
    private String subjectSemesterId;
    private String subjectSectionId;
    private String type;
    private String questionId;
    private String orderNum;
    private String questionContent;
    private String questionType;
    private String questionStatus;
    private Integer questionRightAnswer;
    private String answersContent;
    private String answerAnalyseText;
    private String answerAnalyseVideoUrl;
    private String answerAnalyseVideoImg;
    private Date createTime = new Date();
    private String createUser = "手动录入";
    private Date alterTime = new Date();
    private String alterUser = "手动录入";

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "SchoolUserExamQuestions [id=" + id + ", userId=" + userId + ", schoolSubjectId=" + schoolSubjectId + ", subjectSemesterId="
                + subjectSemesterId + ", subjectSectionId=" + subjectSectionId + ", type=" + type + ", questionId=" + questionId+ ", orderNum=" + orderNum
                + ", questionContent=" + questionContent+ ", questionType=" + questionType+ ", questionStatus=" + questionStatus+ ", questionRightAnswer=" + questionRightAnswer
                + ", answersContent=" + answersContent+ ", answerAnalyseText=" + answerAnalyseText+ ", answerAnalyseVideoUrl=" + answerAnalyseVideoUrl+ ", answerAnalyseVideoImg=" + answerAnalyseVideoImg
                + ", createTime=" + createTime+ ", createUser=" + createUser+ ", alterTime=" + alterTime+ ", alterUser=" + alterUser +"]";
    }

    public String getAnswerAnalyseVideoUrl() {
        return answerAnalyseVideoUrl;
    }

    public void setAnswerAnalyseVideoUrl(String answerAnalyseVideoUrl) {
        this.answerAnalyseVideoUrl = answerAnalyseVideoUrl;
    }

    public String getAnswerAnalyseVideoImg() {
        return answerAnalyseVideoImg;
    }

    public void setAnswerAnalyseVideoImg(String answerAnalyseVideoImg) {
        this.answerAnalyseVideoImg = answerAnalyseVideoImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(Date alterTime) {
        this.alterTime = alterTime;
    }

    public String getAlterUser() {
        return alterUser;
    }

    public void setAlterUser(String alterUser) {
        this.alterUser = alterUser;
    }

    public String getAnswerAnalyseText() {
        return answerAnalyseText;
    }

    public void setAnswerAnalyseText(String answerAnalyseText) {
        this.answerAnalyseText = answerAnalyseText;
    }

    public String getAnswersContent() {
        return answersContent;
    }

    public void setAnswersContent(String answersContent) {
        this.answersContent = answersContent;
    }

    public Integer getQuestionRightAnswer() {
        return questionRightAnswer;
    }

    public void setQuestionRightAnswer(Integer questionRightAnswer) {
        this.questionRightAnswer = questionRightAnswer;
    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubjectSectionId() {
        return subjectSectionId;
    }

    public void setSubjectSectionId(String subjectSectionId) {
        this.subjectSectionId = subjectSectionId;
    }

    public String getSubjectSemesterId() {
        return subjectSemesterId;
    }

    public void setSubjectSemesterId(String subjectSemesterId) {
        this.subjectSemesterId = subjectSemesterId;
    }

    public String getSchoolSubjectId() {
        return schoolSubjectId;
    }

    public void setSchoolSubjectId(String schoolSubjectId) {
        this.schoolSubjectId = schoolSubjectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
