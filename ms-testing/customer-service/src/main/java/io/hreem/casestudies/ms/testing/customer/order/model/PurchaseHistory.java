package io.hreem.casestudies.ms.testing.customer.order.model;

public record PurchaseHistory(
                int numberOfOrders,
                int totalProductQuantity,
                long totalAmount) {
}
