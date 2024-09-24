package org.firstinspires.ftc.teamcode.Testing;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DeepRobot;

@Autonomous(name = "Autonomous Vision Test", group = "Linear OpMode")
public class AutonomousVisionTest extends LinearOpMode {
    /**
     * Override this method and place your code here.
     * <p>
     * Please do not catch {@link InterruptedException}s that are thrown in your OpMode
     * unless you are doing it to perform some brief cleanup, in which case you must exit
     * immediately afterward. Once the OpMode has been told to stop, your ability to
     * control hardware will be limited.
     *
     * @throws InterruptedException When the OpMode is stopped while calling a method
     *                              that can throw {@link InterruptedException}
     */

    Double START_TO_SUB_DISTANCE = 10;

    @Override
    public void runOpMode() throws InterruptedException {
        DeepRobot myRobot = new DeepRobot(hardwareMap, telemetry);

        // Build your trajectories here
        Trajectory goToSub = myRobot.drive.trajectoryBuilder(new Pose2d())
                .forward(START_TO_SUB_DISTANCE)
    }
}
