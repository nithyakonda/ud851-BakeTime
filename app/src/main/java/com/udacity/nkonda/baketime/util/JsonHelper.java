package com.udacity.nkonda.baketime.util;

import com.udacity.nkonda.baketime.data.Ingredient;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nkonda on 3/26/18.
 */

public class JsonHelper {
    private static final String TAG = JsonHelper.class.getSimpleName();
    private static final String ID_JSON = "id";
    private static final String NAME_JSON = "name";
    private static final String INGREDIENTS_JSON = "ingredients";
    private static final String STEPS_JSON = "steps";
    private static final String SERVINGS_JSON = "servings";
    private static final String IMAGE_JSON = "image";

    private static final String QUANTITY_JSON = "quantity";
    private static final String INGREDIENT_JSON = "ingredient";
    private static final String MEASURE_JSON = "measure";

    private static final String SHORT_DESC_JSON = "shortDescription";
    private static final String DESC_JSON = "description";
    private static final String VIDEO_URL_JSON = "videoURL";
    private static final String THUMBNAIL_URL_JSON = "thumbnailURL";

    public static List<Recipe> parseRecipesJsonStr(String recipesJsonStr) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray recipesJsonArr = new JSONArray(recipesJsonStr);
            for (int i = 0; i < recipesJsonArr.length(); i++) {
                JSONObject recipeJson = recipesJsonArr.getJSONObject(i);
                recipes.add(new Recipe(
                        recipeJson.getInt(ID_JSON),
                        recipeJson.getString(NAME_JSON),
                        parseIngredientsJsonArr(recipeJson.getJSONArray(INGREDIENTS_JSON)),
                        parseRecipeStepsJsonArr(recipeJson.getJSONArray(STEPS_JSON)),
                        recipeJson.getInt(SERVINGS_JSON),
                        recipeJson.getString(IMAGE_JSON)
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private static List<Ingredient> parseIngredientsJsonArr(JSONArray ingredientsJsonArr) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArr.length(); i++) {
            try {
                JSONObject ingredientJson = ingredientsJsonArr.getJSONObject(i);
                ingredients.add(new Ingredient(
                        ingredientJson.getString(INGREDIENT_JSON),
                        ingredientJson.getInt(QUANTITY_JSON),
                        ingredientJson.getString(MEASURE_JSON)
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ingredients;
    }

    private static List<RecipeStep> parseRecipeStepsJsonArr(JSONArray recipeStepsJsonArr) {
        List<RecipeStep> steps = new ArrayList<>();
        for (int i = 0; i < recipeStepsJsonArr.length(); i++) {
            try {
                JSONObject recipeStepJson = recipeStepsJsonArr.getJSONObject(i);
                steps.add(new RecipeStep(
                        recipeStepJson.getInt(ID_JSON),
                        recipeStepJson.getString(SHORT_DESC_JSON),
                        recipeStepJson.getString(DESC_JSON),
                        recipeStepJson.getString(VIDEO_URL_JSON),
                        recipeStepJson.getString(THUMBNAIL_URL_JSON)
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return steps;
    }
}
