package com.zss.mall.fragment.goods_list;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zss.kit.DPUtils;
import com.zss.kit.FastjsonUtils;
import com.zss.mall.Constants;
import com.zss.mall.R;

import com.zss.mall.fragment.goods_detail.GoodsDetailFragment;
import com.zss.ui.activity.BaseFragmentActivity;
import com.zss.ui.adapter.recyclerview.BaseRecyclerAdapter;
import com.zss.ui.adapter.recyclerview.GridDividerItemDecoration;
import com.zss.ui.adapter.recyclerview.ViewHolder;
import com.zss.ui.fragment.LazyFragment;

import com.zss.mall.bean.Goods;
import com.zss.mall.bean.GoodsCategory;
import com.zss.mall.kit.AppUtils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class GoodsListFragment extends LazyFragment<GoodsListPresenter> implements GoodsListView {

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    Integer mPage = 1;
    GoodsCategory mGategory;
    BaseRecyclerAdapter<Goods> mAdapter;

    public static GoodsListFragment newInstance(GoodsCategory category) {
        GoodsListFragment fragment = new GoodsListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(category));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_goods_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAdapter = new BaseRecyclerAdapter<Goods>(R.layout.item_goods) {
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
            protected void onItemClick(Goods item, int position) {
                Bundle args = new Bundle();
                args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(item));
                BaseFragmentActivity.createFragmentNewTask(Objects.requireNonNull(getActivity()), GoodsDetailFragment.class, args);
            }
        };
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(DPUtils.dp2px(getResources(), 10)));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        String text1 = getArguments().getString(Constants.INTENT_KEY1);
        mGategory = FastjsonUtils.toObject(text1, GoodsCategory.class);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getPresenter().queryGoodsByCategory(mGategory.id, mGategory.level, mPage, Constants.LIMIT);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPage++;
                getPresenter().queryGoodsByCategory(mGategory.id, mGategory.level, mPage, Constants.LIMIT);
            }
        });
        mRefreshLayout.autoRefresh();
    }

    @Override
    public GoodsListPresenter createPresenter() {
        return new GoodsListPresenter(new GoodsListModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setGoodsList(long currPage, long totalPage, List<Goods> goodsList) {
        if (currPage >= totalPage) {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
            mAdapter.replaceAll(goodsList);
        } else {
            if (currPage == 1) {
                mRefreshLayout.finishRefresh();
                mAdapter.replaceAll(goodsList);
            } else {
                mRefreshLayout.finishLoadMore();
                mAdapter.addAll(goodsList);
            }
        }
    }
}
