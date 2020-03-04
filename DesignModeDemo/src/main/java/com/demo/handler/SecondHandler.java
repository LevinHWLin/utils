package com.demo.handler;

public class SecondHandler extends AbstractHandler {
    protected void handlerRequest(int request) {
        if(request <= 2)
        {
            System.out.println("具体处理者2负责处理该请求！");
        }
        else
        {
            if(getNext()!=null)
            {
                getNext().handlerRequest(request);
            }
            else
            {
                System.out.println("没有人处理该请求！");
            }
        }
    }
}
