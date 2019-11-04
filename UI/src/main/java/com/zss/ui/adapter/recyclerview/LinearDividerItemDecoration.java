package com.zss.ui.adapter.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

public class LinearDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int mOrientation;
    private TextPaint mTextPaint;
    private float listDividerSize;

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private int listDividerColor = Color.parseColor("#e7e7e7");

    public LinearDividerItemDecoration(int orientation, int listDividerSize) {
        mTextPaint = new TextPaint();
        mTextPaint.setColor(this.listDividerColor);
        this.listDividerSize = listDividerSize;
        setOrientation(orientation);
    }

    public LinearDividerItemDecoration(int orientation, int listDividerSize, int listDividerColor) {
        mTextPaint = new TextPaint();
        this.listDividerColor = listDividerColor;
        mTextPaint.setColor(this.listDividerColor);
        this.listDividerSize = listDividerSize;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == HORIZONTAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    /**
     * 绘制垂直分割线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int rectLeft = parent.getPaddingLeft();
        int rectRight = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int rectTop = child.getBottom() + layoutParams.bottomMargin;
            float rectBottom = rectTop + listDividerSize;
            c.drawRect(rectLeft, rectTop, rectRight, rectBottom, mTextPaint);
        }
    }

    /**
     * 绘制水平分割线
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int rectTop = parent.getPaddingTop();
        int rectBottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int rectLeft = child.getRight() + layoutParams.rightMargin;
            float rectRight = rectLeft + listDividerSize;
            c.drawRect(rectLeft, rectTop, rectRight, rectBottom, mTextPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, (int) listDividerSize);
        } else {
            outRect.set(0, 0, (int) listDividerSize, 0);
        }
    }

}