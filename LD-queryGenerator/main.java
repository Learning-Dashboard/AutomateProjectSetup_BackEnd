import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        String RESULT_DIR = "LD-queryGenerator/result";
        try {
            // Crear directorio de resultados si no existe
            File resultDir = new File(RESULT_DIR);
            if (resultDir.exists()) {
                deleteDirectory(resultDir);
            }
            resultDir.mkdirs();

            // Leer el archivo names.txt
            File namesFile = new File("LD-queryGenerator/resources/names.txt");
            File currentDirectory = new File(".");
            String absolutePath = currentDirectory.getAbsolutePath();
            if (!namesFile.exists()) {

                System.out.println("A"+absolutePath);
                System.err.println("El archivo names.txt no existe.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(namesFile));
            String groupName;
            while ((groupName = reader.readLine()) != null) {
                // Leer los nombres de usuario de GitHub
                String[] gitHubUsersArray = reader.readLine().split(" ");
                List<String> gitHubUsers = Arrays.asList(gitHubUsersArray);
                String[] gitHubUsersArraya = gitHubUsers.toArray(new String[0]);

                // Leer los nombres de usuario de Taiga
                String[] taigaUsers = reader.readLine().split(" ");
                List<String> taigaUsersList = Arrays.asList(taigaUsers);

                // Crear directorio para el grupo
                File groupDir = new File(resultDir, groupName);
                groupDir.mkdirs();

                // Copiar plantilla al directorio del grupo
                copyDirectory(new File("LD-queryGenerator/resources/template"), groupDir);

                // Procesar consultas y métricas SD
                processQuery("commits", groupDir.getAbsolutePath(), gitHubUsers);
                processQuery("modifiedlines", groupDir.getAbsolutePath(), gitHubUsers);
                processQuery("assignedtasks", groupDir.getAbsolutePath(), taigaUsersList);
                processQuery("closedtasks", groupDir.getAbsolutePath(), taigaUsersList);

                System.out.print("PASSAAAAAA 11111");

                noParamsProcessing("unassignedtasks", groupDir.getAbsolutePath());
                noParamsProcessing("acceptance_criteria_check", groupDir.getAbsolutePath());
                noParamsProcessing("pattern_check", groupDir.getAbsolutePath());
                noParamsProcessing("tasks_with_EE", groupDir.getAbsolutePath());
                noParamsProcessing("closed_tasks_with_AE", groupDir.getAbsolutePath());
                noParamsProcessing("deviation_effort_estimation_simple", groupDir.getAbsolutePath());
                noParamsProcessing("commits_taskreference", groupDir.getAbsolutePath());
                noParamsProcessing("commits_anonymous", groupDir.getAbsolutePath());

                System.out.print("PASAAAAA 2222");

                SDProcessing("tasks_sd", "tasks", groupDir.getAbsolutePath(), taigaUsersList);
                SDProcessing("commits_sd", "commits", groupDir.getAbsolutePath(), gitHubUsers);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    private static void copyDirectory(File sourceDir, File destDir) throws IOException {
        System.out.print("ENTRAAA COPYDIRECTORY \n");
        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File file : files) {
                File destFile = new File(destDir, file.getName());
                if (file.isDirectory()) {
                    destFile.mkdirs();
                    copyDirectory(file, destFile);
                } else {
                    FileWriter writer = new FileWriter(destFile);
                    FileReader reader = new FileReader(file);
                    int character;
                    while ((character = reader.read()) != -1) {
                        writer.write(character);
                    }
                    reader.close();
                    writer.close();
                }
            }
        }
    }
    public static void processQuery(String fileName, String directoryName, List<String> repoUsers) throws IOException {
        System.out.print("ENTRAAA PROCESSQUERY \n");
        for (String user : repoUsers) {
            String templatePath = directoryName + "/metrics/" + fileName + "_";
            String path = directoryName + "/metrics/" + fileName + "_" + user.replace('.', '_').replace('-', '_');

            Files.copy(Paths.get(templatePath + "template.properties"), Paths.get(path + ".properties"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(templatePath + "template.query"), Paths.get(path + ".query"), StandardCopyOption.REPLACE_EXISTING);

            // Update .query file
            File queryFile = new File(path + ".query");
            BufferedReader reader = new BufferedReader(new FileReader(queryFile));
            StringBuilder queryContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                queryContent.append(line).append(System.lineSeparator());
            }
            reader.close();
            String updatedQuery = queryContent.toString().replace("[USERNAME]", user);
            BufferedWriter writer = new BufferedWriter(new FileWriter(queryFile));
            writer.write(updatedQuery);
            writer.close();

            // Update .properties file
            File propertiesFile = new File(path + ".properties");
            BufferedReader propReader = new BufferedReader(new FileReader(propertiesFile));
            StringBuilder propContent = new StringBuilder();
            String propLine;
            while ((propLine = propReader.readLine()) != null) {
                propContent.append(propLine).append(System.lineSeparator());
            }
            propReader.close();
            String updatedProperties = propContent.toString().replace("[USERNAME]", user);
            BufferedWriter propWriter = new BufferedWriter(new FileWriter(propertiesFile));
            propWriter.write(updatedProperties);
            propWriter.close();
        }

        // Remove template files
        Files.deleteIfExists(Paths.get(directoryName + "/metrics/" + fileName + "_template.query"));
        Files.deleteIfExists(Paths.get(directoryName + "/metrics/" + fileName + "_template.properties"));
    }

    /*private static void processQuery(String fileName, String directoryName, String[] users) {
        // Implementa el procesamiento de consultas aquí
        System.out.println("Procesando consultas para " + fileName + " en el directorio " + directoryName + " para usuarios:");
        for (String user : users) {
            System.out.println(user);
        }
    }*/

    /*private static void noParamsProcessing(String fileName, String directoryName) {
        // Implementa el procesamiento sin parámetros aquí
        System.out.println("Procesando " + fileName + " sin parámetros en el directorio " + directoryName);
    }*/
    public static void noParamsProcessing(String fileName, String directoryName) throws IOException {
        System.out.print("ENTRAAA noParamsProcessing \n");
        String path = directoryName + "/metrics/" + fileName;
        System.out.print(path);

        // Copiar el archivo _template.properties
        Files.copy(Path.of(path + "_template.properties"), Path.of(path + ".properties"), StandardCopyOption.REPLACE_EXISTING);

        // Copiar el archivo _template.query
        Files.copy(Path.of(path + "_template.query"), Path.of(path + ".query"), StandardCopyOption.REPLACE_EXISTING);

        // Eliminar los archivos _template.query y _template.properties
        Files.deleteIfExists(Path.of(directoryName + "/metrics/" + fileName + "_template.query"));
        Files.deleteIfExists(Path.of(directoryName + "/metrics/" + fileName + "_template.properties"));
    }

    private static void SDProcessing(String filename, String prefix, String directoryName, List<String> repoUsers) throws IOException {
        System.out.print("ENTRAAA SDProcessing \n");
        String path = directoryName + "/metrics/" + filename;
        System.out.print(path);
        System.out.print("ENTRAAA 1 \n");
        // Cambiar los puntos y guiones por guiones bajos en los nombres de usuario
        List<String> mongodbUsers = new ArrayList<>();
        System.out.print("ENTRAAA 2 \n");
        for (String user : repoUsers) {
            System.out.print("ENTRAAA 3 \n");
            mongodbUsers.add(user.replace('.', '_').replace('-', '_'));
        }
        System.out.print("ENTRAAA 4 \n");

        // Copiar los archivos de plantilla
        Files.copy(Path.of(path + "_template.properties"), Path.of(path + ".properties"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Path.of(path + "_template.query"), Path.of(path + ".query"), StandardCopyOption.REPLACE_EXISTING);

        System.out.print("ENTRAAA 5 \n");

        // Procesar el archivo .query
        BufferedReader reader = new BufferedReader(new FileReader(path + ".query"));
        StringBuilder fileData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            fileData.append(line).append(System.lineSeparator());
        }
        reader.close();

        String fileContent = fileData.toString();
        int groupBegin = fileContent.indexOf("[GROUP_BEGIN]");
        int groupEnd = fileContent.indexOf("[GROUP_END]");
        String aggTemplate = fileContent.substring(groupBegin + "[GROUP_BEGIN]".length(), groupEnd);
        String groupFileData = "";
        String fileDataEnd = fileContent.substring(groupEnd + "[GROUP_END]\n".length());

        for (String user : repoUsers) {
            String aux = aggTemplate.substring(7);
            aux = aux.replace("[USERNAME]", user);
            aux = aux.replace("[MONGODB_USERNAME]", user.replace('.', '_').replace('-', '_'));
            groupFileData = groupFileData + aux;
        }

        groupFileData = groupFileData.substring(0, groupFileData.length() - 8) + "\n";
        fileContent = fileContent.substring(0, groupBegin) + groupFileData + fileDataEnd;



        int projectBegin = fileContent.indexOf("[PROJECT_BEGIN]");
        int projectEnd = fileContent.indexOf("[PROJECT_END]");
        String projectTemplate = fileContent.substring(projectBegin + "[PROJECT_BEGIN]".length(), projectEnd);
        String projectFileData = "";

        for (String user : repoUsers) {
            String aux = projectTemplate.substring(7);
            aux = aux.replace("[MONGODB_USERNAME]", user.replace('.', '_').replace('-', '_'));
            projectFileData = projectFileData + aux;
        }

        projectFileData = projectFileData.substring(0, projectFileData.length() - 8) + "\n    }\n  }\n]";
        fileContent = fileContent.substring(0, projectBegin) + projectFileData;

        System.out.print(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + ".query".replace("/", File.separator)));
        writer.write(fileContent);
        writer.close();

        // Procesar el archivo .properties
        BufferedReader propReader = new BufferedReader(new FileReader(path + ".properties"));
        StringBuilder propFileData = new StringBuilder();
        String propLine;
        while ((propLine = propReader.readLine()) != null) {
            propFileData.append(propLine).append(System.lineSeparator());
        }
        propReader.close();

        String propertiesContent = propFileData.toString();
        System.out.print(propertiesContent);
        int beginResults = propertiesContent.indexOf("[BEGIN_RESULTS]");
        System.out.print("beeeeeeeeee"+beginResults+"\n");
        int endResults = propertiesContent.indexOf("\n[END_RESULTS]");
        System.out.print("endddddddddd"+endResults+"\n");
        String template = propertiesContent.substring(beginResults + "[BEGIN_RESULTS]\n".length(), endResults) + "\n";

        for (String user : mongodbUsers) {
            String aux = template.replace("[MONGODB_USERNAME]", user);
            propertiesContent = propertiesContent.substring(0, beginResults) + aux + propertiesContent.substring(endResults);
            beginResults += aux.length();
            endResults = propertiesContent.indexOf("\n[END_RESULTS]");
        }

        propertiesContent = propertiesContent.replace("\n\n[END_RESULTS]", "");

        // Calcular la métrica SD
        String sdMetric = "sqrt ( ( ";
        String avg = "( ( ";
        for (String user : mongodbUsers) {
            avg = avg + prefix + user + " / " + prefix + "Total";
            if (!user.equals(mongodbUsers.get(mongodbUsers.size() - 1))) avg = avg + " + ";
        }
        avg = avg + " ) / " + mongodbUsers.size() + " )";

        for (String user : mongodbUsers) {
            String aux = "( ( " + prefix + user + " / " + prefix + "Total )";
            sdMetric = sdMetric + "( " + aux + " - " + avg + " ) ^ 2 ) ";
            if (!user.equals(mongodbUsers.get(mongodbUsers.size() - 1))) sdMetric = sdMetric + "+ ";
        }

        sdMetric = sdMetric + ") / " + mongodbUsers.size() + " )";

        propertiesContent = propertiesContent.replace("[SD_METRIC]", sdMetric);

        BufferedWriter propWriter = new BufferedWriter(new FileWriter(path + ".properties"));
        propWriter.write(propertiesContent);
        propWriter.close();

        // Eliminar los archivos de plantilla
        Files.deleteIfExists(Path.of(directoryName + "/metrics/" + filename + "_template.query"));
        Files.deleteIfExists(Path.of(directoryName + "/metrics/" + filename + "_template.properties"));
        // Implementa el procesamiento de SD aquí
        /*System.out.println("Procesando métricas SD para " + filename + " con prefijo " + prefix + " en el directorio " + directoryName + " para usuarios:");
        for (String user : users) {
            System.out.println(user);
        }*/
    }
}
