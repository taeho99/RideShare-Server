package com.rideshare.review.mapper;

import com.rideshare.review.domain.ScoreDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
    ScoreDTO getScoreByMId(int mId);

    ScoreDTO getScoreById(String id);

    ScoreDTO getScoreByPId(int pid);
}
