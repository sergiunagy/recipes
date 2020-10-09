package guru.springframework.recipes;

import guru.springframework.recipes.controllers.IndexController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RecipesApplication {

    public static void main(String[] args) {
        ApplicationContext ctx= SpringApplication.run(RecipesApplication.class, args);

        IndexController myController= (IndexController) ctx.getBean("indexController");

        System.out.println("reload testy");

    }

}
