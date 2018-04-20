package com.udacity.nkonda.baketime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.recepiesteps.detail.RecipeStepDetailActivity;
import com.udacity.nkonda.baketime.recepiesteps.list.RecipeStepListActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakeTimeWidget extends AppWidgetProvider {
    public static final String ARGKEY_RECIPE_ID = "ARGKEY_RECIPE_ID";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        RemoteViews views = new RemoteViews(
                context.getPackageName(),
                R.layout.bake_time_widget
        );
        Intent intent = new Intent(context, BakeTimeWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.lv_widget_recipe_list, intent);

        Intent clickIntentTemplate = new Intent(context, RecipeStepListActivity.class);
        PendingIntent clickPendingIntentTemplate = PendingIntent.getActivity(context,
                0,
                clickIntentTemplate,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lv_widget_recipe_list, clickPendingIntentTemplate);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, BakeTimeWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        sendRefreshBroadcast(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, BakeTimeWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.lv_widget_recipe_list);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

