package playground.springframework.recipes.domain.repositories;

import playground.springframework.recipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Override
    Set<Recipe> findAll();
}
