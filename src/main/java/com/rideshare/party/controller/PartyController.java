package com.rideshare.party.controller;

import com.rideshare.party.domain.Party;
import com.rideshare.party.service.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyRepository partyRepository;

    @GetMapping("/taxis") //모든 택시 리스트 반환
    public List<Party> taxiParties() {
        return partyRepository.taxiFindAll();
    }

    @GetMapping("/carpools") //모든 카풀 리스트 반환
    public List<Party> carpoolParties() {
        return partyRepository.carpoolFindAll();
    }

    @GetMapping("/{p_id}") //id로 택시+카풀 조회하기
    public Party allSearch(@PathVariable int p_id) {
        return partyRepository.findById(p_id);
    }

    @GetMapping("/taxis/{p_id}") //id로 택시 조회하기
    public Party taxiSearch(@PathVariable int p_id) {
        return partyRepository.taxiFindById(p_id);
    }

    @GetMapping("/carpools/{p_id}") //id로 택시 조회하기
    public Party carpoolSearch(@PathVariable int p_id) {
        return partyRepository.carpoolFindById(p_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/carpools")
    public Party addParty() {
        return null;
    }
}
