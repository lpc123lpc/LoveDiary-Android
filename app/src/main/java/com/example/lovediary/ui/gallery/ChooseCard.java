package com.example.lovediary.ui.gallery;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lovediary.R;

public class ChooseCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_card);

        ImageView img1 = findViewById(R.id.card_picture1_view);
        ImageView img2 = findViewById(R.id.card_picture2_view);

        final Intent intent = new Intent(ChooseCard.this,AddAnniversary.class);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id",R.drawable.dog);
                setResult(100,intent);
                finish();
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id",R.drawable.img1);
                setResult(100,intent);
                finish();
            }
        });
    }
}
