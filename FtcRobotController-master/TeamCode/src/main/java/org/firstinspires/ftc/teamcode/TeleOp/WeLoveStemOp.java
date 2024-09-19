package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.DeepRobot;

public class WeLoveStemOp extends LinearOpMode {

    //Initialise here
    private FtcDashboard dashboard;
    private DeepRobot myRobot;
    private Gamepad driverPad;
    private Gamepad operatorPad;
    @Override
    public void runOpMode() throws InterruptedException {

        dashboard = FtcDashboard.getInstance();

        myRobot = new DeepRobot(hardwareMap, telemetry);
        myRobot.drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        myRobot.drive.setPoseEstimate(Pose);

        driverPad = gamepad1;
        operatorPad = gamepad2;


        waitForStart();
        while(opModeIsActive()){
            //game loop
        }
    }
}
