package com.example.tomek.foto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView imageView;
    static final int CAM_REQUEST = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.image_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent, CAM_REQUEST);

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private File getFile() {
        File folder = new File("sdcard/camera_app");

        if (!folder.exists()) {
            folder.mkdir();
        }
        File image_file = new File(folder, "cam_image.bmp");
        return image_file;
    }

//
//    private void changeBitmapColor(Bitmap sourceBitmap, ImageView image, int color) {
//
//        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
//                sourceBitmap.getWidth(), sourceBitmap.getHeight());
//        Paint p = new Paint();
//        ColorFilter filter = new LightingColorFilter(color, 1);
//        p.setColorFilter(filter);
//        image.setImageBitmap(resultBitmap);
//
//        Canvas canvas = new Canvas(resultBitmap);
//        canvas.drawBitmap(resultBitmap, 0, 0, p);
//    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = "sdcard/camera_app/cam_image.bmp";






         //imageView.setImageDrawable(Drawable.createFromPath(path));

        ColorMatrix colorMatrix;
        Bitmap sourceBitmap = BitmapFactory.decodeFile(path);
        float[] colorTransform = {
                1f, 1f, 0, 0, 0,
                0, 0, 0f, 0, 0,
                0, 0, 0, 0f, 0,
                0, 0, 0, 1f, 0};

        colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0f); //Remove Colour
        colorMatrix.set(colorTransform); //Apply the Red

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        Paint paint = new Paint();
        paint.setColorFilter(colorFilter);

        Display display = getWindowManager().getDefaultDisplay();


        //Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, display.getHeight(), display.getWidth(), display.getHeight());
        //Bitmap resultBitmap =  Bitmap.createBitmap(sourceBitmap, display.getWidth() , display.getHeight()  , display.getWidth(), display.getHeight());
        //Bitmap resultBitmap =  Bitmap.createBitmap(sourceBitmap, sourceBitmap.getHeight(), sourceBitmap.getWidth(),sourceBitmap.getWidth(),sourceBitmap.getHeight());
        Bitmap resultBitmap =  Bitmap.createBitmap(sourceBitmap, display.getWidth(), display.getHeight(), display.getWidth()*2, display.getHeight()*2);
        imageView.setImageBitmap(resultBitmap);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, paint);
    }
}
