package guru.springframework.recipes.bootstrap;

import guru.springframework.recipes.domain.*;
import guru.springframework.recipes.domain.repositories.CategoryRepository;
import guru.springframework.recipes.domain.repositories.RecipeRepository;
import guru.springframework.recipes.domain.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
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
    @Transactional
    public void run(String... args) throws Exception {
        if(recipeRepository.findAll().size()==0){
           loadData();
        }
    }
/*Comments:
* - uom are loaded at startup and each is checked if it exists
* - same with Categories
* - uses a different interface on the DataLoader class: ApplicationListener<ContextRefreshedEvent>
https://stackoverflow.com/questions/54265552/different-ways-to-run-custom-code-before-the-application-starts
* x - implement no-arg and argument constructor for Ingredients
* - set up reverse binding for entities*/

    private void loadData(){

        System.out.println("Bootstrapping data..");
        log.debug("Starting bootstrapping ");
        /*Get and check ingredients*/
        UnitOfMeasure piece = getUnit("Piece");
        UnitOfMeasure tsp = getUnit("Teaspoon");
        UnitOfMeasure tbsp = getUnit("Tablespoon");
        UnitOfMeasure dash = getUnit("Dash");
        UnitOfMeasure cup = getUnit("Cup");

        /*Get categories*/
        Category american = getCategory("American");
        Category mexican = getCategory("Mexican");

        /*********** create Recipe 1 *************************************/
        Recipe guacamole = getGuacamoleRecipe();
        /*Add category to recipe*/
        guacamole.getCategories().add(mexican);

        /*Add Ingredients to Recipe*/
        guacamole.addIngredient(new Ingredient("Ripe avocado", BigDecimal.valueOf(2), piece));
        guacamole.addIngredient(new Ingredient("Salt", BigDecimal.valueOf(0.25), tsp ));
        guacamole.addIngredient(new Ingredient("Lime juice", BigDecimal.valueOf(1),tbsp ));
        guacamole.addIngredient(new Ingredient("Minced onion", BigDecimal.valueOf(2.25), tbsp ));
        guacamole.addIngredient(new Ingredient("serrano chilles", BigDecimal.valueOf(2), piece ));
        guacamole.addIngredient(new Ingredient("minced cilantro", BigDecimal.valueOf(2), tbsp ));
        guacamole.addIngredient(new Ingredient("freshly grated black pepper", BigDecimal.valueOf(1), dash ));

        /*Add notes*/
        Notes note_guac = new Notes();
        note_guac.setRecipeNotes("Be careful handling chiles if using. \n" +
                "Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
        guacamole.setNotes(note_guac);

        /*persist the recipe*/
        recipeRepository.save(guacamole);
        log.debug("Saving recipe: " + guacamole.toString());


        /*********** create Recipe 2 *************************************/
        Recipe spicy_grill_chicken = getSpicyGrilledChickenRecipe();
        /*Add category to recipe*/
        spicy_grill_chicken.getCategories().add(american);

        /*Add Ingredients to Recipe*/
        spicy_grill_chicken.addIngredient(new Ingredient("ancho chili powder", BigDecimal.valueOf(2), tbsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("dried oregano", BigDecimal.valueOf(1), tsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("dried cumin", BigDecimal.valueOf(1), tsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("sugar", BigDecimal.valueOf(1), tsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("salt", BigDecimal.valueOf(0.5), tsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("clove garlic", BigDecimal.valueOf(1), tsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("finely grated orange zest", BigDecimal.valueOf(1), tsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("fresh-squeezed orange juice", BigDecimal.valueOf(3), tbsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("olive oil", BigDecimal.valueOf(2), tbsp  ));
        spicy_grill_chicken.addIngredient(new Ingredient("boneless chicken thighs", BigDecimal.valueOf(6), piece  ));

        /*Add notes*/
        Notes note_chick = new Notes();
        note_chick.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. " +
                "(If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, " +
                "though the flavor won't be quite the same.)");
        spicy_grill_chicken.setNotes(note_chick);

//        log.debug("Saving recipe: " + guacamole.toString());
        /*persist the recipe*/
        recipeRepository.save(spicy_grill_chicken);

    }

    private Category getCategory(String category) {

        Optional<Category> cat = categoryRepository.findByDescription(category);
        if(!cat.isPresent()){
            throw new RuntimeException("Requested category not found : " + category);
        }
        return  cat.get();
    }

    private UnitOfMeasure getUnit(String uomName) {
        Optional<UnitOfMeasure> uom = uomRepository.findByDescription(uomName);
        if(!uom.isPresent()){
            throw new RuntimeException("Expected Unit of measure not found for " + uomName);
        }
        return uom.get();
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
        sgc.setCookTime(8);
        sgc.setPrepTime(10);
        sgc.setServings(7);
        sgc.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        sgc.setDescription("Spicy Grilled Chicken Tacos");
        sgc.setDifficulty(Difficulty.MODERATE);
        sgc.setSource("The google");
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

}
