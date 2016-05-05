package com.kuaidi.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.kuaidi.widget.IBehaivor;

/**
 * Created by doublechen on 2016-5-5.
 */
public class ScrollScaleBehavior extends IBehaivor{
    private int originHeight;
    private int maxHeight = 400;

    public ScrollScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLayoutFinish(View parent, View child) {
        super.onLayoutFinish(parent, child);
        if(originHeight == 0) {
            originHeight = child.getHeight();
        }
    }

    @Override
    public void onNestedScroll(View scrollView, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(scrollView, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (scrollView.getScrollY() == 0) {
            ViewGroup.LayoutParams params = target.getLayoutParams();
            params.height = params.height+Math.abs(dyUnconsumed);
            if(params.height >= maxHeight){
                params.height = maxHeight;
            }
            target.setLayoutParams(params);
        }else if(scrollView.getScaleY() >0 ){
            ViewGroup.LayoutParams params = target.getLayoutParams();
            params.height = params.height - Math.abs(dyConsumed);
            if(params.height < originHeight){
                params.height = originHeight;
            }
            target.setLayoutParams(params);
        }
    }
}
