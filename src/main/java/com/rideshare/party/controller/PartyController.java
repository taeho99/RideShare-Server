package com.rideshare.party.controller;

import com.rideshare.party.domain.Party;
import com.rideshare.party.service.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
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

    /*
    @GetMapping("/taxis/{p_id}") //id로 택시 조회하기
    public Party taxiSearch(@PathVariable int p_id) {
        return partyRepository.taxiFindById(p_id);
    }

    @GetMapping("/carpools/{p_id}") //id로 택시 조회하기
    public Party carpoolSearch(@PathVariable int p_id) {
        return partyRepository.carpoolFindById(p_id);
    }
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/taxis")
    public Party addTaxi(@RequestBody Map<String, String> inputData) {
        inputData.forEach((k, v) -> log.info("key = " + k + ", value = " + v));
        Party addItem = getAddItem(inputData, "택시");
        partyRepository.save(addItem);
        return addItem;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/carpools")
    public Party addCarpool(@RequestBody Map<String, String> inputData) {
        inputData.forEach((k, v) -> log.info("key = " + k + ", value = " + v));
        Party addItem = getAddItem(inputData, "카풀");
        partyRepository.save(addItem);
        return addItem;
    }

    private Party getAddItem(Map<String, String> inputData, String type) {
        return Party.builder()
                .p_type(type)
                .startDate(inputData.get("startDate"))
                .startTime(inputData.get("startTime"))
                .startPoint(inputData.get("startPoint"))
                .startLat(inputData.get("startLat"))
                .startLng(inputData.get("startLng"))
                .endPoint(inputData.get("endPoint"))
                .currentHeadcnt(1)
                .totalHeadcnt(Integer.parseInt(inputData.get("totalHeadcnt")))
                .isConfirm(false)
                .isFinish(false)
                .content(inputData.get("content"))
                .carNumber(inputData.get("carNumber"))
                .build();
    }
}
