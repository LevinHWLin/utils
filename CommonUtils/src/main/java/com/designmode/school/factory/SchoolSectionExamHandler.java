package com.designmode.school.factory;

import com.designmode.school.BaseSchoolExamHandler;
import com.designmode.school.ISchoolExamHandlerService;

import java.util.Map;

public class SchoolSectionExamHandler extends BaseSchoolExamHandler implements ISchoolExamHandlerService {

    public Map<String, Object> getExamQuestionsList(Map<String, Object> params) {
        return null;
    }

    public Map<String, Object> handlerExamAnswers(Map<String, Object> params) {
        System.out.println("单元考试处理考试成绩");
        this.handlerScore();
        return null;
    }

    public Map<String, Object> getExamResult(Map<String, Object> params) {
        return null;
    }

    public Map<String, Object> getExamWrongQuestionsInfo(Map<String, Object> params) {
        return null;
    }
}
