package org.firstinspires.ftc.teamcode.Autonomous;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeepRobot;

@Autonomous(name = "pos2", group = "Linear OpMode")
public class pos2 extends LinearOpMode{

    DeepRobot myRobot;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        myRobot = new DeepRobot(hardwareMap, telemetry);

        myRobot.startPosition();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            // turn to face sumbersible
            myRobot.turnRight();
            sleep(225);
            myRobot.driveStop();

            // move forward to sumbersible
            myRobot.driveForward();
            sleep(1000);
            myRobot.driveStop();

            // turn to face submersible
            myRobot.turnLeft();
            sleep(225);
            myRobot.driveStop();

            // lift linear slides to high rung
            myRobot.linearSlides.setPosition(1200);

            // Aligns arm with the high rung
            myRobot.grabberArm.setArmPosition(1000);

            // Opens claw to place submersible onto high rung
            myRobot.grabberArm.openClaw();

            // Moves robot back to original position
            myRobot.startPosition();

            // turn to face parking
            myRobot.turnRight();
            sleep(675);
            myRobot.driveStop();

            // move forward to parking
            myRobot.driveForward();
            sleep(1000);
            myRobot.driveStop();
        }
    }
}
