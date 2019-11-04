package com.zss.mall.fragment.select_pay;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.zss.kit.DPUtils;
import com.zss.kit.FastjsonUtils;
import com.zss.mall.AppExecutors;
import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.bean.Order;
import com.zss.mall.bean.PayItem;
import com.zss.ui.adapter.recyclerview.BaseRecyclerAdapter;
import com.zss.ui.adapter.recyclerview.LinearDividerItemDecoration;
import com.zss.ui.adapter.recyclerview.ViewHolder;
import com.zss.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class SelectPayFragment extends BaseFragment<SelectPayPresenter> implements SelectPayView {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    BaseRecyclerAdapter<PayItem> mAdapter;

    Order mOrder;

    @Override
    public int getViewId() {
        return R.layout.fragment_select_pay;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAdapter = new BaseRecyclerAdapter<PayItem>(R.layout.item_pay) {
            @Override
            protected void convert(ViewHolder viewHolder, PayItem item, int position) {
                ImageView select = viewHolder.findViewById(R.id.select);
                ImageView icon = viewHolder.findViewById(R.id.icon);
                TextView title = viewHolder.findViewById(R.id.title);
                if (item.isSelect) {
                    select.setImageResource(R.mipmap.check_pressed);
                } else {
                    select.setImageResource(R.mipmap.check_normal);

                }
                icon.setImageResource(item.icon);
                title.setText(item.title);
            }

            @Override
            protected void onItemClick(PayItem item, int position) {
                super.onItemClick(item, position);
                onSingleSelect(item);
            }
        };
        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL, DPUtils.dp2px(getResources(), 1)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public SelectPayPresenter createPresenter() {
        return new SelectPayPresenter(new SelectPayModel(), this);
    }

    @Override
    public void initData() {

        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

        getToolbar().setTitle("选择支付方式");

        assert getArguments() != null;

        String text1 = getArguments().getString(Constants.INTENT_KEY1);
        mOrder = FastjsonUtils.toObject(text1, Order.class);

        PayItem alipay = new PayItem();
        alipay.isSelect = true;
        alipay.icon = R.mipmap.alipay;
        alipay.title = "支付宝";
        alipay.mode = 1;

        PayItem wxpay = new PayItem();
        wxpay.isSelect = false;
        wxpay.icon = R.mipmap.wxpay;
        wxpay.title = "微信";
        wxpay.mode = 2;

        List<PayItem> payItemList = new ArrayList<>();
        payItemList.add(alipay);
        payItemList.add(wxpay);
        mAdapter.replaceAll(payItemList);


    }

    public void onSingleSelect(PayItem selectItem) {
        for (PayItem item : mAdapter.getData()) {
            if (item.equals(selectItem)) {
                item.isSelect = !item.isSelect;
            } else {
                item.isSelect = false;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.sure_btn)
    public void onClick() {
        getPresenter().alipay(mOrder.id);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    ObservableEmitter mEmitter;

    @Override
    public void alipayResult(final String result) {
        PayTask alipay = new PayTask(getActivity());
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
                    @Override
                    public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                        Map<String, String> map = alipay.payV2(result, true);
                        emitter.onNext(map);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Map<String, String> map) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
