package com.zss.ui.row;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.zss.ui.R;

public class RowInputEdit extends ConstraintLayout implements IRow {

    private ImageView mLeftImage;
    private ImageView mRightImage;
    private TextInputLayout mTextInputLayout;
    private TextInputEditText mTextInputEditText;
    private View mBottomLine;

    public RowInputEdit(Context context) {
        super(context);
        initView();
    }

    public RowInputEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttrs(context, attrs);
    }

    public RowInputEdit(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(context, attrs);
    }

    @Override
    public void initView() {
        inflate(getContext(), R.layout.row_input_edit, this);
        mLeftImage = findViewById(R.id.left_image);
        mTextInputLayout = findViewById(R.id.text_input_layout);
        mTextInputEditText = findViewById(R.id.text_input_edit_text);
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
        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RowEdit);

        int leftResId = typedArray
                .getResourceId(R.styleable.RowEdit_re_leftImage, 0);

        int rightResId = typedArray
                .getResourceId(R.styleable.RowEdit_re_rightImage, 0);

        int inputType = typedArray.getInteger(R.styleable.RowEdit_re_input_type, 1);

        switch (inputType) {
            case 1:
                setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 2:
                setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 3:
                setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 4:
                setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 5:
                setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 6:
                setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
        }

        String hintText = typedArray.getString(R.styleable.RowEdit_re_hint);

        boolean bottomLineShow = typedArray
                .getBoolean(R.styleable.RowEdit_re_bottomLineShow, true);

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

    public void setInputType(int inputType) {
        mTextInputEditText.setInputType(inputType);
    }

    public void setText(CharSequence text){
        mTextInputEditText.setText(text);
    }

    public void setHint(CharSequence text) {
        mTextInputLayout.setHint(text);
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
        Editable editable = mTextInputEditText.getText();
        if (editable != null) {
            return editable.toString().trim();
        }
        return "";
    }
}
