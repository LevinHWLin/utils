package com.demo.strategy;

public class PayStrategyFactory {

    public static AbstractPayStrategy getAlipay(){
        return new AlipayStrategy();
    }

    public static AbstractPayStrategy getWxPay(){
        return new WxPayStrategy();
    }

    public static AbstractPayStrategy getCoinPay(){
        return new CoinPayStrategy();
    }
}
