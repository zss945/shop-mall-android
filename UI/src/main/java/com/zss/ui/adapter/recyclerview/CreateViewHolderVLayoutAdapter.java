package com.zss.ui.adapter.recyclerview;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用VLayoutAdapter适配器
 *
 * @param <T>
 * @author zm
 */
public abstract class CreateViewHolderVLayoutAdapter<T> extends DelegateAdapter.Adapter<ViewHolder> {

    private LayoutHelper helper;

    private List<T> data;

    public CreateViewHolderVLayoutAdapter(LayoutHelper helper) {
        this(helper, null);
    }

    public CreateViewHolderVLayoutAdapter(LayoutHelper helper, List<T> data) {
        this.helper = helper;
        this.data = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public T getItem(int position) {
        if (position >= data.size()) return null;
        return data.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        return onCreateView(viewGroup, itemViewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final T item = getItem(position);
        convert(viewHolder, item, position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(viewHolder.itemView, item, viewHolder.getAdapterPosition());
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClick(viewHolder.itemView, item, viewHolder.getAdapterPosition());
                return false;
            }
        });
    }

    protected abstract ViewHolder onCreateView(ViewGroup viewGroup, int position);

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

    protected void onItemClick(View view, T item, int position) {

    }

    protected void onItemLongClick(View view, T item, int position) {

    }

    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyItemRangeInserted(data.size(), elem.size());
    }

    public void set(T oldElem, T newElem) {
        set(data.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        data.set(index, elem);
        notifyItemInserted(index);
    }

    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyItemRemoved(index);
    }

    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return data.contains(elem);
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

}