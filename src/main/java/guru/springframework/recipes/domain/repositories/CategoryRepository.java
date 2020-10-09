package guru.springframework.recipes.domain.repositories;

import guru.springframework.recipes.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
