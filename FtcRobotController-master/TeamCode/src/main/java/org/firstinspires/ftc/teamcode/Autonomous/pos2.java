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
            myRobot.driveForward();
            sleep(1000);
            myRobot.turnLeft();
            sleep(250);
            myRobot.driveStop();
            myRobot.scoreSampleHighChamber();
            myRobot.collectSamplePosition();
            myRobot.startPosition();
            myRobot.turnLeft();
            sleep(2000);
            myRobot.driveForward();
            sleep(1000);
            myRobot.scoreSampleHighBasket();
            myRobot.startPosition();
            myRobot.turnLeft();
            sleep(1000);
            myRobot.driveForward();
            sleep(1000);
            myRobot.driveStop();
        }
    }
}
