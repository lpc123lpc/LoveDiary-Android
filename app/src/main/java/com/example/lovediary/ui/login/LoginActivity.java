package com.example.lovediary.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lovediary.MainActivity;
import com.example.lovediary.R;
import com.example.lovediary.ui.login.LoginViewModel;
import com.example.lovediary.ui.login.LoginViewModelFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    
    private LoginViewModel loginViewModel;
    private TextView textView;
    private String username;
    private int x=0;
    private Thread thread;
    private volatile int flag = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register_button);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        textView = findViewById(R.id.show_dialog_view);
        registerButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivityForResult(intent,300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        loginButton.setOnClickListener(v -> {
            try{
                username = usernameEditText.getText().toString();
                sendRequest(usernameEditText.getText().toString(),passwordEditText.getText().toString());
            }catch (Exception e){
                //
            }

            //loadingProgressBar.setVisibility(View.VISIBLE);

        });

    }

    @SuppressLint("SetTextI18n")
    private void show_dialog(){
        textView.setText("succeed register");
        textView.setVisibility(View.VISIBLE);
    }
    
    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + username;
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
    
    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //show_dialog();
        Toast.makeText(getApplicationContext(), "register succeed", Toast.LENGTH_LONG).show();
    }

    private void sendRequest(String user, String password) throws Exception {
        thread = new Thread( (new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://121.5.50.186:8080/op/login");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("contentType", "application/json");
                    String data = "password="+ URLEncoder.encode(password,"UTF-8")+
                            "&userName="+URLEncoder.encode(user,"UTF-8");

                    //獲取輸出流
                    OutputStream out = connection.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                    out.close();
                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        System.out.println("连接成功");
                        // 请求返回的数据
                        InputStream in = connection.getInputStream();
                        x = in.read();
                        System.out.println("fuck");
                        System.out.println(x);
                        /*while (x != -1) {
                            System.out.println(x);
                        }*/
                    }
                    if (x==49){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        setResult(400,intent);
                        finish();
                    }
                    else{
                        show_dialog();
                    }
                } catch (Exception e) {
                    System.out.println("error");
                    System.out.println(e.toString());
                }

            }
        }));
        thread.start();
        while(thread.isAlive()){
            //
        }
        SharedPreferences sharedPreferences = getSharedPreferences("my_user", Context.MODE_PRIVATE);

        if(x==49) {
            updateUiWithUser();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user",username);
        editor.commit();

        System.out.println(sharedPreferences.getString("user",null));
        System.out.println("store right");
    }

}
