package io.hreem.casestudies.beans;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class RecipeStore {

    List<Recipe> recipes = List.of(
            new Recipe("Regular Coffee", "regular-coffee", 10.6F),
            new Recipe("Espresso", "espresso", 7.5F),
            new Recipe("Double Espresso", "double-espresso", 15F),
            new Recipe("Double Espresso", "double-espresso", 15F),
            new Recipe("Latte Machiato", "latte-machiato", 10F),
            new Recipe("Americano", "americano", 15F));

    public Recipe getRecipe(String sku) {
        return recipes.stream()
                .filter(recipe -> recipe.sku().equals(sku))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No recipe found for sku: " + sku));
    }
}
