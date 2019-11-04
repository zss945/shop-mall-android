package com.zss.mall.fragment.cart;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.zss.kit.BigDecimalUtils;
import com.zss.kit.DPUtils;
import com.zss.kit.FastjsonUtils;
import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.bean.Event;
import com.zss.mall.fragment.goods_detail.GoodsDetailFragment;
import com.zss.mall.fragment.login.LoginFragment;
import com.zss.mall.fragment.submit_order.SubmitOrderFragment;
import com.zss.ui.activity.BaseFragmentActivity;
import com.zss.ui.activity.ToolbarFragmentActivity;
import com.zss.ui.adapter.recyclerview.BaseVLayoutAdapter;
import com.zss.ui.adapter.recyclerview.CreateViewHolderVLayoutAdapter;
import com.zss.ui.adapter.recyclerview.ViewHolder;
import com.zss.mall.bean.CartItemType;
import com.zss.mall.bean.Goods;
import com.zss.mall.kit.AppUtils;
import com.zss.mall.room.entity.Cart;
import com.zss.ui.button.NumberButton;
import com.zss.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 购物车
 *
 * @author zm
 */
public class CartFragment extends BaseFragment<CartPresenter> implements CartView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.select)
    ImageView mSelect;

    @BindView(R.id.total)
    TextView mTotal;

    @BindView(R.id.total_layout)
    View mTotalLayout;

    Integer mPage = 1;
    boolean mIsSelectAll = true;
    List<CartItemType> mCartItemTypeList;

    CreateViewHolderVLayoutAdapter<CartItemType> mCartAdapter;
    BaseVLayoutAdapter<String> mLikeAdapter;
    BaseVLayoutAdapter<Goods> mGoodsAdapter;

    @Override
    public int getViewId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        int space = DPUtils.dp2px(getResources(), 10);
        linearLayoutHelper.setBgColor(AppUtils.getColor(R.color.colorLine));
        linearLayoutHelper.setMarginBottom(space);
        linearLayoutHelper.setDividerHeight(DPUtils.dp2px(getResources(), 1));
        mCartAdapter = new CreateViewHolderVLayoutAdapter<CartItemType>(linearLayoutHelper) {

            @Override
            protected ViewHolder onCreateView(ViewGroup viewGroup, int itemViewType) {
                if (itemViewType == CartItemType.ItemType.ItemTypeEmpty.getValue()) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart_empty, viewGroup, false);
                    return new ViewHolder(view);
                } else {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart, viewGroup, false);
                    return new ViewHolder(view);
                }
            }

            @Override
            protected void convert(ViewHolder viewHolder, CartItemType item, int position) {
                if (item.getItemType() == CartItemType.ItemType.ItemTypeEmpty) {

                } else {
                    ImageView select = viewHolder.findViewById(R.id.select);
                    ImageView image = viewHolder.findViewById(R.id.image);
                    TextView specValues = viewHolder.findViewById(R.id.spec_values);
                    TextView name = viewHolder.findViewById(R.id.name);
                    TextView price = viewHolder.findViewById(R.id.price);
                    NumberButton numberBtn = viewHolder.findViewById(R.id.number_btn);
                    if (item.getCart().getIsSelect() == 1) {
                        select.setImageResource(R.mipmap.check_pressed);
                    } else {
                        select.setImageResource(R.mipmap.check_normal);
                    }
                    specValues.setText(AppUtils.getSelectSpecValue(item.getCart().getSpecificationValues()));
                    name.setText(item.getCart().getName());
                    price.setText(AppUtils.toRMBFormat(item.getCart().getPrice()));
                    select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onClickSelectItem(position);
                        }
                    });
                    numberBtn.setOnNumberChangedListener(new NumberButton.OnNumberChangedListener() {
                        @Override
                        public void onNumberChanged(int number) {
                            onChangeCartNumber(position, number);
                        }
                    });
                    AppUtils.loadImage(item.getCart().getImage(), image);
                }
            }

            @Override
            public int getItemViewType(int i) {
                CartItemType item = mCartItemTypeList.get(i);
                return item.getItemType().getValue();
            }

        };

        SingleLayoutHelper likeHelper = new SingleLayoutHelper();
        mLikeAdapter = new BaseVLayoutAdapter<String>(likeHelper, R.layout.item_like) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView title = viewHolder.findViewById(R.id.title);
                title.setText(item);
            }
        };

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setGap(space);
        gridLayoutHelper.setMargin(space, 0, space, space);
        mGoodsAdapter = new BaseVLayoutAdapter<Goods>(gridLayoutHelper, R.layout.item_goods) {

            @Override
            protected void convert(ViewHolder viewHolder, Goods item, int position) {
                ImageView image = viewHolder.findViewById(R.id.image);
                TextView caption = viewHolder.findViewById(R.id.caption);
                TextView name = viewHolder.findViewById(R.id.name);
                TextView price = viewHolder.findViewById(R.id.price);
                caption.setText(item.caption);
                name.setText(item.name);
                price.setText(AppUtils.toRMBFormat(item.price));
                AppUtils.loadImage(item.image, image);
            }

            @Override
            protected void onItemClick(View view, Goods item, int position) {
                Bundle args = new Bundle();
                args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(item));
                BaseFragmentActivity.createFragmentNewTask(requireContext(), GoodsDetailFragment.class, args);
            }

        };

        VirtualLayoutManager manager = new VirtualLayoutManager(Objects.requireNonNull(getContext()));
        mRecyclerView.setLayoutManager(manager);
        DelegateAdapter mAdapter = new DelegateAdapter(manager);
        mAdapter.addAdapter(mCartAdapter); // 购物车适配器
        mAdapter.addAdapter(mLikeAdapter);  // 猜你喜欢
        mAdapter.addAdapter(mGoodsAdapter); // 商品适配器
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        setSupportEventBus();
        if (getArguments() != null && getArguments().getBoolean(Constants.INTENT_KEY1, false)) {
            int space = DPUtils.dp2px(getResources(), 10);
            mToolbar.setContentInsetsRelative(space, space);
            mToolbar.setContentInsetStartWithNavigation(0);
            mToolbar.setNavigationIcon(com.zss.ui.R.drawable.ic_left_arrow);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        mToolbar.setTitle("购物车");
        getPresenter().queryAllCart();
    }

    @Override
    public CartPresenter createPresenter() {
        return new CartPresenter(new CartModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setCartList(List<Cart> cartList) {
        mCartItemTypeList = new ArrayList<>();
        if (cartList != null && cartList.size() > 0) {
            for (Cart item : cartList) {
                mCartItemTypeList.add(new CartItemType(item));
            }
            mTotalLayout.setVisibility(View.VISIBLE); // 显示底部合计布局
            calcSum();
        } else {
            mCartItemTypeList.add(new CartItemType(CartItemType.ItemType.ItemTypeEmpty));
            mTotalLayout.setVisibility(View.GONE); // 隐藏底部合计布局
        }
        mCartAdapter.replaceAll(mCartItemTypeList);
        getPresenter().queryGoodsByLike(mPage, Constants.LIMIT);
    }

    @Override
    public void setGoodsList(List<Goods> goodsList) {
        List<String> likeList = new ArrayList<>();
        likeList.add("猜你喜欢");
        mLikeAdapter.replaceAll(likeList);
        mGoodsAdapter.replaceAll(goodsList);
    }

    private List<Cart> filterCart() {
        List<Cart> result = new ArrayList<>();
        for (CartItemType cartItem : mCartItemTypeList) {
            if (cartItem.getItemType() == CartItemType.ItemType.ItemTypeCart) {
                result.add(cartItem.getCart());
            }
        }
        return result;
    }

    private List<Cart> filterSelectCart() {
        List<Cart> result = new ArrayList<>();
        for (CartItemType cartItem : mCartItemTypeList) {
            if (cartItem.getItemType() == CartItemType.ItemType.ItemTypeCart && cartItem.getCart().getIsSelect() == 1) {
                result.add(cartItem.getCart());
            }
        }
        return result;
    }

    //计算总金额
    private void calcSum() {
        double sum = 0;
        int count = 0;
        List<Cart> cartList = filterCart();
        for (Cart cart : cartList) {
            if (cart.getIsSelect() == 1) {
                count++;
                double price = Double.valueOf(cart.getPrice()) * cart.getQuantity();
                sum += price;
            }
        }
        if (count > 0 && count == cartList.size()) {
            mIsSelectAll = true;
        } else {
            mIsSelectAll = false;
        }
        String total = BigDecimalUtils.scale(String.valueOf(sum), 2);
        if (mIsSelectAll) {
            mSelect.setImageResource(R.mipmap.check_pressed);
        } else {
            mSelect.setImageResource(R.mipmap.check_normal);
        }
        mTotal.setText(String.format("合计: %s", AppUtils.toRMBFormat(total)));
    }

    //点击选择项
    public void onClickSelectItem(int position) {
        Cart cart = mCartItemTypeList.get(position).getCart();
        if (cart.getIsSelect() == 1) {
            cart.setIsSelect(0);
        } else {
            cart.setIsSelect(1);
        }
        mCartAdapter.replaceAll(mCartItemTypeList);
        calcSum();
    }

    //点击全选
    public void onClickSelectAll() {
        for (CartItemType cartItem : mCartItemTypeList) {
            if (cartItem.getItemType() == CartItemType.ItemType.ItemTypeCart) {
                if (mIsSelectAll) {
                    cartItem.getCart().setIsSelect(0);
                } else {
                    cartItem.getCart().setIsSelect(1);
                }
            }
        }
        mCartAdapter.replaceAll(mCartItemTypeList);
        calcSum();
    }

    //改变购物车数量
    public void onChangeCartNumber(int position, int number) {
        Cart cart = mCartItemTypeList.get(position).getCart();
        cart.setQuantity(number);
        mCartAdapter.replaceAll(mCartItemTypeList);
        calcSum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.CartEvent event) {
        getPresenter().queryAllCart();
    }

    @OnClick({R.id.submit_order, R.id.select, R.id.select_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_order:
                if (AppUtils.isLogin()) {
                    Bundle args = new Bundle();
                    args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(filterSelectCart()));
                    ToolbarFragmentActivity.createFragment(requireContext(), SubmitOrderFragment.class, args);
                } else {
                    ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class);
                }
                break;
            case R.id.select:
                onClickSelectAll();
                break;
            case R.id.select_all:
                onClickSelectAll();
                break;
        }
    }
}
