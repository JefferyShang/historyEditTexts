package com.example.historyedittexts;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ScrollView;

/**
 * 自定义HistoryScrollView
 * 
 * @author Jeffery<br>
 *         创建日期：2016年10月9日
 * @version 1.0
 *
 */
public class HistoryScrollView extends ScrollView {  
        private Context mContext;  
      
        public HistoryScrollView(Context context) {  
            super(context);  
            init(context);  
        }  
      
        public HistoryScrollView(Context context, AttributeSet attrs) {  
            super(context, attrs);  
            init(context);  
      
        }  
      
        public HistoryScrollView(Context context, AttributeSet attrs, int defStyleAttr) {  
            super(context, attrs, defStyleAttr);  
            init(context);  
        }  
      
        private void init(Context context) {  
            mContext = context;  
        }  
      
        @Override  
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
            try {  
                //最大高度显示为屏幕内容高度的一半  
                Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();  
                DisplayMetrics d = new DisplayMetrics();  
                display.getMetrics(d);  
            //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）  
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(d.heightPixels / 3, MeasureSpec.AT_MOST);  
      
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            //重新计算控件高、宽  
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        }  
    }  