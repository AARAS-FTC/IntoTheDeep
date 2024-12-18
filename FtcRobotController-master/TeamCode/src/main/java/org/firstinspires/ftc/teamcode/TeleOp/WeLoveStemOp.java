package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.LinearSlides;

@Config
@TeleOp(group = "drive")
public class WeLoveStemOp extends OpMode {

    private static ElapsedTime timer;
    //Initialise here
    private FtcDashboard dashboard;
    private DeepRobot myRobot;
    MecanumDrive drive;
    private Gamepad driverPad;
    private Gamepad operatorPad;
    private boolean actuatorStat = false;

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
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        driverPad = gamepad1;
        operatorPad = gamepad2;
    }

    @Override
    public void loop() {
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -driverPad.left_stick_y, //TODO Test to see if these are the wrong ie change the negative sign.
                        -driverPad.left_stick_x
                ),
                -driverPad.right_stick_x
        ));

        // DRIVER CONTROLS TODO Change as required
        /**
         * command to open the claw
         */
//        if(driverPad.left_bumper){
//            myRobot.grabberArm.openClaw();
//        }
        /**
         * command to close claw
         */
//        if(driverPad.right_bumper){
//            myRobot.grabberArm.closeClaw();
//        }

        /**
         * commands to slightly adjust linear slides
         */
//        if(driverPad.dpad_up){
//            myRobot.linearSlides.setPosition(myRobot.linearSlides.getPosition()[0] + 50); // TODO change this variable till it just enough
//        }
//        if(driverPad.dpad_down){
//            myRobot.linearSlides.setPosition(myRobot.linearSlides.getPosition()[0] - 50); // TODO change this variable till it just enough
//        }

        /**
         * Commands to slight adjust wrist position
         */
//        if(driverPad.dpad_left){
////            myRobot.grabberArm.setWristPosition(myRobot.grabberArm.getWrist() - 0.2); // TODO change this value as needed
//        }
//        if(driverPad.dpad_right){
////            myRobot.grabberArm.setWristPosition(myRobot.grabberArm.getWrist() + 0.2); // TODO change this value as needed
//        }

        /**
         * position commands
         */
        // OPERATOR COMMANDS TODO change mapping as needed
        if(operatorPad.a){
            myRobot.grabberArm.forward();
        }
        if(operatorPad.y){
            myRobot.grabberArm.openClaw();
        }
        if(operatorPad.b){
            myRobot.grabberArm.backward();
        }
        if(operatorPad.x){
            myRobot.grabberArm.closeClaw();
        }
        if(operatorPad.dpad_down){
            myRobot.linearSlides.down();
        }
        if(operatorPad.dpad_up){
            myRobot.linearSlides.up();
        }

        drive.updatePoseEstimate();
        telemetry.addData("linear slides position left", myRobot.linearSlides.getPosition()[0]);
        telemetry.addData("linear slides position right", myRobot.linearSlides.getPosition()[1]);

        telemetry.addData("arm position", myRobot.grabberArm.getArm());
        telemetry.addData("x", drive.pose.position.x);
        telemetry.addData("y", drive.pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
        telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }
}
