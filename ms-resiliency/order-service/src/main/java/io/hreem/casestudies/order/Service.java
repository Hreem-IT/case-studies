package io.hreem.casestudies.order;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.hreem.casestudies.order.gateway.BaristaService;
import io.hreem.casestudies.order.gateway.BeansReservationRequest;
import io.hreem.casestudies.order.gateway.BeansServiceGateway;
import io.hreem.casestudies.order.gateway.BrewStatus;
import io.hreem.casestudies.order.gateway.CoffeeBrewRequest;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;

@Dependent
public class Service {

    @Inject
    OrdersStore ordersStore;

    @Inject
    @RestClient
    BeansServiceGateway beansService;

    @Inject
    BaristaService baristaService;

    public UUID createOrder(OrderRequest orderRequest) {
        final var orderNumber = UUID.randomUUID();

        // Reserve beans for order
        final var beansReservationRequest = new BeansReservationRequest(orderNumber, orderRequest.sku());
        final var reservationResponse = beansService.reserveBeansForOrder(beansReservationRequest);
        final var reservationId = reservationResponse.readEntity(UUID.class);

        // Create order
        final var order = new Order(
                orderNumber,
                reservationId,
                orderRequest.customerName(),
                orderRequest.sku(),
                orderRequest.quantity(),
                Order.Status.PENDING,
                null);

        // Persist
        ordersStore.addOrder(order);

        // Ask barista to brew coffee
        final var orderBrewRequest = new CoffeeBrewRequest(
                order.orderNumber(),
                order.beansReservationNumber(),
                order.customerName(),
                order.sku(),
                order.quantity());
        final var brewReceipt = baristaService.brewCoffee(orderBrewRequest);
        Log.infof("brewreceipt: %s, %s", brewReceipt.brewRequestId(), brewReceipt.orderNumber());
        ordersStore.updateOrder(order.withBrewRequestId(brewReceipt.brewRequestId(), Order.Status.PROCESSING));

        // Return order id
        return orderNumber;
    }

    @Scheduled(every = "0.3s")
    public void processOrder() {
        // Get processing orders
        final var processingOrders = ordersStore.getOrders().stream()
                .filter(predicate -> predicate.orderStatus() == Order.Status.PROCESSING)
                .collect(Collectors.toList());

        if (processingOrders.isEmpty())
            return;

        // Check on barista
        processingOrders.stream()
                .parallel()
                .map(order -> baristaService.checkBrewStatus(
                        order.orderNumber(),
                        order.brewRequestId()))
                .filter(predicate -> predicate.brewStatus() == BrewStatus.Status.FINISHED)
                .forEach(status -> {
                    // Update order status
                    final var order = ordersStore.getOrder(status.orderNumber());
                    ordersStore.updateOrder(order.withOrderStatus(Order.Status.COMPLETED));
                });

        return;
    }

    public OrderInformation getOrdersInformation() {
        final var orders = ordersStore.getOrders();
        final var pending = orders.stream()
                .filter(predicate -> predicate.orderStatus() == Order.Status.PENDING)
                .count();
        final var processing = orders.stream()
                .filter(predicate -> predicate.orderStatus() == Order.Status.PROCESSING)
                .count();
        final var completed = orders.stream()
                .filter(predicate -> predicate.orderStatus() == Order.Status.COMPLETED)
                .count();
        return new OrderInformation(pending, processing, completed);
    }

}
