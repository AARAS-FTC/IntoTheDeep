package org.firstinspires.ftc.teamcode.TeleOp;

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

@Config
@TeleOp(group = "drive")
public class BasicWeLoveStemOp extends OpMode {

    private static ElapsedTime timer;
    //Initialise here
    private FtcDashboard dashboard;
    private DeepRobot myRobot;
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
        driverPad = gamepad1;
        operatorPad = gamepad2;
    }

    @Override
    public void loop() {

        myRobot.drive(driverPad.left_stick_y, driverPad.left_stick_x, driverPad.right_stick_y); // TODO some of these might need to be negative

        // DRIVER CONTROLS TODO Change as required
        /**
         * command to open the claw
         */
        if(driverPad.left_bumper){
            myRobot.grabberArm.openClaw();
        }
        /**
         * command to close claw
         */
        if(driverPad.right_bumper){
            myRobot.grabberArm.closeClaw();
        }

        /**
         * commands to slightly adjust linear slides
         */
        if(driverPad.dpad_up){
            myRobot.linearSlides.setPosition(myRobot.linearSlides.getPosition()[0] + 50); // TODO change this variable till it just enough
        }
        if(driverPad.dpad_down){
            myRobot.linearSlides.setPosition(myRobot.linearSlides.getPosition()[0] - 50); // TODO change this variable till it just enough
        }

        /**
         * Commands to slight adjust wrist position
         */
        if(driverPad.dpad_left){
//            myRobot.grabberArm.setWristPosition(myRobot.grabberArm.getWrist() - 0.2); // TODO change this value as needed
        }
        if(driverPad.dpad_right){
//            myRobot.grabberArm.setWristPosition(myRobot.grabberArm.getWrist() + 0.2); // TODO change this value as needed
        }

        /**
         * position commands
         */
        if(driverPad.a){
            myRobot.driveWithSamplePosition();
        }
        if(driverPad.x){
            myRobot.raiseToLowRung();
        }
        if(driverPad.b){
            myRobot.raiseToHighRung();
        }
        if(driverPad.y){
            myRobot.climb();
        }

        // OPERATOR COMMANDS TODO change mapping as needed
        if(operatorPad.a){
            myRobot.collectSamplePosition(); // Use this for placing on the ground too
        }
        if(operatorPad.x){
            myRobot.scoreSampleLowBasket();
        }
        if(operatorPad.b){
            myRobot.scoreSampleHighBasket();
        }
        if(operatorPad.left_bumper){
            myRobot.scoreSampleLowChamber();
        }
        if(operatorPad.right_bumper){
            myRobot.scoreSampleHighChamber();
        }

        telemetry.addData("linear slides position left", myRobot.linearSlides.getPosition()[0]);
        telemetry.addData("linear slides position right", myRobot.linearSlides.getPosition()[1]);

        telemetry.addData("arm position", myRobot.grabberArm.getArm());
        telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay().setStroke("#3F51B5");
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }
}
