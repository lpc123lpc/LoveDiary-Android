package com.example.lovediary.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lovediary.R;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder> {
    
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView cardName;
        TextView cardDate;
        TextView cardContent;
        ImageView cardPhoto;
        
        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            cardName = (TextView) itemView.findViewById(R.id.card_name);
            cardDate = (TextView) itemView.findViewById(R.id.card_date);
            cardContent = (TextView) itemView.findViewById(R.id.card_content);
        }
    }
    
    private List<DiaryCard> diaryCards;
    
    
    RVAdapter(List<DiaryCard> diaryCards){
        this.diaryCards = diaryCards;
    }
    
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }
    
    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        cardViewHolder.cardName.setText(diaryCards.get(i).name);
        cardViewHolder.cardDate.setText(diaryCards.get(i).date);
        cardViewHolder.cardContent.setText(diaryCards.get(i).content);
    }
    
    @Override
    public int getItemCount() {
        return diaryCards.size();
    }
}