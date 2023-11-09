package com.rideshare.party.controller;

import com.rideshare.party.domain.PartyDTO;
import com.rideshare.party.domain.Party;
import com.rideshare.party.domain.ScrollDTO;
import com.rideshare.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @GetMapping //택시, 카풀 리스트 반환
    public List<Party> taxiList(@ModelAttribute ScrollDTO scrollDTO) {
        log.info("ScrollDTO = {}", scrollDTO);
        return partyService.listPage(scrollDTO);
    }

    @GetMapping("/{pId}") //id로 택시, 카풀 조회하기
    public Party allSearch(@PathVariable int pId) {
        return partyService.findById(pId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Party addParty(@RequestBody PartyDTO inputData) {
        log.info("{}", inputData);
        return partyService.partySave(inputData);
    }

    @DeleteMapping("/{pId}")
    public void remove(@PathVariable int pId) {
        partyService.deleteById(pId);
    }

    @PutMapping("/{pId}/leave")
    public void leaveParty(@PathVariable int pId) {
        if (partyService.isFinish(pId)) {
            log.info("이미 종료된 파티입니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "종료된 파티");
        }
        partyService.leaveParty(pId);
    }

    @PutMapping("/{pId}")
    public Party edit(@PathVariable int pId, @RequestBody PartyDTO inputData) {
        inputData.setPId(pId);
        return partyService.updateById(inputData);
    }


    @PutMapping("/{pId}/participate")
    public Integer participate(@PathVariable int pId) {
        if (partyService.isFinish(pId) || partyService.isConfirm(pId)) {
            log.info("종료되거나 확정된 파티입니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "확정되거나 종료된 파티");
        }
        if (partyService.isExistUserInParty(pId)) {
            log.info("이미 참여중인 파티입니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 참여중인 파티");
        }
        return partyService.participate(pId);
    }

    //파티의 작성자와 현재 getCurrentMId 같은지 확인 후 confirm 가능하게
    @PutMapping("/{pId}/confirm")
    public void onConfirm(@PathVariable int pId) {
        partyService.onConfirm(pId);
    }

    //파티의 작성자와 현재 getCurrentMId 같은지 확인 후 finish 가능하게
    @PutMapping("/{pId}/finish")
    public void onFinish(@PathVariable int pId) {
        partyService.onFinish(pId);
    }

    @GetMapping("/count")
    public Integer count(@RequestParam("type") String type) {
        return partyService.getCount(type);
    }
}
