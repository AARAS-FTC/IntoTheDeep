package org.firstinspires.ftc.teamcode.CompVision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FindRedSampleCenterProcessor extends OpenCvPipeline implements VisionProcessor {
    private final int SAMPLE_PIXEL_LENGTH = 46; // change this to reality

    // Define an array to hold the rectangles
    private List<String> redPixelCoordinates = new ArrayList<>();

    // Variables for the first red pixel found
    public int intakeX = 320; // Example value for center
    public int intakeY = 400; // Example value for center
    private int firstRedPixelX = -1;
    private int firstRedPixelY = -1;
    private boolean pixelFound = false;
    private boolean centerFound = false;

    int n = 0; int ne = 0; int e = 0; int se = 0; int s = 0; int sw = 0; int w= 0; int nw = 0;
    List<Integer> directionArray;
    private Mat redMask = new Mat();  // Declare redMask as a class-level variable


    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        // Width and height of the frame
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        // Convert the frame to the HSV color space
        Mat hsvFrame = new Mat();
        Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_RGB2HSV);

        // Define the HSV range for red
        Scalar lowerRed1 = new Scalar(0, 100, 100);
        Scalar upperRed1 = new Scalar(11, 255, 255);
        Scalar lowerRed2 = new Scalar(120, 100, 100);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        // Threshold the HSV image to get only red colors
        Mat redMask1 = new Mat();
        Mat redMask2 = new Mat();

        Core.inRange(hsvFrame, lowerRed1, upperRed1, redMask1);
        Core.inRange(hsvFrame, lowerRed2, upperRed2, redMask2);

        // Combine the red masks and store in class-level redMask
        Core.addWeighted(redMask1, 1.0, redMask2, 1.0, 0.0, redMask);

        findFirstRedPixel(redMask);
        if(!centerFound) {
            findRectOrientation(redMask);
        }

        Mat filteredFrame = new Mat();
        Core.bitwise_and(frame, frame, filteredFrame, redMask);

        // Return the filtered frame for display
        return filteredFrame;
    }

    private void findFirstRedPixel(Mat redMask) {
        if (pixelFound) return; // Don't search again if already found

        int maxSearchDistance = Math.min(redMask.rows(), redMask.cols());
        for (int distance = 0; distance < maxSearchDistance; distance++) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dy = -distance; dy <= distance; dy++) {
                    int x = intakeX + dx;
                    int y = intakeY + dy;
                    if (x >= 0 && x < redMask.cols() && y >= 0 && y < redMask.rows()) {
                        if (redMask.get(y, x)[0] > 0) { // Check for red pixel
                            firstRedPixelX = x;
                            firstRedPixelY = y;
                            redPixelCoordinates.add("Row: " + y + ", Col: " + x);
                            pixelFound = true; // Lock the pixel once found
                            return;
                        }
                    }
                }
            }
        }
    }

    private void findRectOrientation(Mat redMask){
        int maxSearchDistance = Math.max(redMask.rows(), redMask.cols());
        if(firstRedPixelX < intakeX){
            // pixel is towards the west, only search west directions
            for(int i = 0; i <= SAMPLE_PIXEL_LENGTH; i++){
                // North (col, row - i )
                if(firstRedPixelX <= redMask.cols() &&
                firstRedPixelY - i >= 0 &&
                firstRedPixelY - i <= redMask.rows()){
                    if (redMask.get(firstRedPixelY - i, firstRedPixelX)[0] > 0){
                        n++;
                    }
                }

                // South
                if(firstRedPixelX <= redMask.cols() &&
                        firstRedPixelY + i >= 0 && firstRedPixelY + i <= redMask.rows()){

                    if (redMask.get(firstRedPixelY + i, firstRedPixelX)[0] > 0){
                        s++;
                    }
                }

                // NorthWest
                if(firstRedPixelX - i <= redMask.cols() && firstRedPixelX - i >= 0 &&
                        firstRedPixelY - i <= redMask.rows() && firstRedPixelY  - i >= 0){

                    if(redMask.get(firstRedPixelY - i, firstRedPixelX - i)[0] > 0){
                        nw++;
                    }
                }

                //SouthWest
                if(firstRedPixelX - i <= redMask.cols() && firstRedPixelX - i >= 0 &&
                        firstRedPixelY + i <= redMask.rows() && firstRedPixelY  + i >= 0){

                    if(redMask.get(firstRedPixelY + i, firstRedPixelX - i)[0] > 0){
                        sw++;
                    }
                }

                //West
                if(firstRedPixelX - i <= redMask.cols() && firstRedPixelX - i >= 0 &&
                        firstRedPixelY <= redMask.rows() && firstRedPixelY >= 0){

                    if(redMask.get(firstRedPixelY, firstRedPixelX - i)[0] > 0){
                        w++;
                    }
                }
            }
        }else{
            //pixel is towards the east, only search east directions
            for(int i = 0; i <= SAMPLE_PIXEL_LENGTH; i++){
                // North (col, row - i )
                if(firstRedPixelX <= redMask.cols() &&
                        firstRedPixelY - i >= 0 &&
                        firstRedPixelY - i <= redMask.rows()){
                    if (redMask.get(firstRedPixelY - i, firstRedPixelX)[0] > 0){
                        n++;
                    }
                }

                // South
                if(firstRedPixelX <= redMask.cols() &&
                        firstRedPixelY + i >= 0 && firstRedPixelY + i <= redMask.rows()){

                    if (redMask.get(firstRedPixelY + i, firstRedPixelX)[0] > 0){
                        s++;
                    }
                }

                // NorthEast
                if(firstRedPixelX + i <= redMask.cols() && firstRedPixelX + i >= 0 &&
                        firstRedPixelY - i <= redMask.rows() && firstRedPixelY  - i >= 0){

                    if(redMask.get(firstRedPixelY - i, firstRedPixelX + i)[0] > 0){
                        ne++;
                    }
                }

                //SouthEast
                if(firstRedPixelX + i <= redMask.cols() && firstRedPixelX + i >= 0 &&
                        firstRedPixelY + i <= redMask.rows() && firstRedPixelY  + i >= 0){

                    if(redMask.get(firstRedPixelY + i, firstRedPixelX + i)[0] > 0){
                        se++;
                    }
                }

                // East
                if(firstRedPixelX + i <= redMask.cols() && firstRedPixelX + i >= 0 &&
                        firstRedPixelY<= redMask.rows() && firstRedPixelY>= 0){

                    if(redMask.get(firstRedPixelY, firstRedPixelX - i)[0] > 0){
                        e++;
                    }
                }
            }
        }

        directionArray = Arrays.asList(n, ne, e, se, s, sw, w, nw);
    }

    // Getter for red pixel coordinates
    public Point getRedPixelCoordinates() {
        return new Point(firstRedPixelX, firstRedPixelY);
    }

    public Point getPixelCenter(){
        centerFound = true;
        int pixelsToCenter = getMaxDirection() / 2;
        switch(getPixelorientation()) {
            case 0: // North
                return new Point(firstRedPixelX, firstRedPixelY - pixelsToCenter);

            case 1: // North East
                return new Point(firstRedPixelX + pixelsToCenter, firstRedPixelY - pixelsToCenter);
            case 2: // East
                return new Point(firstRedPixelX + pixelsToCenter, firstRedPixelY);
            case 3: // South East
                return new Point(firstRedPixelX + pixelsToCenter, firstRedPixelY + pixelsToCenter);
            case 4: // South
                return new Point(firstRedPixelX, firstRedPixelY + pixelsToCenter);
            case 5: // South West
                return new Point(firstRedPixelX - pixelsToCenter, firstRedPixelY + pixelsToCenter);
            case 6: // West
                return new Point(firstRedPixelX - pixelsToCenter, firstRedPixelY);
            case 7:
                return new Point(firstRedPixelX - pixelsToCenter, firstRedPixelY - pixelsToCenter);
            default:
                return new Point(firstRedPixelX, firstRedPixelY);
        }
    }

    public int getPixelorientation(){
        return directionArray.indexOf(getMaxDirection());
    }

    public List<Integer> getDirectionArray(){
        return directionArray;
    }

    private int getMaxDirection(){

        return Collections.max(directionArray);

    }

    @Override
    public Mat processFrame(Mat input) {
        // Convert the frame to the HSV color space
        Mat hsvFrame = new Mat();
        Imgproc.cvtColor(input, hsvFrame, Imgproc.COLOR_RGB2HSV);

        // Define the HSV range for red
        Scalar lowerRed1 = new Scalar(0, 100, 100);
        Scalar upperRed1 = new Scalar(11, 255, 255);
        Scalar lowerRed2 = new Scalar(120, 100, 100);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        // Threshold the HSV image to get only red colors
        Mat redMask1 = new Mat();
        Mat redMask2 = new Mat();

        Core.inRange(hsvFrame, lowerRed1, upperRed1, redMask1);
        Core.inRange(hsvFrame, lowerRed2, upperRed2, redMask2);

        // Combine the red masks and store in class-level redMask
        Core.addWeighted(redMask1, 1.0, redMask2, 1.0, 0.0, redMask);

        findFirstRedPixel(redMask);
        if(!centerFound) {
            findRectOrientation(redMask);
        }

        Mat filteredFrame = new Mat();
        Core.bitwise_and(input, input, filteredFrame, redMask);

        // Return the filtered frame for display
        return filteredFrame;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(scaleCanvasDensity * 4);

        // Draw a 5x5 yellow square at the search origin
        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.GREEN);
        yellowPaint.setStyle(Paint.Style.FILL);
        int smallSquareSize = 5;
        int originLeft = intakeX - smallSquareSize / 2;
        int originTop = intakeY - smallSquareSize / 2;
        int originRight = originLeft + smallSquareSize;
        int originBottom = originTop + smallSquareSize;
        canvas.drawRect(originLeft * scaleBmpPxToCanvasPx, originTop * scaleBmpPxToCanvasPx, originRight * scaleBmpPxToCanvasPx, originBottom * scaleBmpPxToCanvasPx, yellowPaint);

        drawFoundPixel(canvas, scaleBmpPxToCanvasPx);
