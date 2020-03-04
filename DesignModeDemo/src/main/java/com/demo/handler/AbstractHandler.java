package com.demo.handler;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public abstract class AbstractHandler {

    public AbstractHandler getNext() {
        return next;
    }

    public void setNext(AbstractHandler next) {
        this.next = next;
    }

    private AbstractHandler next;

    protected abstract void handlerRequest(int request);

}
