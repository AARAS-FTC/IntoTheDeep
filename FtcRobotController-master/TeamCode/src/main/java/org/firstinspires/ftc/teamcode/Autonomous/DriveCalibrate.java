package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.DeepRobot;
import org.firstinspires.ftc.teamcode.Autonomous.enums.ParkingMode;

@Autonomous(name = "USED FOR TESTING ONLY", group = "Linear OpMode")
public class DriveCalibrate extends LinearOpMode {
    DeepRobot myRobot;

    Movements myMovements;
    Gamepad driver;
    Gamepad operator;

    @Override
    public void runOpMode() throws InterruptedException {
        myRobot = new DeepRobot(hardwareMap, telemetry);
        myMovements = new Movements(myRobot);

        myRobot.startPosition();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        boolean canRun = true;
        myMovements.park(ParkingMode.REDCLOSE);
        while(opModeIsActive() && canRun){




            canRun = false; // make sure loop doesn't run again
        }
    }
}
