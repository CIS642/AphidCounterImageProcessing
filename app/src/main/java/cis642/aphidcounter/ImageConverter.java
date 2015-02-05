package cis642.aphidcounter;


import android.graphics.Matrix;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Staton on 9/25/2014.
 */
public class ImageConverter {
    public static Mat grayScalConversion(Mat src){
        Mat grayScaled = new Mat();
        Imgproc.cvtColor(src,grayScaled,Imgproc.COLOR_RGB2GRAY);
        return grayScaled;
    }
    public static Mat imadjust(Mat src){
        Mat imadjusted = src.clone();
        //Imgproc.equalizeHist(src,imadjusted);
        imadjusted.convertTo(src,-1, 1.5, -100.0);
        return imadjusted;
    }
    public static Mat imopen(Mat src, Mat strel){
        Mat dst = new Mat();
        Imgproc.erode(src,dst,strel);
        Log.i("IMGconv.imopen","eroded");
        Mat dst2 = new Mat();
        Imgproc.dilate(dst,dst2,strel);
        Log.i("IMGconv.imopen","dilated");
        return dst2;
    }
    public static Mat imtophat(Mat src,Mat strel){
        Mat topHatted = new Mat();
        Mat imopened = new Mat();
        imopened = imopen(src,strel);
        Core.absdiff(src,imopened,topHatted);
        return topHatted;
    }
    public static Mat imfill(Mat src){
        Mat floodMask = new Mat();
        floodMask = src.clone();
        Imgproc.Canny(src, floodMask, 100, 255);
        Imgproc.copyMakeBorder(floodMask, floodMask, 1, 1, 1, 1, Imgproc.BORDER_REPLICATE);
        Mat filled = src.clone();
        Imgproc.floodFill(filled,floodMask,new Point(1,1), new Scalar(255));
        return filled;
    }
    public static Mat superFill(Mat src){
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat heirarchy = new Mat();
        Imgproc.findContours(src.clone(),contours,heirarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        Mat  holes = new Mat();
        Mat moreHoles = new Mat();
        Iterator<MatOfPoint> contourIte = contours.iterator();
        Imgproc.drawContours(src,contours,0,new Scalar(255),Imgproc.CV_WARP_FILL_OUTLIERS,8,heirarchy);
        while (contourIte.hasNext()){
            MatOfPoint contInst = contourIte.next();

        }
        return src;
    }
    public static Mat findEdges(Mat src){
        Mat mask = new Mat();
        Imgproc.Canny(src,mask,100,200);
        return mask;
    }


    public static Mat octagonStrel(int size){
        Mat octaStrel = new Mat();
        int dim = 1 + (size *2);
        Mat strel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(dim,dim));
        octaStrel = rotate(strel);
        return octaStrel;
    }


    private static Mat rotate(Mat src){
        Point pt = new Point(src.width()/2,src.height()/2);
        Mat r = new Mat();
        r = Imgproc.getRotationMatrix2D(pt,45.0,1.0);
        Mat rotatedImage = new Mat();
        Imgproc.warpAffine(src,rotatedImage,r,new Size(src.width(),src.height()));
        return rotatedImage;
    }

    private static void displayMat(Mat mat){
        for(int i = 0 ; i < mat.width(); i ++){
            String line = "";
            for(int j = 0 ; j < mat.height() ; j ++){
                line += String.valueOf((int)mat.get(i,j)[0]) + " ";
            }
            Log.i("",line);
        }
    }


}
