package com.rideshare.party.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Party {
    private int pId;
    private String type;
    private String startDate;
    private String startTime;
    private String startPoint;
    private String startLat;
    private String startLng;
    private String endPoint;
    private int currentHeadcnt;
    private int totalHeadcnt;
    private Boolean isConfirm;
    private Boolean isFinish;
    private String carNumber;
    private String content;
    private List<String> people;

}
