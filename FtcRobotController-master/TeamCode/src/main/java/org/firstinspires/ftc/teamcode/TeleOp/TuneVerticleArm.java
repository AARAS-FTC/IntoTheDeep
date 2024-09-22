package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;


//https://www.youtube.com/watch?v=E6H6Nqe6qJo
@Config
public class TuneVerticleArm extends OpMode {
    private PIDController controller;

    public static double p = 0, i = 0, d = 0, f = 0;
    public static int target = 0;

    private final double ticks_in_degree = 3895.9 / 360.0;

    private DcMotorEx arm_motor;

    @Override
    public void init() {

        controller = new PIDController(p, i, d);
        arm_motor = hardwareMap.get(DcMotorEx.class, "arm_motor");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        int armPos =  arm_motor.getCurrentPosition();
        double pid = controller.calculate(armPos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double power = pid + ff;
        arm_motor.setPower(power);

        telemetry.addData("position", armPos);
        telemetry.addData("target", target);
        telemetry.update();
    }
}
