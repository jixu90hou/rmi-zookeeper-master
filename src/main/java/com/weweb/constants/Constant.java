package com.weweb.constants;

/**
 * Created by jackshen on 2017/5/21.
 */
public interface Constant {

    String ZK_CONNECTION_STRING = "10.3.1.222:2181";
    int ZK_SESSION_TIMEOUT = 5000;
    String ZK_REGISTRY_PATH = "/registry";
    String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
}