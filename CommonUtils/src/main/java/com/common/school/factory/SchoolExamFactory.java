package com.common.school.factory;

import com.common.factch.AbstractGoodFactory;
import com.common.school.AbstractSchoolExamFactory;
import com.common.school.BaseSchoolExamHandler;
import com.common.school.ISchoolExamHandlerService;

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
