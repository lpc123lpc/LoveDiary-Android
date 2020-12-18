package com.example.lovediary.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
                register(usernameEditText.getText().toString(),passwordEditText.getText().toString());
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
        textView.setText("wrong username or password");
        textView.setVisibility(View.VISIBLE);
    }
    
    /*private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + username;
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
    
    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }*/

    private void register(String user,String password) throws Exception {
        Thread thread = new Thread( (new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://121.5.50.186:8080/op/register");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
                    String matchCode = generateFixedLengthNum(6);
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("contentType", "application/json");
                    String data = "password="+ URLEncoder.encode(password,"UTF-8")+
                            "&userName="+URLEncoder.encode(user,"UTF-8")+
                            "&matchCode="+URLEncoder.encode(matchCode,"UTF-8");

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
                    }

                } catch (Exception e) {
                    System.out.println("error");
                    System.out.println(e.toString());
                }

            }
        }));
        thread.start();
    }


    private void sendRequest(String user,String password) throws Exception {
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
                        startActivity(intent);
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

    }

    public String generateFixedLengthNum(int length) {
        // 获取绝对值
        length = Math.abs(length);
        Random random = new Random();
        // 获取随机数，去除随机数前两位(0.)
        String randomValue = String.valueOf(random.nextDouble()).substring(2);
        String value = "";
        int maxLength = randomValue.length();
        // 获取随机数字符串长度，并计算需要生成的长度与字符串长度的差值
        int diff = length - maxLength;
        if (diff > 0) {
            // 如果差值大于0，则说明需要生成的串长大于获取的随机数长度，此时需要将最大长度设置为当前随机串的长度
            length = maxLength;
            // 同时递归调用该随机数获取方法，获取剩余长度的随机数
            value += generateFixedLengthNum(diff);
        }
        // 获取最终的随机数
        value = randomValue.substring(0, length) + value;
        return value;
    }
}
