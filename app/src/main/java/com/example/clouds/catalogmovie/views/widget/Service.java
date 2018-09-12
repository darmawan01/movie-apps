package com.example.clouds.catalogmovie.views.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class Service extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
