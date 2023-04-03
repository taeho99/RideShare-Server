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
    private static final Map<Integer, Party> store = new HashMap<>();
    private static int seq = 0;

    private PartyRepository(){}

    public Party save(Party party) {
        party.setP_id(++seq);

        store.put(party.getP_id(), party);
        return store.get(party.getP_id());
    }

    public List<Party> taxiFindAll() {
        List<Party> result = new ArrayList<>();
        store.forEach((k, v) -> {
            if (v.getP_type().equals("택시"))
                result.add(v);
        });
        return result;
    }

    public List<Party> carpoolFindAll() {
        List<Party> result = new ArrayList<>();
        store.forEach((k, v) -> {
            if (v.getP_type().equals("카풀"))
                result.add(v);
        });
        return result;
    }

    public Party findById(int p_id) {
        if(!store.containsKey(p_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data");
        return store.get(p_id);
    }

    /*
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
    */

    public void clearAllStore() {
        store.clear();
    }

    /*
    public void clearTaxiStore() {
        taxiStore.clear();
    }

    public void clearCarpoolStore() {
        carpoolStore.clear();
    }
    */
}
