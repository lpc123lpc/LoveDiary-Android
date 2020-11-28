package com.example.lovediary.ui.gallery;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lovediary.MainActivity;
import com.example.lovediary.R;

public class AddAnniversary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anniversary);

        Button button = findViewById(R.id.anniversary_confirm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_Title = findViewById(R.id.add_anniversary_title);
                String title = edit_Title.getText().toString();
                EditText edit_content = findViewById(R.id.add_anniversary_content);
                String content = edit_content.getText().toString();
                EditText edit_date = findViewById(R.id.add_anniversary_date);
                String date = edit_date.getText().toString();

                //将内容传给服务器；
                //返回fragment
                Intent intent = new Intent();
                intent.setClass(AddAnniversary.this, MainActivity.class);
                intent.putExtra("id","nav_gallery");
                startActivity(intent);
            }
        });
    }
}
