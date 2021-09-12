package com.nilphumiphat.improvecameraonandroid11.java;

import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.activity.result.contract.ActivityResultContracts;

import com.nilphumiphat.improvecameraonandroid11.R;

// ใช้ static import เพื่อเรียกใช้ extension จาก kotlin บน java
import static com.nilphumiphat.improvecameraonandroid11.ImageUtilKt.*;

public class JavaCodeActivity extends AppCompatActivity {

    private int cameraReq = 55555;

    private Bitmap bitmapData = null;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.java_main_activity);

        ((Button) findViewById(R.id.btnOpenCamera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraProcess();
            }
        });
    }

    private ActivityResultLauncher<Intent> startCameraActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        if (result.getData().getExtras() != null) {
                            bitmapData = ((Bitmap) result.getData().getExtras().get("data"));
                            if (bitmapData != null) {
                                ((ImageView) findViewById(R.id.imgView)).setImageBitmap(bitmapData);

                                // เรียกใช้ extension method จาก kotlin
                                saveImage(bitmapData, getApplicationContext());
                            }
                        }
                    }
                }
            }
    );

    private boolean isPermissionGrant(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void permissionGrant(String[] permissionLists, int requestId) {
        ActivityCompat.requestPermissions(this, permissionLists, requestId);
    }

    public void openCameraProcess() {
        if (!isPermissionGrant(Manifest.permission.CAMERA)) {
            permissionGrant(new String[]{Manifest.permission.CAMERA}, cameraReq);
            openCameraProcess();
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startCameraActivityResult.launch(cameraIntent);
    }

}
