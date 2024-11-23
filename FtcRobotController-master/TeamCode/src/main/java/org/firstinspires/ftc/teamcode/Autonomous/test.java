package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DeepRobot;
import org.firstinspires.ftc.teamcode.SubSystems.LinearSlides;
import org.firstinspires.ftc.teamcode.Utils.Constant;


@Autonomous(name = "linearslidespos", group = "Linear OpMode")
public class test extends LinearOpMode {
    DeepRobot myRobot;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTelemetry = dashboard.getTelemetry();
    ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        myRobot = new DeepRobot(hardwareMap, telemetry);

        myRobot.startPosition();

        telemetry.addData("Status","Initialised");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("linearslidespos", myRobot.linearSlides.getPosition());
            telemetry.update();
        }
    }
}
