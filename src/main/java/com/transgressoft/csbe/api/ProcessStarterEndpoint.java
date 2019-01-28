package com.transgressoft.csbe.api;

import com.google.common.collect.ImmutableMap;
import com.transgressoft.csbe.model.ProcessStartRequest;
import com.transgressoft.csbe.model.uuid.GeneratedUuid;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class ProcessStarterEndpoint {

    @Autowired
    private CamundaProcessStarter processStarter;

    @RequestMapping (path = "/start/{key}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, Object>> startProcess(
            @PathVariable String key,
            @GeneratedUuid String businessKey,
            @RequestBody Map<String, Object> variables) {

        ProcessStartRequest request = new ProcessStartRequest(key, null, businessKey, "process-start", variables);

        ProcessInstanceWithVariables instance = processStarter.start(request);

        Map<String, Object> response = ImmutableMap.<String, Object>builder()
                .put("id", instance.getId())
                .put("businessKey", instance.getBusinessKey())
                .put("processDefinitionId", instance.getProcessDefinitionId())
                .put("isEnded", instance.isEnded())
                .put("variables", instance.getVariables())
                .build();

        return ResponseEntity.ok(response);
    }
}
