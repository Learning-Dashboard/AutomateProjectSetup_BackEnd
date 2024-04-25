package com.upc.gessi.automation.domain.controllers;

import com.upc.gessi.automation.domain.models.Project;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ConfigurationController {

    @Autowired
    ProjectController projectController;

    @Autowired
    StudentController studentController;

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

    public Boolean configQR_connect_script(String name,String subject,String type){
        Path path = Paths.get("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qrconnect");
        System.out.print(path);
        Path newPath = path.resolve("scripts/run."+type+"_"+subject+".sh");
        String pathi = newPath.toString();
        System.out.println(pathi);

        try {
            // Leer el archivo de script Bash
            BufferedReader reader = new BufferedReader(new FileReader(pathi));
            StringBuilder scriptContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("COMMITS_COLLECTION_NAME=(")) {
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) + " \"" + type+"_"+subject+"_"+name+".commits" + "\"" + line.substring(endIndex);
                    }
                }
                if(line.contains("ISSUES_COLLECTION_NAME=(")){
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) + " \"" + type+"_"+subject+"_"+name+".issues" + "\"" + line.substring(endIndex);
                    }
                }
                if (line.contains("EPICS_COLLECTION_NAME=(")) {
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) + " \"" + type+"_"+subject+"_"+name+".epics" + "\"" + line.substring(endIndex);
                    }
                }
                if(line.contains("USERSTORIES_COLLECTION_NAME=(")){
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) + " \"" + type+"_"+subject+"_"+name+".userstories" + "\"" + line.substring(endIndex);
                    }
                }
                if(line.contains("TASKS_COLLECTION_NAME=(")){
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) + " \"" + type+"_"+subject+"_"+name+".tasks" + "\"" + line.substring(endIndex);
                    }
                }
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
        Path filea = newPath.resolve("mongo.properties");
        String pathi = filea.toString();
        System.out.println(filea);

        StringBuilder contentAdd = new StringBuilder();
        String newLine = null;
        if(type == "github") {
            newLine = "github_" + subject + "_" + name + ".commits, github_" + subject + "_" + name + ".issues \n";
        }
        else if(type == "taiga") {
            newLine = "taiga_" + subject + "_" + name + ".issues, taiga_" + subject + "_" + name + ".epics, \\ \n" + "taiga_" + subject + "_" + name + ".userstories, taiga_" + subject + "_" + name + ".tasks \n";
        }
        try {
            File file = new File(pathi);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            String lastGithubLine = null;
            StringBuilder modifiedContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                // Agrega la línea al contenido modificado
                modifiedContent.append(line).append("\n");

                // Si la línea comienza con "github", actualiza lastGithubLine
                if (line.trim().startsWith(type)) {
                    lastGithubLine = line;
                }
            }

            reader.close();

            // Si encontramos al menos una línea que comienza con "github"
            if (lastGithubLine != null) {
                // Genera la nueva línea que quieres agregar
                // Encuentra la posición de la última línea de "github" en el contenido modificado

                // Encuentra la posición de la última línea de "github" en el contenido modificado
                int index = modifiedContent.lastIndexOf(lastGithubLine);
                // Inserta la nueva línea después de la última línea de "github"

                modifiedContent.insert(index + lastGithubLine.length() , ", \\");

                modifiedContent.insert(index + lastGithubLine.length() + 4, newLine);
            }

            // Imprime el contenido modificado
            System.out.println(modifiedContent.toString());

            // Si deseas guardar los cambios en el archivo, puedes hacerlo aquí
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(modifiedContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        String id;
        String newTask = null;
        if(type == "github"){
            id=projectController.getIdGithub(name,subject);
            System.out.print("   "+ id+ "\n");

            newTask = "tasks."+num_task+".github.url="+id+"\n" +
                    "tasks."+num_task+".github.commit.topic=github_"+subject+"_"+name+".commits\n" +
                    "tasks."+num_task+".github.issue.topic=github_"+subject+"_"+name+".issues\n" +
                    "tasks."+num_task+".taiga.task.topic=taiga_"+subject+"_"+name+".tasks\n";
        }
        else if(type == "taiga"){

            id = projectController.getIdTaiga(name,subject);
            System.out.print("   "+ id+ "\n");
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
            content.append(newTask).append("\n");
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
    public void configQR_eval_script(String name, String subject){
        Path path = Paths.get("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qreval");
        System.out.print(path);
        Path newPath = path.resolve("scripts/run_eval_periodic.sh");
        String pathi = newPath.toString();
        System.out.println(pathi);

        try {
            // Leer el archivo de script Bash
            BufferedReader reader = new BufferedReader(new FileReader(pathi));
            StringBuilder scriptContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("FACTORS_COLLECTION_NAME=(")) {
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) +", "+ " \"" + "factors."+name + "\"" + line.substring(endIndex);
                    }
                }
                if(line.contains("STRATEGIC_INDICATORS_COLLECTION_NAME=(")){
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) +", "+ " \"" + "strategic_indicators."+name + "\"" + line.substring(endIndex);
                    }
                }
                if (line.contains("RELATIONS_COLLECTION_NAME=(")) {
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) +", "+ " \"" + "relations."+name+".epics" + "\"" + line.substring(endIndex);
                    }
                }

                scriptContent.append(line).append("\n");
            }
            reader.close();

            // Escribir el archivo modificado
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathi));
            writer.write(scriptContent.toString());
            writer.close();

            System.out.println("Archivo modificado exitosamente.");

        } catch (IOException e) {
            System.err.println("Error al modificar el archivo: " + e.getMessage());
        }

    }

    public Boolean getEvalProjects(String name, String subject){
        Path path = Paths.get("LD-queryGenerator","resources");
        Path newPath = path.resolve("names.txt");
        System.out.print(newPath);
        String pathi = newPath.toString();
        //System.out.println(pathi);

        Integer id_proj = projectController.getId(name,subject);
        ArrayList<String> github_usernames = studentController.getStudentsGithubProject(id_proj);
        ArrayList<String> taiga_usernames = studentController.getStudentsTaigaProject(id_proj);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(pathi, false))) {
            writer.write(name+ "\n");
            for(int i = 0; i < github_usernames.size(); i++){
                writer.write(github_usernames.get(i));
                if(i < github_usernames.size() -1){
                    writer.write(" ");
                }
            }
            writer.write("\n");
            for(int i = 0; i < taiga_usernames.size(); i++){
                writer.write(taiga_usernames.get(i));
                if(i < taiga_usernames.size() -1){
                    writer.write(" ");
                }
            }
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void createFolderProject(String name, String subject) throws IOException, InterruptedException {

        String directorioActual = System.getProperty("user.dir");

        // Ruta del comando Python
        String comandoPython = "java";

        // Ruta relativa del script Python
        String rutaRelativaScript = "\\LD-queryGenerator\\main.java";

        // Convertir la ruta relativa a una ruta absoluta
        File archivoScript = new File(directorioActual, rutaRelativaScript);
        String rutaAbsolutaScript = archivoScript.getAbsolutePath();

        // Combinar el comando y la ruta del script
        String comandoCompleto = comandoPython + " " + rutaAbsolutaScript;

        // Ejecutar el comando
        Process proceso = Runtime.getRuntime().exec(comandoCompleto);
        System.out.print(proceso);
        System.out.print("CACATUA \n");

        // Leer la salida del proceso
        BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }

        // Esperar a que el proceso termine
        int resultado = proceso.waitFor();
        System.out.print(proceso.getErrorStream());
        System.out.println("El proceso terminó con código de salida: " + resultado);

        String dir_home = System.getProperty("user.dir");
        System.out.print(dir_home);

        String dir_project ="\\LD-queryGenerator\\result\\"+name;

        System.out.print(dir_project);

        modifiedProjectProperties(name,subject);

        File dir_act = new File(dir_home+dir_project);
        File dir_new = new File("C:/Users/norac/Desktop/aqui/workshop/Learning Dashboard/docker/node-qreval/projects/"+subject+"_"+name);

        boolean exito = dir_act.renameTo(dir_new);
        System.out.print(exito);

    }

    public void modifiedProjectProperties(String name, String subject) throws IOException {
            System.out.print("ENTRAAA PROCESSQUERY \n");
            String dir_home = System.getProperty("user.dir");
            String directoryName =dir_home+"\\LD-queryGenerator\\result\\"+name;
            String fileName = "project.properties";
            System.out.print(directoryName);
            System.out.print(fileName);

            File queryFile = new File(directoryName+"\\" + fileName);
            BufferedReader reader = new BufferedReader(new FileReader(queryFile));
            StringBuilder queryContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                queryContent.append(line).append(System.lineSeparator());
            }
            reader.close();
            String updatedQuery = queryContent.toString().replace("[PROJECT_NAME]", name);
            updatedQuery = updatedQuery.replace("[PROJECT]", name);
            updatedQuery= updatedQuery.replace("[SUBJECT]", subject);
            System.out.print(updatedQuery);
            BufferedWriter writer = new BufferedWriter(new FileWriter(queryFile));
            writer.write(updatedQuery);
            writer.close();
    }
}
