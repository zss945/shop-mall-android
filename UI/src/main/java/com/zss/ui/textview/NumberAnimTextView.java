package com.zss.ui.textview;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字动画，修改数组越界问题
 * @author zm
 */
public class NumberAnimTextView extends AppCompatTextView {

    private long mDuration = 1000; // 动画持续时间 ms，默认1s

    private ValueAnimator animator;

    private TimeInterpolator mTimeInterpolator = new LinearInterpolator(); // 动画速率

    private AnimEndListener mEndListener; // 动画正常结束监听事件

    public NumberAnimTextView(Context context) {
        super(context);
    }

    public NumberAnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberAnimTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置动画持续时间，默认为1s。需要在setNumberWithAnim之前设置才有效
     * @param duration
     */
    public void setDuration(long duration) {
        if (duration > 0) {
            mDuration = duration;
        }
    }

    /**
     * 设置动画速率，默认为LinearInterpolator。需要在setNumberWithAnim之前设置才有效
     * @param timeInterpolator
     */
    public void setAnimInterpolator(TimeInterpolator timeInterpolator) {
        mTimeInterpolator = timeInterpolator;
    }

    /**
     * 设置数据并且开始动画
     * @param number
     * @param decimals new DecimalFormat("#0.00")
     */
    public void setNumberWithStartAnimator(final BigDecimal number, final DecimalFormat decimals) {
        clearAnimator();
        animator = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                BigDecimal bigDecimal = new BigDecimal(endValue.toString());
                BigDecimal fraBigDecimal = new BigDecimal(fraction);
                BigDecimal value = bigDecimal.multiply(fraBigDecimal);
                return decimals.format(value);
            }
        }, number);
        startAnimator();
    }

    // 清除动画
    public void clearAnimator() {
        if (null != animator) {
            if (animator.isRunning()) {
                animator.removeAllListeners();
                animator.removeAllUpdateListeners();
                animator.cancel();
            }
            animator = null;
        }
    }

    // 设置时常与过程处理，启动动画
    private void startAnimator() {
        if (null != animator) {
            animator.setDuration(mDuration);

            animator.setInterpolator(mTimeInterpolator);

            // 动画过程中获取当前值，显示
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    setText(valueAnimator.getAnimatedValue().toString());
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (null != mEndListener) { // 动画不是中途取消，而是正常结束
                        mEndListener.onAnimFinish();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            animator.start();
        }
    }

    public void setAnimEndListener(AnimEndListener listener) {
        mEndListener = listener;
    }

    // 动画显示数字的结束监听，当动画结束显示正确的数字时，可能需要做些处理
    public interface AnimEndListener {
        void onAnimFinish();
    }
}