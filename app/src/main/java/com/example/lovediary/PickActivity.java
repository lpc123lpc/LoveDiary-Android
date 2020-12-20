package com.example.lovediary;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lovediary.helper.PermissionHelper;

public class PickActivity extends AppCompatActivity {
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_main);
        requestPermissions();
    }

    //1
    private void requestPermissions() {
        PermissionHelper
                .with(this)
                .permissions(PERMISSIONS)
                .CallBack(new PermissionHelper.OnPermissionRequestListener() {
                    @Override
                    public void onGranted() {
//                        Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied() {
                        PermissionHelper.showDeniedTipDialog(PickActivity.this);
                        Toast.makeText(PickActivity.this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                })
                .request();
    }
    //2
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void OnSingleSelect(View view) {
        startActivity(new Intent(this, com.example.lovediary.single.SinglePickActivity.class));
    }

    public void OnMultiSelect(View view) {
        //startActivity(new Intent(this, org.jay.androidpickimage.multi.postimage.PostImagesActivity.class));
    }
}
