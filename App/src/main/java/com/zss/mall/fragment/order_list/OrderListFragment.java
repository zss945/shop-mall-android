package com.zss.mall.fragment.order_list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zss.kit.DPUtils;
import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.bean.Order;
import com.zss.mall.bean.OrderItem;
import com.zss.mall.kit.AppUtils;
import com.zss.ui.adapter.recyclerview.BaseRecyclerAdapter;
import com.zss.ui.adapter.recyclerview.LinearDividerItemDecoration;
import com.zss.ui.adapter.recyclerview.ViewHolder;
import com.zss.ui.fragment.LazyFragment;

import java.util.List;

import butterknife.BindView;

public class OrderListFragment extends LazyFragment<OrderListPresenter> implements OrderListView {

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    Integer mPage = 1;
    BaseRecyclerAdapter<Order> mAdapter;
    int status;

    public static OrderListFragment newInstance(int status) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.INTENT_KEY1, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        int space = DPUtils.dp2px(getResources(), 10);
        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL, space, AppUtils.getColor(R.color.colorBg)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        status = getArguments().getInt(Constants.INTENT_KEY1);
        switch (status) {
            case 1: //待付款
                mAdapter = new BaseRecyclerAdapter<Order>(R.layout.item_order_unpay) {
                    @Override
                    protected void convert(ViewHolder viewHolder, Order item, int position) {
                        TextView sn = viewHolder.findViewById(R.id.sn);
                        TextView cancel = viewHolder.findViewById(R.id.cancel);
                        TextView name = viewHolder.findViewById(R.id.name);
                        ImageView image1 = viewHolder.findViewById(R.id.image1);
                        ImageView image2 = viewHolder.findViewById(R.id.image2);
                        ImageView image3 = viewHolder.findViewById(R.id.image3);
                        TextView spec = viewHolder.findViewById(R.id.spec);
                        TextView price = viewHolder.findViewById(R.id.price);
                        TextView option1 = viewHolder.findViewById(R.id.option1);
                        sn.setText(String.format("订单编号：%s", item.sn));
                        price.setText(AppUtils.toRMBFormat(item.amount));
                        if (item.list.size() >= 3) {
                            OrderItem item1 = item.list.get(0);
                            OrderItem item2 = item.list.get(1);
                            OrderItem item3 = item.list.get(2);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.VISIBLE);
                            AppUtils.loadImage(item1.image, image1);
                            AppUtils.loadImage(item2.image, image2);
                            AppUtils.loadImage(item3.image, image3);
                            name.setText("");
                            spec.setText("");
                        } else if (item.list.size() == 2) {
                            OrderItem item1 = item.list.get(0);
                            OrderItem item2 = item.list.get(1);
                            AppUtils.loadImage(item1.image, image1);
                            AppUtils.loadImage(item2.image, image2);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.GONE);
                            name.setText("");
                            spec.setText("");
                        } else if (item.list.size() == 1) {
                            OrderItem item1 = item.list.get(0);
                            AppUtils.loadImage(item1.image, image1);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.GONE);
                            image3.setVisibility(View.GONE);
                            name.setText(item1.name);
                            spec.setText(AppUtils.getSelectSpecValue(item1.specificationValues));
                        }
                    }
                };
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 2: //待发货
                mAdapter = new BaseRecyclerAdapter<Order>(R.layout.item_order_unsend) {
                    @Override
                    protected void convert(ViewHolder viewHolder, Order item, int position) {
                        TextView sn = viewHolder.findViewById(R.id.sn);
                        TextView cancel = viewHolder.findViewById(R.id.cancel);
                        TextView name = viewHolder.findViewById(R.id.name);
                        ImageView image1 = viewHolder.findViewById(R.id.image1);
                        ImageView image2 = viewHolder.findViewById(R.id.image2);
                        ImageView image3 = viewHolder.findViewById(R.id.image3);
                        TextView spec = viewHolder.findViewById(R.id.spec);
                        TextView option1 = viewHolder.findViewById(R.id.option1);
                        sn.setText(String.format("订单编号：%s", item.sn));
                        if (item.list.size() >= 3) {
                            OrderItem item1 = item.list.get(0);
                            OrderItem item2 = item.list.get(1);
                            OrderItem item3 = item.list.get(2);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.VISIBLE);
                            AppUtils.loadImage(item1.image, image1);
                            AppUtils.loadImage(item2.image, image2);
                            AppUtils.loadImage(item3.image, image3);
                            name.setText("");
                            spec.setText("");
                        } else if (item.list.size() == 2) {
                            OrderItem item1 = item.list.get(0);
                            OrderItem item2 = item.list.get(1);
                            AppUtils.loadImage(item1.image, image1);
                            AppUtils.loadImage(item2.image, image2);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.GONE);
                            name.setText("");
                            spec.setText("");
                        } else if (item.list.size() == 1) {
                            OrderItem item1 = item.list.get(0);
                            AppUtils.loadImage(item1.image, image1);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.GONE);
                            image3.setVisibility(View.GONE);
                            name.setText(item1.name);
                            spec.setText(AppUtils.getSelectSpecValue(item1.specificationValues));
                        }
                    }
                };
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 3: //待收货
                mAdapter = new BaseRecyclerAdapter<Order>(R.layout.item_order_unreceive) {
                    @Override
                    protected void convert(ViewHolder viewHolder, Order item, int position) {
                        TextView sn = viewHolder.findViewById(R.id.sn);
                        TextView name = viewHolder.findViewById(R.id.name);
                        ImageView image1 = viewHolder.findViewById(R.id.image1);
                        ImageView image2 = viewHolder.findViewById(R.id.image2);
                        ImageView image3 = viewHolder.findViewById(R.id.image3);
                        TextView spec = viewHolder.findViewById(R.id.spec);
                        TextView option1 = viewHolder.findViewById(R.id.option1);
                        TextView option2 = viewHolder.findViewById(R.id.option2);
                        TextView option3 = viewHolder.findViewById(R.id.option3);
                        sn.setText(String.format("订单编号：%s", item.sn));
                        if (item.list.size() >= 3) {
                            OrderItem item1 = item.list.get(0);
                            OrderItem item2 = item.list.get(1);
                            OrderItem item3 = item.list.get(2);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.VISIBLE);
                            AppUtils.loadImage(item1.image, image1);
                            AppUtils.loadImage(item2.image, image2);
                            AppUtils.loadImage(item3.image, image3);
                            name.setText("");
                            spec.setText("");
                        } else if (item.list.size() == 2) {
                            OrderItem item1 = item.list.get(0);
                            OrderItem item2 = item.list.get(1);
                            AppUtils.loadImage(item1.image, image1);
                            AppUtils.loadImage(item2.image, image2);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.GONE);
                            name.setText("");
                            spec.setText("");
                        } else if (item.list.size() == 1) {
                            OrderItem item1 = item.list.get(0);
                            AppUtils.loadImage(item1.image, image1);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.GONE);
                            image3.setVisibility(View.GONE);
                            name.setText(item1.name);
                            spec.setText(AppUtils.getSelectSpecValue(item1.specificationValues));
                        }
                    }
                };
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 4: //已完成
                mAdapter = new BaseRecyclerAdapter<Order>(R.layout.item_order_finished) {
                    @Override
                    protected void convert(ViewHolder viewHolder, Order item, int position) {
                        TextView sn = viewHolder.findViewById(R.id.sn);
                        TextView cancel = viewHolder.findViewById(R.id.cancel);
                        TextView name = viewHolder.findViewById(R.id.name);
                        ImageView image1 = viewHolder.findViewById(R.id.image1);
                        ImageView image2 = viewHolder.findViewById(R.id.image2);
                        ImageView image3 = viewHolder.findViewById(R.id.image3);
                        TextView spec = viewHolder.findViewById(R.id.spec);
                        TextView option1 = viewHolder.findViewById(R.id.option1);
                        TextView option2 = viewHolder.findViewById(R.id.option2);
                        sn.setText(String.format("订单编号：%s", item.sn));
                        if (item.list.size() >= 3) {
                            OrderItem item1 = item.list.get(0);
                            OrderItem item2 = item.list.get(1);
                            OrderItem item3 = item.list.get(2);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.VISIBLE);
                            AppUtils.loadImage(item1.image, image1);
                            AppUtils.loadImage(item2.image, image2);
                            AppUtils.loadImage(item3.image, image3);
                            name.setText("");
                            spec.setText("");
                        } else if (item.list.size() == 2) {
                            OrderItem item1 = item.list.get(0);
                            OrderItem item2 = item.list.get(1);
                            AppUtils.loadImage(item1.image, image1);
                            AppUtils.loadImage(item2.image, image2);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.GONE);
                            name.setText("");
                            spec.setText("");
                        } else if (item.list.size() == 1) {
                            OrderItem item1 = item.list.get(0);
                            AppUtils.loadImage(item1.image, image1);
                            image1.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.GONE);
                            image3.setVisibility(View.GONE);
                            name.setText(item1.name);
                            spec.setText(AppUtils.getSelectSpecValue(item1.specificationValues));
                        }
                    }
                };
                mRecyclerView.setAdapter(mAdapter);
                break;
        }
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getPresenter().queryOrderList(status, mPage, Constants.LIMIT);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPage++;
                getPresenter().queryOrderList(status, mPage, Constants.LIMIT);
            }
        });
        mRefreshLayout.autoRefresh();

    }

    @Override
    public OrderListPresenter createPresenter() {
        return new OrderListPresenter(new OrderListModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setOrderList(long currPage, long totalPage, List<Order> orderList) {
        if (currPage >= totalPage) {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
            mAdapter.replaceAll(orderList);
        } else {
            if (currPage == 1) {
                mRefreshLayout.finishRefresh();
                mAdapter.replaceAll(orderList);
            } else {
                mRefreshLayout.finishLoadMore();
                mAdapter.addAll(orderList);
            }
        }
    }

}
