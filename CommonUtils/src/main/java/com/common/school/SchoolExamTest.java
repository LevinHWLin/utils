package com.common.school;

import com.common.school.factory.SchoolExamFactory;

public class SchoolExamTest {

    public static void main(String[] args){
        AbstractSchoolExamFactory schoolExamFactory = new SchoolExamFactory();

        schoolExamFactory.getSchoolSectionExamHandler().handlerExamAnswers(null);

        String headImg = "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLsiaMmMhuwgSsIHJY8jcPLOibdvIjbufSibhic9x7cp9icu3BypvF0p3hfbt00MKlk6qjt9mOwAYdwia8w/132";
        System.out.println(headImg.contains("http://") || headImg.contains("https://"));
        System.out.println(headImg.contains("https://"));
    }
}
