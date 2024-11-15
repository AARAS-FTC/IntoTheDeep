package org.firstinspires.ftc.teamcode.Autonomous.SimpleOps;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DeepRobot;
import org.firstinspires.ftc.teamcode.Utils.Constant;

@Autonomous(name = "Simple Blue Auto Position 1", group = "robot")
public class ExampleAuto extends LinearOpMode {
    DeepRobot myRobot;

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        myRobot = new DeepRobot(hardwareMap, telemetry);
        myRobot.startPosition();

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        // Step 1: Drive forwards for 3 second
        myRobot.driveForward(); // drive straight forward, you may need to change lateral/ yaw if the robot isnt going straight
        timer.reset();
        while(opModeIsActive() && (timer.seconds() < Constant.timeToSubFromStart)){
            telemetry.addData("Current step", "driving forwards for %4.1f S", timer.seconds());
            telemetry.update();

        }

        //Step2: stop
        myRobot.driveStop();

        // Step 3: raise to score low rung position
        myRobot.scoreSampleLowChamber();

        //Step 4 : Drive forwards a bit
        myRobot.driveForward();
        timer.reset();
        while(opModeIsActive() && (timer.seconds() < Constant.timeToAlignWithSub)){
            telemetry.addData("Current step", "driving forwards to align to rung for %4.1f S", timer.seconds());
            telemetry.update();
        }

        // step 5 : stop
        myRobot.driveStop();

        // step 6 : drop specimen // Todo you will probably need to align better here byt you get the point.
        myRobot.grabberArm.openClaw();

        //Step 7 raise slides
        myRobot.scoreSampleLowChamber();

        //drive back a bit
        myRobot.driveBack();
        timer.reset();
        while(opModeIsActive() && (timer.seconds() < Constant.timeToAlignWithSub)){
            telemetry.addData("Current step", "driving backwards for %4.1f S", timer.seconds());
            telemetry.update();
        }

        // step 8 : stop
        myRobot.driveStop();

        // step 9 : back to start
        myRobot.driveBack(); // drive straight forward, you may need to change lateral/ yaw if the robot isnt going straight
        timer.reset();
        while(opModeIsActive() && (timer.seconds() < Constant.timeToSubFromStart)){
            telemetry.addData("Current step", "driving backwards for %4.1f S", timer.seconds());
            telemetry.update();
        }

        //Step10: stop
        myRobot.driveStop();

    }
}
