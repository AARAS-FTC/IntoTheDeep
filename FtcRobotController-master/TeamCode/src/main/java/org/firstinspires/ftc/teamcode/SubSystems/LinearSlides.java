package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LinearSlides {
    //Motors
    private DcMotor leftLinearSlide;
    private DcMotor rightLinearSlide;

    private final int MIN_POSITION = 0;
    private final int MAX_POSITION = 1400; // TODO find the actual max

    public LinearSlides(HardwareMap hm){
        leftLinearSlide = hm.get(DcMotor.class, "slideLeft");
        rightLinearSlide = hm.get(DcMotor.class, "slideRight");

        // Reset encoders
        leftLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //direction of motors
        leftLinearSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        rightLinearSlide.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set the motors to run with encoders.
        leftLinearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Hold position
        leftLinearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLinearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Change Position
     * INPUT: pos - change we want from current position
     * Move the linear slide up or down from current position
     */
    public void setPosition(int pos){
        if(pos >= MIN_POSITION && pos <= MAX_POSITION) {
            leftLinearSlide.setTargetPosition(pos);
            rightLinearSlide.setTargetPosition(pos);

            leftLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftLinearSlide.setPower(0.8);
            rightLinearSlide.setPower(0.8);

            // Stop the motors after reaching the position
            leftLinearSlide.setPower(0.1);
            rightLinearSlide.setPower(0.1);
        }
    }

    public void climb(double power){
        leftLinearSlide.setPower(power);
        rightLinearSlide.setPower(power);
        leftLinearSlide.setTargetPosition(300);
        rightLinearSlide.setTargetPosition(300);

        leftLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftLinearSlide.setPower(power);
        rightLinearSlide.setPower(power);
    }

    public int[] getPosition(){
        int[] pos = {leftLinearSlide.getCurrentPosition(), rightLinearSlide.getCurrentPosition()};
        return pos;
    }
}