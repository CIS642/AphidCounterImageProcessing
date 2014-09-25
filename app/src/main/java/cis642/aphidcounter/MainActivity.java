package cis642.aphidcounter;

import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.*;


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
        Mat source = Highgui.imread("/res/drawable-hdpi/ic_launcher.png", Highgui.CV_LOAD_IMAGE_COLOR);
        imageConverter = new ImageConverter(source);
        imageConverter.convertImage();  /* Convert the image */

        Mat converted_img;
        converted_img = imageConverter.getConvertedImage();

        Highgui.imwrite("/res/drawable-hdpi/outputimage.png", converted_img);

    }
}
























