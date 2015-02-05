package cis642.aphidcounter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
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
        increment = 0;
        grayScaled = new Mat();
        originalImage = new Mat();
        matDisplay = new Mat();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private int increment,width,height;
    private Mat originalImage, resizedImage,grayScaled, matDisplay, J4, background, I2,I3, I4,M1, I5;
    private double resaled_widthFactor, rescaled_heightFactor;

    @Override
    public void onClick(View view) {
    imageConverter = new ImageConverter();
        Log.i("Process state: ", String.valueOf(increment));
        try {
            if(increment == 0){
                originalImage = Utils.loadResource(MainActivity.this, R.drawable.bw_original2);
                width = originalImage.width();
                height = originalImage.height();
                resizedImage = new Mat();
                if(width > 1200 || height > 1200){

                    Imgproc.resize(originalImage,resizedImage,new Size(1000, 850), 0, 0,Imgproc.INTER_NEAREST);
                    resaled_widthFactor = resizedImage.width()/(originalImage.width()*1.0);
                    rescaled_heightFactor = resizedImage.height()/(originalImage.height() * 1.0);
                    width = (int)(width * resaled_widthFactor);
                    height = (int) (height * rescaled_heightFactor);
                    originalImage = resizedImage;
                }

                matDisplay = originalImage.clone();
            }
            if(increment == 1){
                grayScaled = ImageConverter.grayScalConversion(originalImage);
                matDisplay = grayScaled;
            }
            if(increment ==2 ){
                J4 = new Mat();
                J4 = imageConverter.imadjust(grayScaled);
                matDisplay = J4;
            }
            if(increment == 3){
                background = new Mat();
                int strelSize = (int)(450 * resaled_widthFactor);
                Mat strel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(strelSize,strelSize));
                background = imageConverter.imopen(grayScaled,strel);
                matDisplay = background;
            }
            if(increment == 4 ){
                I2 = new Mat();
                Core.absdiff(J4,background,I2);
                matDisplay = I2;
            }
            if(increment == 5) {
                int strelSize = (int)(210 * resaled_widthFactor);
                Mat octaStrel = imageConverter.octagonStrel(strelSize);
                Log.i("STATE 5: ", "Created octagon strel");
                I3 = new Mat();
                I3 = imageConverter.imtophat(I2, octaStrel);
                matDisplay = I3;
            }
            if(increment == 6){
                I4 = new Mat();
                I4 = imageConverter.imfill(I3);
                matDisplay = I4;
            }
            if(increment == 7){
                M1 = new Mat();
                Imgproc.medianBlur(I4,M1,3);
                matDisplay = M1;
            }
            if(increment == 8){
                I5 = new Mat();
                I5 = imageConverter.imfill(M1);
                matDisplay = I5;
            }
            if(increment == 9){
                I6 = new Mat();
                int strelSize = (int)(45 * resaled_widthFactor);
                Mat strel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,new Size(strelSize,strelSize));
                I6 = imageConverter.imtophat(I5, strel);
                matDisplay = I6;
            }
            if(increment == 10){
                I7 = new Mat();
                I7 = imageConverter.imfill(I6);
                matDisplay = I7;
            }
            if(increment == 11){
                M2 = new Mat();
                Imgproc.medianBlur(I7,M2,3);
                matDisplay = M2;
            }
            if(increment == 12){
                I8 = new Mat();
                //I8 = imageConverter.imfill(M2);
                I8 = imageConverter.superFill(M2);
                matDisplay = I8;
            }
            if(increment == 13){
                I9 = new Mat();
                int strelSize = (int)(10 * resaled_widthFactor);
                Log.i("STRELSIZE:", String.valueOf(strelSize));
                if(strelSize < 3){
                    strelSize = 5;
                }
                Mat strel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_ELLIPSE, new Size(strelSize,strelSize));
                I9 = imageConverter.imtophat(I8,strel);
                matDisplay = I9;
            }
            if(increment == 14){
                M3 = new Mat();
                M3 = imageConverter.imfill(I9);
                matDisplay = M3;
            }
            if(increment == 15){
                I10 = new Mat();
                int strelSize = (int)(25 * resaled_widthFactor);
                Mat strel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_ELLIPSE, new Size(strelSize,strelSize));
                I10 = imageConverter.imtophat(M3,strel);
                matDisplay = I10;
            }
            if(increment == 16){
                I11 = new Mat();
                I11 = imageConverter.imfill(I10);
                matDisplay = I11;
            }
            if(increment == 17){
                I12 = new Mat();
                I12 = imageConverter.imadjust(I11);
                matDisplay = I12;
            }
            if(increment <= 18){
                Mat strel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_ELLIPSE, new Size(4,4));
                Imgproc.dilate(I12.clone(),I12,strel);
                matDisplay = I12;
            }


            Bitmap displayedImage = Bitmap.createBitmap(matDisplay.width(),matDisplay.height(),Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(matDisplay,displayedImage);
            ImageView aphidImage = (ImageView) findViewById(R.id.aphidImage);
            aphidImage.setImageBitmap(displayedImage);
            increment ++;
        } catch(Exception e){
            e.printStackTrace();
        }

    }
    Mat I6,I7,M2,I8,I9,M3,I10,I11,I12;

}
























