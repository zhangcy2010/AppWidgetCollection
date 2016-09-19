package hades.org.appwidgetcollection;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Hades on 16/9/19.
 */
public class StackWidgetProvider extends AppWidgetProvider{
    public static final String EXTRA_ITEM = "org.hades.desktop.EXTRA_ITEM";
    public static final String TOAST_ACTION = "org.hades.desktop.TOAST_ACITON";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //创建RemoteViews对象，加载布局文件
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context, StackWidgetService.class);
        //使用intent更新rv中的stack_view
        remoteViews.setRemoteAdapter(R.id.stack_view, intent);
        //设置当stackWidgetService提供的列表项为空时，直接显示empty_item组件
        remoteViews.setEmptyView(R.id.stack_view, R.id.empty_view);
        //创建启动StackWidgetProvider组件的Intent
        Intent toastIntent = new Intent(context, StackWidgetProvider.class);
        //为该Intent设置Action属性
        toastIntent.setAction(this.TOAST_ACTION);
        //将Intent包装成 PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //将PendingIntent与stack——view关联
        remoteViews.setPendingIntentTemplate(R.id.stack_view, pendingIntent);
        ComponentName componentName = new ComponentName(context,StackWidgetProvider.class);
        appWidgetManager.updateAppWidget(componentName, remoteViews);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(this.TOAST_ACTION)) {
            //获取Intent中的数据
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            //显示Toast提示
            Toast.makeText(context, "点击第"+viewIndex+"个列表项", Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}
