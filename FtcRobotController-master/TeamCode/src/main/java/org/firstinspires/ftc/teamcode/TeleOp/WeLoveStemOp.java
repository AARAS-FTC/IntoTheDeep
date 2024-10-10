package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeepRobot;
import org.firstinspires.ftc.teamcode.Drawing;

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

        myRobot = new DeepRobot(hardwareMap, telemetry);
        driverPad = gamepad1;
        operatorPad = gamepad2;
    }

    @Override
    public void loop() {
        myRobot.drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x
                ),
                -gamepad1.right_stick_x
        ));

        myRobot.drive.updatePoseEstimate();

        telemetry.addData("x", myRobot.drive.pose.position.x);
        telemetry.addData("y", myRobot.drive.pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(myRobot.drive.pose.heading.toDouble()));
        telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), myRobot.drive.pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }
}

