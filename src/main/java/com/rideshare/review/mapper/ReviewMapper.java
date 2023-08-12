package com.rideshare.review.mapper;

import com.rideshare.review.domain.ScoreDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {
    ScoreDTO getScoreByMId(int mId);

    ScoreDTO getScoreById(String id);

    ScoreDTO getScoreByPId(int pid);

    void setScoreById(@Param("id") String id, @Param("score") int score);

    void setScoreByMId(@Param("mid") int mid, @Param("score") int score);

    void setScoreByPId(@Param("pid") int pid, @Param("score") int score);
}
