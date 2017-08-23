package com.hasura.rania.myblog;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by rania on 8/22/2017.
 */

public class ArticleItemViewHolder extends RecyclerView.ViewHolder {
    TextView articleName;
    CardView cardView;
    public ArticleItemViewHolder(View itemView) {
        super(itemView);
        articleName = (TextView) itemView.findViewById(R.id.articleName);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
    }

}
