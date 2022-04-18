package com.assignment.Assignment.challenge;

import org.jetbrains.annotations.NotNull;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Tunes {


    private static HashMap<String, JSONArray> settings = new HashMap<>();
    private static ArrayList arrayToPut = null;
    private static HashMap<String, ArrayList<String>> jukeboxes = new HashMap<>();
    private static HashMap<String, String> models = new HashMap<>();


    public static void main(String[] args) {
        try {
            get("515ef38b-0529-418f-a93a-7f2347fc5805", "", "", "");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static List<String> get(String settingsId, String model, String offset, String limit) throws Exception{


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                builder.connectTimeout(30, TimeUnit.SECONDS);
                builder.readTimeout(30, TimeUnit.SECONDS);
                builder.writeTimeout(30, TimeUnit.SECONDS);
                OkHttpClient client = builder.build();

                String url = "http://my-json-server.typicode.com/touchtunes/tech-assignment/settings";

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {


                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseData = response.body().string();

                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                JSONArray jsonArray = jsonObject.getJSONArray("settings");

                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonReq = (JSONObject) jsonArray.get(i);

                                    settings.put(((jsonReq.getString("id"))), (jsonReq.getJSONArray("requires")));
                                    System.out.println("Requires : " + jsonReq.getJSONArray("requires") + " for " + ((jsonReq.getString("id"))));
                                }


                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

                OkHttpClient client2 = builder.build();
                String url2 = "http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes";
                Request request2 = new Request.Builder()
                        .url(url2)
                        .build();
                client2.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {


                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseData = response.body().string();

                            try {
                                JSONArray jkboxes = new JSONArray(responseData);
                                for(int i = 0; i < jkboxes.length(); i++) {
                                    JSONArray components = ((JSONObject) jkboxes.get(i)).getJSONArray("components");

                                    models.put(((JSONObject) jkboxes.get(i)).getString("id"), ((JSONObject) jkboxes.get(i)).getString("model"));

                                    if(components.length() != 0) {
                                        arrayToPut = new ArrayList();
                                        for(int y = 0; y < components.length(); y++) {
                                            JSONObject individualComponent = (JSONObject) components.get(y);
                                            arrayToPut.add(individualComponent.getString("name"));

                                        }
                                    } else {
                                        arrayToPut = new ArrayList();
                                    }
                                    System.out.println("JUKEBOX: " + arrayToPut + " for " + ((JSONObject) jkboxes.get(i)).getString("id") + ", model = " + ((JSONObject) jkboxes.get(i)).getString("model"));
                                    jukeboxes.put(((JSONObject) jkboxes.get(i)).getString("id"), arrayToPut);

                                }

                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        });
        System.out.println("hello");
        thread.start();

        try {
            thread.join();
            System.out.println("entered");

        } catch (Exception e) {
            e.printStackTrace();
        }

        for(long i = 0; i < 1000000000l; i++){

        }

        JSONArray query = settings.get(settingsId);
        System.out.println(query);


        ArrayList<String> result = new ArrayList<>();

        for (String e : jukeboxes.keySet()) {        // get settings hardware requirement

            int match = 0;
            ArrayList arr = jukeboxes.get(e);

            for (Object s : query){         // get hardwares of each jukebox
                if(arr.contains(s.toString())) {
                    match++;
                }
            }
            if (query.length() == match) {
                result.add(e);
            }
        }

        if(model != null && !model.trim().equalsIgnoreCase("")) {
            ArrayList<String> finalResult = new ArrayList();
            for(String e : result) {
                if(models.get(e).equalsIgnoreCase(model)){
                    finalResult.add(e);
                }
            }
            result = finalResult;
        }

        if(!limit.equalsIgnoreCase("-1")) {
            int limitParameter = -1;
            try {
                limitParameter = Integer.valueOf(limit);
                if(limitParameter < result.size()) {
                    ArrayList<String> newList = new ArrayList<>();
                    for(int i = 0; i < limitParameter; i++) {
                        newList.add(result.get(i));
                    }
                    result = newList;
                }
            } catch (Exception e) {
                throw new Exception("Enter integer");
            }
        }

        if(!offset.equalsIgnoreCase("-1")) {
            int offsetParameter = -1;
            try {
                System.out.println("OFFSET = " + offset);
                offsetParameter = Integer.valueOf(offset);
                if(offsetParameter <= result.size()) {
                    ArrayList<String> newList = new ArrayList<>();
                    for (int i = offsetParameter; i < result.size(); i++) {
                        newList.add(result.get(i));
                    }
                    result = newList;
                }
            } catch (Exception e) {
                throw new Exception("Enter integer");
            }
        }


        System.out.println(result);

        return result;

    }




}
