package com.zss.ui.row;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zss.ui.R;

public class RowSettingText extends ConstraintLayout implements IRow {

    private ImageView mLeftImage;
    private ImageView mRightImage;
    private TextView mTitle;
    private TextView mSummary;
    private TextView mStatus;
    private View mBottomLine;

    public RowSettingText(Context context) {
        super(context);
        initView();
    }

    public RowSettingText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttrs(context, attrs);
    }

    public RowSettingText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(context, attrs);
    }

    @Override
    public void initView() {
        inflate(getContext(), R.layout.row_setting_text, this);
        mLeftImage = findViewById(R.id.left_image);
        mTitle = findViewById(R.id.title);
        mSummary = findViewById(R.id.summary);
        mStatus = findViewById(R.id.status);
        mRightImage = findViewById(R.id.right_image);
        mBottomLine = findViewById(R.id.bottom_line);
    }

    @Override
    public void setBottomLine(boolean isShow) {
        if (isShow) {
            mBottomLine.setVisibility(VISIBLE);
        } else {
            mBottomLine.setVisibility(GONE);
        }
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RowSettingText);

        int leftResId = typedArray
                .getResourceId(R.styleable.RowSettingText_rst_leftImage, 0);

        int rightResId = typedArray
                .getResourceId(R.styleable.RowSettingText_rst_rightImage, 0);

        CharSequence titleText = typedArray
                .getText(R.styleable.RowSettingText_rst_title);

        CharSequence summaryText = typedArray
                .getText(R.styleable.RowSettingText_rst_summary);

        CharSequence statusText = typedArray
                .getText(R.styleable.RowSettingText_rst_status);

        boolean bottomLineShow = typedArray
                .getBoolean(R.styleable.RowSettingText_rst_bottomLineShow, true);

        if (leftResId != 0) {
            setLeftImageResource(leftResId);
        }

        if (rightResId != 0) {
            setRightImageResource(leftResId);
        }

        if (!TextUtils.isEmpty(titleText)) {
            setTitle(titleText);
        }

        if (!TextUtils.isEmpty(summaryText)) {
            setSummary(summaryText);
        }

        if (!TextUtils.isEmpty(statusText)) {
            setStatus(statusText);
        }

        setBottomLine(bottomLineShow);

        typedArray.recycle();
    }

    public void setLeftImage(boolean isShow) {
        if (isShow) {
            mLeftImage.setVisibility(VISIBLE);
        } else {
            mLeftImage.setVisibility(GONE);
        }
    }

    public void setRightImage(boolean isShow){
        if (isShow) {
            mRightImage.setVisibility(VISIBLE);
        } else {
            mRightImage.setVisibility(GONE);
        }
    }

    public void setStatusPadding(int left, int top, int right, int bottom){
       mStatus.setPadding(left, top, right, bottom);
    }

    public void setLeftImageResource(int resId) {
        mLeftImage.setVisibility(VISIBLE);
        mLeftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        mRightImage.setVisibility(VISIBLE);
        mRightImage.setImageResource(resId);
    }

    public void setTitle(CharSequence text) {
        mTitle.setText(text);
    }

    public void setSummary(CharSequence text) {
        mSummary.setText(text);
    }

    public void setStatus(CharSequence text) {
        mStatus.setText(text);
    }

}
