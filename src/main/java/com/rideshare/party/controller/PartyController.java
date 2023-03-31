package com.rideshare.party.controller;

import com.rideshare.party.domain.Party;
import com.rideshare.party.service.PartyService;
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

    private final PartyService partyService;

    @GetMapping("/taxis") //모든 택시 파티 리스트 반환
    public List<Party> taxiParties() {
        return partyService.taxiFindAll();
    }

    @GetMapping("/carpools")
    public List<Party> carpoolParties() {
        return partyService.carpoolFindAll();
    }

    /**
     * 테스트용 데이터
     */
    @PostConstruct
    public void init() {
        partyService.save(createTaxiParty("기숙사", "남춘천역"));
        partyService.save(createTaxiParty("후문", "남춘천역"));
        partyService.save(createTaxiParty("천지관", "남춘천역"));
        partyService.save(createTaxiParty("백록관", "남춘천역"));
        partyService.save(createTaxiParty("정문", "남춘천역"));
        partyService.save(createTaxiParty("남춘천역", "기숙사"));
        partyService.save(createTaxiParty("남춘천역", "공쪽"));
        partyService.save(createTaxiParty("터미널", "기숙사"));
        partyService.save(createTaxiParty("터미널", "자쪽"));
        partyService.save(createTaxiParty("후평동", "후문"));

        partyService.save(createCarpoolParty("기숙사", "남양주"));
        partyService.save(createCarpoolParty("백록관", "구리"));
        partyService.save(createCarpoolParty("기숙사", "의정부"));
        partyService.save(createCarpoolParty("천지관", "동대문"));
        partyService.save(createCarpoolParty("정문", "중랑구"));
        partyService.save(createCarpoolParty("공쪽", "청량리"));
        partyService.save(createCarpoolParty("평내호평역", "정문"));
        partyService.save(createCarpoolParty("가평역", "백록관"));
        partyService.save(createCarpoolParty("남춘천역", "기숙사"));
        partyService.save(createCarpoolParty("인천", "기숙사"));
    }

    private Party createTaxiParty(String start, String end) {
        return Party.builder()
                .p_type("택시")
                .startTime(new Date())
                .startPoint(start)
                .endPoint(end)
                .currentHeadcnt(2)
                .totalHeadcnt(4)
                .isConfirm(false)
                .build();
    }

    private Party createCarpoolParty(String start, String end) {
        return Party.builder()
                .p_type("카풀")
                .startTime(new Date())
                .startPoint(start)
                .endPoint(end)
                .currentHeadcnt(4)
                .totalHeadcnt(4)
                .isConfirm(true)
                .content("카풀내용예제asdfghjkdsfjdfjk")
                .carNumber("12가3456")
                .build();
    }
}
