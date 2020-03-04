package com.demo.strategy;

public class TestStrategy {

    public static void main(String[] args){
        AbstractPayStrategy alipay = PayStrategyFactory.getAlipay();
        alipay.pay();
        alipay.payCallback();

        AbstractPayStrategy wxPay = PayStrategyFactory.getWxPay();
        wxPay.pay();
        wxPay.payCallback();

        AbstractPayStrategy coinPay = PayStrategyFactory.getCoinPay();
        coinPay.pay();
        coinPay.payCallback();
    }
}
