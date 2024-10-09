package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeepRobot;

@Config
@TeleOp(group = "drive")
public class WeLoveStemOp extends OpMode {

    private static ElapsedTime timer;
    //Initialise here
    private FtcDashboard dashboard;
    private DeepRobot myRobot;
    private Gamepad driverPad;
    private Gamepad operatorPad;

    public void telemetryUpdate(){
        //
        telemetry.addData("Timer: ", ".%2f", timer.time());
        telemetry.update();
    }
    @Override
    public void init() {
        timer = new ElapsedTime();

        dashboard = FtcDashboard.getInstance();

//        myRobot.drive.setPoseEstimate(Pose);

        driverPad = gamepad1;
        operatorPad = gamepad2;
    }

    @Override
    public void loop() {
        // TODO Drive code, check the direction on joysticks
//        myRobot.drive.setWeightedDrivePower(
//                new Pose2d(
//                        -driverPad.left_stick_x,
//                        -driverPad.left_stick_y,
//                        -driverPad.right_stick_x
//                )
//        );
//        myRobot.drive.update();
//        telemetryUpdate();
    }
}

