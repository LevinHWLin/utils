package com.designmode.school;

import java.util.Map;

public interface ISchoolExamHandlerService {

    /**
     * 获取考试考题列表
     * @param params
     * @return
     */
    Map<String, Object> getExamQuestionsList(Map<String,Object> params);

    /**
     * 处理考试答题记录，计算考试成绩
     * @param
     * @return
     */
    Map<String, Object> handlerExamAnswers(Map<String,Object> params);

    /**
     * 获取考试结果
     * @param params
     * @return
     */
    Map<String, Object> getExamResult(Map<String,Object> params);

    /**
     * 获取考试错题信息
     * @param params
     * @return
     */
    Map<String, Object> getExamWrongQuestionsInfo(Map<String,Object> params);
}
