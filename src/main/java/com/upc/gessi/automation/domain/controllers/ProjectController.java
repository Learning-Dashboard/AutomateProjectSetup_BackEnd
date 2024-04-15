package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Project;
import com.upc.gessi.automation.domain.respositories.ProjectRepository;
import com.upc.gessi.automation.rest.DTO.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
    public Boolean configQR_connect_scriptG(String name,String subject){
        Path path = Paths.get("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qrconnect");
        System.out.print(path);
        Path newPath = path.resolve("scripts/run.github_" + subject+".sh");
        String pathi = newPath.toString();
        System.out.println(pathi);

        List<String> newCommits = new ArrayList<>();
        String commits = "github_"+subject+"_"+name+".commits";
        newCommits.add(commits);

        List<String> newIssues = new ArrayList<>();
        String issue = "github_"+subject+"_"+name+".issues";
        newIssues.add(issue);

        try {
            // Leer el archivo de script Bash
            BufferedReader reader = new BufferedReader(new FileReader(pathi));
            StringBuilder scriptContent = new StringBuilder();
            String line;
            boolean inCommitsCollection = false;
            boolean inIssues = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("COMMITS_COLLECTION_NAME=(")) {
                    inCommitsCollection = true;
                }
                if(line.contains("ISSUES_COLLECTION_NAME=(")){
                    inIssues = true;
                }


                if (inCommitsCollection) {
                    // Buscar el final del paréntesis
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        // Agregar los nuevos elementos dentro del paréntesis
                        for (String newCommit : newCommits) {
                            line = line.substring(0, endIndex) + " \"" + newCommit + "\"" + line.substring(endIndex);
                        }
                        inCommitsCollection = false; // Ya hemos modificado la línea de COMMITS_COLLECTION_NAME
                    }
                }
                if (inIssues) {
                    // Buscar el final del paréntesis
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        // Agregar los nuevos elementos dentro del paréntesis
                        for (String newIssue : newIssues) {
                            line = line.substring(0, endIndex) + " \"" + newIssue + "\"" + line.substring(endIndex);
                        }
                        inIssues = false; // Ya hemos modificado la línea de COMMITS_COLLECTION_NAME
                    }
                }
                // Agregar la línea al contenido del script
                scriptContent.append(line).append("\n");
            }
            reader.close();

            // Escribir el archivo modificado
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathi));
            writer.write(scriptContent.toString());
            writer.close();

            System.out.println("Archivo modificado exitosamente.");
            return true;
        } catch (IOException e) {
            System.err.println("Error al modificar el archivo: " + e.getMessage());
        }
        return true;
    }
    public Boolean configQR_connect_scriptT(String name,String subject){
        Path path = Paths.get("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qrconnect");
        System.out.print(path);
        Path newPath = path.resolve("scripts/run.taiga_" + subject+".sh");
        String pathi = newPath.toString();
        System.out.println(pathi);
        try {
            // Leer el archivo de script Bash
            BufferedReader reader = new BufferedReader(new FileReader(pathi));
            StringBuilder scriptContent = new StringBuilder();
            String line;
            boolean inEpicsCollection = false;
            boolean inIssues = false;
            boolean inUserStories = false;
            boolean inTasks = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("EPICS_COLLECTION_NAME=(")) {
                    inEpicsCollection = true;
                }
                if(line.contains("ISSUES_COLLECTION_NAME=(")){
                    inIssues = true;
                }
                if(line.contains("USERSTORIES_COLLECTION_NAME=(")){
                    inUserStories = true;
                }
                if(line.contains("TASKS_COLLECTION_NAME=(")){
                    inTasks = true;
                }
                if (inEpicsCollection) {
                    // Buscar el final del paréntesis
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        // Agregar los nuevos elementos dentro del paréntesis
                        line = line.substring(0, endIndex) + " \"" + "taiga_"+subject+"_"+name+".epics" + "\"" + line.substring(endIndex);
                        inEpicsCollection = false; // Ya hemos modificado la línea de COMMITS_COLLECTION_NAME
                    }
                }
                if (inIssues) {
                    // Buscar el final del paréntesis
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        // Agregar los nuevos elementos dentro del paréntesis
                        line = line.substring(0, endIndex) + " \"" + "taiga_"+subject+"_"+name+".issues" + "\"" + line.substring(endIndex);
                        inIssues = false; // Ya hemos modificado la línea de COMMITS_COLLECTION_NAME
                    }
                }
                if (inUserStories) {
                    // Buscar el final del paréntesis
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        // Agregar los nuevos elementos dentro del paréntesis
                        line = line.substring(0, endIndex) + " \"" + "taiga_"+subject+"_"+name+".userstories" + "\"" + line.substring(endIndex);
                        inUserStories = false; // Ya hemos modificado la línea de COMMITS_COLLECTION_NAME
                    }
                }
                if (inTasks) {
                    // Buscar el final del paréntesis
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        // Agregar los nuevos elementos dentro del paréntesis
                        line = line.substring(0, endIndex) + " \"" + "taiga_"+subject+"_"+name+".tasks" + "\"" + line.substring(endIndex);
                        inTasks = false; // Ya hemos modificado la línea de COMMITS_COLLECTION_NAME
                    }
                }
                // Agregar la línea al contenido del script
                scriptContent.append(line).append("\n");
            }
            reader.close();

            // Escribir el archivo modificado
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathi));
            writer.write(scriptContent.toString());
            writer.close();

            System.out.println("Archivo modificado exitosamente.");
            return true;
        } catch (IOException e) {
            System.err.println("Error al modificar el archivo: " + e.getMessage());
        }
        return true;
    }

    public Boolean configQR_connect_configM(String name, String subject, String type){
        Path path = Paths.get("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qrconnect");
        System.out.print(path);
        Path newPath = path.resolve("config/"+type+"_" + subject);
        System.out.print(newPath);
        Path file = newPath.resolve("mongo.properties");
        String pathi = file.toString();
        System.out.println(file);

        if(type == "github") {
            String newLine="github_"+subject+"_"+name+".commits, github_"+subject+"_"+name+".issues, \\";
           try(BufferedReader reader = new BufferedReader(new FileReader(pathi))) {
               StringBuilder content = new StringBuilder();
               String line;
               String lastGithubLine= null;
               while ((line = reader.readLine()) != null) {
                   if(line.startsWith("topics=") && !line.contains("github")){
                       System.out.print("line "+line);
                   }else if(line.trim().startsWith("github")){
                       lastGithubLine = line;
                       System.out.print("ultima  "+lastGithubLine);


                   }
                   //System.out.println(line);
                   if(line.startsWith("topics=")){
                       System.out.print("line "+line);
                       if(line.contains("github")){

                       }else{

                       }
                   }
           }

           } catch (FileNotFoundException e) {
               throw new RuntimeException(e);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
        }

        return true;
    }

    public Boolean configQR_connect_configGT(String name, String subject,String type) {

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

            /*try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathi))) {
                writer.write(content.toString());
                System.out.println("Nueva tarea añadida al archivo con éxito.");
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