//        pixelCalibrationLine(canvas, scaleBmpPxToCanvasPx, scaleCanvasDensity);
        if (pixelFound) {
            drawPixelCenter(canvas, scaleBmpPxToCanvasPx);
        }
    }

    private void drawFoundPixel(Canvas canvas, float scaleBmpPxToCanvasPx ){
        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);
        int squareSize = 2; // Adjust size as needed
        int left = firstRedPixelX - squareSize / 2;
        int top = firstRedPixelY - squareSize / 2;
        int right = left + squareSize;
        int bottom = top + squareSize;
        canvas.drawRect(left * scaleBmpPxToCanvasPx, top * scaleBmpPxToCanvasPx,
                right * scaleBmpPxToCanvasPx, bottom * scaleBmpPxToCanvasPx, bluePaint);
    }

    private void drawPixelCenter(Canvas canvas, float scaleBmpPxToCanvasPx){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        Point center = getPixelCenter();
        int squareSize = 2; // Adjust size as needed
        int left = (int) center.x - squareSize / 2;
        int top = (int) center.y - squareSize / 2;
        int right = left + squareSize;
        int bottom = top + squareSize;
        canvas.drawRect(left * scaleBmpPxToCanvasPx, top * scaleBmpPxToCanvasPx,
                right * scaleBmpPxToCanvasPx, bottom * scaleBmpPxToCanvasPx, paint);
    }
    private void pixelCalibrationLine(Canvas canvas, float scaleBmpPxToCanvasPx, float scaleCanvasDensity){
        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStrokeWidth(scaleCanvasDensity * 4);

        canvas.drawLine(firstRedPixelX * scaleBmpPxToCanvasPx, firstRedPixelY * scaleBmpPxToCanvasPx,
                Math.max(0, firstRedPixelX - SAMPLE_PIXEL_LENGTH) * scaleBmpPxToCanvasPx,
                firstRedPixelY * scaleBmpPxToCanvasPx, greenPaint);
    }

}
