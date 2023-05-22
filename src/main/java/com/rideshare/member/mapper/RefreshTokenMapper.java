package com.rideshare.member.mapper;

import com.rideshare.member.domain.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {
    Optional<RefreshToken> findByKey(String key);

    void save(RefreshToken refreshToken);

    void update(RefreshToken refreshToken);

    void delete(String key);
}
