package com.rideshare.party.domain;

import lombok.Data;

@Data
public class TaxiDTO {
    private String startPoint;
    private String startLat;
    private String startLng;
    private String endPoint;
    private int totalHeadcnt;
    private String startDate;
    private String startTime;
}
