package com.rideshare.party.service;

import com.rideshare.party.domain.Party;

import java.util.List;
import java.util.Map;

public interface PartyRepository {
    Party save(Party party);

    List<Party> taxiFindAll();

    List<Party> carpoolFindAll();

    Party findById(int p_id);

    void clearAllStore();

    void deleteById(int p_id);

    void updateById(int p_id, Map<String, String> inputData);
}
