package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotMode;

public class LinearSlides implements Subsystem{
    private DcMotor leftLinearSlide;
    private DcMotor rightLinearSlide;
    private static final int MAX_POSITION = 1550;
    private static final int MIN_POSITION = 0;

    // PID coefficients
    private double kP = 0.005;
    private double kI = 0.0001;
    private double kD = 0.001;

    private boolean usePID = true; // Toggle for enabling/disabling PID control

    private int targetPosition;
    private double integralSum = 0;
    private double lastError = 0;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runCommand(RobotMode mode) {

    }

    public LinearSlides(HardwareMap hm) {
        leftLinearSlide = hm.get(DcMotor.class, "l_linear_slides");
        rightLinearSlide = hm.get(DcMotor.class, "r_linear_slides");

        leftLinearSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        rightLinearSlide.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reset encoders before setting mode
        leftLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLinearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftLinearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLinearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void applyPIDControl() {
        double error = targetPosition - leftLinearSlide.getCurrentPosition();
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / timer.seconds();

        double power = (kP * error) + (kI * integralSum) + (kD * derivative);

        leftLinearSlide.setPower(power);
        rightLinearSlide.setPower(power);

        lastError = error;
        timer.reset();
    }

    public void changePosition(int pos) {
        int currentPosition = Math.max(getPosition()[0], getPosition()[1]);
        int newPos = currentPosition + pos;

        if (newPos > MAX_POSITION || newPos < MIN_POSITION) return;

        targetPosition = newPos;
        if (usePID) {
            applyPIDControl();
        } else {
            setTargetPositionDirect(targetPosition, 0.6);
        }
    }

    public void setTargetPosition(int position, double power) {
        targetPosition = position;
        if (usePID) {
            // Use PID control to reach the target position
            while (Math.abs(targetPosition - leftLinearSlide.getCurrentPosition()) > 10) { // tolerance of 10 counts
                applyPIDControl();
            }
            // Stop motors once the target position is reached within tolerance
            leftLinearSlide.setPower(0);
            rightLinearSlide.setPower(0);
        } else {
            // Direct power control if PID is disabled
            setTargetPositionDirect(position, power);
        }
    }

    private void setTargetPositionDirect(int position, double power) {
        leftLinearSlide.setTargetPosition(position);
        rightLinearSlide.setTargetPosition(position);

        leftLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftLinearSlide.setPower(power);
        rightLinearSlide.setPower(power);
    }

    public void bottomPosition() {
        setTargetPosition(MIN_POSITION, 0.8);
    }

    public void topPosition() {
        setTargetPosition(1200, 0.8);
    }

    public void climbPosition() {
        setTargetPosition(1240, 0.8);
    }

    public void climb(double power) {
        setTargetPosition(300, power);
    }

    public void reset() {
        setTargetPosition(1400, 0.4);
    }

    public void manualDrive(boolean up) {
        double power = up ? 0.1 : -0.1;
        leftLinearSlide.setPower(power);
        rightLinearSlide.setPower(power);
    }

    public int[] getPosition() {
        return new int[]{leftLinearSlide.getCurrentPosition(), rightLinearSlide.getCurrentPosition()};
    }
}
