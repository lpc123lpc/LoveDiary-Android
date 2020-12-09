package com.example.lovediary.ui.gallery;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.engine.Resource;
import com.example.lovediary.R;

import java.util.ArrayList;
import java.util.List;

public class AniViewAdapter extends RecyclerView.Adapter<AniViewAdapter.AnniversaryCardViewHolder> {

    public static class AnniversaryCardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView cardName;
        TextView cardDate;
        TextView cardContent;
        ImageView cardPhoto;

        AnniversaryCardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.anniversary_card);
            cardName = (TextView) itemView.findViewById(R.id.anniversary_name);
            cardDate = (TextView) itemView.findViewById(R.id.anniversary_date);
            cardContent = (TextView) itemView.findViewById(R.id.anniversary_content);
            cardPhoto = (ImageView) itemView.findViewById(R.id.anniversary_img);
        }
    }

    private List<AnniversaryCard> anniversaryCards;


    AniViewAdapter(List<AnniversaryCard> anniversaryCards){
        this.anniversaryCards = anniversaryCards;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AnniversaryCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.anniversary_card, viewGroup, false);
        AnniversaryCardViewHolder pvh = new AnniversaryCardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AnniversaryCardViewHolder cardViewHolder, int i) {
        cardViewHolder.cardName.setText(anniversaryCards.get(i).name);
        cardViewHolder.cardContent.setText(anniversaryCards.get(i).content);
        cardViewHolder.cardDate.setText(anniversaryCards.get(i).date);
        cardViewHolder.cardPhoto.setImageResource(anniversaryCards.get(i).imgId);
    }

    @Override
    public int getItemCount() {
        return anniversaryCards.size();
    }
}