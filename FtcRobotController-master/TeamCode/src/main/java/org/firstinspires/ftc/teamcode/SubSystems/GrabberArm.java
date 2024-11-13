package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotMode;
import org.firstinspires.ftc.teamcode.Utils.PIDFController;

public class GrabberArm implements Subsystem{

	private final double TICKS_PER_DEGREE = 3895.9 / 360.0;

	private Servo claw = null;
	private Servo wrist = null;
	private DcMotorEx arm = null;
	private Servo actuator = null;

	public static double kP = 0.0009;
	public static double kI = 0.0;
	public static double kD = 0.00001;
	public static double kV = 600;
	public static double kA = 0.005;
	public static double kStatic = 0.005;
	public int targetPosition = 0;

	private PIDFController pidfController;

	public GrabberArm(HardwareMap hm){
		claw = hm.get(Servo.class, "claw");
		wrist = hm.get(Servo.class, "wrist");
		actuator = hm.get(Servo.class, "arm_extender");
		arm = hm.get(DcMotorEx.class, "arm");

		arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		arm.setDirection(DcMotorSimple.Direction.REVERSE);
		arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		PIDFController.PIDCoefficients pidCoeffs = new PIDFController.PIDCoefficients();

		pidCoeffs.kP = kP;
		pidCoeffs.kI = kI;
		pidCoeffs.kD = kD;
		pidfController = new PIDFController(pidCoeffs, kV, kA, kStatic,
				(position, velocity) -> Math.cos(Math.toRadians(position / TICKS_PER_DEGREE)) * kStatic);
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

	public void setArmTarget(int pos){
		targetPosition = pos;
	}

	public void armLoop(){
		pidfController.pid.kP = kP;
		pidfController.pid.kI = kI;
		pidfController.pid.kD = kD;
		pidfController.targetPosition = targetPosition;

		int armPosition = arm.getCurrentPosition();
		double powerOutput = pidfController.update(armPosition);

		// Set motor power
		arm.setPower(powerOutput);
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
		} else actuator.setPosition(Math.min(pos, 0.82));
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
