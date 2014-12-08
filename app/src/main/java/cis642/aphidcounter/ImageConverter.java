package cis642.aphidcounter;


import android.app.Service;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.File;

/**
 * Created by Staton on 9/25/2014.
 */
public class ImageConverter{

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
        this.convertedImage = new Mat();
        this.imageBg = new Mat();
    }

    /**
     * Algorithm for converting the original color image to a black and white binary image.
     */
    private final double bgStrelConst_width = 0.02;
    private final double bgStrelConst_height = 0.02;
    public void ConvertImage() {
        //Getting image variables to be used with respect to image size used.
        int width = source.width();
        int height = source.height();
        int bgStrel_width = (int) Math.round(width * bgStrelConst_width);
        int bgStrel_height = (int) Math.round(height * bgStrelConst_height);


        //converts the image to grayscale
        Log.i("Process Trace", "begining grayscale conversion");
        Imgproc.cvtColor(source, convertedImage, Imgproc.COLOR_RGB2GRAY);
        Log.i("Process Trace","done with grayscale conversion");


        //adjust the image intensity/contrast
        Log.i("Process Trace","begining image intensity/contrast adjustment");
        convertedImage.convertTo(convertedImage, -1, 1.0, -100.0);
        Log.i("Process Trace","done with image intensity/contrast adjustment");
        //Removing Background
        //Creating background Strel
        imageBg = convertedImage.clone();
        Imgproc.medianBlur(imageBg, imageBg, 21);//blur out aphids in bg
        Imgproc.equalizeHist(imageBg,imageBg);
        //Imgproc.threshold(imageBg,imageBg,90,110,Imgproc.THRESH_BINARY);
       // Imgproc.erode(imageBg,imageBg,Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(bgStrel_width,bgStrel_height)));


        Core.absdiff(convertedImage, imageBg, convertedImage);

        //convertedImage.convertTo(convertedImage,-1,1.0, -100);

    }

    private Mat imageBg;
    public Mat getImageBg(){
        return imageBg;
    }

}
