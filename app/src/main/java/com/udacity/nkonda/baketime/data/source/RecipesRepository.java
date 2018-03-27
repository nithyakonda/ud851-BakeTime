package com.udacity.nkonda.baketime.data.source;

import android.content.Context;

import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.data.Ingredient;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.RecipeStep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by nkonda on 3/25/18.
 */

public class RecipesRepository implements RecipesDataSource{
    private static final String TAG = RecipesRepository.class.getSimpleName();
    private static final int RECIPE_JSON_RES_ID = R.raw.recipes_json;

    private static List<Recipe> sRecipes;
    private final Context mContext;

    public RecipesRepository(Context context) {
        mContext = context;
    }

    @Override
    public List<Recipe> getRecipes() {
        return null;
    }

    @Override
    public List<Ingredient> getIngredients(int recipeId) {
        return null;
    }

    @Override
    public List<RecipeStep> getRecipeStepDetail(int recipeStepId) {
        return null;
    }

    private void parseRecipesJson() {

    }

    private String getRecipeJsonString() {
        InputStream is = mContext.getResources().openRawResource(RECIPE_JSON_RES_ID);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}
