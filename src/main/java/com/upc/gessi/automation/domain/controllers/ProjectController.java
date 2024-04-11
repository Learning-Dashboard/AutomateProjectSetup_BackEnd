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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRep;

    public ProjectController(ProjectRepository projectRep) {
        this.projectRep = projectRep;
    }


    public void createProject(ProjectDTO pDTO){
        Project project = new Project(pDTO.getName(),pDTO.getSubject(),pDTO.getURL_github(),pDTO.getURL_taiga(),pDTO.getURL_sheets(), pDTO.getID_github(), pDTO.getID_taiga());
        projectRep.save(project);

    }

    public Integer getId(String name, String subject) {
        Project project = projectRep.findByNameAndSubject(name, subject);
        return project.getId();
    }
    public Integer getLastTask(String subject,String type){
        Path path = Paths.get("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qrconnect");
        System.out.print(path);
        Path PathFile = path.resolve("config/"+type+"_" + subject + "/"+type+".properties");
        String pathfile = PathFile.toString();
        System.out.println(pathfile);

        Integer num_teams = 0;

        try (BufferedReader reader= new BufferedReader(new FileReader(pathfile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if(line.startsWith(type+".teams.num")){
                    String[] parts = line.split("=");
                    if(parts.length ==2){
                        num_teams = Integer.parseInt(parts[1].trim());

                    }
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return num_teams;
    }

    public Boolean configQR_connect(String name, String subject,String type) {

        Path path = Paths.get("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qrconnect");
        System.out.print(path);
        Path newPath = path.resolve("config/"+type+"_" + subject);
        System.out.print(newPath);
        Path file = newPath.resolve(type+".properties");
        String pathi = file.toString();
        System.out.println(file);

        Integer num_task = getLastTask(subject,type);
        System.out.print(num_task);
        Project proj_config = projectRep.findByNameAndSubject(name,subject);
        String id;
        String newTask = null;
        if(type == "github"){
            id=proj_config.getID_github();
            newTask = "tasks."+num_task+".github.url="+id+"\n" +
                    "tasks."+num_task+".github.commit.topic=github_"+subject+"_"+name+".commits\n" +
                    "tasks."+num_task+".github.issue.topic=github_"+subject+"_"+name+".issues\n" +
                    "tasks."+num_task+".taiga.task.topic=taiga_"+subject+"_"+name+".tasks\n";
        }
        else if(type == "taiga"){
            id = proj_config.getID_taiga();
            newTask = "tasks."+num_task+".slug="+id+"\n" +
                    "tasks."+num_task+".taiga.issue.topic=taiga_"+subject+"_"+name+".issues\n" +
                    "tasks."+num_task+".taiga.epic.topic=taiga_"+subject+"_"+name+".epics\n" +
                    "tasks."+num_task+".taiga.userstory.topic=taiga_"+subject+"_"+name+".userstories\n" +
                    "tasks."+num_task+".taiga.task.topic=taiga_"+subject+"_"+name+".tasks\n";
            
        }
        
        File sourceFile = file.toFile();
        StringBuilder contentAdd = new StringBuilder();
        Integer num_teams = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(pathi))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if(line.startsWith(type+".teams.num")){

                    Pattern pattern = Pattern.compile(type+"\\.teams\\.num=(\\d+)");
                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()){
                        num_teams = Integer.parseInt(matcher.group(1));
                        System.out.print("aaaaaaa  "+num_teams);
                        num_teams++;
                        line=type+".teams.num=" + num_teams;
                        System.out.println(line);
                    }
                }
                content.append(line).append("\n");
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
