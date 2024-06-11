package com.upc.gessi.automation.rest.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.upc.gessi.automation.domain.controllers.StrategicIndicatorController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.List;

@RestController
@RequestMapping(value="/strategic")
public class StrategicIndicatorRestController {

    @Autowired
    StrategicIndicatorController strategicIndicatorController;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody List<String> projects){
        for(String name : projects) {
            OkHttpClient client = new OkHttpClient();
            HttpClient httpClient = HttpClient.newHttpClient();
            JsonArray resultArray = new JsonArray();
            Gson gson = new Gson();

            try {

                Request getRequestFactor = new Request.Builder()
                        .url(new URL("http://host.docker.internal:8888/api/qualityFactors?prj=" + name))
                        .build();

                Response getResponseFact = client.newCall(getRequestFactor).execute();
                //HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                if (getResponseFact.isSuccessful()) {
                    System.out.print("AAAAAAAAAA");
                    ResponseBody data = getResponseFact.body();
                    System.out.print(data);
                    if (data != null) {
                        String dataString = data.string();
                        System.out.println(dataString);
                        JsonArray json = JsonParser.parseString(dataString).getAsJsonArray();
                        for (int i = 0; i < json.size(); i++) {
                            JsonObject element = json.get(i).getAsJsonObject();
                            System.out.print(element + "\n");
                            String externalid = element.get("externalId").getAsString();
                            System.out.print(externalid + "\n");
                            Integer id = element.get("id").getAsInt();
                            System.out.print(id);

                            JsonObject newObj = new JsonObject();
                            newObj.addProperty("id", id);
                            newObj.addProperty("externalId", externalid);
                            resultArray.add(newObj);
                            //metricController.setFactorMetric(externalid,factor);
                        }
                        strategicIndicatorController.createStrategicIndicator(name, resultArray);
                    }
                } else {
                    System.out.print("AAAAAAAAAAAAAAAAAA");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
