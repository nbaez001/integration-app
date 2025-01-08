package com.empresa.integration.controller;

import com.empresa.integration.intfc.MessageGateway;
import com.empresa.integration.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final MessageGateway messageGateway;

    @PostMapping
    public void processStudentDetails(@RequestBody Student student) {
        messageGateway.sendMessage(student);
    }
}
