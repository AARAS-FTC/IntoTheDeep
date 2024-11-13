package org.firstinspires.ftc.teamcode.CompVision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name = "Find Sample", group = "Iterative OpMode")
public class FindRedSampleCenterOpMode extends OpMode {

    private FindRedSampleCenterProcessor findRedSampleCenterProcessor;
    private VisionPortal visionPortal;

    @Override
    public void init() {
        // Initialize the vision processor and portal
        findRedSampleCenterProcessor = new FindRedSampleCenterProcessor();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class,  "C:/Test1.png"), findRedSampleCenterProcessor);
    }

    @Override
    public void start() {
        // You can add any one-time processing you need here

        telemetry.addData("Pixel Location", findRedSampleCenterProcessor.getRedPixelCoordinates());
        telemetry.update();
    }

    @Override
    public void loop() {
        // No looping actions required in this linear flow
        double d = Math.sqrt(
                Math.pow(findRedSampleCenterProcessor.getPixelCenter().x - findRedSampleCenterProcessor.intakeX, 2)
        + Math.pow(findRedSampleCenterProcessor.getPixelCenter().y - findRedSampleCenterProcessor.intakeY, 2));

        telemetry.addData("Pixel Center", findRedSampleCenterProcessor.getPixelCenter());
        telemetry.addData("Directions (N NE E SE S SW W NW)", findRedSampleCenterProcessor.getDirectionArray());
        telemetry.addData("distance from Intake to Sample in Pixels", d);
        telemetry.update();

    }

    @Override
    public void stop() {
        // Clean up resources when stopping (if needed)
//        if (visionPortal != null) {
//            visionPortal.stopStreaming();
//        }
    }
}
