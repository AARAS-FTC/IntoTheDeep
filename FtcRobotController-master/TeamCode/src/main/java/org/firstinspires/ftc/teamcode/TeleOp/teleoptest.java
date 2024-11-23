package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DeepRobot;
import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(group = "drive")
public class teleoptest extends OpMode {
    private static ElapsedTime timer;
    //Initialise here
    private FtcDashboard dashboard;
    private DeepRobot myRobot;
    private Gamepad driverPad;
    private Gamepad operatorPad;
    private boolean actuatorStat = false;

    public void telemetryUpdate() {
        //
        telemetry.addData("Timer: ", ".%2f", timer.time());
        telemetry.update();
    }

    @Override
    public void init() {
        timer = new ElapsedTime();

        dashboard = FtcDashboard.getInstance();

        myRobot = new DeepRobot(hardwareMap, telemetry);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        driverPad = gamepad1;
        operatorPad = gamepad2;
    }

    @Override
    public void loop() {

        myRobot.drive(driverPad.left_stick_y, driverPad.left_stick_x, driverPad.right_stick_y);

        if (driverPad.a) {
            myRobot.linearSlides.setPosition(0);
        }
    }
}