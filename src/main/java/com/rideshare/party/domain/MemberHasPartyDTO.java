package com.rideshare.party.domain;

import lombok.Data;

@Data
public class MemberHasPartyDTO {
    private int jId;
    private final int mId, pId;
    private final boolean isWriter;
}
