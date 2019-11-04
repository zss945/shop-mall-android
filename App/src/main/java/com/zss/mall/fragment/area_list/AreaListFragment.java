package com.zss.mall.fragment.area_list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zss.kit.DPUtils;
import com.zss.kit.FastjsonUtils;
import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.bean.Area;
import com.zss.mall.bean.Event;
import com.zss.ui.adapter.recyclerview.BaseRecyclerAdapter;
import com.zss.ui.adapter.recyclerview.LinearDividerItemDecoration;
import com.zss.ui.adapter.recyclerview.ViewHolder;
import com.zss.ui.fragment.BaseFragment;
import com.zss.ui.mvp.IPresenter;

import org.greenrobot.eventbus.EventBus;
import java.util.List;

import butterknife.BindView;

public class AreaListFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    BaseRecyclerAdapter<Area> mAdapter;

    private Area area; //当前选择区域

    public static AreaListFragment newInstance(List<Area> areaList) {
        return newInstance(areaList, null);
    }

    public static AreaListFragment newInstance(List<Area> areaList, Area area) {
        AreaListFragment fragment = new AreaListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(areaList));
        if (area != null) {
            args.putString(Constants.INTENT_KEY2, FastjsonUtils.toString(area));
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_area_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAdapter = new BaseRecyclerAdapter<Area>(R.layout.item_area) {
            @Override
            protected void convert(ViewHolder viewHolder, Area item, int position) {
                ViewGroup viewGroup = viewHolder.findViewById(R.id.root_layout);
                TextView name = viewHolder.findViewById(R.id.name);
                name.setText(item.name);
                if (area != null && area.id == item.id) {
                    viewGroup.setBackgroundResource(R.color.colorOrange);
                } else {
                    viewGroup.setBackgroundResource(R.color.colorWhite);
                }
            }

            @Override
            protected void onItemClick(Area item, int position) {
                area = item;
                Event.ClickAreaEvent event = new Event.ClickAreaEvent();
                event.area = item;
                EventBus.getDefault().post(event);
            }
        };
        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL, DPUtils.dp2px(getResources(), 1)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        String text1 = getArguments().getString(Constants.INTENT_KEY1);
        List<Area>  areaList = FastjsonUtils.toList(text1, Area.class);
        if (getArguments().containsKey(Constants.INTENT_KEY2)) {
            String key2 = getArguments().getString(Constants.INTENT_KEY2);
            area = FastjsonUtils.toObject(key2, Area.class);
        }
        mAdapter.addAll(areaList);
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    public Area getArea() {
        return area;
    }
}
