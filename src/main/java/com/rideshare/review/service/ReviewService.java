package com.rideshare.review.service;

import com.rideshare.review.domain.ScoreDTO;
import com.rideshare.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewMapper reviewMapper;

    public ScoreDTO getScoreByMId(int mId) {
        return reviewMapper.getScoreByMId(mId);
    }

    public ScoreDTO getScoreById(String id) {
        return reviewMapper.getScoreById(id);
    }

    public ScoreDTO getScoreByPId(int pid) {
        return reviewMapper.getScoreByPId(pid);
    }
}
