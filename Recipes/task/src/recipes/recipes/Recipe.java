package recipes.recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;
import recipes.users.User;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Recipe {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @UpdateTimestamp
    private LocalDateTime date;
    @NotBlank
    private String description;
    @NotEmpty
    @Type(type = "list-array")
    @Column(name = "ingredients", columnDefinition = "text[]")
    private List<String> ingredients;
    @NotEmpty
    @Type(type = "list-array")
    @Column(name = "directions", columnDefinition = "text[]")
    private List<String> directions = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    @JsonIgnore
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Recipe(Recipe recipe) {
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.directions = recipe.getDirections();
        this.ingredients = recipe.getIngredients();
        this.category = recipe.getCategory();
    }
}
