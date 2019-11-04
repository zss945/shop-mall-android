package com.zss.ui.row;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zss.ui.R;

public class RowText extends ConstraintLayout implements IRow {

    private ImageView mLeftImage;
    private ImageView mRightImage;
    private TextView mTextView;
    private View mBottomLine;

    public RowText(Context context) {
        super(context);
        initView();
    }

    public RowText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttrs(context, attrs);
    }

    public RowText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RowText);

        int leftResId = typedArray
                .getResourceId(R.styleable.RowText_rt_leftImage, 0);

        int rightResId = typedArray
                .getResourceId(R.styleable.RowText_rt_rightImage, 0);

        String text = typedArray.getString(R.styleable.RowText_rt_text);

        String hintText = typedArray.getString(R.styleable.RowText_rt_hint);

        boolean bottomLineShow = typedArray
                .getBoolean(R.styleable.RowText_rt_bottomLineShow, true);

        setText(text);

        setHint(hintText);

        if (leftResId != 0) {
            setLeftImageResource(leftResId);
        }

        if (rightResId != 0) {
            setRightImageResource(leftResId);
        }

        setBottomLine(bottomLineShow);

        typedArray.recycle();
    }

    @Override
    public void initView() {
        inflate(getContext(), R.layout.row_text, this);
        mLeftImage = findViewById(R.id.left_image);
        mTextView = findViewById(R.id.edit_text);
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

    public void setText(CharSequence text) {
        mTextView.setText(text);
    }

    public void setHint(CharSequence text) {
        mTextView.setHint(text);
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

    public void setLeftImageResource(int resId) {
        mLeftImage.setVisibility(VISIBLE);
        mLeftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        mRightImage.setVisibility(VISIBLE);
        mRightImage.setImageResource(resId);
    }

    public String getText() {
        return mTextView.getText().toString();
    }

}