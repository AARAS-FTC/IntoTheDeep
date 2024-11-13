package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Utils.PIDFController;


//https://www.youtube.com/watch?v=E6H6Nqe6qJo
@Config
@TeleOp
public class TuneVerticleArm extends OpMode {
    public static double kP = 0.0009;
    public static double kI = 0.0;
    public static double kD = 0.00001;
    public static double kV = 600;
    public static double kA = 0.005;
    public static double kStatic = 0.005;
    public static int targetPosition = 0;

    private PIDFController pidfController;
    private DcMotorEx armMotor;
    private ElapsedTime timer;

    // Conversion factor from encoder ticks to degrees
    private final double TICKS_PER_DEGREE = 3895.9 / 360.0;

    @Override
    public void init() {
        // Initialize PIDFController with the PID coefficients and feedforward terms
        PIDFController.PIDCoefficients pidCoeffs = new PIDFController.PIDCoefficients();
        pidCoeffs.kP = kP;
        pidCoeffs.kI = kI;
        pidCoeffs.kD = kD;
        pidfController = new PIDFController(pidCoeffs, kV, kA, kStatic,
                (position, velocity) -> Math.cos(Math.toRadians(position / TICKS_PER_DEGREE)) * kStatic);

        // Hardware initialization
        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Reset integral sum and timer
        pidfController.reset();
        timer = new ElapsedTime();
    }

    @Override
    public void loop() {
        // Update the PID coefficients in real-time based on dashboard values
        pidfController.pid.kP = kP;
        pidfController.pid.kI = kI;
        pidfController.pid.kD = kD;
        pidfController.targetPosition = targetPosition;

        int armPosition = armMotor.getCurrentPosition();
        double powerOutput = pidfController.update(armPosition);

        // Set motor power
        armMotor.setPower(powerOutput);

        // Telemetry to monitor and tune PID parameters
        telemetry.addData("Position", armPosition);
        telemetry.addData("Target Position", targetPosition);
        telemetry.addData("Power Output", powerOutput);
        telemetry.update();
    }
}
