package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.respositories.ProjectRepository;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRep;


    public void createProject(ProjectDTO pDTO){
        Project project = new Project(pDTO.getName(),pDTO.getSubject(),pDTO.getURL_github(),pDTO.getURL_taiga(),pDTO.getURL_sheets(), pDTO.getID_github(), pDTO.getID_taiga());
        projectRep.save(project);

    }

    public Integer getId(String name, String subject) {
        List<Project> projects = projectRep.findByNameAndSubject(name, subject);
        if (!projects.isEmpty()) {
            return projects.get(0).getId(); // Devuelve el ID del primer proyecto encontrado
        } else {
            return null; // O puedes manejar el caso en que no se encuentre ningún proyecto
        }
    }
    public void getLastTask(){

    }

    public Boolean configQR_connect(String name, String subject) {
        Path path = Paths.get("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qrconnect");
        System.out.print(path);
        Path newPath = path.resolve("config/github_" + subject);
        System.out.print(newPath);
        Path file = newPath.resolve("github.properties");
        String pathi = file.toString();
        System.out.println(file);

        String newTask = "tasks.2.github.url=NuevoProyecto\n" +
                "tasks.2.github.commit.topic=github_nuevo_proyecto.commits\n" +
                "tasks.2.github.issue.topic=github_nuevo_proyecto.issues\n" +
                "tasks.2.taiga.task.topic=taiga_nuevo_proyecto.tasks\n";


        File sourceFile = file.toFile();
        StringBuilder contentAdd = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(pathi))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                System.out.println(line);
            }

            // Agregar la nueva tarea al final del contenido
            content.append(newTask);

            // Escribir el contenido actualizado de vuelta al archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathi))) {
                writer.write(content.toString());
                System.out.println("Nueva tarea añadida al archivo con éxito.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
