package com.upc.gessi.automation.domain.controllers.config;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Controller
public class QrEvalController {
    public void configure_eval_script(String name, String subject){

        String path = "home/qreval/run/scripts/run_eval_periodic.sh";
        try {
            // Leer el archivo de script Bash
            BufferedReader reader = new BufferedReader(new FileReader(path));
            StringBuilder scriptContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("FACTORS_COLLECTION_NAME=(") && !line.contains(subject+"_"+name)) {
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) +" , "+ " \"" + "factors."+name + "\"" + line.substring(endIndex);
                    }
                }
                if(line.contains("STRATEGIC_INDICATORS_COLLECTION_NAME=(") && !line.contains(subject+"_"+name)){
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) +" , "+ " \"" + "strategic_indicators."+name + "\"" + line.substring(endIndex);
                    }
                }
                if (line.contains("RELATIONS_COLLECTION_NAME=(") && !line.contains(subject+"_"+name)) {
                    int endIndex = line.indexOf(')');
                    if (endIndex != -1) {
                        line = line.substring(0, endIndex) +" , "+ " \"" + "relations."+name+ "\"" + line.substring(endIndex);
                    }
                }

                scriptContent.append(line).append("\n");
            }
            reader.close();

            // Escribir el archivo modificado
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(scriptContent.toString());
            writer.close();

            System.out.println("Archivo modificado exitosamente.");

        } catch (IOException e) {
            System.err.println("Error al modificar el archivo: " + e.getMessage());
        }

    }

    public Boolean getEvalProjects(String name, String subject,Integer id_proj, ArrayList<String> github_usernames,ArrayList<String> taiga_usernames){
        String path = "/home/run/generator/resources/names.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
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
        String path = "/home/run/generator/main.java";

        /*String directorioActual = System.getProperty("user.dir");*/

        // Ruta del comando Python
        String comandoPython = "java";

        // Ruta relativa del script Python
        //String rutaRelativaScript = "\\LD-queryGenerator\\main.java";

        // Convertir la ruta relativa a una ruta absoluta
        // File archivoScript = new File(path);
        //String rutaAbsolutaScript = archivoScript.getAbsolutePath();

        // Combinar el comando y la ruta del script
        String comandoCompleto = comandoPython + " "+path ;

        System.out.print("comand "+comandoCompleto+"\n");

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

        BufferedReader error = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
        String lineaer;
        while ((lineaer = error.readLine()) != null) {
            System.out.println(lineaer);
        }

        // Esperar a que el proceso termine
        int resultado = proceso.waitFor();
        System.out.print(proceso.getErrorStream());
        System.out.println("El proceso terminó con código de salida: " + resultado);


        String path_old_dir = "/home/run/generator/result/"+name;
        Path source = Paths.get(path_old_dir);
        System.out.print(path_old_dir);

        modifiedProjectProperties(name,subject);

        File dir_act = new File(path_old_dir);
        System.out.print("telelelelelelele "+dir_act+"\n");
        String path_new = "/home/qreval/run/projects/"+subject+"_"+name;
        File destination = new File(path_new);
        if (destination.exists() && destination.isDirectory()) {
            System.out.println("El directorio de destino ya existe.");
        }
        else {
            FileUtils.moveDirectory(dir_act, destination);
        }

        //.moveDirectory(dir_act,destination);
    }

    public void modifiedProjectProperties(String name, String subject) throws IOException {
        String path = "/home/run/generator/result/"+name+"/project.properties";

        File queryFile = new File(path);
        System.out.print("\nfsdjgfksgfkjdhsjk "+queryFile+"\n");
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
