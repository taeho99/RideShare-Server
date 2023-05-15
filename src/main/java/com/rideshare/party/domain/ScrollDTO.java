package com.rideshare.party.domain;

import lombok.Data;

@Data
public class ScrollDTO {
    private int lastId, amount;
    private String type, keyword;
}
