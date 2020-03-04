package com.designmode.school.factory;

import com.designmode.school.AbstractSchoolExamFactory;
import com.designmode.school.BaseSchoolExamHandler;
import com.designmode.school.ISchoolExamHandlerService;

public class SchoolExamFactory extends AbstractSchoolExamFactory {


    public ISchoolExamHandlerService getSchoolSectionExamHandler() {

        ISchoolExamHandlerService schoolExamHandlerService = new SchoolSectionExamHandler();

        return schoolExamHandlerService;
    }

    public BaseSchoolExamHandler getSchoolSubjectExamHandler() {
        return null;
    }

    public BaseSchoolExamHandler getSchoolPreshoolExamHandler() {
        return null;
    }

    public BaseSchoolExamHandler getSchoolWrongExamHandler() {
        return null;
    }

    public BaseSchoolExamHandler getSchoolExamDrainageHandler() {
        return null;
    }
}
