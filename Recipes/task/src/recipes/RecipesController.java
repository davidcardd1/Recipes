package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.recipes.Id;
import recipes.recipes.Recipe;
import recipes.recipes.RecipeService;
import recipes.users.User;
import recipes.users.UserService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
public class RecipesController {

    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;


    @PostMapping("/api/recipe/new")
    public Id postRecipe(@Valid @RequestBody Recipe sentRecipe, Authentication auth) {
        User user = userService.findUserByUsername(auth.getName());
        Recipe createdRecipe = recipeService.save(new Recipe(sentRecipe));
        createdRecipe.setUser(user);
        recipeService.save(createdRecipe);
        return new Id((int) createdRecipe.getId());
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        if (recipeService.findRecipeById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return recipeService.findRecipeById(id);
        }
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> findRecipes(@Valid @RequestParam(name = "name", required = false) String name, @Valid @RequestParam(name = "category", required = false) String category) {
        if ((name == null && category == null) || (name != null && category != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            List<Recipe> recipeList;
            if (name != null) {
                if (name.isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                recipeList = recipeService.findRecipeByNameContainsIgnoreCaseOrderByDateDesc(name);
            } else {
                if (category.isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                recipeList = recipeService.findRecipeByCategoryIgnoreCaseOrderByDateDesc(category);
            }
            return recipeList;
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable int id, Authentication auth) {
        if (recipeService.findRecipeById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            if (auth.getName().equals(recipeService.findRecipeById(id).getUser().getEmail())) {
                recipeService.delete(recipeService.findRecipeById(id));
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
    }

    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@Valid @RequestBody Recipe sentRecipe, @PathVariable int id, Authentication auth) {
        if (recipeService.findRecipeById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            if (auth.getName().equals(recipeService.findRecipeById(id).getUser().getEmail())) {
                recipeService.findRecipeById(id).setCategory(sentRecipe.getCategory());
                recipeService.findRecipeById(id).setName(sentRecipe.getName());
                recipeService.findRecipeById(id).setDirections(sentRecipe.getDirections());
                recipeService.findRecipeById(id).setIngredients(sentRecipe.getIngredients());
                recipeService.findRecipeById(id).setDescription(sentRecipe.getDescription());
                recipeService.save(recipeService.findRecipeById(id));
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
    }



}
