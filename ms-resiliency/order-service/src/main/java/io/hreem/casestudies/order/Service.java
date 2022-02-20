package io.hreem.casestudies.order;

import java.util.List;
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

        // Return order id
        return orderNumber;
    }

    @Scheduled(every = "10s")
    public void processOrder() throws InterruptedException {
        // Get pending and processing orders
        final var pendingOrders = ordersStore.getOrders().stream()
                .filter(predicate -> predicate.orderStatus() == Order.Status.PENDING)
                .collect(Collectors.toList());
        final var processingOrders = ordersStore.getOrders().stream()
                .filter(predicate -> predicate.orderStatus() == Order.Status.PROCESSING)
                .collect(Collectors.toList());

        if (pendingOrders.isEmpty() && processingOrders.isEmpty())
            return;

        // Ask barista to brew coffee
        pendingOrders.stream()
                .map(order -> new CoffeeBrewRequest(
                        order.orderNumber(),
                        order.beansReservationNumber(),
                        order.customerName(),
                        order.sku(),
                        order.quantity()))
                .parallel()
                .map(baristaService::brewCoffee)
                .forEach(brewReceipt -> {
                    // Update order status
                    Log.infof("brewreceipt: %s, %s", brewReceipt.brewRequestId(), brewReceipt.orderNumber());
                    final var order = ordersStore.getOrder(brewReceipt.orderNumber());
                    ordersStore.updateOrder(order.withBrewRequestId(brewReceipt.brewRequestId(), Order.Status.PROCESSING));
                });

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

    public List<Order> getAllOrders() {
        return ordersStore.getOrders();
    }

}
