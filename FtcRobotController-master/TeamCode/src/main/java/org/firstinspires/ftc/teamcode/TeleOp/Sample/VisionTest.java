package org.firstinspires.ftc.teamcode.TeleOp.Sample;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.DeepRobot;
import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@TeleOp(name = "VisionTest", group = "Linear OpMode")
public class VisionTest extends LinearOpMode {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    DeepRobot myRobot;
    Gamepad driver;
    Gamepad operator;

    WebcamName camera;
    VisionPortal visionPortal;
    VisionPortal.Builder vb;
    @Override
    public void runOpMode() throws InterruptedException {
        myRobot = new DeepRobot(hardwareMap, telemetry);

        camera = hardwareMap.get(WebcamName.class, "Webcam");
        vb = new VisionPortal.Builder();
        vb.setCamera(camera);
//        vb.setCameraResolution(new Size(640, 480));
        visionPortal = vb.build();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

        }
//        myRobot.reset();
    }
}
