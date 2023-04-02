package com.rideshare.party.service;

import com.rideshare.party.domain.Party;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PartyRepository {
    private static final Map<Integer, Party> taxiStore = new HashMap<>();
    private static final Map<Integer, Party> carpoolStore = new HashMap<>();
    private static int seq = 0;

    private PartyRepository(){}

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
        if(taxiStore.containsKey(p_id))
            return taxiStore.get(p_id);
        else if(carpoolStore.containsKey(p_id))
            return carpoolStore.get(p_id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data");
    }

    public Party taxiFindById(int p_id) {
        if(!taxiStore.containsKey(p_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data");
        return taxiStore.get(p_id);
    }

    public Party carpoolFindById(int p_id) {
        if(!carpoolStore.containsKey(p_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data");
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
