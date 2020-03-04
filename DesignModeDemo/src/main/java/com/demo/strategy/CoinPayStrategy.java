package com.demo.strategy;

public class CoinPayStrategy extends AbstractPayStrategy {
    public void pay() {
        System.out.println("进行学农币支付");
    }

    public void payCallback() {
        System.out.println("学农币支付回调处理");
    }
}
