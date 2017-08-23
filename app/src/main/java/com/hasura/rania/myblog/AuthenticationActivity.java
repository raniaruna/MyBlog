package com.hasura.rania.myblog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {
    EditText username,password;
    ProgressDialog progressDialog;
    public static void startActivity(Activity startingActivity){
        Intent intent = new Intent(startingActivity,AuthenticationActivity.class);
        startingActivity.startActivity(intent);
        startingActivity.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setTitle("Sign In / Register");
        setContentView(R.layout.activity_authentication);
        username  = (EditText)findViewById(R.id.username);
        password  = (EditText)findViewById(R.id.password);

        Button signInButton =(Button) findViewById(R.id.signInButton);
        Button registerButton =(Button) findViewById(R.id.registerButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    //perform SignIn
                    performSignIn();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    //perform Register
                    performRegisteration();
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait..");
    }
    private Boolean isFormValid(){
        if(username.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Username cannot be left empty",Toast.LENGTH_LONG).show();
            return false;
        }
        if(password.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Password cannot be left empty",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
    private void performSignIn(){
        // for mock testing
        //new SignInIask().execute(username.getText().toString().trim(),password.getText().toString().trim());
        showProgressDialog(true);
        ApiManager.getApiInterface().login(new AuthenticationRequest(username.getText().toString().trim(),password.getText().toString().trim()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        showProgressDialog(false);
                        if(response.isSuccessful()){
                            //showAlert("Welcome",response.body().getMessage());
                            navigateToArticleListActivity();
                        } else {
                            try {
                                String errorMessage = response.errorBody().string();
                                try {
                                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage, ErrorResponse.class);
                                    showAlert("SignIn Failed",errorResponse.getError());
                                } catch (JsonSyntaxException jsonException) {
                                    jsonException.printStackTrace();
                                    showAlert("SignIn Failed","something went wroung");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                showAlert("SignIn Failed","something went wroung");
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        showProgressDialog(false);
                        showAlert("SignIn Failed","something went wroung");
                    }
                });
    }
    private void performRegisteration(){
        showProgressDialog(true);
        ApiManager.getApiInterface().registeration(new AuthenticationRequest(username.getText().toString().trim(),password.getText().toString().trim()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        showProgressDialog(false);
                        if(response.isSuccessful()){
                            showAlert("Welcome",response.body().getMessage());
                        } else {
                            try {
                                String errorMessage = response.errorBody().string();
                                try {
                                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage, ErrorResponse.class);
                                    showAlert("Registeration Failed",errorResponse.getError());
                                } catch (JsonSyntaxException jsonException) {
                                    jsonException.printStackTrace();
                                    showAlert("Registeration Failed","something went wroung");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                showAlert("Registeration Failed","something went wroung");
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        showProgressDialog(false);
                        showAlert("Registeration Failed","something went wroung");
                    }
                });
    }
    private void showProgressDialog(Boolean shouldShow){
            if(shouldShow){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
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
    private void navigateToArticleListActivity(){
        Intent intent = new Intent(this,ArticleListActivity.class);
        startActivity(intent);
    }

    //mock class to test login
    class SignInIask extends AsyncTask<String,Void,Boolean>{
        String mockUsername = "test";
        String mockPassword = "password";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgressDialog(false);
            if(aBoolean){
                showAlert("Welcome","You are successfully signed In");
            } else {
                showAlert("Failed","username/password incorrect");
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String username =params[0];
            String password =params[1];
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return username.contentEquals(mockUsername) && password.contentEquals(mockPassword) ;
        }
    }

}
