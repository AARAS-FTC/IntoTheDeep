package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LinearSlides {
    //Motors
    private DcMotor leftLinearSlide;
    private DcMotor rightLinearSlide;

    private final int MIN_POSITION = 0;
    private final int MAX_POSITION = 1500;

    public LinearSlides(HardwareMap hm){
        leftLinearSlide = hm.get(DcMotor.class, "slideLeft");
        rightLinearSlide = hm.get(DcMotor.class, "slideRight");

        // Set the motors to run with encoders.
        leftLinearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Reset encoders
        leftLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLinearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //direction of motors
        leftLinearSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLinearSlide.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /**
     * Change Position
     * INPUT: pos - change we want from current position
     * Move the linear slide up or down from current position
     */
    public void setPosition(int pos){
        if(pos >= MIN_POSITION && pos <= MAX_POSITION) {
            leftLinearSlide.setPower(0.6);
            rightLinearSlide.setPower(0.6);

            leftLinearSlide.setTargetPosition(pos);
            rightLinearSlide.setTargetPosition(pos);

            leftLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void up(){
        leftLinearSlide.setPower(0.6);
        rightLinearSlide.setPower(0.6);

        if(leftLinearSlide.getCurrentPosition() < MAX_POSITION) {
            leftLinearSlide.setTargetPosition(leftLinearSlide.getCurrentPosition() + 30);
            rightLinearSlide.setTargetPosition(rightLinearSlide.getCurrentPosition() + 30);

            leftLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void down(){
        leftLinearSlide.setPower(0.6);
        rightLinearSlide.setPower(0.6);

        if(leftLinearSlide.getCurrentPosition() > MIN_POSITION){
            leftLinearSlide.setTargetPosition(leftLinearSlide.getCurrentPosition() - 30);
            rightLinearSlide.setTargetPosition(rightLinearSlide.getCurrentPosition() - 30);

            leftLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightLinearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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