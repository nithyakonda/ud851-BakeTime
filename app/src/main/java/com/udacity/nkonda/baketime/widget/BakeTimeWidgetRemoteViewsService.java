package com.udacity.nkonda.baketime.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by nkonda on 4/19/18.
 */

public class BakeTimeWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakeTimeWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
