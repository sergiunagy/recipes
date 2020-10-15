package guru.springframework.recipes.services;

import guru.springframework.recipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipesService {

    public Set<Recipe> getRecipes();
    public Recipe findById(Long id);
}
