package com.example.lovediary.ui.home;

import java.io.Serializable;

public class DiaryCard implements Serializable {
    String name;
    String date;
    String content;
    
    DiaryCard(String name, String date, String content) {
        this.name = name;
        this.date = date;
        this.content = content;
    }
}
