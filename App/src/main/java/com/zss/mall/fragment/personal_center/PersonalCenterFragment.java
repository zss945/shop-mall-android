package com.zss.mall.fragment.personal_center;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zss.kit.DPUtils;
import com.zss.mall.R;
import com.zss.mall.bean.Member;
import com.zss.mall.kit.AppUtils;
import com.zss.ui.fragment.BaseFragment;
import com.zss.ui.mvp.IPresenter;
import com.zss.ui.row.RowSettingText;

import butterknife.BindView;

public class PersonalCenterFragment extends BaseFragment {


    @BindView(R.id.user_id)
    RowSettingText mUserId;

    @BindView(R.id.phone)
    RowSettingText mPhone;

    @Override
    public int getViewId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        getToolbar().setTitle("个人中心");
        Member member = AppUtils.getMember();

        mUserId.setStatus(member.sn);
        mUserId.setRightImage(false);
        int space = DPUtils.dp2px(getResources(), 16);
        mUserId.setStatusPadding(0, 0, space, 0);
        mPhone.setStatus(AppUtils.formatPhone(member.mobile));
        mPhone.setRightImage(false);
        mPhone.setStatusPadding(0, 0, space, 0);
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

}
