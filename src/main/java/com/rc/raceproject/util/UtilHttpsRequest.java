package com.rc.raceproject.util;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UtilHttpsRequest {

    public static JSONObject sendGetRequestResult (String urlStr, Map<String, Object> params) {
        JSONObject root = null;
        HttpsURLConnection conn = null;
        BufferedReader br = null;
        try{

            StringBuilder apiUrl = new StringBuilder();

            apiUrl.append(urlStr);
            boolean first = true;

            for(Map.Entry<String,Object> entry : params.entrySet()) {
                if(first) {
                    first = false;
                    apiUrl.append("?");
                }else{
                    apiUrl.append("&");
                }
                apiUrl.append(entry.getKey()).append("=").append(entry.getValue());
            }

            URL url = new URL(apiUrl.toString());
            conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");


            int responseCode = conn.getResponseCode();

            if(responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();

            String line;
            while((line = br.readLine()) != null){
                response.append(line);
            }

            // Parse JSON response using org.json
             root = new JSONObject(response.toString());

        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            if(br != null){
                try{
                    br.close();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }

            if(conn != null){
                conn.disconnect();
            }
        }

        return root;
    }


    public static void main(String[] args) {
        // 여기에 api 하는거 만들기
        Map<String,Object> params = new HashMap<>();
        params.put("ServiceKey", "Epvs%2FreCM4sWdAWEz5386HNUxjNFrYcSOx48ZYbZ%2Bed8jWBcWEbYDEBUoIVBqjMhdTe5O%2FdAdiQ8kES8XlUSvw%3D%3D");
        params.put("pageNo", 1);
        params.put("numOfRows", 5);
        params.put("_type", "json");
        // response -> body -> items -> item(array)
        JSONObject apiResult = sendGetRequestResult("https://apis.data.go.kr/B551015/API8_2/raceHorseInfo_2", params);
        JSONObject responseJSON = (JSONObject) apiResult.get("response");
        JSONObject bodyJSON = (JSONObject) responseJSON.get("body");
        JSONObject itemsJSON = (JSONObject) bodyJSON.get("items");
        JSONArray item = (JSONArray) itemsJSON.get("item");


        for(int i = 0 ; i < item.length(); i++){

            JSONObject json = (JSONObject) item.get(i);

            System.out.println(json.toString());

        }
    }
}
