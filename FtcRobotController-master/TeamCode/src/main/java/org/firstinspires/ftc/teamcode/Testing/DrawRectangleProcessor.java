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
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

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
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(4);

        int rectWidth = 32;
        int rectHeight = 22;

        // Convert pivot coordinates to canvas coordinates
        float pivotXCanvas = pivotX * scaleBmpPxToCanvasPx;
        float pivotYCanvas = pivotY * scaleBmpPxToCanvasPx;

        RotatedRect maxRedPixelRect = null;
        int maxRedPixelCount = -1;

        // Loop to draw 6 rectangles, each rotated by 45 degrees
        for (int i = 0; i < 6; i++) {
            // Define the rotation angle for the current rectangle
            double angle = 45 * i;

            // Create the rotated rectangle with the bottom-left corner on the pivot
            Point bottomLeftCorner = new Point(pivotX, pivotY);
            RotatedRect rotatedRect = new RotatedRect(
                    bottomLeftCorner, // Center will be recalculated
                    new Size(rectWidth, rectHeight), // Size of the rectangle
                    angle // Rotation angle in degrees
            );

            // Set the position to align the bottom-left corner
            Point center = new Point(bottomLeftCorner.x + rectWidth / 2.0, bottomLeftCorner.y - rectHeight);
            rotatedRect = new RotatedRect(center, new Size(rectWidth, rectHeight), angle);

            // Draw the rectangle
            drawRotatedRectOnCanvas(rotatedRect, canvas, scaleBmpPxToCanvasPx, rectPaint);

            // Count red pixels in the rotated rectangle
            int redPixelCount = countRedPixelsInRotatedRect(rotatedRect);

            // Update if this rectangle has more red pixels
            if (redPixelCount > maxRedPixelCount) {
                maxRedPixelCount = redPixelCount;
                maxRedPixelRect = rotatedRect;
            }
        }

        // Highlight the rectangle with the most red pixels
        if (maxRedPixelRect != null) {
            rectPaint.setColor(Color.GREEN);
            drawRotatedRectOnCanvas(maxRedPixelRect, canvas, scaleBmpPxToCanvasPx, rectPaint);
        }
    }

    // Function to draw the rotated rectangle on the canvas
    private void drawRotatedRectOnCanvas(RotatedRect rotatedRect, Canvas canvas, float scaleBmpPxToCanvasPx, Paint paint) {
        Point[] vertices = new Point[4];
        rotatedRect.points(vertices); // Get the 4 vertices of the rotated rectangle

        // Draw the lines connecting the vertices
        for (int j = 0; j < 4; j++) {
            float startX = (float) (vertices[j].x * scaleBmpPxToCanvasPx);
            float startY = (float) (vertices[j].y * scaleBmpPxToCanvasPx);
            float endX = (float) (vertices[(j + 1) % 4].x * scaleBmpPxToCanvasPx);
            float endY = (float) (vertices[(j + 1) % 4].y * scaleBmpPxToCanvasPx);
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }

    // Function to count red pixels inside a rotated rectangle
    private int countRedPixelsInRotatedRect(RotatedRect rotatedRect) {
        // Get the bounding box of the rotated rectangle
        Rect boundingRect = rotatedRect.boundingRect();

        // Create a mask for the rotated rectangle
        Mat mask = Mat.zeros(redMask.size(), redMask.type());
        Imgproc.ellipse(mask, rotatedRect, new Scalar(255, 255, 255), -1); // Fill the rotated rectangle in the mask

        // Extract the region of interest (ROI) from the redMask using the bounding box
        Mat roi = new Mat(redMask, boundingRect);

        // Count non-zero pixels (i.e., red pixels) in the mask for the ROI
        return Core.countNonZero(roi);
    }

//    private int countRedPixelsInRect(Rect rect) {
//        int redPixelCount = 0;
//
//        // Iterate over each pixel in the rectangle's area
//        for (int y = rect.y; y < rect.y + rect.height; y++) {
//            for (int x = rect.x; x < rect.x + rect.width; x++) {
//                if (isRedPixel(x, y)) {
//                    redPixelCount++;
//                }
//            }
//        }
//
//        return redPixelCount;
//    }

//    private boolean isRedPixel(int x, int y) {
//        // Check if the pixel is red in the red mask (ensure this matches your red detection logic)
//        if (x >= 0 && x < redMask.cols() && y >= 0 && y < redMask.rows()) {
//            return redMask.get(y, x)[0] > 0;  // Assumes redMask is a single channel binary mask
//        }
//        return false;
//    }

}
