package cis642.aphidcounter;

import android.graphics.Matrix;
import android.widget.ImageView;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Staton on 9/25/2014.
 */
public class ImageConverter {

    Mat source;
    Mat converted_image;

    public ImageConverter(Mat source) {
        this.source = source;
    }

    public void convertImage() {
        Imgproc.medianBlur(source, converted_image, 15);
    }

    public Mat getConvertedImage() {
        return converted_image;
    }

}
