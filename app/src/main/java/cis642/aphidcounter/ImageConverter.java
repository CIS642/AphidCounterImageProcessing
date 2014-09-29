package cis642.aphidcounter;

import android.graphics.Matrix;
import android.os.Environment;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.File;

/**
 * Created by Staton on 9/25/2014.
 */
public class ImageConverter {

    /**
     * The source image (as a Matrix).
     */
    private Mat source;

    /**
     * The final converted image (as a Matrix).
     */
    private Mat convertedImage;

    /**
     * Sets the source image matrix.
     * @param source The source image's matrix.
     */
    public void SetSource(Mat source) {
        this.source = source;
    }

    /**
     * Gets the source image matrix.
     * @return THe source image's matrix.
     */
    public Mat GetSource() {
        return this.source;
    }

    /**
     * Gets the converted image's matrix.
     * @return The converted image's matrix.
     */
    public Mat GetConvertedImage() {
        return this.convertedImage;
    }

    /**
     * Constructs a new ImageConverter object.
     */
    public ImageConverter() {
        this.source = new Mat();
    }

    /**
     * Algorithm for converting the original color image to a black and white binary image.
     */
    public void ConvertImage() {
        convertedImage = new Mat();
        //Imgproc.medianBlur(source, convertedImage, 15);
        //Imgproc.equalizeHist(source, convertedImage);

        // Adjust the contrast and brightness of the image.
        // The 3rd parameter handles contrast, the 4th parameter handles brightness.
        source.convertTo(convertedImage, -1, 1.0, -50.0);
    }

}
