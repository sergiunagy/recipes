package guru.springframework.recipes.bootstrap;

import guru.springframework.recipes.domain.*;
import guru.springframework.recipes.domain.repositories.CategoryRepository;
import guru.springframework.recipes.domain.repositories.RecipeRepository;
import guru.springframework.recipes.domain.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository uomRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository uomRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.uomRepository = uomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(recipeRepository.findAll().size()==0){
            loadData();
        }
    }

    private void loadData(){
        System.out.println("Bootstrapping data..");

        /*********** create Recipe 1 *************************************/
        Recipe guacamole = getGuacamoleRecipe();
        /*Add category to recipe*/
        guacamole.getCategories().add(categoryRepository.findByDescription("Mexican").get());

        /*Add Ingredients to Recipe*/
        guacamole.getIngredients().add(getIngredient("Ripe avocado", BigDecimal.valueOf(2), "Piece"));
        guacamole.getIngredients().add(getIngredient("Salt", BigDecimal.valueOf(0.25), "Teaspoon"));
        guacamole.getIngredients().add(getIngredient("Lime juice", BigDecimal.valueOf(1), "Tablespoon"));
        guacamole.getIngredients().add(getIngredient("Minced onion", BigDecimal.valueOf(2.25), "Tablespoon"));
        guacamole.getIngredients().add(getIngredient("serrano chilles", BigDecimal.valueOf(2), "Piece"));
        guacamole.getIngredients().add(getIngredient("minced cilantro", BigDecimal.valueOf(2), "Tablespoon"));
        guacamole.getIngredients().add(getIngredient("freshly grated black pepper", BigDecimal.valueOf(1), "Dash"));

        /*Add notes*/
        Notes note_guac = new Notes();
        note_guac.setRecipeNotes("Be careful handling chiles if using. \n" +
                "Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
        guacamole.setNotes(note_guac);

        /*persist the recipe*/
        recipeRepository.save(guacamole);

        /*********** create Recipe 2 *************************************/
        Recipe spicy_grill_chicken = getSpicyGrilledChickenRecipe();
        /*Add category to recipe*/
        spicy_grill_chicken.getCategories().add(categoryRepository.findByDescription("American").get());

        /*Add Ingredients to Recipe*/
        spicy_grill_chicken.getIngredients().add(getIngredient("ancho chili powder", BigDecimal.valueOf(2), "Tablespoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("dried oregano", BigDecimal.valueOf(1), "Teaspoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("dried cumin", BigDecimal.valueOf(1), "Teaspoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("sugar", BigDecimal.valueOf(1), "Teaspoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("salt", BigDecimal.valueOf(0.5), "Teaspoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("clove garlic", BigDecimal.valueOf(1), "Tablespoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("finely grated orange zest", BigDecimal.valueOf(1), "Tablespoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("fresh-squeezed orange juice", BigDecimal.valueOf(3), "Tablespoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("olive oil", BigDecimal.valueOf(2), "Tablespoon"));
        spicy_grill_chicken.getIngredients().add(getIngredient("boneless chicken thighs", BigDecimal.valueOf(6), "Piece"));

        /*Add notes*/
        Notes note_chick = new Notes();
        note_chick.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. " +
                "(If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, " +
                "though the flavor won't be quite the same.)");
        spicy_grill_chicken.setNotes(note_chick);

        /*persist the recipe*/
        recipeRepository.save(spicy_grill_chicken);

    }

    private Recipe getGuacamoleRecipe() {
        Recipe guacamole = new Recipe();
        guacamole.setCookTime(0);
        guacamole.setPrepTime(10);
        guacamole.setServings(4);
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDescription("How to Make Perfect Guacamole");
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setSource("");
        guacamole.setDirections("1. Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt " +
                "knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.) \n" +
                "\n\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving." +
                "\n\n"+
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover " +
                "it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve. \n"+
                "\n\n"  );
        return guacamole;
    }

    private Recipe getSpicyGrilledChickenRecipe() {
        Recipe sgc = new Recipe();
        sgc.setCookTime(0);
        sgc.setPrepTime(10);
        sgc.setServings(4);
        sgc.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        sgc.setDescription("Spicy Grilled Chicken Tacos");
        sgc.setDifficulty(Difficulty.EASY);
        sgc.setSource("");
        sgc.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. " +
                "Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. " +
                "Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up " +
                "in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving." +
                "\n\n"+
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, " +
                "tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges. \n"+
                "\n\n"  );
        return sgc;
    }

    private Ingredient getIngredient(String s, BigDecimal i, String unit) {
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(s);
        ingredient.setAmount(i);
        ingredient.setUom(uomRepository.findByDescription(unit).get());
        return ingredient;
    }
}
