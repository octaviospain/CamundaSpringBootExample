package com.transgressoft.csbe.api;

import com.transgressoft.csbe.model.ProcessStartRequest;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CamundaProcessStarter {

    @Value ("${process.starter.default-tag:stable}")
    private String defaultTag;

    @Autowired
    private ProcessEngine engine;

    public ProcessInstanceWithVariables start(ProcessStartRequest request) {
        String businessKey = request.getBusinessKey();
        String processKey = request.getProcessKey();
        String tag = request.getTag();

        final ProcessDefinition definition = findDefinition(processKey, tag);

        if (definition == null) {
            throw new ProcessNotDeployedException(processKey);
        }

        RuntimeService service = engine.getRuntimeService();

        return (ProcessInstanceWithVariables) service.startProcessInstanceById(definition.getId(), businessKey, request.getVariables());
    }

    private ProcessDefinition findDefinition(String processKey, String tag) {
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase("latest", tag)) {
            return findLatestDefinition(processKey);
        }

        final ProcessDefinition definition = findDefinitionByTag(processKey, tag);

        return (definition == null) ? findLatestDefinition(processKey) : definition;
    }

    private ProcessDefinition findLatestDefinition(String processKey) {
        return engine.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .latestVersion()
                .singleResult();
    }

    private ProcessDefinition findDefinitionByTag(String processKey, String tagRequest) {
        String tag = getDefaultTagIfBlank(tagRequest);

        List<ProcessDefinition> list = engine.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .versionTag(tag)
                .orderByProcessDefinitionVersion().desc()
                .list();

        return list.isEmpty() ? null : list.get(0);
    }

    private String getDefaultTagIfBlank(String tagRequest) {
        return org.apache.commons.lang3.StringUtils.defaultIfBlank(tagRequest, defaultTag);
    }
}
