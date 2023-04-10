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
public class MemoryPartyRepository implements PartyRepository {
    private static final Map<Integer, Party> store = new HashMap<>();
    private static int seq = 0;

    private MemoryPartyRepository(){}

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

    public void clearAllStore() {
        store.clear();
    }

    @Override
    public void deleteById(int p_id) {
        if(!store.containsKey(p_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data");
        store.remove(p_id);
    }

    @Override
    public Party updateById(int p_id, Map<String, String> inputData) {
        if(!store.containsKey(p_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data");
        Party party = store.get(p_id);
        party.setStartPoint(inputData.get("startPoint"));
        party.setStartLat(inputData.get("startLat"));
        party.setStartLng(inputData.get("startLng"));
        party.setEndPoint(inputData.get("endPoint"));
        party.setTotalHeadcnt(Integer.parseInt(inputData.get("totalHeadcnt")));
        party.setStartDate(inputData.get("startDate"));
        party.setStartTime(inputData.get("startTime"));
        if (party.getP_type().equals("카풀")) {
            party.setCarNumber(inputData.get("carNumber"));
            party.setContent(inputData.get("content"));
        }
        return party;
    }

    public void onConfirm(int p_id) {
        store.get(p_id).setConfirm(true);
    }

    public void onFinish(int p_id) {
        store.get(p_id).setFinish(true);
    }
}
