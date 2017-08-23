package com.hasura.rania.myblog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rania on 8/23/2017.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleItemViewHolder>{
    Helper helper;
    /* //test data
    String[] artcleList ={"article1",
            "article2",
            "article3",
            "article4",
            "article5",
            "article6"};
            */
    List<Article> artcleList = new ArrayList<>();
    public interface Helper{
        void onArticleClicked(Article article);
    }

    public ArticleListAdapter(Helper helper) {
        this.helper = helper;
    }

    @Override
    public ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article,parent,false);

        return new ArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleItemViewHolder holder, final int position) {
        // set data
        //holder.articleName.setText(artcleList[position]); //for test string
        holder.articleName.setText(artcleList.get(position).getHeading());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for test
                //Toast.makeText(ArticleListActivity.this,"Article Clicked :"+artcleList.get(position).getHeading(),Toast.LENGTH_LONG)
                  //      .show();
                helper.onArticleClicked(artcleList.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return artcleList.size();  //artcleList.length;
    }

    public void setData(List<Article> data){
        this.artcleList = data;
        this.notifyDataSetChanged();

    }
}
