package com.designmode.school;

/**
 * 课堂考试抽象父类接口
 */
public abstract class AbstractSchoolExamFactory {

    /**
     * 课堂单元考试
     * @return
     */
    public abstract ISchoolExamHandlerService getSchoolSectionExamHandler();
    /**
     * 课堂毕业考试
     * @return
     */
    public abstract BaseSchoolExamHandler getSchoolSubjectExamHandler();
    /**
     * 课堂学前评测
     * @return
     */
    public abstract BaseSchoolExamHandler getSchoolPreshoolExamHandler();
    /**
     * 课堂错题测验
     * @return
     */
    public abstract BaseSchoolExamHandler getSchoolWrongExamHandler();
    /**
     * 课堂考试引流
     * @return
     */
    public abstract BaseSchoolExamHandler getSchoolExamDrainageHandler();

}
