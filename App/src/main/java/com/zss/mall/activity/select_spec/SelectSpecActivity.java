package com.zss.mall.activity.select_spec;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.zss.kit.DPUtils;
import com.zss.kit.FastjsonUtils;
import com.zss.kit.StatusBarUtils;
import com.zss.kit.ToastUtils;
import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.bean.Event;
import com.zss.mall.bean.Goods;
import com.zss.mall.bean.Product;
import com.zss.mall.bean.SpecificationItem;
import com.zss.mall.bean.SpecificationValue;
import com.zss.mall.bean.TagBean;
import com.zss.mall.fragment.login.LoginFragment;
import com.zss.mall.fragment.submit_order.SubmitOrderFragment;
import com.zss.mall.kit.AppUtils;
import com.zss.mall.room.entity.Cart;
import com.zss.ui.activity.ToolbarFragmentActivity;
import com.zss.ui.activity.base.BaseActivity;
import com.zss.ui.adapter.recyclerview.BaseVLayoutAdapter;
import com.zss.ui.adapter.recyclerview.ViewHolder;
import com.zss.ui.button.NumberButton;
import com.zss.ui.tagview.TagContainerLayout;
import com.zss.ui.tagview.TagView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 半透明效果，需要设置主题样式android:theme="@style/AppTheme.Black.Translucent"，所以才用Activity实现
 */
public class SelectSpecActivity extends BaseActivity<SelectSpecPresenter> implements SelectSpecView {

    @BindView(R.id.image)
    ImageView mImage;

    @BindView(R.id.price)
    TextView mPrice;

    @BindView(R.id.select)
    TextView mSelect;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    Goods mGoods;

    Product mProduct;

    BaseVLayoutAdapter<SpecificationItem> mSpecAdapter;

    BaseVLayoutAdapter<String> mBuyNumberAdapter;

    //用户选择的规格
    SparseArray<TagBean> selectedTagArr = new SparseArray<>();

