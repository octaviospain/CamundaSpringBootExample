/*
 * Copyright (c) 2018. CreativeDock s.r.o. All rights reserved.
 */
package com.transgressoft.csbe.model;

import java.util.*;

public class ProcessStartRequest {

    private final String processKey;
    private final String tag;
    private final String businessKey;
    private final String origin;
    private final Map<String, Object> variables;

    public ProcessStartRequest(String processKey, String tag, String businessKey, String origin,
                               Map<String, Object> variables) {
        this.processKey = processKey;
        this.tag = tag;
        this.businessKey = businessKey;
        this.origin = origin;
        this.variables = variables;
    }

    public String getProcessKey() {
        return processKey;
    }

    public String getTag() {
        return tag;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public String getOrigin() {
        return origin;
    }

    public Map<String, Object> getVariables() {
        return Collections.unmodifiableMap(variables);
    }
}
