package com.rideshare.party.controller;

import com.rideshare.party.domain.CarpoolDTO;
import com.rideshare.party.domain.Party;
import com.rideshare.party.domain.TaxiDTO;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/taxis")
    public Party addTaxi(@RequestBody TaxiDTO inputData) {
        Party addItem = addTaxiItem(inputData);
        return partyRepository.save(addItem);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/carpools")
    public Party addCarpool(@RequestBody CarpoolDTO inputData) {
        Party addItem = addCarpoolItem(inputData);
        return partyRepository.save(addItem);
    }

    @DeleteMapping("/{p_id}")
    public void remove(@PathVariable int p_id) {
        partyRepository.deleteById(p_id);
    }

    @PutMapping("/{p_id}")
    public Party edit(@PathVariable int p_id, @RequestBody Map<String, String> inputData) {
        return partyRepository.updateById(p_id, inputData);
    }


    private Party addTaxiItem(TaxiDTO inputData) {
        return new Party(
                0,
                "택시",
                inputData.getStartDate(),
                inputData.getStartTime(),
                inputData.getStartPoint(),
                inputData.getStartLat(),
                inputData.getStartLng(),
                inputData.getEndPoint(),
                1, // currentHeadCnt
                inputData.getTotalHeadcnt(),
                null,
                null,
                false,
                false
        );
    }

    private Party addCarpoolItem(CarpoolDTO inputData) {
        return new Party(
                0,
                "카풀",
                inputData.getStartDate(),
                inputData.getStartTime(),
                inputData.getStartPoint(),
                inputData.getStartLat(),
                inputData.getStartLng(),
                inputData.getEndPoint(),
                1, // currentHeadCnt
                inputData.getTotalHeadcnt(),
                inputData.getCarNumber(),
                inputData.getContent(),
                false,
                false
        );
    }
}
