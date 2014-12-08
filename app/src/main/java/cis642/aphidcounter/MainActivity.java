package cis642.aphidcounter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.*;

import static cis642.aphidcounter.R.drawable.ic_launcher;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button take_picture_button;
    ImageView aphid_image;
    ImageConverter imageConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
        /**
         * Initialize the Take Picture button.
         */
        take_picture_button = (Button) findViewById(R.id.take_picture_button);
        take_picture_button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        Mat source = new Mat();
        Mat convertedImage = new Mat();
        Mat aphidBg = new Mat();

        imageConverter = new ImageConverter();

        try {
            // Load the image resource as a Mat:
            source = Utils.loadResource(MainActivity.this, R.drawable.test_img2_small);

            imageConverter.SetSource(source);                       // Set the source Mat
            imageConverter.ConvertImage();                          // Convert the image Mat
            convertedImage = imageConverter.GetConvertedImage();    // Get the converted Image Mat
            aphidBg = imageConverter.getImageBg();






            // Create a bitmap to store the converted image:
            Bitmap bmConvertedImage = Bitmap.createBitmap(convertedImage.cols(),
                                                          convertedImage.rows(),
                                                          Bitmap.Config.ARGB_8888);
            Bitmap bmConvertedBg = Bitmap.createBitmap(aphidBg.cols(),
                    aphidBg.rows(),
                    Bitmap.Config.ARGB_8888);

            Utils.matToBitmap(convertedImage, bmConvertedImage);    // Convert the Mat to bitmap
            Utils.matToBitmap(aphidBg,bmConvertedBg);

            // Get the imageview of the pic shown on the app screen:
            ImageView ivAphidPic = (ImageView) findViewById(R.id.aphidImage);
            ImageView ivAphidBg = (ImageView) findViewById(R.id.aphidBg);

            // Update the image shown on the app screen to the newly converted image:
            ivAphidPic.setImageBitmap(bmConvertedImage);
            ivAphidBg.setImageBitmap(bmConvertedBg);

        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
























