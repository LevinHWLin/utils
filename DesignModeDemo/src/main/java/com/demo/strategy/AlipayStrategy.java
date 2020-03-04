package com.demo.strategy;

public class AlipayStrategy extends AbstractPayStrategy {
    public void pay() {
        System.out.println("进行支付宝支付");
    }

    public void payCallback() {
        System.out.println("支付宝支付回调处理");
    }
}
