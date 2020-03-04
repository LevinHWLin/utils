package com.common.factch;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGoodFactory {
    protected Map<String, GoodService> goodsMap = new HashMap<String, GoodService>();

    protected void registerGoods(){

    }


    public void handler(){
        System.out.println(this.goodsMap);
    }


}
