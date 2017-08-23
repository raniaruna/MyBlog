package com.hasura.rania.myblog;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleListActivity extends AppCompatActivity {
    public static final String TAG ="ArticleListActivity";
    RecyclerView recyclerView;
    ArticleListAdapter articleListAdapter;
    ProgressDialog progressDialog;


    public  static void startActivity(Activity startingActivity) {
        Intent intent = new Intent(startingActivity,ArticleListActivity.class);
        startingActivity.startActivity(intent);
        startingActivity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
		setTitle("Article List");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Feaching Articles...");
        
        //step 1 create Adapter
        //recyclerView.setAdapter(new ArticleListAdapter());
        articleListAdapter = new ArticleListAdapter( new ArticleListAdapter.Helper(){
            @Override
            public void onArticleClicked(Article article) {
                Log.i(TAG,article.toString());
                ArticleDetailActivity.startActivity(ArticleListActivity.this,article);
            }
        });
        recyclerView.setAdapter(articleListAdapter);
        //step 2 create layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
		progressDialog.show();
        ApiManager.getApiInterface().getArticles()
                .enqueue(new Callback<List<Article>>() {
                    @Override
                    public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                        if(response.isSuccessful()) {
                            progressDialog.dismiss();
                            articleListAdapter.setData(response.body());
                        } else {
                            //show laert
                            progressDialog.dismiss();
                            showAlert("Failed","something went wrong");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Article>> call, Throwable t) {
                        progressDialog.dismiss();
                        showAlert("Failed","something went wrong");
                    }
                });


    }
    private void showAlert(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_article_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                //logout implement
                progressDialog.show();
                ApiManager.getApiInterface().logout().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        progressDialog.dismiss();
                        AuthenticationActivity.startActivity(ArticleListActivity.this);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        progressDialog.dismiss();
                        showAlert("Failed","logout failed for some reason");
                    }
                });

                return true;
            default:
            return super.onOptionsItemSelected(item);
        }

    }

}
