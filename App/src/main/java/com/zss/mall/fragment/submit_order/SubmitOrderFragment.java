package com.zss.mall.fragment.submit_order;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.zss.kit.BigDecimalUtils;
import com.zss.kit.DPUtils;
import com.zss.kit.FastjsonUtils;
import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.bean.Address;
import com.zss.mall.bean.Event;
import com.zss.mall.bean.Order;
import com.zss.mall.bean.SumFee;
import com.zss.mall.fragment.address.AddressFragment;
import com.zss.mall.fragment.select_pay.SelectPayFragment;
import com.zss.mall.kit.AppUtils;
import com.zss.mall.room.entity.Cart;
import com.zss.ui.activity.ToolbarFragmentActivity;
import com.zss.ui.adapter.recyclerview.BaseVLayoutAdapter;
import com.zss.ui.adapter.recyclerview.ViewHolder;
import com.zss.ui.button.NumberButton;
import com.zss.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class SubmitOrderFragment extends BaseFragment<SubmitOrderPresenter> implements SubmitOrderView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.total)
    TextView mTotal;

    BaseVLayoutAdapter<Cart> mCartAdapter;

    BaseVLayoutAdapter<Address> mAddressAdapter;

    BaseVLayoutAdapter<SumFee> mSumFeeAdapter;

    @Override
    public int getViewId() {
        return R.layout.fragment_submit_order;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        SingleLayoutHelper addressHelper = new SingleLayoutHelper();
        mAddressAdapter = new BaseVLayoutAdapter<Address>(addressHelper, R.layout.item_address) {
            @Override
            protected void convert(ViewHolder viewHolder, Address item, int position) {
                ViewGroup viewGroup = viewHolder.findViewById(R.id.root_layout);
                TextView consignee = viewHolder.findViewById(R.id.consignee);
                TextView phone = viewHolder.findViewById(R.id.phone);
                TextView address = viewHolder.findViewById(R.id.address);
                TextView isDefault = viewHolder.findViewById(R.id.is_default);
                ImageView rightImage = viewHolder.findViewById(R.id.right_image);
                viewGroup.setBackgroundResource(R.color.colorOrange);
                consignee.setText(item.consignee);
                phone.setText(item.phone);
                address.setText(String.format("%s %s", item.areaName, item.address));
                if (item.isDefault == 0) {
                    isDefault.setVisibility(View.GONE);
                } else {
                    isDefault.setVisibility(View.VISIBLE);
                }
                rightImage.setImageResource(R.mipmap.icon_right);
            }

            @Override
            protected void onItemClick(View view, Address item, int position) {
                super.onItemClick(view, item, position);
                assert getActivity() != null;
                Bundle args = new Bundle();
                args.putString(Constants.INTENT_KEY1,  FastjsonUtils.toString(item));
                ToolbarFragmentActivity.createFragment(getActivity(), AddressFragment.class, args);
            }

        };
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        int space = DPUtils.dp2px(getResources(), 10);
        linearLayoutHelper.setMargin(0, space, 0, space);
        linearLayoutHelper.setDividerHeight(DPUtils.dp2px(getResources(), 1));
        linearLayoutHelper.setBgColor(AppUtils.getColor(R.color.colorLine));
        mCartAdapter = new BaseVLayoutAdapter<Cart>(linearLayoutHelper, R.layout.item_cart) {
            @Override
            protected void convert(ViewHolder viewHolder, Cart item, int position) {
                ImageView select = viewHolder.findViewById(R.id.select);
                select.setVisibility(View.GONE);
                ImageView image = viewHolder.findViewById(R.id.image);
                TextView specValues = viewHolder.findViewById(R.id.spec_values);
                TextView name = viewHolder.findViewById(R.id.name);
                TextView price = viewHolder.findViewById(R.id.price);
                NumberButton numberBtn = viewHolder.findViewById(R.id.number_btn);
                if (item.getIsSelect() == 1) {
                    select.setImageResource(R.mipmap.check_pressed);
                } else {
                    select.setImageResource(R.mipmap.check_normal);
                }
                specValues.setText(AppUtils.getSelectSpecValue(item.getSpecificationValues()));
                name.setText(item.getName());
                price.setText(AppUtils.toRMBFormat(item.getPrice()));
                numberBtn.setOnNumberChangedListener(new NumberButton.OnNumberChangedListener() {
                    @Override
                    public void onNumberChanged(int number) {
                    }
                });
                AppUtils.loadImage(item.getImage(), image);
            }
        };
        SingleLayoutHelper mSumFeeHelper = new SingleLayoutHelper();
        mSumFeeHelper.setMargin(0, 0, 0, space);
        mSumFeeAdapter = new BaseVLayoutAdapter<SumFee>(mSumFeeHelper, R.layout.item_sum_fee) {
            @Override
            protected void convert(ViewHolder viewHolder, SumFee item, int position) {
                TextView goodsTotalValue = viewHolder.findViewById(R.id.goods_total_value);
                TextView freightValue = viewHolder.findViewById(R.id.freight_value);
                goodsTotalValue.setText(item.goodsTotal);
                freightValue.setText(item.freight);
            }
        };
        VirtualLayoutManager manager = new VirtualLayoutManager(Objects.requireNonNull(getContext()));
        mRecyclerView.setLayoutManager(manager);
        DelegateAdapter mAdapter = new DelegateAdapter(manager);
        mAdapter.addAdapter(mAddressAdapter);
        mAdapter.addAdapter(mCartAdapter);
        mAdapter.addAdapter(mSumFeeAdapter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        setSupportEventBus();
        getToolbar().setTitle("提交订单");
        assert getArguments() != null;
        String text1 = getArguments().getString(Constants.INTENT_KEY1);
        List<Cart> cartList = FastjsonUtils.toList(text1, Cart.class);
        mCartAdapter.replaceAll(cartList);
        if (getArguments().containsKey(Constants.INTENT_KEY2)) {
            String text2 = getArguments().getString(Constants.INTENT_KEY2);
            Address address = FastjsonUtils.toObject(text2, Address.class);
            if (address != null) {
                setAddress(address);
            } else {
                getPresenter().getDefaultAddress();
            }
        } else {
            getPresenter().getDefaultAddress();
        }
        calcSum();
    }

    private void calcSum() {
        double sum = 0;
        for (Cart cart : mCartAdapter.getData()) {
            double price = Double.valueOf(cart.getPrice()) * cart.getQuantity();
            sum += price;
        }
        String total = BigDecimalUtils.scale(String.valueOf(sum), 2);
        mTotal.setText(AppUtils.toRMBFormat(total));
        List<SumFee> sumFeeList = new ArrayList<>();
        SumFee sumFee = new SumFee();
        sumFee.goodsTotal = AppUtils.toRMBFormat(total);
        sumFee.freight = AppUtils.toRMBFormat("0");
        sumFeeList.add(sumFee);
        mSumFeeAdapter.replaceAll(sumFeeList);
    }

    @Override
    public SubmitOrderPresenter createPresenter() {
        return new SubmitOrderPresenter(new SubmitOrderModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setAddress(Address address) {
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        mAddressAdapter.replaceAll(addressList);
    }

    @OnClick(R.id.submit_order)
    public void onClick() {
        Long addressId = mAddressAdapter.getItem(0).id;
        getPresenter().submitOrder(addressId, "", "", "", mCartAdapter.getData());
    }

    @Override
    public void goPay(Order order) {
        Bundle args = new Bundle();
        args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(order));
        ToolbarFragmentActivity.createFragment(requireContext(), SelectPayFragment.class, args);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.SelectAddressEvent event) {
        setAddress(event.address);
    }

}
