package com.zss.mall.fragment.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.bean.Event;
import com.zss.mall.bean.Member;
import com.zss.mall.fragment.address.AddressFragment;
import com.zss.mall.fragment.login.LoginFragment;
import com.zss.mall.fragment.order.OrderFragment;
import com.zss.mall.fragment.personal_center.PersonalCenterFragment;
import com.zss.mall.fragment.setting.SettingFragment;
import com.zss.mall.kit.AppUtils;
import com.zss.ui.activity.ToolbarFragmentActivity;
import com.zss.ui.fragment.BaseFragment;
import com.zss.ui.mvp.IPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 *
 * @author zm
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.photo)
    ImageView mPhoto;

    @BindView(R.id.go_login)
    TextView mGoLogin;

    @Override
    public int getViewId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        setSupportEventBus();
        onEvent(new Event.LoginEvent());
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.photo, R.id.go_login, R.id.un_pay_order, R.id.un_send_order, R.id.un_receive_order, R.id.finished_order, R.id.address_mgr, R.id.help, R.id.setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo:
                if (AppUtils.isLogin()) {
                    ToolbarFragmentActivity.createFragment(requireContext(), PersonalCenterFragment.class);
                } else {
                    ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class);
                }
                break;
            case R.id.go_login:
                if (!AppUtils.isLogin()) {
                    ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class, null);
                }
                break;
            case R.id.un_pay_order:
                if (AppUtils.isLogin()) {
                    Bundle args = new Bundle();
                    args.putInt(Constants.INTENT_KEY1, 0);
                    ToolbarFragmentActivity.createFragment(requireContext(), OrderFragment.class, args);
                } else {
                    ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class);
                }
                break;
            case R.id.un_send_order:
                if (AppUtils.isLogin()) {
                    Bundle args = new Bundle();
                    args.putInt(Constants.INTENT_KEY1, 1);
                    ToolbarFragmentActivity.createFragment(requireContext(), OrderFragment.class, args);
                } else {
                    ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class);
                }
                break;
            case R.id.un_receive_order:
                if (AppUtils.isLogin()) {
                    Bundle args = new Bundle();
                    args.putInt(Constants.INTENT_KEY1, 2);
                    ToolbarFragmentActivity.createFragment(requireContext(), OrderFragment.class, args);
                } else {
                    ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class);
                }
                break;
            case R.id.finished_order:
                if (AppUtils.isLogin()) {
                    Bundle args = new Bundle();
                    args.putInt(Constants.INTENT_KEY1, 3);
                    ToolbarFragmentActivity.createFragment(requireContext(), OrderFragment.class, args);
                } else {
                    ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class);
                }
                break;
            case R.id.address_mgr:
                if (AppUtils.isLogin()) {
                    ToolbarFragmentActivity.createFragment(requireContext(), AddressFragment.class);
                } else {
                    ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class);
                }
                break;
            case R.id.help:
                break;
            case R.id.setting:
                ToolbarFragmentActivity.createFragment(requireContext(), SettingFragment.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.LoginEvent event) {
        Member member = AppUtils.getMember();
        if (member != null) {
            mGoLogin.setText(AppUtils.formatPhone(member.mobile));
        } else {
            mGoLogin.setText("登录/注册 >");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.CartEvent event) {

    }

}
