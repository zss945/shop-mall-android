package com.zss.mall.fragment.address;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zss.kit.DPUtils;
import com.zss.kit.FastjsonUtils;
import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.bean.Address;
import com.zss.mall.bean.Event;
import com.zss.mall.fragment.address_edit.AddressEditFragment;
import com.zss.ui.activity.ToolbarFragmentActivity;
import com.zss.ui.adapter.recyclerview.BaseRecyclerAdapter;
import com.zss.ui.adapter.recyclerview.LinearDividerItemDecoration;
import com.zss.ui.adapter.recyclerview.ViewHolder;
import com.zss.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressFragment extends BaseFragment<AddressPresenter> implements AddressView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    BaseRecyclerAdapter<Address> mAdapter;

    Address mSelectAddress; //选择的地址

    @Override
    public int getViewId() {
        return R.layout.fragment_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL, DPUtils.dp2px(getResources(), 1)));
        mAdapter = new BaseRecyclerAdapter<Address>(R.layout.item_address) {
            @Override
            protected void convert(ViewHolder viewHolder, Address item, int position) {
                ViewGroup viewGroup = viewHolder.findViewById(R.id.root_layout);
                TextView consignee = viewHolder.findViewById(R.id.consignee);
                TextView phone = viewHolder.findViewById(R.id.phone);
                TextView address = viewHolder.findViewById(R.id.address);
                TextView isDefault = viewHolder.findViewById(R.id.is_default);
                ImageView rightImage = viewHolder.findViewById(R.id.right_image);
                if (mSelectAddress != null && mSelectAddress.id.equals(item.id)) {
                    viewGroup.setBackgroundResource(R.color.colorOrange);
                } else {
                    viewGroup.setBackgroundResource(R.color.colorWhite);
                }
                consignee.setText(item.consignee);
                phone.setText(item.phone);
                address.setText(String.format("%s %s", item.areaName, item.address));
                if (item.isDefault == 0) {
                    isDefault.setVisibility(View.GONE);
                } else {
                    isDefault.setVisibility(View.VISIBLE);
                }
                rightImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        assert getActivity() != null;
                        Bundle args = new Bundle();
                        args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(item));
                        ToolbarFragmentActivity.createFragment(getActivity(), AddressEditFragment.class, args);
                    }
                });
            }

            @Override
            protected void onItemClick(Address item, int position) {
                super.onItemClick(item, position);
                if (mSelectAddress != null) {
                    Event.SelectAddressEvent event = new Event.SelectAddressEvent();
                    event.address = item;
                    EventBus.getDefault().post(event);
                    finish();
                }
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        setSupportEventBus();
        getToolbar().setTitle("地址管理");
        assert getArguments() != null;
        getPresenter().queryAddressList();
        String text1 = getArguments().getString(Constants.INTENT_KEY1);
        mSelectAddress = FastjsonUtils.toObject(text1, Address.class);
    }

    @Override
    public AddressPresenter createPresenter() {
        return new AddressPresenter(new AddressModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R.id.add_btn)
    public void onClick() {
        ToolbarFragmentActivity.createFragment(getActivity(), AddressEditFragment.class);
    }

    @Override
    public void setAddressList(List<Address> addressList) {
        mAdapter.replaceAll(addressList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.ReloadAddressEvent event) {
        getPresenter().queryAddressList();
    }

}
