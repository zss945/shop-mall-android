package com.zss.ui.row;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.zss.ui.R;

public class RowSettingSwitch extends ConstraintLayout implements IRow {

    private TextView mTitle;
    private TextView mSummary;
    private Switch mSwitchBtn;
    private View mBottomLine;

    public RowSettingSwitch(Context context) {
        super(context);
        initView();
    }

    public RowSettingSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttrs(context, attrs);
    }

    public RowSettingSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(context, attrs);
    }

    @Override
    public void initView() {
        inflate(getContext(), R.layout.row_setting_switch, this);

        mTitle = findViewById(R.id.title);
        mSummary = findViewById(R.id.summary);
        mSwitchBtn = findViewById(R.id.switch_btn);
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
                R.styleable.RowSettingSwitch);

        CharSequence titleText = typedArray
                .getText(R.styleable.RowSettingSwitch_rss_title);

        CharSequence summaryText = typedArray
                .getText(R.styleable.RowSettingSwitch_rss_summary);

        boolean bottomLineShow = typedArray
                .getBoolean(R.styleable.RowSettingSwitch_rss_bottomLineShow, true);

        if (!TextUtils.isEmpty(titleText)) {
            setTitle(titleText);
        }

        if (!TextUtils.isEmpty(summaryText)) {
            setSummary(summaryText);
        }

        setBottomLine(bottomLineShow);

        typedArray.recycle();
    }

    public void setTitle(CharSequence text) {
        mTitle.setText(text);
    }

    public void setSummary(CharSequence text) {
        mSummary.setText(text);
    }

    public void setChecked(boolean checked) {
        mSwitchBtn.setChecked(checked);
    }

}
