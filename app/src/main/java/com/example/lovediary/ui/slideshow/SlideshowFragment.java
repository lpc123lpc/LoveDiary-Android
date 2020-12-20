package com.example.lovediary.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lovediary.R;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {
    
    private SlideshowViewModel slideshowViewModel;
    private List<PhotoCard> PhotoCards;
    private RecyclerView photo_view;
    private RVAdapter adapter;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
    
        Button add_photo_button = root.findViewById(R.id.photo_add_button);
        add_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivityForResult(new Intent(getActivity(), AddPhoto.class),200);
            }
        });
        photo_view = root.findViewById(R.id.rv_photo);
        
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        photo_view.setLayoutManager(llm);
        photo_view.setHasFixedSize(true);
        initCards();
        initialAdapter();
        return root;
        
    }
    
    @Override
    public void onStart() {
        super.onStart();
    }
    
    private void initCards(){
        PhotoCards = new ArrayList<>();
        PhotoCards.add(new PhotoCard(R.drawable.img3));
        PhotoCards.add(new PhotoCard(R.drawable.img4));
    }
    
    public void addCard(PhotoCard card){
        PhotoCards.add(card);
    }
    
    private void initialAdapter(){
        adapter = new RVAdapter(PhotoCards);
        photo_view.setAdapter(adapter);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        PhotoCard card = (PhotoCard) data.getSerializableExtra("card");
        PhotoCards.add(card);
        initialAdapter();
    }
}