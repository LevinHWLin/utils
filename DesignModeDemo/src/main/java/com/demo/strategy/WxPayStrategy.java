package com.demo.strategy;

public class WxPayStrategy extends AbstractPayStrategy {
    public void pay() {
        System.out.println("进行微信支付");
    }

    public void payCallback() {
        System.out.println("微信支付回调处理");
    }
}
