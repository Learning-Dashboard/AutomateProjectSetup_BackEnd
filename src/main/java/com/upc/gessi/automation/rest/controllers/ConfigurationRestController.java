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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigurationRestController {

    @Autowired
    SubjectController subjectController;

    @Autowired
    ConfigurationController configurationController;

    @GetMapping()
    //public Boolean getconfigProject(@RequestParam(name="name") String name, @RequestParam(name= "subject") String subject){
    public Boolean getconfigProject(@RequestParam Map<String,String[]> parameters){
        for (Map.Entry<String, String[]> project : parameters.entrySet()) {
            String key = project.getKey();
            String values = String.valueOf(project.getValue());
            String[] params = values.split(",");
            String name = params[0];
            String subject = params[1];

            if (subjectController.getGithub(subject)) {
                configurationController.configQR_connect_configGT(name, subject, "github");
                configurationController.configQR_connect_script(name, subject, "github");
                configurationController.configQR_connect_configM(name, subject, "github");
            }
            if (subjectController.getTaiga(subject)) {
                configurationController.configQR_connect_configGT(name, subject, "taiga");
                configurationController.configQR_connect_script(name, subject, "taiga");
                configurationController.configQR_connect_configM(name, subject, "taiga");
            }
            if (subjectController.getSheets(subject)) {
                configurationController.configQR_connect_configGT(name, subject, "sheets");
                configurationController.configQR_connect_script(name, subject, "sheets");
            }
        }
        return true;

    }

    @GetMapping(value="/eval")
    public Boolean getEvalProject (@RequestParam Map<String,String[]> parameters) throws IOException, InterruptedException {
        for (Map.Entry<String, String[]> project : parameters.entrySet()) {
            String key = project.getKey();
            String values = String.valueOf(project.getValue());
            String[] params = values.split(",");
            String name = params[0];
            String subject = params[1];

            configurationController.getEvalProjects(name, subject);
            System.out.print("Entra EVALProject \n");
            configurationController.createFolderProject(name, subject);
            System.out.print("Passa crearFolder \n");
            configurationController.configQR_eval_script(name, subject);
        }
        return true;

    }
}
