package com.rideshare.party.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Party {
    private int p_id;
    private String p_type;
//    private Date startTime;
    private String startDate;
    private String startTime;
    private String startPoint;
    private String startLat;
    private String startLng;
    private String endPoint;
    private int currentHeadcnt;
    private int totalHeadcnt;
    private String carNumber;
    private String content;
    private boolean isConfirm;
    private boolean isFinish;

//    // 날짜 반환
//    public String getStartDateStr() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(startTime);
//    }
//
//    // 시간 반환
//    public String getStartTimeStr() {
//        SimpleDateFormat sdf = new SimpleDateFormat("a KK:mm");
//        return sdf.format(startTime);
//    }
}
