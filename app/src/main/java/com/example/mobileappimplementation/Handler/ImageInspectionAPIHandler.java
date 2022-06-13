package com.example.mobileappimplementation.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileappimplementation.R;
import com.example.mobileappimplementation.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageInspectionAPIHandler extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_inspection);
        ImageView imageViewer = findViewById(R.id.crop_image);
        Bundle caller = getIntent().getExtras();
        if (caller.containsKey("imageBitmapData")) {
            Bitmap image = (Bitmap) caller.getParcelable("imageBitmapData");
            image = Bitmap.createScaledBitmap(image,224,224,false);
            imageViewer.setImageBitmap(image);
            try {
                predictImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Uri image = (Uri) caller.getParcelable("imageUriData");
            imageViewer.setImageURI(image);
            Bitmap toBitmap = null;
            try {
                toBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                toBitmap = Bitmap.createScaledBitmap(toBitmap,224,224,false);
                predictImage(toBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void predictImage(Bitmap bitmap) throws IOException {
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            int [] intValues = new int[224 * 224];
            bitmap.getPixels(intValues,0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            int pixels = 0;
            for(int i = 0; i < 224; i++){
                for(int j = 0; j < 224; j++){
                    int val = intValues[pixels++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f /255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f /255.f));
                    byteBuffer.putFloat((val  & 0xFF) * (1.f /255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float [] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;

            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String [] classes ={
                    "This is a healthy wheat crop",
                    "This is a healthy apple fruit",
                    "This wheat crop is contaminated with leave rust disease",
                    "This apple is contaminated with scab",
                    "This cherry crop is contaminated with powdery mildew",
                    "This peach crop is contaminated with bacterial spot",
                    "This is a healthy cherry crop",
                    "This blueberry crop is healthy",//this pos
                    "This corn crop is contaminated with leaf spot",
                    "This is a health corn crop",
                    "This is a healthy peach crop",
                    "This is a healthy tomato crop",
                    "This tomato crop is contaminated with leaf spot"
            };
            TextView result = findViewById(R.id.image_inspection_result);
            boolean checkOne = !(classes[maxPos].equals("This is a healthy wheat crop"));
            boolean checkTwo = !(classes[maxPos].equals("This wheat crop is contaminated with leave rust disease"));
            if(checkOne && checkTwo) {
                result.setText("Wheat crop not detected");
                return;
            }
            result.setText(classes[maxPos]);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }

    public static String fetchModelFile(Context context, String modelName) throws IOException {
        File file = new File(context.getFilesDir(), modelName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(modelName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }
}