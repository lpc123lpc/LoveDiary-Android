package com.example.lovediary.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lovediary.MainActivity;
import com.example.lovediary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddDiary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        Button button = findViewById(R.id.diary_confirm);

        button.setOnClickListener(v -> {
            EditText titleText = findViewById(R.id.add_diary_title);
            EditText contentText = findViewById(R.id.add_diary_content);
            String title = titleText.getText().toString();
            String content = contentText.getText().toString();
            String date = getTime();

            DiaryCard card = new DiaryCard(title,date,content);
            Intent intent = new Intent(AddDiary.this, MainActivity.class);
            intent.putExtra("card",card);
            setResult(100,intent);
            finish();
        });
    }

    private String getTime(){
        @SuppressLint("SimpleDateFormat")
        /*SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式

        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区

        Date curDate = new Date(System.currentTimeMillis()); //获取当前时间

        String createDate = formatter.format(curDate);   //格式转换*/
        Calendar calendar = Calendar.getInstance();//取得当前时间的年月日 时分秒

        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH)+1;

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year+"-"+month+"-"+day;
    }



}
