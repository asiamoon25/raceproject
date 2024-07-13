package com.rc.raceproject.service;

import com.rc.raceproject.annotation.NoLogging;
import com.rc.raceproject.util.UtilHttpsRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HorseApiService {

    @Value("${horse.api.serviceKey}")
    private String serviceKey;

    @Value("${horse.api.url.horse-info-url}")
    private String horseInfoUrl;


    public Integer horseApiCount(String url) {
        Map<String,Object> params = new HashMap<>();
        params.put("ServiceKey", serviceKey);
        params.put("pageNo", 1);
        params.put("numOfRows", 1);
        params.put("_type", "json");
        //response -> body -> totalCount
        JSONObject apiResult = UtilHttpsRequest.sendGetRequestResult(url, params);

        JSONObject responseJSON = (JSONObject) apiResult.get("response");
        JSONObject bodyJSON = (JSONObject) responseJSON.get("body");
        Integer totalCount = (Integer) bodyJSON.get("totalCount");


        if(totalCount == null) {
            totalCount = 0;
        }
        return totalCount;
    }


    @NoLogging
    public JSONArray fetchHorseInfoApiResult() {
        // 여기에 api 하는거 만들기
        Map<String,Object> params = new HashMap<>();
        Integer totalCount = horseApiCount(horseInfoUrl);
        params.put("ServiceKey", serviceKey);
        params.put("pageNo", 1);
        params.put("numOfRows", totalCount);
        params.put("_type", "json");

        // response -> body -> items -> item(array)
        JSONObject apiResult = (totalCount != null && totalCount > 0) ? UtilHttpsRequest.sendGetRequestResult(horseInfoUrl, params) : null;

        JSONArray item = null;
        if(apiResult != null && !apiResult.isEmpty()){
            JSONObject responseJSON = (JSONObject) apiResult.get("response");
            JSONObject bodyJSON = (JSONObject) responseJSON.get("body");
            JSONObject itemsJSON = (JSONObject) bodyJSON.get("items");
            item = (JSONArray) itemsJSON.get("item");
        }
        return item;
    }
}
