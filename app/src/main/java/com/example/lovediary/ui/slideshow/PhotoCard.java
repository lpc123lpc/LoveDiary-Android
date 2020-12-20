package com.example.lovediary.ui.slideshow;

import java.io.Serializable;

public class PhotoCard implements Serializable {
    int imgId;
    
    PhotoCard(int img){
        this.imgId = img;
    }
    
}
