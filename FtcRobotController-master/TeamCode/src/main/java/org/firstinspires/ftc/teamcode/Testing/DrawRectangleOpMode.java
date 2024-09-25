package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

import java.util.List;

@Autonomous(name = "Find Sample", group = "Iterative OpMode")
public class DrawRectangleOpMode extends OpMode {
    private DrawRectangleProcessor drawRectangleProcessor;
    private VisionPortal visionPortal;

    @Override
    public void init() {
        drawRectangleProcessor = new DrawRectangleProcessor();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "C:/Test1.png"), drawRectangleProcessor);



    }

    @Override
    public void loop() {

    }

    @Override
    public void start() {
//        visionPortal.stopStreaming();
        // Retrieve and log the red pixel coordinates
//        List<String> redPixels = drawRectangleProcessor.getRedPixelCoordinates();
//        for (String coordinates : redPixels) {
//            telemetry.addData("Red Pixel", coordinates);
//        }
//        telemetry.addData("Red Pixel", redPixels);
        // Update telemetry
        telemetry.update();
    }
}