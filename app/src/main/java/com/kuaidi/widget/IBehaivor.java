package com.kuaidi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 */
public class IBehaivor {
    public IBehaivor(Context context, AttributeSet attrs) {
    }

    /**
     * @param parent
     * @param child
     * @param event
     * @return
     */
    public boolean onTouchEvent(View parent, View child, MotionEvent event) {
        return false;
    }


    public void onTouchMove(View parent, View child, MotionEvent event, float x, float y, float oldx, float oldy) {

    }

    public void onLayoutFinish(View parent, View child){

    }

    public void onSizeChanged(View parent, View child, int w, int h, int oldw, int oldh){

    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d("cici","onStartNestedScroll");
        return true;
    }

    public void onStopNestedScroll(View child) {
        Log.d("cici","onStopNestedScroll");
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.d("cici","onNestedScrollAccepted");
    }

    public void onNestedScroll(View scrollView, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

}