package com.rideshare.party.service;

import com.rideshare.party.domain.CarpoolDTO;
import com.rideshare.party.domain.Party;

import java.util.List;

public interface PartyRepository {
    Party save(Party party);

    List<Party> taxiFindAll();

    List<Party> carpoolFindAll();

    Party findById(int p_id);

    void clearAllStore();

    void deleteById(int p_id);

    Party updateById(int p_id, CarpoolDTO inputData);
}
