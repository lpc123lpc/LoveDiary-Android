package com.example.lovediary.ui.gallery;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lovediary.MainActivity;
import com.example.lovediary.R;
import com.example.lovediary.messages.MessageListActivity;
import com.example.lovediary.ui.home.DiaryCard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    
    private GalleryViewModel galleryViewModel;

    private List<AnniversaryCard> anniversaryCards;
    private RecyclerView anniversary_view;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        FloatingActionButton add_anniversary_button = root.findViewById(R.id.anniversary_add_button);
        add_anniversary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //               .setAction("Action", null).show();
                startActivity(new Intent(getActivity(), AddAnniversary.class));
            }
        });
        anniversary_view = root.findViewById(R.id.rv_anniversary);

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        anniversary_view.setLayoutManager(llm);
        anniversary_view.setHasFixedSize(true);
        initCards();
        initialAdapter();
        return root;

    }


    private void initCards(){
        anniversaryCards = new ArrayList<>();
        anniversaryCards.add(new AnniversaryCard("在一起的日子","wuhu","2020/10/1","dog"));
    }

    public void addCard(AnniversaryCard card){
        anniversaryCards.add(card);
    }

    private void initialAdapter(){
        AniViewAdapter adapter = new AniViewAdapter(anniversaryCards);
        anniversary_view.setAdapter(adapter);
    }




}