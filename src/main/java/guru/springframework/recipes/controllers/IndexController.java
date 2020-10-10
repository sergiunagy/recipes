package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Category;
import guru.springframework.recipes.domain.UnitOfMeasure;
import guru.springframework.recipes.domain.repositories.CategoryRepository;
import guru.springframework.recipes.domain.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(){
        Optional<Category> category = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Tablespoon");

        System.out.println(category.get().getId());
        System.out.println(unitOfMeasure.get().getId());
        return "index";
    }
}
