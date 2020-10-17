package playground.springframework.recipes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"categories"})
@ToString(exclude = {"ingredients", "notes", "categories"})
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;

    @Lob
    private String directions;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private Byte[] image;
    private String url;

    @OneToOne(cascade= CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients=new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name="recipe_id"),
            inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories=new HashSet<Category>() ;


    public void setNotes(Notes notes) {
        if(notes!=null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
        categories.iterator().forEachRemaining(category -> category.getRecipes().add(this));
    }

    public Recipe addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
        return this;
    }
}
