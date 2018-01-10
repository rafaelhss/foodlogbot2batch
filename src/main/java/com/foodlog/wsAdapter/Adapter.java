package com.foodlog.wsAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

/**
 * Created by rafael on 16/11/17.
 */
@Service
public class Adapter {


    private String host = "http://localhost:8080";

    private String token = null;


    public Adapter(){
        String env_host = System.getenv("FOODLOG_HOST");
        if(env_host != null && !env_host.isEmpty()){
            host = env_host;
        }
        System.out.println("Usando host: " + host);
    }


    public String getToken() {
        if(token == null){

            String credentials = "{\"username\":\"admin\",\"password\":\"admin\",\"rememberMe\":true}";
            String url = host + "/api/authenticate";

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, credentials);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                String json =  response.body().string();

                Token t = new Gson().fromJson(json, Token.class);
                token = t.getId_token();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    protected  <T> T doPost(String url, String json, Class<T> classOfT){

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + getToken())
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String jsonResp =  response.body().string();

            T t = new Gson().fromJson(jsonResp, classOfT);
            return t;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected  <T> T doGet(String url, Class<T> classOfT){

        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(host + url)
                .addHeader("Authorization", "Bearer " + getToken())
                .build();
        Response response = null;
        try {
            //System.out.println("GET " + url);
            response = client.newCall(request).execute();
            String jsonResp =  response.body().string();

            //System.out.println("jsonResp: " + jsonResp);

            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, type, jsonDeserializationContext) ->
                    Instant.parse(json.getAsJsonPrimitive().getAsString())).create();
            T t = gson.fromJson(jsonResp, classOfT);


            return t;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void setHost(String host) {
        this.host = host;
    }
}
