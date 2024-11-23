package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeepRobot;

@Autonomous(name = "pos1", group = "Linear OpMode")
public class pos1 extends LinearOpMode {

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

        while(opModeIsActive()){
            // drive forward just before sumbersible
            myRobot.driveForward();
            sleep(1000);
            myRobot.driveStop();

            // lift linear slides to high rung
            myRobot.linearSlides.setPosition(1200);

            // Aligns arm with the high rung
            myRobot.grabberArm.setArmPosition(1000);

            // Opens claw to place submersible onto high rung
            myRobot.grabberArm.openClaw();

            // Moves robot back to original position
            myRobot.startPosition();

            // Drive back to start position
            myRobot.driveBack();
            sleep(1000);
            myRobot.driveStop();

            // turn right
            myRobot.turnRight();
            sleep(450);
            myRobot.driveStop();

            // forward to parking
            myRobot.driveForward();
            sleep(1000);
            myRobot.driveStop();
        }
    }
}
