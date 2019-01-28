package com.transgressoft.csbe.api;

public class ProcessNotDeployedException extends RuntimeException {

    public ProcessNotDeployedException(String processKey) {
        super("Process not deployed: " + processKey);
    }
}
