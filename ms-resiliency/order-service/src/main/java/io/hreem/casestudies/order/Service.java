package io.hreem.casestudies.order;

import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Dependent
public class Service {

    @Inject
    OrdersStore ordersStore;

    @Inject
    @RestClient
    BeansServiceGateway beansService;

    public UUID createOrder(OrderRequest orderRequest) {
        final var orderNumber = UUID.randomUUID();

        // Reserve beans for order
        final var beansReservationRequest = new BeansReservationRequest(orderNumber, orderRequest.sku());
        final var reservationNumber = beansService.reserveBeansForOrder(beansReservationRequest);

        // Create order
        final var order = new Order();
        order.sku = orderRequest.sku();
        order.orderNumber = orderNumber;
        order.quantity = orderRequest.quantity();
        order.customerName = orderRequest.customerName();
        order.beansReservationNumber = reservationNumber;

        // Return order id
        return orderNumber;
    }

}
