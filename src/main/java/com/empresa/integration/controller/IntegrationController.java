package com.empresa.integration.controller;

import com.empresa.integration.model.Student;
import com.empresa.integration.service.IntegrationGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integrate")
@Slf4j
public class IntegrationController {

    @Autowired
    private IntegrationGateway integrationGateway;

    @GetMapping(value = "{name}")
    public String getMessageFromIntegrationService(@PathVariable("name") String name) {
        return integrationGateway.sendMessage(name);
    }

    @PostMapping
    public String processStudentDetails(@RequestBody Student student) {
        log.info(student.toString());
        return integrationGateway.processStudentDetails(student);
    }
}
