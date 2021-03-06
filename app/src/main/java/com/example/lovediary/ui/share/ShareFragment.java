package com.example.lovediary.ui.share;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.lovediary.R;
import com.example.lovediary.ui.login.LoginActivity;
import com.example.lovediary.ui.login.Register;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;

public class ShareFragment extends Fragment {
    
    private ShareViewModel shareViewModel;
    private int x;
    String user;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        EditText editText = root.findViewById(R.id.match);
        Button button = root.findViewById(R.id.match_button);
        button.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_user", Context.MODE_PRIVATE);
            user = sharedPreferences.getString("user",null);
            System.out.println(user);
            try {
                match(user,editText.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return root;
    }

    private void match(String user,String matchCode) throws Exception {
        Thread thread = new Thread( (new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://121.5.50.186:8080/op/match");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("contentType", "application/json");
                    String data = "userName="+URLEncoder.encode(user,"UTF-8")+
                            "&matchCode="+URLEncoder.encode(matchCode,"UTF-8");

                    //???????????????
                    OutputStream out = connection.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                    out.close();

                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        System.out.println("????????????");
                        // ?????????????????????
                        InputStream in = connection.getInputStream();
                        x = in.read();
                        System.out.println("fuck");
                        System.out.println(x);
                    }

                } catch (Exception e) {
                    System.out.println("error");
                    System.out.println(e.toString());
                }

            }
        }));
        thread.start();
        while (thread.isAlive()){

        }
        if (x==49){
            Toast.makeText(requireActivity().getApplicationContext(), "match succeed", Toast.LENGTH_LONG).show();
        }
    }
}