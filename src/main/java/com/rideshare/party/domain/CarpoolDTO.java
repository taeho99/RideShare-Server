package com.rideshare.party.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class CarpoolDTO {
    private String startPoint;
    private String startLat;
    private String startLng;
    private String endPoint;
    private int totalHeadcnt;
    private String startDate;
    private String startTime;
    private String carNumber;
    private String content;
}
