package com.example.lovediary.ui.gallery;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lovediary.MainActivity;
import com.example.lovediary.R;
import com.example.lovediary.ui.login.LoginActivity;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Random;

public class AddAnniversary extends AppCompatActivity {
    Button choose;
    ImageView imageView;
    private Calendar cal;
    private int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anniversary);

        Button button = findViewById(R.id.anniversary_confirm_button);
        choose = findViewById(R.id.choose_card_picture);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent = new Intent(AddAnniversary.this,ChooseCard.class);
                startActivityForResult(intent,100);
            }
        });

        getDate();
        EditText editDate = findViewById(R.id.add_anniversary_date);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editDate.setText(year + "-" + (month+1) + "-" + day);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(AddAnniversary.this,0,listener,year,month,day);
                dialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_Title = findViewById(R.id.add_anniversary_title);
                String title = edit_Title.getText().toString();
                EditText edit_content = findViewById(R.id.add_anniversary_content);
                String content = edit_content.getText().toString();
                EditText edit_date = findViewById(R.id.add_anniversary_date);
                String date = edit_date.getText().toString();
                int imgID = (int)imageView.getTag();
                //将内容传给服务器；
                //返回fragment
                Intent intent = new Intent();
                AnniversaryCard card = new AnniversaryCard(title,content,date,imgID);
                intent.setClass(AddAnniversary.this, MainActivity.class);
                intent.putExtra("card", card);
                setResult(200,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        int  id = data.getIntExtra("id",0);
        choose.setVisibility(View.INVISIBLE);
        imageView = findViewById(R.id.add_picture_img);
        imageView.setImageResource(id);
        imageView.setTag(id);
        imageView.setVisibility(View.VISIBLE);
    }

    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);    //获取年月日时分秒
        //Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);  //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }

    private void sendRequest(String user,String password) throws Exception {
        Thread thread = new Thread( (new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://121.5.50.186:8080/op/add");
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
                    }

                } catch (Exception e) {
                    System.out.println("error");
                    System.out.println(e.toString());
                }

            }
        }));
        thread.start();
    }

}
