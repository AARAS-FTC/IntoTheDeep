package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotMode;

public class GrabberArm implements Subsystem{
	private Servo claw = null;
	private Servo wrist = null;
	private DcMotorEx arm = null;
	private Servo actuator = null;


	public static final double NEW_P = 2.5;
	public static final double NEW_I = 0.1;
	public static final double NEW_D = 0.2;


	public GrabberArm(HardwareMap hm){
		claw = hm.get(Servo.class, "claw");
		wrist = hm.get(Servo.class, "wrist");
		actuator = hm.get(Servo.class, "arm_extender");
		arm = hm.get(DcMotorEx.class, "arm");

		arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		arm.setDirection(DcMotorSimple.Direction.REVERSE);
		arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}

	@Override
	public void runCommand(RobotMode mode) {

	}

	public void openClaw(){
		claw.setPosition(1);
	}

	public void closeClaw(){
		claw.setPosition(0);
	}


	public void setWristPosition(double pos){
		wrist.setPosition(pos + 0.36);
	}

	public void changeArmPosition(int pos){
		//if arm is at bottom don't move
		if(pos < 0 && arm.getCurrentPosition() < 0) return;

		double power = 0.3;
		arm.setTargetPosition(arm.getCurrentPosition() + pos);

		arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		arm.setPower(power);

		// Stop the motors after reaching the position
		arm.setPower(0.1);

		// Switch back to RUN_USING_ENCODER mode
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}



	public void setArmPosition(int pos){
		double power = 0.3;
		arm.setTargetPosition(pos);

		arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		arm.setPower(power);
	}

	public void extendActuator(double pos){
		if(pos < 0.17){
			actuator.setPosition(0.17);
		} else if (pos > 0.82) {
			actuator.setPosition(0.82);
		}else {
			actuator.setPosition(pos);
		}
	}
	
	public void manualPower(){
		arm.setPower(0.05);
	}

	public int getArm(){
		return arm.getCurrentPosition();
	}

	public double getWrist(){
		return wrist.getPosition();
	}

	public double getClaw(){
		return claw.getPosition();
	}


}
