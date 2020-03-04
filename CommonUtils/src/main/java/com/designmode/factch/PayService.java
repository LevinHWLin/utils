package com.designmode.factch;

import java.util.Map;

public interface PayService {
    Map<String,Object> wxPay();
    Map<String,Object> aliPay();
    Map<String,Object> xnCoinPay();
}
