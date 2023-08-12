package com.rideshare.review.service;

import com.rideshare.party.service.PartyService;
import com.rideshare.review.domain.ScoreDTO;
import com.rideshare.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final PartyService partyService;

    public ScoreDTO getScoreByMId(int mId) {
        return reviewMapper.getScoreByMId(mId);
    }

    public ScoreDTO getScoreById(String id) {
        return reviewMapper.getScoreById(id);
    }

    public ScoreDTO getScoreByPId(int pid) {
        return reviewMapper.getScoreByPId(pid);
    }

    public void setScoreById(String id, int score) {
        reviewMapper.setScoreById(id, score);
    }

    public void setScoreByMId(int mid, int score) {
        reviewMapper.setScoreByMId(mid, score);
    }

    public void setScoreByPId(int pid, int score) {
        if (!partyService.findById(pid).getIsFinish()) {
            //파티가 종료되지 않았으면 리뷰 불가
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파티가 종료되지 않았습니다.");
        }
        reviewMapper.setScoreByPId(pid, score);

    }
}