    @Override
    public int getViewId() {
        return R.layout.activity_select_spec;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        int space = DPUtils.dp2px(getResources(), 10);
        linearLayoutHelper.setMarginTop(space);
        linearLayoutHelper.setDividerHeight(space);
        mSpecAdapter = new BaseVLayoutAdapter<SpecificationItem>(linearLayoutHelper, R.layout.item_spec_lable) {
            @Override
            protected void convert(ViewHolder viewHolder, SpecificationItem item, int position) {
                TextView name = viewHolder.findViewById(R.id.name);
                TagContainerLayout tagLayout = viewHolder.findViewById(R.id.tag_layout);
                name.setText(item.name);
                List<String> mTagList = getTagList(item);
                tagLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int tagIdx, String text) {
                        TagBean tagBean = selectedTagArr.get(position);
                        if (tagBean == null) {
                            selectedTagArr.put(position, new TagBean(tagIdx, text));
                            tagLayout.selectTagView(tagIdx);
                        } else if (tagIdx == tagBean.tagIdx && text.equals(tagBean.text)) {
                            selectedTagArr.remove(position);
                            tagLayout.deselectTagView(tagIdx);
                        } else {
                            tagLayout.deselectTagView(tagBean.tagIdx);
                            selectedTagArr.put(position, new TagBean(tagIdx, text));
                            tagLayout.selectTagView(tagIdx);
                        }
                        if (selectedTagArr.size() == mSpecAdapter.getItemCount()) {
                            List<SpecificationValue> specValueList = new ArrayList<>();
                            //按照顺序添加规格
                            for (SpecificationItem specItem : mSpecAdapter.getData()) {
                                SpecificationValue specValue = getTagSpecificationValue(specItem);
                                if(specValue != null){
                                    specValueList.add(specValue);
                                }
                            }
                            getPresenter().getProductBySpec(mGoods.id, specValueList);
                        }
                    }

                    @Override
                    public void onTagLongClick(int tagIdx, String text) {

                    }

                    @Override
                    public void onSelectedTagDrag(int tagIdx, String text) {

                    }

                    @Override
                    public void onTagCrossClick(int tagIdx) {

                    }
                });
                tagLayout.setTags(mTagList);
            }
        };

        SingleLayoutHelper buyNumberHelper = new SingleLayoutHelper();
        buyNumberHelper.setMarginTop(space);
        buyNumberHelper.setMarginBottom(space);
        mBuyNumberAdapter = new BaseVLayoutAdapter<String>(buyNumberHelper, R.layout.item_buy_number) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                NumberButton numberButton = viewHolder.findViewById(R.id.number_btn);
                numberButton.setNumber(item);
            }
        };

        VirtualLayoutManager manager = new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        DelegateAdapter mAdapter = new DelegateAdapter(manager);
        mAdapter.addAdapter(mSpecAdapter);
        mAdapter.addAdapter(mBuyNumberAdapter);
        mRecyclerView.setAdapter(mAdapter);

        //默认数量为1
        mBuyNumberAdapter.add("1");
    }

    @Override
    public SelectSpecPresenter createPresenter() {
        return new SelectSpecPresenter(new SelectSpecModel(), this);
    }

    @Override
    public void initData() {
        StatusBarUtils.setTranslucentStatus(this); //透明状态栏
        StatusBarUtils.setStatusBarDarkTheme(this, true); //状态栏文字黑色
        Bundle args = getIntent().getExtras();
        if (args != null && args.containsKey(Constants.INTENT_KEY1)) {
            mGoods = FastjsonUtils.toObject(args.getString(Constants.INTENT_KEY1), Goods.class);
            String selectSpecItem = String.format("请选择 %s", AppUtils.getSelectSpecItem(mGoods.specificationItems));
            List<SpecificationItem> specValueList = FastjsonUtils.toList(mGoods.specificationItems, SpecificationItem.class);
            List<SpecificationItem> enableList = new ArrayList<>();
            for (SpecificationItem item : specValueList) {
                if (item.selectIds != null && item.selectIds.size() > 0) {
                    enableList.add(item);
                }
            }
            mSpecAdapter.addAll(enableList);
            AppUtils.loadImage(mGoods.image, mImage);
            mPrice.setText(AppUtils.toRMBFormat(mGoods.price));
            mSelect.setText(selectSpecItem);
        } else {
            finish();
        }
    }

    @OnClick({R.id.add_cart, R.id.buy, R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_cart:
                if (this.mProduct == null) {
                    ToastUtils.showToast("请选择 " + AppUtils.getSelectSpecItem(mGoods.specificationItems));
                    return;
                }
                getPresenter().saveCartWithDB(mProduct.id, 1);
                break;
            case R.id.buy:
                goSubmitOrder();
                break;
            case R.id.close:
                finish();
                break;
        }
    }

    @Override
    public void setProduct(Product product) {
        this.mProduct = product;
        mPrice.setText(AppUtils.toRMBFormat(product.price));
        mSelect.setText(AppUtils.getSelectSpecValue(product.specificationValues));
    }

    @Override
    public void goBack() {
        finish();
    }

    private void goSubmitOrder() {
        if (this.mProduct == null) {
            ToastUtils.showToast("请选择 " + AppUtils.getSelectSpecItem(mGoods.specificationItems));
            return;
        }
        if (AppUtils.isLogin()) {
            List<Cart> cartList = new ArrayList<>();
            Cart cart = new Cart();
            cart.setProductId(this.mProduct.id);
            cart.setQuantity(1);
            cart.setGoodsId(mGoods.id);
            cart.setName(mGoods.name);
            cart.setImage(mGoods.image);
            cart.setPrice(mProduct.price);
            cart.setSpecificationValues(mProduct.specificationValues);
            cartList.add(cart);
            Bundle args = new Bundle();
            args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(cartList));
            ToolbarFragmentActivity.createFragment(this, SubmitOrderFragment.class, args);
        } else {
            ToolbarFragmentActivity.createFragment(this, LoginFragment.class);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public List<String> getTagList(SpecificationItem item){
        List<String> mTagList = new ArrayList<>();
        for (SpecificationValue spec : item.options) {
            if (item.selectIds.contains(spec.id)) {
                mTagList.add(spec.value);
            }
        }
        return mTagList;
    }

    public SpecificationValue getTagSpecificationValue(SpecificationItem item) {
        for (SpecificationValue specValue : item.options) {
            if (item.selectIds.contains(specValue.id)) {
                for (int i = 0; i < selectedTagArr.size(); i++) {
                    TagBean tagBean = selectedTagArr.valueAt(i);
                    if (specValue.value.equals(tagBean.text)) {
                        return specValue;
                    }
                }
            }
        }
        return null;
    }

}
