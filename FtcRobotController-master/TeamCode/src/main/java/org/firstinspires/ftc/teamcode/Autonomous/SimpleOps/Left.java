package org.firstinspires.ftc.teamcode.Autonomous.SimpleOps;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Autonomous.Actions.ActuatorActions;
import org.firstinspires.ftc.teamcode.Autonomous.Actions.ArmActions;
import org.firstinspires.ftc.teamcode.Autonomous.Actions.ClawActions;
import org.firstinspires.ftc.teamcode.Autonomous.Actions.LiftActions;
import org.firstinspires.ftc.teamcode.Autonomous.Actions.WristAction;
import org.firstinspires.ftc.teamcode.DeepRobot;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "LeftSideAuto", group = "RRauto")
public class Left extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(-35, -60, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        ActuatorActions actuator = new ActuatorActions(hardwareMap);
        ArmActions arm = new ArmActions(hardwareMap);
        ClawActions claw = new ClawActions(hardwareMap);
        LiftActions lift = new LiftActions(hardwareMap);
        WristAction wrist = new WristAction(hardwareMap);

        DeepRobot myRobot = new DeepRobot(hardwareMap, telemetry);

        TrajectoryActionBuilder driveToSubmersible =drive.actionBuilder(initialPose)
                .lineToY(-38)
                .turn(Math.toRadians(-90))
                .lineToX(-15)
                .turn(Math.toRadians(90));

        Action slightForward = driveToSubmersible.fresh()
                .lineToY(-33)
                .build();

        Action driveAndPark = slightForward.fresh()
                .lineToY(-60)
                .build();

    }
}
