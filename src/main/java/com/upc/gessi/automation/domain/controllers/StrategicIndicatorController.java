package com.upc.gessi.automation.domain.controllers;

import com.google.gson.*;
import com.upc.gessi.automation.domain.models.Factor;
import okhttp3.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StrategicIndicatorController {

    public void create(String name){
        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        JsonArray resultArray = new JsonArray();
        Gson gson = new Gson();

        try {
            System.out.println("CRIDAAAAA____CREATEEEE");
            Request getRequestFactor = new Request.Builder()
                    .url(new URL("http://host.docker.internal:8888/api/qualityFactors?prj="+name))
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
                        System.out.print(element+"\n");
                        String externalid = element.get("externalId").getAsString();
                        System.out.print(externalid+"\n");
                        Integer id = element.get("id").getAsInt();
                        System.out.print(id);

                        JsonObject newObj = new JsonObject();
                        newObj.addProperty("id",id);
                        newObj.addProperty("externalId",externalid);
                        resultArray.add(newObj);
                        //metricController.setFactorMetric(externalid,factor);
                    }
                    createStrategicIndicator(name,resultArray);
                }
            } else {
                System.out.print("AAAAAAAAAAAAAAAAAA");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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
            System.out.println("CRIDAAAAA____POSTTTTTT");
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
