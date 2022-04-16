import com.sun.istack.internal.NotNull;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Tunes {

    private static HashMap<String, JSONArray> settings = new HashMap<>();
    private static JSONArray arrayToPut = null;
    private static HashMap<JSONArray, String> jukeboxes = new HashMap<>();


    public static void main(String[] args) {
        get("e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a", "", "", "");
    }


    private static void get(String settingsId, String model, String offset, String limit) {

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
                            settings.put(((jsonReq.getString("id")).toString()), (jsonReq.getJSONArray("requires")));
                        }


                    } catch (Exception e){
                        System.out.println("Error");
                    }

                }
            }
        });

//        OkHttpClient client2 = builder.build();
//        String url2 = "http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes";
//        Request request2 = new Request.Builder()
//                .url(url2)
//                .build();
//        client2.newCall(request2).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String responseData = response.body().string();
//
//                    try {
//                        JSONArray jkboxes = new JSONArray(responseData);
//                        for(int i = 0; i < jkboxes.length(); i++) {
//                            System.out.println(((JSONObject) jkboxes.get(i)).getString("id"));
//                            JSONArray components = ((JSONObject) jkboxes.get(i)).getJSONArray("components");
//                            System.out.println("ALL COMPONENTS: " + components);
//
//                            if(components.length() != 0) {
//                                arrayToPut = new JSONArray();
//                                for(int y = 0; y < components.length(); y++) {
//                                    JSONObject individualComponent = (JSONObject) components.get(y);
//                                    arrayToPut.put(individualComponent.getString("name"));
//
//                                }
//                            } else {
//                                arrayToPut = components;
//                            }
//                            jukeboxes.put(arrayToPut, ((JSONObject) jkboxes.get(i)).getString("id"));
//                        }
//
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });


    }

}
