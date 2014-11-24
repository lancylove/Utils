package com.lancy.utils.differentlistview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lancy.utils.R;
import com.lancy.utils.differentlistview.SlidView.OnSlideListener;

public class ListviewActivity extends Activity implements OnSlideListener, OnItemClickListener, OnTouchListener,OnClickListener{

    private ListView listview;
    private ArrayList<MyItem> list;
    private Context mcontext;
    SlideAdapter slideAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.differentlistview);
        mcontext = this;
        listview = (ListView) findViewById(R.id.differentlistview);
        list = new ArrayList<MyItem>();
        list.add(new MyItem("11"));
        list.add(new MyItem("12"));
        list.add(new MyItem("13"));
        slideAdapter = new SlideAdapter(list);
        listview.setAdapter(slideAdapter);
        listview.setOnItemClickListener(this);
        listview.setOnTouchListener(this);

    }

    private class SlideAdapter extends BaseAdapter {
        ArrayList<MyItem> list;
        private LayoutInflater mInflater;

        SlideAdapter(ArrayList<MyItem> list) {
            super();
            mInflater = getLayoutInflater();
            this.list = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SlidView slidView = (SlidView) convertView;
            ViewHolder holder = null;
            if(slidView == null){
                View v = mInflater.from(mcontext).inflate(R.layout.listviewitem, null);
                slidView = new SlidView(mcontext);
                slidView.setContentView(v);
                slidView.setOnSlideListener(ListviewActivity.this);
                holder = new ViewHolder(slidView);
                slidView.setTag(holder);
            }else{
                holder = (ViewHolder) slidView.getTag();
            } 
            MyItem item = list.get(position);
            item.slidview = slidView;
            item.slidview.shrink();
           holder.tv.setText(list.get(position).getText());
           holder.deleteHolder.setOnClickListener(ListviewActivity.this);
          TextView d = (TextView) holder.deleteHolder.findViewById(R.id.delete);
          d.setText("delete"+position);
            return slidView;
        }

    }
    SlidView mFocusedItemView = null;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-gene rated method stub
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int x = (int) event.getX();
                int y = (int) event.getY();
                //我们想知道当前点击了哪一行  
                int position = listview.pointToPosition(x, y);
//                Log.e("onTouch", "postion=" + position);  
                if (position != ListView.INVALID_POSITION) {
                    //得到当前点击行的数据从而取出当前行的item。  
                    //可能有人怀疑，为什么要这么干？为什么不用getChildAt(position)？  
                    //因为ListView会进行缓存，如果你不这么干，有些行的view你是得不到的。  
                    MyItem data = (MyItem) listview.getItemAtPosition(position);
                    mFocusedItemView = data.getSlidview();
//                    Log.e("onTouch", "FocusedItemView=" + mFocusedItemView.getButtonText());
                }else{
                    mFocusedItemView = null;
                }
            }
            default:
                break;
        }
        //向当前点击的view发送滑动事件请求，其实就是向SlideView发请求  
        if (mFocusedItemView != null) {
//            Log.i("onTouch", "Slidview不为空");
            mFocusedItemView.onRequireTouchEvent(event);
        }
        return super.onTouchEvent(event);  
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Toast.makeText(mcontext, ""+position+1, 0).show();

    }

    SlidView mLastSlideViewWithStatusOn = null;
    
    @Override
    public void onSlide(View view, int status) {
        // TODO Auto-generated method stub
        Log.i("ListviewActivity", "status:" + status);
        
        // 如果当前存在已经打开的SlideView，那么将其关闭  
        if (mLastSlideViewWithStatusOn != null  
                && mLastSlideViewWithStatusOn != view) {  
            mLastSlideViewWithStatusOn.shrink();  
        }  
        // 记录本次处于打开状态的view  
        if (status == SLIDE_STATUS_ON) {  
            mLastSlideViewWithStatusOn = (SlidView) view;  
        } 
        
        
    }
    
    private class ViewHolder{
        TextView tv;
        public ViewGroup deleteHolder; 
        
        public ViewHolder(View v){
            tv = (TextView) v.findViewById(R.id.itemtv);
            deleteHolder = (ViewGroup) v.findViewById(R.id.holder);
        }
        
    }

    private class MyItem {
        private String text;
        private SlidView slidview;

        public MyItem(String text) {
            super();
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public SlidView getSlidview() {
            return slidview;
        }

        public void setSlidview(SlidView slidview) {
            this.slidview = slidview;
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.holder) {  
            int position = listview.getPositionForView(v);  
            if (position != ListView.INVALID_POSITION) {  
                list.remove(position);  
                slideAdapter.notifyDataSetChanged();  
            }  
            Log.e("onClick", "onClick v=" + v);  
        } 
    }

}
