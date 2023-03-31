package com.rideshare.party.service;

import com.rideshare.party.domain.Party;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PartyService {
    private static final Map<Integer, Party> taxiStore = new HashMap<>();
    private static final Map<Integer, Party> carpoolStore = new HashMap<>();
    private static int seq = 0;

    private PartyService(){}

    public Party save(Party party) {
        party.setP_id(++seq);

        if(party.getP_type().equals("택시"))
            taxiStore.put(party.getP_id(), party);
        else
            carpoolStore.put(party.getP_id(), party);
        return party;
    }

    public List<Party> taxiFindAll() {
        return new ArrayList<>(taxiStore.values());
    }

    public List<Party> carpoolFindAll() {
        return new ArrayList<>(carpoolStore.values());
    }

    public Party findById(int p_id) {
        if(taxiStore.get(p_id) != null)
            return taxiStore.get(p_id);
        else
            return carpoolStore.get(p_id);
    }

    public void clearAllStore() {
        taxiStore.clear();
        carpoolStore.clear();
    }

    public void clearTaxiStore() {
        taxiStore.clear();
    }

    public void clearCarpoolStore() {
        carpoolStore.clear();
    }
}
