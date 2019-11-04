package com.zss.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 公用ViewHolder
 *
 * @author zm
 *
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public ViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<View>();
    }

    public <T extends View> T findViewById(int viewId) {
        return findView(viewId);
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T findView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getView() {
        return itemView;
    }


}