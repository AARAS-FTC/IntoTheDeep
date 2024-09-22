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
    private Rect[] gridRects = new Rect[9];
    private List<String> redPixelCoordinates = new ArrayList<>();

    // Variables for the first red pixel found
    private int firstRedPixelX = -1;
    private int firstRedPixelY = -1;

    private Mat filteredFrame = new Mat();  // Mat for holding the filtered frame
    private int IntakeLocX = 320; // Example value for center
    private int IntakeLocY = 400; // Example value for center

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        // Width and height of the frame
        int frameWidth = 640;
        int frameHeight = 480;

        // Calculate the width and height of each grid cell
        int cellWidth = frameWidth / 3;
        int cellHeight = frameHeight / 3;

        // Create the 9 rectangles for the 3x3 grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = col * cellWidth;
                int y = row * cellHeight;
                gridRects[row * 3 + col] = new Rect(x, y, cellWidth, cellHeight);
            }
        }
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

        // Combine the red masks
        Mat redMask = new Mat();
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

        // Draw each of the 9 rectangles on the canvas
        for (Rect rect : gridRects) {
            canvas.drawRect(makeGraphicsRect(rect, scaleBmpPxToCanvasPx), rectPaint);
        }

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
            drawIntercardinalRectangles(canvas, firstRedPixelX, firstRedPixelY, scaleBmpPxToCanvasPx);
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

    private void drawIntercardinalRectangles(Canvas canvas, int pivotX, int pivotY, float scaleBmpPxToCanvasPx) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.YELLOW); // Set the color for the outlines
        rectPaint.setStyle(Paint.Style.STROKE); // Set to STROKE for outlines
        rectPaint.setStrokeWidth(4); // Adjust the stroke width as needed

        // Define the size of the rectangles
        int rectWidth = 64;
        int rectHeight = 20;

        // Calculate the positions of the rectangles in intercardinal directions
        int[][] directions = {
                {0, -rectHeight},    // North
                {rectWidth / 2, -rectHeight / 2}, // Northeast
                {rectWidth, 0},      // East
                {rectWidth / 2, rectHeight / 2},   // Southeast
                {0, rectHeight},     // South
                {-rectWidth / 2, rectHeight / 2},  // Southwest
                {-rectWidth, 0},     // West
                {-rectWidth / 2, -rectHeight / 2}  // Northwest
        };

        // Draw each rectangle
        for (int[] direction : directions) {
            int left = pivotX + direction[0] - rectWidth / 2;
            int top = pivotY + direction[1] - rectHeight / 2;
            int right = left + rectWidth;
            int bottom = top + rectHeight;
            canvas.drawRect(left * scaleBmpPxToCanvasPx, top * scaleBmpPxToCanvasPx, right * scaleBmpPxToCanvasPx, bottom * scaleBmpPxToCanvasPx, rectPaint);
        }
    }

}
