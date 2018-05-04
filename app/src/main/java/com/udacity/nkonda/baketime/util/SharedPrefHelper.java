package com.udacity.nkonda.baketime.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class SharedPrefHelper {

    public final static String PREF_KEY_RECIPE_ID = "PREF_KEY_RECIPE_ID";
    public final static String PREF_KEY_RECIPE_NAME = "PREF_KEY_RECIPE_NAME";

    public static void setDesiredRecipe(Context context, int recipeId, String recipeName) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREF_KEY_RECIPE_ID, recipeId);
        editor.putString(PREF_KEY_RECIPE_NAME, recipeName);
        editor.commit();
    }

    public static int getRecipeId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(
                PREF_KEY_RECIPE_ID, 0);
    }

    public static String getRecipeNAme(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(
                PREF_KEY_RECIPE_NAME, "");
    }
}
