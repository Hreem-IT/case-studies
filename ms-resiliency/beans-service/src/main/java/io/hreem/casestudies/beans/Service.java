package io.hreem.casestudies.beans;

import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class Service {

    @Inject
    BeansStore beansStore;

    @Inject
    RecipeStore recipeStore;

    public float getBeansStockGrams() {
        return beansStore.getBeansStockGrams();
    }

    public UUID reserveBeansForUpcomingOrder(@Valid BeansReservationRequest request) {
        // Fetch recipe for requested reservation
        final var recipe = recipeStore.getRecipe(request.sku());

        // Check if beans are available is available
        if (beansStore.getBeansStockGrams() < recipe.requiredGramsOfBeans())
            throw new BadRequestException("Not enough beans in stock to fullfill reservation request!");

        final var reservationNumber = UUID.randomUUID();
        beansStore.reserveBeans(recipe.requiredGramsOfBeans(), reservationNumber);

        return reservationNumber;
    }

    public void unreserveBeans(UUID reservationNumber) {
        beansStore.unreserveBeans(reservationNumber);
    }

    public Map<UUID, Float> getReservedBeans() {
        return beansStore.getReservations();
    }

}
