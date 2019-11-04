package com.zss.mall.fragment.address_edit;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.zss.kit.FastjsonUtils;
import com.zss.kit.ToastUtils;
import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.activity.select_area.SelectAreaActivity;
import com.zss.mall.bean.Address;
import com.zss.mall.bean.Area;
import com.zss.mall.bean.Event;
import com.zss.mall.kit.AppUtils;
import com.zss.ui.fragment.BaseFragment;
import com.zss.ui.row.RowEdit;
import com.zss.ui.row.RowText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressEditFragment extends BaseFragment<AddressEditPresenter> implements AddressEditView {

    @BindView(R.id.area)
    RowText mArea;

    @BindView(R.id.address)
    RowEdit mAddress;

    @BindView(R.id.consignee)
    RowEdit mConsignee;

    @BindView(R.id.phone)
    RowEdit mPhone;

    @BindView(R.id.is_default)
    CheckBox mIsDefault;

    Address mEditAddress;

    List<Area> mSelectAreaList;

    @Override
    public int getViewId() {
        return R.layout.fragment_address_edit;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        setSupportEventBus();
        if (getArguments() != null && getArguments().containsKey(Constants.INTENT_KEY1)) {
            getToolbar().setTitle("修改地址");
            String text1 = getArguments().getString(Constants.INTENT_KEY1);
            mEditAddress = FastjsonUtils.toObject(text1, Address.class);
            mAddress.setText(mEditAddress.address);
            mConsignee.setText(mEditAddress.consignee);
            mPhone.setText(mEditAddress.phone);
            mIsDefault.setChecked(mEditAddress.isDefault == 1);
            getPresenter().queryCurrentLevelAreaById(mEditAddress.areaId);
        } else {
            getToolbar().setTitle("新增地址");
        }
    }

    @OnClick({R.id.area, R.id.save_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.area:
                if (mSelectAreaList != null && !mSelectAreaList.isEmpty()) {
                    Bundle args = new Bundle();
                    args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(mSelectAreaList));
                    goIntent(SelectAreaActivity.class, args);
                } else {
                    goIntent(SelectAreaActivity.class);
                }
                break;
            case R.id.save_btn:
                String addressText = mAddress.getText();
                String consigneeText = mConsignee.getText();
                String phoneText = mPhone.getText();
                if (mSelectAreaList == null || mSelectAreaList.isEmpty()) {
                    ToastUtils.showToast("省份，城市，区县不能为空");
                    return;
                }
                if (addressText.length() == 0) {
                    ToastUtils.showToast("地址不能为空");
                    return;
                }

                if (consigneeText.length() == 0) {
                    ToastUtils.showToast("收货人不能为空");
                    return;
                }

                if (phoneText.length() == 0) {
                    ToastUtils.showToast("手机号码不能为空");
                    return;

                } else if (!AppUtils.isValidateMobile(phoneText)) {
                    ToastUtils.showToast("手机号码格式不正确");
                    return;
                }
                Area childArea = mSelectAreaList.get(mSelectAreaList.size() - 1);
                if (mEditAddress != null) {
                    getPresenter().updateAddress(mEditAddress.id, childArea.id, addressText, consigneeText, phoneText, mIsDefault.isChecked() ? 1 : 0);
                } else {
                    getPresenter().saveAddress(childArea.id, addressText, consigneeText, phoneText, mIsDefault.isChecked() ? 1 : 0);
                }
                break;
        }
    }

    @Override
    public AddressEditPresenter createPresenter() {
        return new AddressEditPresenter(new AddressEditModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.SelectAreaEvent event) {
        setSelectAreaList(event.areaList);
    }

    @Override
    public void saveUpdateSuccess() {
        EventBus.getDefault().post(new Event.ReloadAddressEvent());
        finish();
    }

    @Override
    public void setSelectAreaList(List<Area> areaList) {
        mSelectAreaList = areaList;
        StringBuilder mBuilder = new StringBuilder();
        for (Area item : areaList) {
            mBuilder.append(" ");
            mBuilder.append(item.name);
        }
        mArea.setText(mBuilder.substring(1));
    }
}
