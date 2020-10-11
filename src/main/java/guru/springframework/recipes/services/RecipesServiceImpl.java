package guru.springframework.recipes.services;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.domain.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipesServiceImpl implements RecipesService{

    private final RecipeRepository recipeRepository;

    public RecipesServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> getRecipes(){
        /*Solution from course - create a local hashSet and return it. Unclear what benefits this has*/
        Set<Recipe> rec = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(rec::add);
        return rec;
        /*My solution: use the HashSet returned by the repository*/
        //return recipeRepository.findAll();
    }
}
