package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotMode;
import org.firstinspires.ftc.teamcode.Utils.Constant;
import org.firstinspires.ftc.teamcode.Utils.PIDFController;

public class GrabberArm implements Subsystem{

	private final double TICKS_PER_DEGREE = 3895.9 / 360.0;
	private final double ARM_MAX = 1400; // Todo change this to the actual arm max
	private final double WRIST_MAX = 5;
	private final double WRIST_MIN = 0;

	private Servo claw = null;
//	private Servo wrist = null;
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
		actuator = hm.get(Servo.class, "actuator");
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

//	public void openClaw(){
//		claw.setPosition(Constant.ClawOpen);
//	} // TODO check if these are the right values

	public void closeClaw(){
		claw.setPosition(Constant.ClawClose);
	}

	public void setarmposition(int pos){
		arm.setPower(0.7);
		arm.setTargetPosition(pos);
		arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

//	public void setWristPosition(double pos){
//		if(pos >= WRIST_MIN && pos <= WRIST_MAX) {
//			wrist.setPosition(pos);
//		}
//	}

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
		if(pos < ARM_MAX) {
			double power = 0.3;
			arm.setTargetPosition(pos);

			arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			arm.setPower(power);
		}
	}

	public void openClaw(){
		if(claw.getPosition() >= 0){
			claw.setPosition(Constant.ClawOpen);
		}
	}

	public void forward(){
		arm.setPower(0.7);
		arm.setTargetPosition(arm.getCurrentPosition() + 50);
		arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

	public void backward(){
		arm.setPower(0.7);
		arm.setTargetPosition(arm.getCurrentPosition() - 50);
		arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

	public void extend(){
		actuator.setPosition(500);
	}

	public void contract(){
		actuator.setPosition(0);
	}

//	public void extendActuator(double pos){
//		if(pos < 0.17){
//			actuator.setPosition(0.17);
//		} else actuator.setPosition(Math.min(pos, 0.82));
//	}

	public int getArm(){
		return arm.getCurrentPosition();
	}

//	public double getWrist(){
//		return wrist.getPosition();
//	}

	public double getClaw(){
		return claw.getPosition();
	}


}
