package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.DeepRobot;

public class WeLoveStemOp extends LinearOpMode {

    //Initialise here
    private FtcDashboard dashboard;
    private DeepRobot myRobot;
    private Gamepad driverPad;
    private Gamepad operatorPad;
    @Override
    public void runOpMode() throws InterruptedException {

        dashboard = FtcDashboard.getInstance();

        myRobot = new DeepRobot(hardwareMap, telemetry);
        myRobot.drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        myRobot.drive.setPoseEstimate(Pose);

        driverPad = gamepad1;
        operatorPad = gamepad2;


        waitForStart();

        if(isStopRequested()) return;

        while(opModeIsActive()){
            // Drive code, check the direction on these
            myRobot.drive.setWeightedDrivePower(
                    new Pose2d(
                            -driverPad.left_stick_x,
                            -driverPad.left_stick_x,
                            -driverPad.right_stick_x
                    )
            );
            myRobot.drive.update();

        }
    }
}
