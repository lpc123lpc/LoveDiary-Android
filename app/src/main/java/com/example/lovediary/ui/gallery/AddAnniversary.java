package com.example.lovediary.ui.gallery;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lovediary.MainActivity;
import com.example.lovediary.R;

public class AddAnniversary extends AppCompatActivity {
    Button choose;
    ImageView imageView;
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
}
