package com.rideshare.party.controller;

import com.rideshare.party.domain.Party;
import com.rideshare.party.service.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyRepository partyRepository;

    @GetMapping("/taxis") //모든 택시 파티 리스트 반환
    public List<Party> taxiParties() {
        return partyRepository.taxiFindAll();
    }

    @GetMapping("/carpools")
    public List<Party> carpoolParties() {
        return partyRepository.carpoolFindAll();
    }


}
