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

import com.zss.ui.R;

public class RowEdit extends ConstraintLayout implements IRow {

    private ImageView mLeftImage;
    private ImageView mRightImage;
    private EditText mEditText;
    private View mBottomLine;

    public RowEdit(Context context) {
        super(context);
        initView();
    }

    public RowEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttrs(context, attrs);
    }

    public RowEdit(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RowEdit);

        int leftResId = typedArray
                .getResourceId(R.styleable.RowEdit_re_leftImage, 0);

        int rightResId = typedArray
                .getResourceId(R.styleable.RowEdit_re_rightImage, 0);

        int inputType = typedArray.getInteger(R.styleable.RowEdit_re_input_type, 1);

        switch (inputType) {
            case 0:
                setInputType(InputType.TYPE_NULL);
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

    @Override
    public void initView() {
        inflate(getContext(), R.layout.row_edit, this);
        mLeftImage = findViewById(R.id.left_image);
        mEditText = findViewById(R.id.edit_text);
        mRightImage = findViewById(R.id.right_image);
        mBottomLine = findViewById(R.id.bottom_line);
    }

    @Override
    public void setBottomLine(boolean isShow) {
        if(isShow){
            mBottomLine.setVisibility(VISIBLE);
        } else {
            mBottomLine.setVisibility(GONE);
        }
    }

    public void setInputType(int inputType) {
        mEditText.setInputType(inputType);
    }

    public void setHint(CharSequence text) {
        mEditText.setHint(text);
    }

    public void setSelection(int index){
        mEditText.setSelection(index);
    }

    public void setLeftImageResource(int resId) {
        mLeftImage.setVisibility(VISIBLE);
        mLeftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        mRightImage.setVisibility(VISIBLE);
        mRightImage.setImageResource(resId);
    }

    public void setText(CharSequence text){
        mEditText.setText(text);
    }

    public String getText() {
        Editable editable = mEditText.getText();
        if (editable != null) {
            return editable.toString().trim();
        }
        return "";
    }

    public EditText getEditText() {
        return mEditText;
    }
}