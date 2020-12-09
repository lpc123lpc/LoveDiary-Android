package com.example.lovediary.ui.gallery;

import java.io.Serializable;

public class AnniversaryCard implements Serializable {
    String name;
    String content;
    String date;
    int imgId;

    AnniversaryCard(String name, String content, String date,int img){
        this.name = name;
        this.content = content;
        this.date = date;
        this.imgId = img;
    }

}
