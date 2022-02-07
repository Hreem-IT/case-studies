package io.hreem.casestudies.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BeansStore {

    float beansStockGrams = 500;
    Map<UUID, Float> reservedBeans = new HashMap<>();

    public float getBeansStockGrams() {
        return beansStockGrams;
    }

    public Map<UUID, Float> getReservations() {
        return reservedBeans;
    }

    public void reserveBeans(float reservableBeans, UUID reservationNumber) {
        this.reservedBeans.put(reservationNumber, reservableBeans);
        this.beansStockGrams -= reservableBeans;
    }

    public void unreserveBeans(UUID reservationNumber) {
        this.beansStockGrams += this.reservedBeans.get(reservationNumber);
    }

}
