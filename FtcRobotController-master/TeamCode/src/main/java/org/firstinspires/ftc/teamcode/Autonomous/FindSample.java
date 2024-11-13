package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CompVision.FindRedSampleCenterProcessor;
import org.firstinspires.ftc.teamcode.DeepRobot;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "RoadRunner Red Pixel Scan", group = "Autonomous")
public class FindSample extends LinearOpMode {

    // Declare the drive and camera variables
    DeepRobot myRobot;
    MecanumDrive drive;
    private OpenCvCamera camera;
    private FindRedSampleCenterProcessor findRedSampleCenterProcessor;

    @Override
    public void runOpMode() {
        myRobot = new DeepRobot(hardwareMap, telemetry);
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        findRedSampleCenterProcessor = new FindRedSampleCenterProcessor();
        // Initialize Road Runner drive;

        // Initialize camera and processor
        initializeCamera(hardwareMap);

        // Display ready status
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the start of the opmode
        waitForStart();

        if (opModeIsActive()) {
            // Drive forward 20 inches
//            Trajectory forwardTrajectory = drive.actionBuilder();

//            drive.followTrajectory(forwardTrajectory);

            // Process frame and get the red pixel coordinates
            Point redPixelCoordinates = findRedSampleCenterProcessor.getRedPixelCoordinates();

            // Send red pixel coordinates to telemetry
            telemetry.addData("Red Pixel X", redPixelCoordinates.x);
            telemetry.addData("Red Pixel Y", redPixelCoordinates.y);
            telemetry.update();

            // Wait for a few seconds to ensure telemetry is visible
            sleep(3000);
        }
    }

    private void initializeCamera(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createInternalCamera(
                OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        findRedSampleCenterProcessor = new FindRedSampleCenterProcessor();

        // Set processor to camera
        camera.setPipeline(findRedSampleCenterProcessor);

        // Open the camera device and start streaming
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // This method is called when the camera is successfully opened
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                // This method is called if there's an error opening the camera
                telemetry.addData("Camera Error", errorCode);
                telemetry.update();
            }
        });
    }
}
