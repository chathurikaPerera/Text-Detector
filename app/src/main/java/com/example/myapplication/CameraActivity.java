package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {

    TextView text;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        image = findViewById(R.id.imageView);
        text = findViewById(R.id.text);



        /*byte[] byteArrayExtra = getIntent().getByteArrayExtra("photo");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length, new BitmapFactory.Options());
        image.setImageBitmap(bitmap);*/

        Bundle bundle = getIntent().getExtras();
        //String s = bundle.getString("s");
        if(getIntent().hasExtra("photo")){
            byte[] byteArrayExtra = getIntent().getByteArrayExtra("photo");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length, new BitmapFactory.Options());
            image.setImageBitmap(bitmap);


            //FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            //2.get a istance of firebase vision
            FirebaseVision firebaseVision = FirebaseVision.getInstance();
            //create an instance of firebaseVisionTextReconizor
            FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
            //create a task to process the image
            Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
            //if task is success
            task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    String s = firebaseVisionText.getText();
                    text.setText(s);
                }
            });
            //if the task is fail
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
        //text.setText(s);

    }

}