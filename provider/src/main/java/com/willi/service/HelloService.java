package com.willi.service;

import java.io.Serializable;

public interface HelloService extends Serializable {
    static final long serialVersionUID = -8020775750799142361L;
    String hello(String msg);
}
