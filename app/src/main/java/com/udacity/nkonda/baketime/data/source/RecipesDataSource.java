package com.udacity.nkonda.baketime.data.source;

import com.udacity.nkonda.baketime.data.Ingredient;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.RecipeStep;

import java.util.List;

/**
 * Created by nkonda on 3/25/18.
 */

public interface RecipesDataSource {
    List<Recipe> getRecipes();
    List<Ingredient> getIngredients(int recipeId);
    List<RecipeStep> getRecipeStepDetail(int recipeStepId);
}
