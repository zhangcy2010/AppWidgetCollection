package hades.org.appwidgetcollection;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by Hades on 16/9/19.
 */
public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    private class StackRemoteViewsFactory implements RemoteViewsFactory {

        //定义一个数组来保存该组件生成的多个列表项
        private int items[] = null;
        private Context mContext;
        public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
            this.mContext = applicationContext;
        }

        @Override
        public void onCreate() {
            //初始化数组
            items = new int[]{
                    R.drawable.bomb5,
                    R.drawable.bomb6,
                    R.drawable.bomb7,
                    R.drawable.bomb8,
                    R.drawable.bomb9,
                    R.drawable.bomb10,
                    R.drawable.bomb11,
                    R.drawable.bomb12,
                    R.drawable.bomb13,
                    R.drawable.bomb14,
                    R.drawable.bomb15,
                    R.drawable.bomb16
            };
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            items = null;
        }

        @Override
        public int getCount() {
            return items.length;
        }
        //该方法返回值控制个位置所显示的RemoteViews
        @Override
        public RemoteViews getViewAt(int i) {
            //创建RemoteViews对象，加载布局文件
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            //更新widget_item.xml布局文件中的widget_item
            rv.setImageViewResource(R.id.widget_item, items[i]);
            //创建Intent，用于传递数据
            Intent fillIntent = new Intent();
            fillIntent.putExtra(StackWidgetProvider.EXTRA_ITEM, i);
            //设置当单击该RemoteViews时传递的fillIntent包含的数据
            rv.setOnClickFillInIntent(R.id.widget_item, fillIntent);
            //此处让线程暂停0.5秒来模拟加载该组件
            try {
                System.out.print("加载【" + i + "】位置的组件");
                Thread.sleep(500);

            } catch (Exception e) {
                e.printStackTrace();

            }
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
