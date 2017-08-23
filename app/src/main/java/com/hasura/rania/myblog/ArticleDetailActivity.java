package com.hasura.rania.myblog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ArticleDetailActivity extends AppCompatActivity {

    public static final String TAG = "ArticleDetailActivity";
    private final static String ARTICLE_KEY="articleKey";
    public  static void startActivity(Activity startingActivity,Article article) {
        Intent intent = new Intent(startingActivity,ArticleDetailActivity.class);
        Log.i(TAG,article.toString());
        intent.putExtra(ARTICLE_KEY, article);
        startingActivity.startActivity(intent);
       // startingActivity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        TextView articleTitle =(TextView) findViewById(R.id.articleTitle);
        TextView articleBody =(TextView) findViewById(R.id.articleBody);
        if(getIntent()!=null){
            Article article =getIntent().getParcelableExtra(ARTICLE_KEY);
            articleTitle.setText(article.getTitle());
            articleBody.setText(Html.fromHtml(article.getContent(),Html.FROM_HTML_MODE_COMPACT));
            setTitle(article.getHeading());
            Log.i(TAG,article.toString());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
