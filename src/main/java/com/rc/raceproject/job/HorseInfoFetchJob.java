package com.rc.raceproject.job;

import com.rc.raceproject.annotation.NoLogging;
import com.rc.raceproject.entity.Horses;
import com.rc.raceproject.repository.HorseRepository;
import com.rc.raceproject.service.HorseApiService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HorseInfoFetchJob {

    private final HorseApiService horseInfoApiService;
    private final HorseRepository horseRepository;
    @NoLogging
    @Scheduled(cron = "0 */1 * * * *") // 매일 오전 6시
    void fetchHorseInfoApiResult() {
        // start log

        // proc log
       JSONArray horseInfo = horseInfoApiService.fetchHorseInfoApiResult();
       for(int i = 0; i < horseInfo.length(); i++){
           JSONObject horse = (JSONObject) horseInfo.get(i);

            Optional<Horses> hr = horseRepository.findByHrNo(horse.get("hrNo").toString());

            if(hr.isEmpty()){
                Horses hr2 = new Horses();
                hr2.setHrNo(horse.get("hrNo").toString());
                hr2.setHrName((String)horse.get("hrName"));
                hr2.setSex((String) horse.get("sex"));
                hr2.setBirthday(horse.get("birthday").toString());
                hr2.setRank((String) horse.get("rank"));
                hr2.setRating(Integer.parseInt(horse.get("rating").toString()));
                hr2.setHrLastAmt(horse.get("hrLastAmt").toString());
                hr2.setMeet((String) horse.get("meet"));
                hr2.setName((String) horse.get("name"));
                try{
                    horseRepository.save(hr2);
                    // success log
                }catch(Exception e){
                    e.printStackTrace();
                    // error log
                }
            }
       }
        // end log
        System.out.println("------end scheduler------");
    }
}
