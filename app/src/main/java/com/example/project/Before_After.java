package com.example.project;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Before_After extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String BEFORE_IMAGE_NAME = "before_image.jpg";
    private static final String AFTER_IMAGE_NAME = "after_image.jpg";
    private ImageView imageViewBefore;
    private ImageView imageViewAfter;
    private ActivityResultLauncher<Intent> cameraActivity;
    private String currentPhotoType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_after);

        imageViewBefore = findViewById(R.id.imageViewBefore);
        imageViewAfter = findViewById(R.id.imageViewAfter);
        Button buttonTakeBefore = findViewById(R.id.buttonTakeBefore);
        Button buttonTakeAfter = findViewById(R.id.buttonTakeAfter);

        // Set up the camera activity launcher
        cameraActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                            if ("before".equals(currentPhotoType)) {
                                imageViewBefore.setImageBitmap(bitmap);
                                saveImageToFile(bitmap, BEFORE_IMAGE_NAME);
                            } else if ("after".equals(currentPhotoType)) {
                                imageViewAfter.setImageBitmap(bitmap);
                                saveImageToFile(bitmap, AFTER_IMAGE_NAME);
                            }
                        }
                    }
                }
        );

        buttonTakeBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPhotoType = "before";
                checkCameraPermission();
            }
        });

        // Button to capture "after" image
        buttonTakeAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPhotoType = "after";
                checkCameraPermission();
            }
        });
    }

    // Open the camera
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            cameraActivity.launch(intent);
        }
    }

    // Check for camera permissions and request if not granted
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        } else {
            openCamera();
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Save the image to internal storage
    private void saveImageToFile(Bitmap bitmap, String fileName) {
        File directory = getApplicationContext().getFilesDir(); // Internal storage directory
        File file = new File(directory, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); // Compress as JPEG with quality 100
            Toast.makeText(this, fileName + " saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save " + fileName, Toast.LENGTH_SHORT).show();
        }
    }
}
