package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.MetricController;
import okhttp3.*;
import com.google.gson.*;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;

@RestController
@RequestMapping("/metric")
public class MetricRestController {

    @Autowired
    MetricController metricController;

    @GetMapping(value="/categ")
    public void putCateg(){
        metricController.addCategoryMetric("bravo11",4);
    }

    @GetMapping(value="/CA")
    public void adCat(){
        metricController.createMetricCategory("Default");
    }

    @GetMapping
    public String getMetrics(@RequestParam(name = "name") String name) throws IOException, InterruptedException, URISyntaxException {
        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

    try {
        /*Request getRequest = new Request.Builder()
                .url(new URL("http://host.docker.internal:8888/api/metrics?prj="+name))
                .build();

        Response getResponse = client.newCall(getRequest).execute();
        //HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        if (getResponse.isSuccessful() ) {
            System.out.print("AAAAAAAAAA");
            ResponseBody data = getResponse.body();
            System.out.print(data);
            if(data !=null) {
                String dataString = data.string();
                System.out.println(dataString);
                JsonArray json = JsonParser.parseString(dataString).getAsJsonArray();
                for(int i= 0;i<json.size(); i++){
                    JsonObject element = json.get(i).getAsJsonObject();
                    Integer externalid = element.get("id").getAsInt();
                    System.out.print(externalid);
                    String metric_name = element.get("externalId").getAsString();
                    System.out.print(metric_name);
                    //String factor = element.get("categoryName").getAsString();
                    //System.out.print(factor);

                    JsonObject object = element.getAsJsonObject("project");

                    String project = object.get("name").getAsString();
                    metricController.createMetric(externalid,metric_name,project);
                }
            }
        } else {
            System.out.print("AAAAAAAAAAAAAAAAAA");
        }*/
        Request getRequestFactor = new Request.Builder()
                .url(new URL("http://host.docker.internal:8888/api/metrics/current?prj="+name))
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
                    String externalid = element.get("id").getAsString();
                    System.out.print(externalid+"\n");
                    JsonArray qualityFactorsArray = element.getAsJsonArray("qualityFactors");
                    System.out.print("qualityFactorsArray  : ");
                    String factor = qualityFactorsArray.get(0).getAsString();
                    System.out.print(factor);
                    System.out.print("\n"+ externalid+"  "+factor+"\n");
                    metricController.setFactorMetric(externalid,factor);
                }
            }
        } else {
            System.out.print("AAAAAAAAAAAAAAAAAA");
        }
    }catch (IOException e){
        e.printStackTrace();
    }
        return "patata";
    };
}
