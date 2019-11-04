package com.zss.ui.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zss.ui.R;
import com.zss.ui.dialog.CommonDialog;
import com.zss.ui.row.RowEdit;

public class NumberButton extends LinearLayout {

    private TextView mNumber;
    private int minValue = 1;
    private int maxValue = 200;
    private OnNumberChangedListener mListener;

    public void setOnNumberChangedListener(OnNumberChangedListener mListener) {
        this.mListener = mListener;
    }

    public NumberButton(Context context) {
        super(context);
        initView();
    }

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NumberButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.button_number, this);
        mNumber = findViewById(R.id.number);
        findViewById(R.id.sub).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = getNumber();
                if (count > minValue) {
                    count--;
                    mNumber.setText(String.valueOf(count));
                }
            }
        });
        findViewById(R.id.add).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = getNumber();
                if (count < maxValue) {
                    count++;
                    mNumber.setText(String.valueOf(count));

                }
            }
        });
        mNumber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonDialog dialog = new CommonDialog(getContext());
                dialog.setTitle("输入购物车数量");
                dialog.setMiddleView(R.layout.dialog_button_number);
                final RowEdit mRowEdit = dialog.findViewById(R.id.row_edit);
                mRowEdit.setText(String.valueOf(getNumber()));
                mRowEdit.setSelection(mNumber.getText().length());
                dialog.setOnClickCancelListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialog.setOnClickSureListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            int number = Integer.parseInt(mRowEdit.getText());
                            if (number <= 0) {
                                number = minValue;
                            } else if (number > maxValue) {
                                number = maxValue;
                            }
                            mNumber.setText(String.valueOf(number));
                            if (mListener != null) {
                                mListener.onNumberChanged(number);
                            }

                        }
                    }
                });
                dialog.show();
                mRowEdit.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showInput(mRowEdit.getEditText());
                    }
                }, 500);
            }
        });
        mNumber.setText(String.valueOf(minValue));

    }

    public int getNumber() {
        try {
            return Integer.parseInt(mNumber.getText().toString());
        } catch (Exception e) {
            mNumber.setText(String.valueOf(minValue));
            return minValue;
        }
    }

    public void setNumber(String number){
        mNumber.setText(number);
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public interface OnNumberChangedListener {
        void onNumberChanged(int number);
    }

    /**
     * 显示软键盘
     */
    public void showInput(EditText editText) {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }

    /**
     * 隐藏键盘
     */
    public void hideInput(EditText editText) {
        if (editText != null) {
            InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
