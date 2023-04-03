package com.rideshare;

import com.rideshare.party.domain.Party;
import com.rideshare.party.service.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final PartyRepository partyRepository;
    private final DateFormatter df;

    /**
     * 테스트용 데이터
     */
    @PostConstruct
    public void init() {
        partyRepository.save(createTaxiParty("기숙사", "남춘천역"));
        partyRepository.save(createTaxiParty("후문", "남춘천역"));
        partyRepository.save(createTaxiParty("천지관", "남춘천역"));
        partyRepository.save(createTaxiParty("백록관", "남춘천역"));
        partyRepository.save(createTaxiParty("정문", "남춘천역"));
        partyRepository.save(createTaxiParty("남춘천역", "기숙사"));
        partyRepository.save(createTaxiParty("남춘천역", "공쪽"));
        partyRepository.save(createTaxiParty("터미널", "기숙사"));
        partyRepository.save(createTaxiParty("터미널", "자쪽"));
        partyRepository.save(createTaxiParty("후평동", "후문"));

        partyRepository.save(createCarpoolParty("기숙사", "남양주"));
        partyRepository.save(createCarpoolParty("백록관", "구리"));
        partyRepository.save(createCarpoolParty("기숙사", "의정부"));
        partyRepository.save(createCarpoolParty("천지관", "동대문"));
        partyRepository.save(createCarpoolParty("정문", "중랑구"));
        partyRepository.save(createCarpoolParty("공쪽", "청량리"));
        partyRepository.save(createCarpoolParty("평내호평역", "정문"));
        partyRepository.save(createCarpoolParty("가평역", "백록관"));
        partyRepository.save(createCarpoolParty("남춘천역", "기숙사"));
        partyRepository.save(createCarpoolParty("인천", "기숙사"));
    }

    private Party createTaxiParty(String start, String end) {
        return Party.builder()
                .p_type("택시")
                .startDate(df.dateToDateStr(new Date()))
                .startTime(df.dateToTimeStr(new Date()))
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
                .startDate(df.dateToDateStr(new Date()))
                .startTime(df.dateToTimeStr(new Date()))
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
