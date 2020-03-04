package com.designmode.factch;

import java.util.Map;

public class VipGood implements GoodService{

    public String getGoodType() {
        return "VIP";
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
