package com.upc.gessi.automation.domain.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.upc.gessi.automation.domain.models.Factor;
import okhttp3.*;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StrategicIndicatorController {

    public void createStrategicIndicator(String project, JsonArray factors){

        List<Integer> ids1 = new ArrayList<>();
        List<Integer> ids2 = new ArrayList<>();
        List<Integer> ids3 = new ArrayList<>();
        for(JsonElement element: factors){
            JsonObject obj =element.getAsJsonObject();
            int i = obj.get("id").getAsInt();
            String external = obj.get("externalId").getAsString();
            if(external.equals("fulfillmentoftasks") || external.equals("taskscontribution") || external.equals("unassignedtasks")){
                ids1.add(i);
                ids1.add(-1);
            }
            else if(external.equals("commitstasksrelation") || external.equals("taskseffortinformation") || external.equals("userstoriesdefinitionquality")){
                ids2.add(i);
                ids2.add(-1);
            }
            else if(external.startsWith("commits") || external.equals("modifiedlinescontribution")){
                ids3.add(i);
                ids3.add(-1);
            }
        }
        String name = "Backlog Management";
        postStrategicIndicator(name,ids1,project);
        name = "Information Completeness";
        postStrategicIndicator(name,ids2,project);
        name= "Repository Contribution";
        postStrategicIndicator(name,ids3,project);

    }

    public void postStrategicIndicator(String name, List<Integer> ids,String project){

        String idString = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        System.out.print("\n "+idString+"\n");
        System.out.print(ids+"\n");

        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //.addFormDataPart("prj",project)
                .addFormDataPart("name",name)
                .addFormDataPart("description","")
                .addFormDataPart("threshold","")
                .addFormDataPart("quality_factors", idString)
                .build();

        try{
            Request postCategory = new Request.Builder()
                    .url(new URL("http://host.docker.internal:8888/api/strategicIndicators?prj="+project))
                    .addHeader("Accept", "*/*")
                    .post(requestBody)
                    .build();

            Response postResponse = client.newCall(postCategory).execute();
            System.out.println(postResponse.body().string());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
