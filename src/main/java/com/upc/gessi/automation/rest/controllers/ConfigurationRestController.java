package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.ConfigurationController;
import com.upc.gessi.automation.domain.controllers.SubjectController;
import com.upc.gessi.automation.domain.models.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/config")
public class ConfigurationRestController {

    @Autowired
    SubjectController subjectController;

    @Autowired
    ConfigurationController configurationController;

    @GetMapping()
    public Boolean getconfigProject(@RequestParam(name="name") String name, @RequestParam(name= "subject") String subject){

        if(subjectController.getGithub(subject)){
            configurationController.configQR_connect_configGT(name,subject,"github");
            configurationController.configQR_connect_script(name,subject,"github");
            configurationController.configQR_connect_configM(name, subject,"github");
        }
        if(subjectController.getTaiga(subject)){
            configurationController.configQR_connect_configGT(name,subject,"taiga");
            configurationController.configQR_connect_script(name,subject,"taiga");
            configurationController.configQR_connect_configM(name, subject,"taiga");
        }
        if(subjectController.getSheets(subject)){
            configurationController.configQR_connect_configGT(name, subject, "sheets");
            configurationController.configQR_connect_script(name,subject,"sheets");
        }
        return true;

    }

    @GetMapping(value="/eval")
    public Boolean getEvalProject (@RequestParam(name="name") String name, @RequestParam(name= "subject") String subject) throws IOException, InterruptedException {
        configurationController.getEvalProjects(name,subject);
        configurationController.createFolderProject(name,subject);
        configurationController.configQR_eval_script(name,subject);
        return true;

    }
}
