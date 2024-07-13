package com.rc.raceproject.controller;

import com.rc.raceproject.entity.Member;
import com.rc.raceproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/join/proc")
    public Map<String, Object> joinMember(@RequestBody Map<String,Object> params) {

        Member m = memberService.addUser(params);
        Map<String, Object> map = new HashMap<>();

        if(m != null){
            map.put("saveResult", true);
        }else{
            map.put("saveResult", false);
        }

        return map;
    }
}
