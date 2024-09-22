package org.firstinspires.ftc.teamcode.Testing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class DrawRectangleProcessor implements VisionProcessor {

    // Define an array to hold the rectangles
    private List<String> redPixelCoordinates = new ArrayList<>();

    // Variables for the first red pixel found
    private int firstRedPixelX = -1;
    private int firstRedPixelY = -1;

    private Mat filteredFrame = new Mat();  // Mat for holding the filtered frame
    private int IntakeLocX = 320; // Example value for center
    private int IntakeLocY = 400; // Example value for center
    private Mat redMask = new Mat();  // Declare redMask as a class-level variable


    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        // Width and height of the frame
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        // Convert the frame to the HSV color space
        Mat hsvFrame = new Mat();
        Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);

        // Define the HSV range for red
        Scalar lowerRed1 = new Scalar(0, 100, 20);
        Scalar upperRed1 = new Scalar(180, 255, 255);
        Scalar lowerRed2 = new Scalar(160, 100, 20);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        // Threshold the HSV image to get only red colors
        Mat redMask1 = new Mat();
        Mat redMask2 = new Mat();

        Core.inRange(hsvFrame, lowerRed1, upperRed1, redMask1);
        Core.inRange(hsvFrame, lowerRed2, upperRed2, redMask2);

        // Combine the red masks and store in class-level redMask
        Core.addWeighted(redMask1, 1.0, redMask2, 1.0, 0.0, redMask);

        // Clear previous red pixel coordinates only if first red pixel wasn't found
        if (firstRedPixelX == -1 && firstRedPixelY == -1) {
            redPixelCoordinates.clear();
            findFirstRedPixel(redMask);
        }

        return frame;  // Return the original frame (or modify if needed)
    }

    private void findFirstRedPixel(Mat redMask) {
        int maxSearchDistance = Math.max(redMask.rows(), redMask.cols());
        for (int distance = 0; distance < maxSearchDistance; distance++) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dy = -distance; dy <= distance; dy++) {
                    int x = IntakeLocX + dx;
                    int y = IntakeLocY + dy;
                    if (x >= 0 && x < redMask.cols() && y >= 0 && y < redMask.rows()) {
                        if (redMask.get(y, x)[0] > 0) { // Check for red pixel
                            firstRedPixelX = x;
                            firstRedPixelY = y;
                            redPixelCoordinates.add("Row: " + y + ", Col: " + x);
                            return; // Stop after finding the first red pixel
                        }
                    }
                }
            }
        }
    }

    // Getter for red pixel coordinates
    public List<String> getRedPixelCoordinates() {
        return new ArrayList<>(redPixelCoordinates);
    }

    private android.graphics.Rect makeGraphicsRect(Rect rect, float scaleBmpPxToCanvasPx) {
        int left = Math.round(rect.x * scaleBmpPxToCanvasPx);
        int top = Math.round(rect.y * scaleBmpPxToCanvasPx);
        int right = left + Math.round(rect.width * scaleBmpPxToCanvasPx);
        int bottom = top + Math.round(rect.height * scaleBmpPxToCanvasPx);

        return new android.graphics.Rect(left, top, right, bottom);
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(scaleCanvasDensity * 4);

        // Draw a blue square around the first red pixel found
        if (firstRedPixelX != -1 && firstRedPixelY != -1) {
            Paint bluePaint = new Paint();
            bluePaint.setColor(Color.BLUE);
            bluePaint.setStyle(Paint.Style.FILL);
            int squareSize = 2; // Adjust size as needed
            int left = firstRedPixelX - squareSize / 2;
            int top = firstRedPixelY - squareSize / 2;
            int right = left + squareSize;
            int bottom = top + squareSize;
            canvas.drawRect(left * scaleBmpPxToCanvasPx, top * scaleBmpPxToCanvasPx, right * scaleBmpPxToCanvasPx, bottom * scaleBmpPxToCanvasPx, bluePaint);

            // Draw rectangles in intercardinal directions
            drawRotatedRectangles(canvas, firstRedPixelX, firstRedPixelY, scaleBmpPxToCanvasPx);
        }

        // Draw a 5x5 yellow square at the search origin
        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.GREEN);
        yellowPaint.setStyle(Paint.Style.FILL);
        int smallSquareSize = 5;
        int originLeft = IntakeLocX - smallSquareSize / 2;
        int originTop = IntakeLocY - smallSquareSize / 2;
        int originRight = originLeft + smallSquareSize;
        int originBottom = originTop + smallSquareSize;
        canvas.drawRect(originLeft * scaleBmpPxToCanvasPx, originTop * scaleBmpPxToCanvasPx, originRight * scaleBmpPxToCanvasPx, originBottom * scaleBmpPxToCanvasPx, yellowPaint);
    }

    private void drawRotatedRectangles(Canvas canvas, int pivotX, int pivotY, float scaleBmpPxToCanvasPx) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.BLUE); // Set the color for the outlines
        rectPaint.setStyle(Paint.Style.STROKE); // Set to STROKE for outlines
        rectPaint.setStrokeWidth(4); // Adjust the stroke width as needed

        // Define the size of the rectangles
        int rectWidth = 46;
        int rectHeight = 22;

        // Define the center of rotation, which is the first red pixel
        float pivotXCanvas = pivotX * scaleBmpPxToCanvasPx;
        float pivotYCanvas = pivotY * scaleBmpPxToCanvasPx;

        // Loop through to draw 6 rectangles, each rotated by 45 degrees more than the last
        for (int i = 0; i < 7; i++) {
            // Save the canvas state before applying transformations
            canvas.save();

            // Rotate the canvas by 45 degrees * i around the bottom-left corner (pivotX, pivotY)
            canvas.rotate(45 * i, pivotXCanvas, pivotYCanvas);

            // Draw the rectangle with its bottom-left corner at (pivotX, pivotY)
            float left = pivotXCanvas;
            float top = pivotYCanvas - rectHeight * scaleBmpPxToCanvasPx;
            float right = left + rectWidth * scaleBmpPxToCanvasPx;
            float bottom = pivotYCanvas;

            // Draw the rectangle
            canvas.drawRect(left, top, right, bottom, rectPaint);

            // Restore the canvas to the state before the rotation for the next rectangle
            canvas.restore();
        }
    }

    private int countRedPixelsInRect(Rect rect) {
        int redPixelCount = 0;

        // Iterate over each pixel in the rectangle's area
        for (int y = rect.y; y < rect.y + rect.height; y++) {
            for (int x = rect.x; x < rect.x + rect.width; x++) {
                if (isRedPixel(x, y)) {
                    redPixelCount++;
                }
            }
        }

        return redPixelCount;
    }

    private boolean isRedPixel(int x, int y) {
        // Check if the pixel is red in the red mask (ensure this matches your red detection logic)
        if (x >= 0 && x < redMask.cols() && y >= 0 && y < redMask.rows()) {
            return redMask.get(y, x)[0] > 0;  // Assumes redMask is a single channel binary mask
        }
        return false;
    }

}
