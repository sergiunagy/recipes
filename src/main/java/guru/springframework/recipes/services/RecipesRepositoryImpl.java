package guru.springframework.recipes.services;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.domain.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RecipesRepositoryImpl implements RecipeRepository {
    @Override
    public <S extends Recipe> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Recipe> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Recipe> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Set<Recipe> findAll() {
        return null;
    }

    @Override
    public Iterable<Recipe> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Recipe entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Recipe> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
