package io.hreem.casestudies.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrdersStore {

    Map<UUID, Order> orders = new HashMap<>();

    public void addOrder(Order order) {
        upsertOrder(order);
    }

    public void updateOrder(Order order) {
        upsertOrder(order);
    }

    private void upsertOrder(Order order) {
        orders.put(order.orderNumber(), order);
    }

    public List<Order> getOrders() {
        return List.of(orders.values().toArray(Order[]::new));
    }

    public Order getOrder(UUID orderNumber) {
        return orders.get(orderNumber);
    }

}
