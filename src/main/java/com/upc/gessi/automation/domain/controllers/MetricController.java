package com.upc.gessi.automation.domain.controllers;

import com.google.gson.*;
import com.upc.gessi.automation.domain.models.Metric;
import com.upc.gessi.automation.domain.respositories.MetricRepository;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MetricController {

    @Autowired
    MetricRepository metricRepository;

    public void createMetric(Integer externalid, String name,String project){
        Metric m = new Metric(externalid,name,project);
        metricRepository.save(m);
    }

    public void setFactorMetric(String externalid,String factor){
        System.out.print("\n ENTRAAAAAAAAAAAAAAAAA \n");
        Metric met = metricRepository.findByName(externalid);
        System.out.print(met.getName());
        met.setFactor(factor);
        metricRepository.save(met);
    }

    public void addCategoryMetric(String project,Integer num_students){
        List<Metric> metrics = metricRepository.findAllByProject(project);

        for(Metric m : metrics) {
            System.out.print(m.getName());
            if (m.getName().startsWith("commits_anonymous") || m.getName().startsWith("commits_sd") || m.getName().startsWith("deviation_effort_estimation_simple") || m.getName().startsWith("tasks_sd") || m.getName().startsWith("unassignedtasks")) {
                if(!existsCategory("Reversed Default")){
                    createMetricCategory("Reversed Default");
                }
                System.out.print("entra ifff");
                putCategory(m.getExternalid(), m.getProject(), "Reversed Default");
            } else if (m.getName().startsWith("commits_") || m.getName().startsWith("modifiedlines_")) {
                if(!existsCategory("NoCategory")){
                    createMetricCategory("NoCategory");
                }
                putCategory(m.getExternalid(), m.getProject(), "NoCategory");
            } else if (m.getName().startsWith("assignedtasks_")) {
                if(!existsCategory(num_students + " members contribution")){
                    createMetricCategory(num_students + " members contribution");
                }
                putCategory(m.getExternalid(), m.getProject(), num_students + " members contribution");
            }
        }
    }

    public void putCategory(Integer id, String project,String Category){
        //System.out.print("OIOIOIOIOIO");
        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("categoryName",Category)
                .addFormDataPart("threshold","")
                .addFormDataPart("url","")
                .build();

        try{
            Request putCategory = new Request.Builder()
                .url(new URL("http://host.docker.internal:8888/api/metrics/"+id+"?prj="+project))
                .addHeader("Accept", "*/*")
                .put(requestBody)
                .build();

            Response putResponse = client.newCall(putCategory).execute();
            System.out.println(putResponse.body().string());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean existsCategory(String category){
        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();
        Boolean found = false;

        try {
            Request getRequest = new Request.Builder()
                    .url(new URL("http://host.docker.internal:8888/api/metrics/list"))
                    .build();

            Response getResponse = client.newCall(getRequest).execute();
            if (getResponse.isSuccessful() ) {
                ResponseBody data = getResponse.body();
                if (data != null) {
                    String dataString = data.string();
                    JsonArray json = JsonParser.parseString(dataString).getAsJsonArray();
                    for (JsonElement elemnt : json) {
                        if(category == elemnt.getAsString()){
                            return true;
                        }else{
                            found = false;
                        }
                    }
                }
            }
            else {
                System.out.print("AAAAAAAAAAAAAAAAAA");
            }
            return found;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMetricCategory(String category){
        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();
        RequestBody requestBody = null;
        String[] upperThreshold ={};
        String[] color ={};
        String[] type={};

        if(category == "Default"){
            upperThreshold = new String[]{"100", "67", "33"};
            color = new String[]{"#00ff00", "#ff8000", "#ff0000"};
            type= new String[]{"Good", "Neutral", "Bad"};
        }
        else if(category == "NoCategory"){
            upperThreshold = new String[]{"100", "0", "0"};
            color = new String[]{"#9eacd6", "#ff8000", "#ff0000"};
            type= new String[]{"Neutral", "Neutral", "Bad"};
        }
        else if(category.contains("members contribution")){
            if(category.contains("4")){
                upperThreshold = new String[]{"100", "40", "30","20","10"};
            } else if (category.contains("5")) {
                upperThreshold = new String[]{"100", "35", "25","15","5"};
            } else if (category.contains("3")) {
                upperThreshold = new String[]{"100", "48", "38","28","10"};
            } else if (category.contains("6")) {
                upperThreshold = new String[]{"100", "30", "20","10","5"};
            } else if (category.contains("7")) {
                upperThreshold = new String[]{"100", "32", "22","12","7"};
            } else if (category.contains("8")) {
                upperThreshold = new String[]{"100", "30", "20","10","6"};
            }
            color = new String[]{"#ff0000", "#ff8000", "#00ff00","#ff8000", "#ff0000"};
            type= new String[]{"High", "Up", "Well","Down","Low"};
        }
        List<JsonObject> jsonArray = new ArrayList<>();

        for(int i = 0; i<upperThreshold.length; i++){
            JsonObject obj = new JsonObject();
            obj.addProperty("upperThreshold",upperThreshold[i]);
            obj.addProperty("color",color[i]);
            obj.addProperty("type",type[i]);
            jsonArray.add(obj);
        }

        String requestBodyJson = gson.toJson(jsonArray);
        requestBody = RequestBody.create(MediaType.parse("application/json"),requestBodyJson);
        try{
            Request putCategory = new Request.Builder()
                    .url(new URL("http://host.docker.internal:8888/api/metrics/categories?name="+category))
                    //.addHeader("Accept","application/json")
                    .addHeader("Accept", "*/*")
                    .put(requestBody)
                    .build();

            Response putResponse = client.newCall(putCategory).execute();
            System.out.println(putResponse.body().string());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Integer> getMetricsFactor(String project, String factor){
        List<Integer> ids = new ArrayList<>();
        List<Metric> metrics = metricRepository.findAllByProject(project);
        for(Metric m : metrics){
            System.out.print("ENTRAAAAAAAA");
            System.out.print(m.getFactor());
            System.out.print(" "+factor);
            if(m.getFactor().equals(factor)){
                System.out.print("sasasasasasa\n");
                ids.add(m.getExternalid());
                ids.add(-1);
            }
        }
        return ids;
    }
}
