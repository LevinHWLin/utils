package com.common.factch;

import java.util.Map;

public class SubjectGood implements GoodService {

    public String getGoodType() {
        return "Subject";
    }

    public Map<String, Object> wxPay() {
        return null;
    }

    public Map<String, Object> aliPay() {
        return null;
    }

    public Map<String, Object> xnCoinPay() {
        return null;
    }
}
