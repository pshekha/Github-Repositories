package com.pshekha.githubrepos.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pshekha.githubrepos.R;


import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;


import java.util.List;
import java.util.ArrayList;

import com.pshekha.githubrepos.Model.Item;

/**
 * Created by krrathore on 11/14/18.
 */

public class AdapterRepoList extends PagedListAdapter<Item, AdapterRepoList.ItemViewHolder> {

    private Context mCtx;

    public AdapterRepoList(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.rv_rowitem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = getItem(position);

        if (item != null) {
            holder.tvRepoName.setText(item.getName());
            holder.tvStars.setText(String.valueOf(item.getStargazersCount()));
            holder.tvDescription.setText(item.getDescription());
            holder.tvAvatar.setText(item.getOwner().getAvatarUrl());

        }
    }

    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Item>() {
                @Override
                public boolean areItemsTheSame(Item oldItem, Item newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Item oldItem, Item newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvRepoName;
        TextView tvStars;
        TextView tvDescription;
        TextView tvAvatar;

        public ItemViewHolder(View itemView) {
            super(itemView);

            tvRepoName = itemView.findViewById(R.id.txtRepoName);
            tvStars = itemView.findViewById(R.id.txtStarCount);
            tvDescription=itemView.findViewById(R.id.txtRepoDesc);
            tvAvatar= itemView.findViewById(R.id.txtAvatar);

        }

    }
}

