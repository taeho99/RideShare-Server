package com.rideshare.review.controller;

import com.rideshare.member.util.SecurityUtil;
import com.rideshare.review.domain.ScoreDTO;
import com.rideshare.review.domain.ScoreVO;
import com.rideshare.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ScoreDTO getMyScore() {
        return reviewService.getScoreByMId(SecurityUtil.getCurrentMemberId());
    }

    @GetMapping("/id/{id}")
    public ScoreDTO getScoreById(@PathVariable String id) {
        return reviewService.getScoreById(id);
    }

    @GetMapping("/mid/{mid}")
    public ScoreDTO getScoreByMId(@PathVariable int mid) {
        return reviewService.getScoreByMId(mid);
    }

    @GetMapping("/pid/{pid}") //파티장의 점수 조회
    public ScoreDTO getScoreByPId(@PathVariable int pid) {
        return reviewService.getScoreByPId(pid);
    }

    @PostMapping("/id/{id}")
    public void setScoreById(@PathVariable String id, @RequestBody ScoreVO scoreVO) {
        reviewService.setScoreById(id, scoreVO.getScore());
    }

    @PostMapping("/mid/{mid}")
    public void setScoreByMId(@PathVariable int mid, @RequestBody ScoreVO scoreVO) {
        reviewService.setScoreByMId(mid, scoreVO.getScore());
    }

    @PostMapping("/pid/{pid}") //파티장에게 점수 부여, 종료되지 않은 파티는 점수 부여 불가능
    public void setScoreByPId(@PathVariable int pid, @RequestBody ScoreVO scoreVO) {
        reviewService.setScoreByPId(pid, scoreVO.getScore());
    }


}
