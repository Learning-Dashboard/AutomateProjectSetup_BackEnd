package com.upc.gessi.automation.domain.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.upc.gessi.automation.domain.models.Factor;
import com.upc.gessi.automation.domain.models.Iteration;
import com.upc.gessi.automation.domain.respositories.IterationRepository;
import com.upc.gessi.automation.rest.DTO.IterationDTO;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IterationController {

    @Autowired
    IterationRepository iterationRepository;

    public Integer getExternalId(String name){
        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        try {

            Request getRequestFactor = new Request.Builder()
                    .url(new URL("http://host.docker.internal:8888/api/iterations"))
                    .build();

            Response getResponseFact = client.newCall(getRequestFactor).execute();
            //HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            if (getResponseFact.isSuccessful() ) {
                System.out.print("AAAAAAAAAA");
                ResponseBody data = getResponseFact.body();
                System.out.print(data);
                if(data !=null) {
                    String dataString = data.string();
                    System.out.println(dataString);
                    JsonArray json = JsonParser.parseString(dataString).getAsJsonArray();
                    for(int i= 0;i<json.size(); i++){
                        JsonObject element = json.get(i).getAsJsonObject();
                        if(element.get("name").getAsString().equals(name)){
                            System.out.print(element.get("id").getAsInt());
                            return element.get("id").getAsInt();
                        }
                    }
                }
            } else {
                System.out.print("AAAAAAAAAAAAAAAAAA");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    public void addProject(String name, Integer id_project){
        Integer id_iteration = getExternalId(name);
        if(!id_iteration.equals(-1)) {
            Iteration i = iterationRepository.findByName(name);
            OkHttpClient client = new OkHttpClient();
            HttpClient httpClient = HttpClient.newHttpClient();
            Gson gson = new Gson();

            String[] projectsID = i.getProjects().split(",");


            JsonObject rootObject = new JsonObject();
            JsonArray projects = new JsonArray();

            for (String id : projectsID) {
                System.out.print(id);
                projects.add(id.trim());
            }
            projects.add(id_project.toString());
            rootObject.add("project_ids", projects);

            JsonObject iterationInfo = new JsonObject();
            iterationInfo.addProperty("name", name);
            iterationInfo.addProperty("label", i.getSubject());
            iterationInfo.addProperty("fromDate", i.getFromData());
            iterationInfo.addProperty("toDate", i.getToData());
            rootObject.add("iteration", iterationInfo);

            String body = gson.toJson(rootObject);
            System.out.print(body);
            RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json"));

            try {
                Request postCategory = new Request.Builder()
                        .url(new URL("http://host.docker.internal:8888/api/iterations/"+ id_iteration))
                        .addHeader("Accept", "*/*")
                        .put(requestBody)
                        .build();

                Response postResponse = client.newCall(postCategory).execute();
                System.out.println(postResponse.body().string());

                if(postResponse.isSuccessful()){
                    String old = i.getProjects();
                    old += "," + id_project;
                    i.setProjectIds(old);
                    iterationRepository.save(i);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addIteration(IterationDTO i){
        System.out.print("aaaaaa");
        String name = i.getName();
        System.out.print(name);
        String subject = i.getSubject();
        System.out.print(subject);
        String from = i.getFromData();
        System.out.print(from);
        String to = i.getToData();
        System.out.print(to);

        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        String[] projectsID = i.getProjects().split(",");



        JsonObject rootObject = new JsonObject();
        JsonArray projects = new JsonArray();

        for(String id :projectsID){
            System.out.print(id);
            projects.add(id.trim());
        }
        rootObject.add("project_ids",projects);

        JsonObject iterationInfo = new JsonObject();
        iterationInfo.addProperty("name",name);
        iterationInfo.addProperty("label",subject);
        iterationInfo.addProperty("fromDate",from);
        iterationInfo.addProperty("toDate",to);
        rootObject.add("iteration",iterationInfo);

        String body = gson.toJson(rootObject);
        System.out.print(body);
        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json"));

        try{
            Request postCategory = new Request.Builder()
                    .url(new URL("http://host.docker.internal:8888/api/iterations"))
                    .addHeader("Accept", "*/*")
                    .post(requestBody)
                    .build();

            Response postResponse = client.newCall(postCategory).execute();
            System.out.println(postResponse.body().string());

            if(postResponse.isSuccessful()){
                Iteration newIteration = new Iteration(i.getName(),i.getSubject(),i.getFromData(),i.getToData(),i.getProjects());
                iterationRepository.save(newIteration);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
