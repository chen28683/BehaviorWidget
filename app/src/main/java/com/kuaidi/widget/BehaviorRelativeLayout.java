package com.kuaidi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.kuaidi.behaviorwidget.R;

import java.lang.reflect.Constructor;

/**
 * Created by doublechen on 2016-4-13.
 * 支持behivor的realtivelayout
 */
public class BehaviorRelativeLayout extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener, NestedScrollingParent {
    private final NestedScrollingParentHelper mNestedScrollingParentHelper =
            new NestedScrollingParentHelper(this);

    public BehaviorRelativeLayout(Context context) {
        super(context);
    }

    public BehaviorRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.BehaviorRelativeLayout);
        a.recycle();
    }

    public BehaviorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("cici", "getHeight-->" + getHeight() + "h-->" + h + "oldf-->" + oldh);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams param = (LayoutParams) child.getLayoutParams();
            if (param.getBehavior() != null) {
                param.getBehavior().onSizeChanged(this, child, w, h, oldw, oldh);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onGlobalLayout() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams param = (LayoutParams) child.getLayoutParams();
            if (param.getBehavior() != null) {
                param.getBehavior().onLayoutFinish(this, child);
            }
        }
    }


    public static class LayoutParams extends RelativeLayout.LayoutParams {
        private IBehaivor mBehavior;

        public IBehaivor getBehavior() {
            return mBehavior;
        }

        public IBehaivor setBehavior(IBehaivor behavior) {
            return mBehavior = behavior;
        }

        public LayoutParams(Context c, AttributeSet attrs) {

            super(c, attrs);
            final TypedArray a = c.obtainStyledAttributes(attrs,
                    R.styleable.BehaviorRelativeLayout);
            mBehavior = parseBehavior(c, attrs, a.getString(
                    R.styleable.BehaviorRelativeLayout_behavior));
            a.recycle();
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    static final Class<?>[] CONSTRUCTOR_PARAMS = new Class<?>[]{
            Context.class,
            AttributeSet.class
    };

    static IBehaivor parseBehavior(Context context, AttributeSet attrs, String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        try {
            final Class clazz = Class.forName(name, true,
                    context.getClassLoader());
            Constructor c = clazz.getConstructor(CONSTRUCTOR_PARAMS);
            c.setAccessible(true);
            return (IBehaivor) c.newInstance(context, attrs);
        } catch (Exception e) {
            throw new RuntimeException("Could not inflate Behavior subclass " + name, e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onTouchDown(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            onTouchMove(event);
        }
        return super.onTouchEvent(event);
    }

    private void onTouchMove(MotionEvent event) {
        float moveX = event.getRawX();
        float moveY = event.getRawY();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams param = (LayoutParams) child.getLayoutParams();
            if (param.getBehavior() != null) {
                param.getBehavior().onTouchMove(this, child, event, moveX, moveY, lastX, lastY);
            }
        }
        lastY = moveY;
        lastX = moveX;
    }

    public float lastX;
    public float lastY;

    private void onTouchDown(MotionEvent event) {
        lastX = event.getRawX();
        lastY = event.getRawY();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d("cici","onStartNestedScroll");
        return true;
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.d("cici","onStopNestedScroll");
        mNestedScrollingParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.d("cici","onNestedScrollAccepted");
        mNestedScrollingParentHelper.onNestedScrollAccepted(child,target,axes);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d("cici","onNestedScroll");
        Log.d("cici","dxConsumed -->" +dxConsumed + " dyConsumed-->"+dyConsumed);
        Log.d("cici","dxUnconsumed -->" +dxUnconsumed + " dyUnconsumed-->"+dyUnconsumed);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams param = (LayoutParams) child.getLayoutParams();
            if (param.getBehavior() != null) {
                param.getBehavior().onNestedScroll(target ,child, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
            }
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }
}