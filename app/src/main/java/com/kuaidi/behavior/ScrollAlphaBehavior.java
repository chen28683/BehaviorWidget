package com.kuaidi.behavior;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kuaidi.widget.IBehaivor;

/**
 * Created by doublechen on 2016-5-4.
 */
public class ScrollAlphaBehavior extends IBehaivor {
    private int originHeight;
    private int maxHeight = 400;

    public ScrollAlphaBehavior(Context context, AttributeSet attrs) {
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
        if (scrollView.getScrollY() <= maxHeight - originHeight) {
            ViewCompat.setAlpha(target, scrollView.getScrollY() * 1.0f / (maxHeight - originHeight));
        } else if (scrollView.getScrollY() == 0) {
            dxUnconsumed = dxUnconsumed < maxHeight ? dyUnconsumed : maxHeight;
            ViewCompat.setAlpha(target, target.getAlpha() * 255 - dxUnconsumed / maxHeight);
        }
    }
}
