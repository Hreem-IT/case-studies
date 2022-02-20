package io.hreem.casestudies.barista;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BrewsStore {

    Map<UUID, Brew> brews = new HashMap<>();

    public void addOrder(Brew brew) {
        upsertBrew(brew);
    }

    public void updateBrew(Brew brew) {
        upsertBrew(brew);
    }

    private void upsertBrew(Brew brew) {
        brews.put(brew.brewRequestId(), brew);
    }

    public Brew getBrew(UUID brewId) {
        return brews.get(brewId);
    }

    public List<Brew> getBrews() {
        return brews.values().stream().collect(Collectors.toList());
    }
}
