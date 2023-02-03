package recipes.recipes;

import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findRecipeById(long id);

    void delete(Recipe entity);

    List<Recipe> findRecipeByNameContainsIgnoreCaseOrderByDateDesc(@NotBlank String name);

    List<Recipe> findRecipeByCategoryIgnoreCaseOrderByDateDesc(@NotBlank String category);

}
