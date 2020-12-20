package com.example.lovediary.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lovediary.R;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder> {
    
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView cardPhoto;
        
        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.photo_card);
            cardPhoto = (ImageView) itemView.findViewById(R.id.photo_img);
        }
    }
    
    private List<PhotoCard> photoCards;
    
    
    RVAdapter(List<PhotoCard> photoCards){
        this.photoCards = photoCards;
    }
    
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    
    @Override
    public RVAdapter.CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_card, viewGroup, false);
        RVAdapter.CardViewHolder pvh = new RVAdapter.CardViewHolder(v);
        return pvh;
    }
    
    @Override
    public void onBindViewHolder(RVAdapter.CardViewHolder cardViewHolder, int i) {
        cardViewHolder.cardPhoto.setImageResource(photoCards.get(i).imgId);
    }
    
    @Override
    public int getItemCount() {
        return photoCards.size();
    }
}
