package com.zss.ui.adapter.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用RecyclerView适配器
 *
 * @param <T>
 * @author zm
 */
public abstract class CreateViewHolderRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    public List<T> data;

    public CreateViewHolderRecyclerAdapter() {
        this(null);
    }

    public CreateViewHolderRecyclerAdapter(List<T> data) {
        this.data = data == null ? new ArrayList<T>() : data;
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
                onItemClick(item, viewHolder.getAdapterPosition());
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClick(item, viewHolder.getAdapterPosition());
                return false;
            }
        });
    }

    protected abstract ViewHolder onCreateView(ViewGroup viewGroup, int position);

    protected abstract void convert(ViewHolder viewHolder, T item, int position);


    protected void onItemClick(T item, int position) {

    }

    protected void onItemLongClick(T item, int position) {

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