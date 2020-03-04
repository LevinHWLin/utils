package com.demo.handler;

public class TestHandler {

    public static void main(String[] args){

        AbstractHandler first = new FirstHandler();
        AbstractHandler second = new SecondHandler();
        first.setNext(second);
        first.handlerRequest(2);
    }
}
