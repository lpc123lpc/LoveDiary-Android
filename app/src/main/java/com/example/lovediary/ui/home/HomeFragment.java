package com.example.lovediary.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.lovediary.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
        FloatingActionButton button = root.findViewById(R.id.add_diary);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddDiary.class);
                startActivityForResult(intent,100);
            }
        });
        return root;
    }
    
    private void initCards() {
        diaryCards = new ArrayList<>();
        diaryCards.add(new DiaryCard("Emma", "2020/11/24", "n 55IW I."));
    }
    
    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(diaryCards);
        rv.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        DiaryCard card = (DiaryCard) data.getSerializableExtra("card");
        diaryCards.add(card);
        initializeAdapter();
    }

    private void sendRequest(String user,String password) throws Exception {
        Thread thread = new Thread( (new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://121.5.50.186:8080/op/get");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("contentType", "application/json");
                    String data = "password="+ URLEncoder.encode(password,"UTF-8")+
                            "&userName="+URLEncoder.encode(user,"UTF-8");

                    //獲取輸出流
                    int x=0;
                    OutputStream out = connection.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                    out.close();
                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        System.out.println("连接成功");
                        // 请求返回的数据
                        InputStream in = connection.getInputStream();
                        x = in.read();
                        System.out.println("fuck");
                        System.out.println(x);
                        /*while (x != -1) {
                            System.out.println(x);
                        }*/
                    }
                    if (x==49){
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    System.out.println("error");
                    System.out.println(e.toString());
                }

            }
        }));
        thread.start();

    }
}