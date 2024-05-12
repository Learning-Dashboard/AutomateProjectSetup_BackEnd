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
        System.out.print("OIOIOIOIOIO");
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
            //.addHeader("Accept","application/json")
                .addHeader("Accept", "*/*")
            //.addHeader("Accept-Encoding", "gzip, deflate, br, zstd")
            //.addHeader("Accept-Language", "es,es-ES;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6,ca;q=0.5")
            //.addHeader("Connection", "keep-alive")
            //.addHeader("Cookie", "xFOEto4jYAjdMeR3Pas6_=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTcxNTU4MjIxMH0.zdylHK0yVwYosQuXpXUmK1_Fga6fZ57ICS8EE7waVABtMUI60kN3kfupaJCZiG8YNi16d2FKiP28IqIlijmkug; JSESSIONID=C4B8EB45E71885F0588F2451DA668162")
            //.addHeader("Host", "localhost:8888")
            //.addHeader("Origin", "http://localhost:8888")
            //.addHeader("Referer", "http://localhost:8888/Metrics/Configuration")
            //.addHeader("Sec-Fetch-Dest", "empty")
            //.addHeader("Sec-Fetch-Mode", "cors")
            // .addHeader("Sec-Fetch-Site", "same-origin")
            //.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36 Edg/124.0.0.0")
            //.addHeader("X-Requested-With", "XMLHttpRequest")
            //.addHeader("sec-ch-ua", "\"Chromium\";v=\"124\", \"Microsoft Edge\";v=\"124\", \"Not-A.Brand\";v=\"99\"")
            //.addHeader("sec-ch-ua-mobile", "?0")
            //.addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .put(requestBody)
                    .build();

            Response putResponse = client.newCall(putCategory).execute();
            System.out.println(putResponse.body().string());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*public String putCategory(){

        List<Metric> metrics = metricRepository.fi
        Integer id = 377;
        String project = "bravo11";
        String cat = "sss";
        OkHttpClient client = new OkHttpClient();
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();

        Cookie cookie = new Cookie.Builder()
                .domain("localhost")
                .path("/")
                .name("xFOEto4jYAjdMeR3Pas6_")
                .value("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTcxNTU4MjIxMH0.zdylHK0yVwYosQuXpXUmK1_Fga6fZ57ICS8EE7waVABtMUI60kN3kfupaJCZiG8YNi16d2FKiP28IqIlijmkug")
                .build();
        String jsonBody = "{\"categoryName\": \"sss\"}";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("categoryName","sss")
                .addFormDataPart("threshold","")
                .addFormDataPart("url","")
                .build();

        try{
            Request putCategory = new Request.Builder()
                    .url(new URL("http://host.docker.internal:8888/api/metrics/377?prj=bravo11"))
                    //.addHeader("Accept","application/json")*/
                    //.addHeader("Accept", "*/*")
                    //.addHeader("Accept-Encoding", "gzip, deflate, br, zstd")
                    //.addHeader("Accept-Language", "es,es-ES;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6,ca;q=0.5")
                    //.addHeader("Connection", "keep-alive")
                    //.addHeader("Cookie", "xFOEto4jYAjdMeR3Pas6_=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTcxNTU4MjIxMH0.zdylHK0yVwYosQuXpXUmK1_Fga6fZ57ICS8EE7waVABtMUI60kN3kfupaJCZiG8YNi16d2FKiP28IqIlijmkug; JSESSIONID=C4B8EB45E71885F0588F2451DA668162")
                    //.addHeader("Host", "localhost:8888")
                    //.addHeader("Origin", "http://localhost:8888")
                    //.addHeader("Referer", "http://localhost:8888/Metrics/Configuration")
                    //.addHeader("Sec-Fetch-Dest", "empty")
                    //.addHeader("Sec-Fetch-Mode", "cors")
                    // .addHeader("Sec-Fetch-Site", "same-origin")
                    //.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36 Edg/124.0.0.0")
                    //.addHeader("X-Requested-With", "XMLHttpRequest")
                    //.addHeader("sec-ch-ua", "\"Chromium\";v=\"124\", \"Microsoft Edge\";v=\"124\", \"Not-A.Brand\";v=\"99\"")
                    //.addHeader("sec-ch-ua-mobile", "?0")
                    //.addHeader("sec-ch-ua-platform", "\"Windows\"")
                   /* .put(requestBody)
                    .build();

            Response putResponse = client.newCall(putCategory).execute();
            System.out.println(putResponse.body().string());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "aaaaa";
    }*/

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
        if(category == "NoCategory"){

        }
    }
}
