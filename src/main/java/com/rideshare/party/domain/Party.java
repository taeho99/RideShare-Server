package com.rideshare.party.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Party {
    private int p_id;
    private String p_type;
//    private Date startTime;
    private String startDate;
    private String startTime;
    private String startPoint;
    private String endPoint;
    private int currentHeadcnt;
    private int totalHeadcnt;
    private boolean isConfirm;
    private String carNumber;
    private String content;

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
