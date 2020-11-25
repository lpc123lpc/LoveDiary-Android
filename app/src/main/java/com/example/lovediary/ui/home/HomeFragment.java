package com.example.lovediary.ui.home;

import android.os.Bundle;
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
import com.example.lovediary.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    
    private HomeViewModel homeViewModel;
    
    private List<DiaryCard> diaryCards;
    private RecyclerView rv;
    
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rv = root.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        
        initCards();
        initializeAdapter();
        return root;
    }
    
    private void initCards() {
        diaryCards = new ArrayList<>();
        diaryCards.add(new DiaryCard("Emma", "2020/11/24", "n 55IW I."));
        diaryCards.add(new DiaryCard("Emma", "2020/11/25", "你是我温暖的手套，冰冷的啤酒。带着太阳光气息的衬衫，日复一日的梦想."));
        diaryCards.add(new DiaryCard("Emma", "2020/11/25",
                "I love you more than beans and rice\n" +
                        "I love you blue, I love you green\n" +
                        "I love you more than peach ice cream\n" +
                        "I love you north, south, east and west\n" +
                        "You’re the one I love the best\n" +
                        "\n" +
                        "前两句出现在美剧《绝望的主妇》凄惨的最后一季.\n" +
                        "这是Mike曾经对Susan说过的唯一诗句，也是Susan在葬礼上的致辞。"));
        diaryCards.add(new DiaryCard("Emma", "2020/12/21", "文本文本文本文本文本文本文" +
                "本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本"));
    }
    
    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(diaryCards);
        rv.setAdapter(adapter);
    }
    
}