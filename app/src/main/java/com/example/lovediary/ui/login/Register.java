package com.example.lovediary.ui.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lovediary.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView username = findViewById(R.id.username_register);
        TextView password = findViewById(R.id.password_register);
        TextView confirm = findViewById(R.id.password_confirm);
        Button button = findViewById(R.id.confirm_register);

        button.setOnClickListener(v -> {
            String name = username.getText().toString();
            String pass = password.getText().toString();
            String pass_confirm = confirm.getText().toString();

            if (pass.equals(pass_confirm)){
                try {
                    register(name,pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "password not same", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

                    //???????????????
                    OutputStream out = connection.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                    out.close();
                    int x;
                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        System.out.println("????????????");
                        // ?????????????????????
                        InputStream in = connection.getInputStream();
                        x = in.read();
                        System.out.println("fuck");
                        System.out.println(x);
                    }
                    Intent intent = new Intent(Register.this,LoginActivity.class);
                    setResult(300,intent);
                    finish();
                } catch (Exception e) {
                    System.out.println("error");
                    System.out.println(e.toString());
                }

            }
        }));
        thread.start();
    }

    public String generateFixedLengthNum(int length) {
        // ???????????????
        length = Math.abs(length);
        Random random = new Random();
        // ??????????????????????????????????????????(0.)
        String randomValue = String.valueOf(random.nextDouble()).substring(2);
        String value = "";
        int maxLength = randomValue.length();
        // ??????????????????????????????????????????????????????????????????????????????????????????
        int diff = length - maxLength;
        if (diff > 0) {
            // ??????????????????0??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            length = maxLength;
            // ???????????????????????????????????????????????????????????????????????????
            value += generateFixedLengthNum(diff);
        }
        // ????????????????????????
        value = randomValue.substring(0, length) + value;
        return value;
    }
}
