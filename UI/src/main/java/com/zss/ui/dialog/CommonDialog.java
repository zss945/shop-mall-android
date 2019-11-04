package com.zss.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.zss.ui.R;

/**
 * 公用对话框
 *
 * @author zm
 */
public class CommonDialog extends Dialog {

    private TextView mTitle;
    private TextView mContext;
    private ImageView mClose;
    private TextView mCancel;
    private TextView mSure;
    private boolean isDismiss = true;
    private OnDismissListener mOnDismissListener;
    private View.OnClickListener mOnSureListener, mOnCancelListener, mOnCloseListener;

    public CommonDialog(Context context) {
        super(context, R.style.AppTheme_Dialog);
        setContentView(R.layout.dialog);
        setCanceledOnTouchOutside(true);
        initView();
    }

    public CommonDialog(Context context, CharSequence title, CharSequence content) {
        this(context);
        mTitle.setText(title);
        mContext.setText(content);
    }

    public CommonDialog(Context context, int title, int content) {
        this(context, context.getString(title), context.getString(content));
    }

    private void initView() {
        mTitle = findViewById(R.id.title);
        mContext = findViewById(R.id.content);
        mClose = findViewById(R.id.close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCloseListener != null) {
                    mOnCloseListener.onClick(v);
                }
                if (isDismiss) {
                    dismiss();
                }

            }
        });
        mCancel = findViewById(R.id.cancel_btn);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCancelListener != null) {
                    mOnCancelListener.onClick(v);
                }

                if (isDismiss) {
                    dismiss();
                }
            }
        });
        mSure = findViewById(R.id.sure_btn);
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSureListener != null) {
                    mOnSureListener.onClick(v);
                }
                if (isDismiss) {
                    dismiss();
                }
            }
        });
        super.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mOnDismissListener != null) {
                    mOnDismissListener.onDismiss(dialog);
                }
                removeMiddleView();
                removeBottomView();
            }
        });
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    @Override
    public void setTitle(CharSequence text) {
        mTitle.setText(text);
    }

    public void setContent(CharSequence text) {
        mContext.setText(text);
    }

    public void setOnClickCancelListener(CharSequence text, View.OnClickListener listener) {
        mCancel.setText(text);
        mCancel.setVisibility(View.VISIBLE);
        this.mOnCancelListener = listener;
    }

    public void setOnClickCancelListener(View.OnClickListener listener) {
        mCancel.setVisibility(View.VISIBLE);
        this.mOnCancelListener = listener;

    }

    public void setOnClickSureListener(CharSequence text, View.OnClickListener listener) {
        mSure.setText(text);
        mSure.setVisibility(View.VISIBLE);
        this.mOnSureListener = listener;

    }

    public void setOnClickSureListener(View.OnClickListener listener) {
        mSure.setVisibility(View.VISIBLE);
        this.mOnSureListener = listener;
    }

    public void setOnClickCloseListener(View.OnClickListener listener) {
        this.mOnCloseListener = listener;
    }

    public void setMiddleView(View v) {
        setView(R.id.middle_layout, v);
    }

    public void setMiddleView(int layoutResId) {
        setView(R.id.middle_layout, layoutResId);
    }

    private void setView(int rootId, View v) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ViewGroup layout = findViewById(rootId);
        layout.setVisibility(View.VISIBLE);
        layout.removeAllViews();
        layout.addView(v, lp);
    }

    private void setView(int rootId, int layoutId) {
        ViewGroup layout = findViewById(rootId);
        layout.setVisibility(View.VISIBLE);
        layout.removeAllViews();
        getLayoutInflater().inflate(layoutId, layout);
    }

    private void removeMiddleView() {
        ViewGroup layout = findViewById(R.id.middle_layout);
        layout.removeAllViews();
    }

    private void removeBottomView() {
        ViewGroup layout = findViewById(R.id.bottom_layout);
        layout.removeAllViews();
    }

    public void setDisplayTopEnable(boolean enable) {
        if (enable) {
            findViewById(R.id.top_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.top_layout).setVisibility(View.GONE);
        }
    }

    public void setDisplayMiddleEnable(boolean enable) {
        if (enable) {
            findViewById(R.id.middle_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.middle_layout).setVisibility(View.GONE);
        }
    }

    public void setDisplayBottomEnable(boolean enable) {
        if (enable) {
            findViewById(R.id.bottom_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.bottom_layout).setVisibility(View.GONE);
        }
    }

    public void setDismiss(boolean dismiss) {
        isDismiss = dismiss;
    }

    public void show() {
        try {
            super.show();
        } catch (Exception e) {
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }

}
