package org.firstinspires.ftc.teamcode.Testing;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TestVisionProcessor implements VisionProcessor {
    public Rect rectLeft = new Rect(110, 42, 40, 40);
    public Rect rectMiddle = new Rect(160, 42, 40, 40);
    public Rect rectRight = new Rect(210, 42, 40, 40);

    Mat submat = new Mat();
    Mat hsvmat = new Mat();

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, hsvmat, Imgproc.COLOR_RGB2HSV);

        double satRectLeft = getAvgSaturation(hsvmat, rectLeft);

        return null;
    }

    protected double getAvgSaturation(Mat input, Rect rect){
        submat = input.submat(rect);
        Scalar color = Core.mean(submat);
        return color.val[1];
    }
    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
