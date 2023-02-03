package recipes.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeById(long id) {
        return recipeRepository.findRecipeById(id);
    }

    public List<Recipe> findRecipeByNameContainsIgnoreCaseOrderByDateDesc(String name) {
        return recipeRepository.findRecipeByNameContainsIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> findRecipeByCategoryIgnoreCaseOrderByDateDesc(String category) {
        return recipeRepository.findRecipeByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public Recipe save(Recipe toSave) {
        return recipeRepository.save(toSave);
    }

    public void delete(Recipe toDelete) {recipeRepository.delete(toDelete);}
 }
