package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.SubSystems.GrabberArm;
import org.firstinspires.ftc.teamcode.SubSystems.LinearSlides;
import org.firstinspires.ftc.teamcode.SubSystems.MechanismState;
import org.firstinspires.ftc.vision.VisionPortal;

public class DeepRobot {
	//contains all the code for interacting with the robots functions
	private ElapsedTime runtime = new ElapsedTime();
	private ElapsedTime mechTimer = new ElapsedTime();
	private DcMotorEx frontLeft = null;
	private DcMotorEx backLeft = null;
	private DcMotorEx frontRight = null;
	private DcMotorEx backRight = null;
	private HardwareMap hardwareMap;
	private Telemetry telemetry;
	private GrabberArm grabberArm;
	private LinearSlides linearSlides;

	private Pose2d beginPose;
	public MecanumDrive drive;
	private MechanismState mechanismState = MechanismState.INIT;
	private final double LOW_SPEED = 0.3;
	private final double MEDIUM_SPEED = 0.6;
	private final double HIGH_SPEED = 1.0;
	private double currentSpeedLimit = HIGH_SPEED;

	/**
	 * Initialises the robot with all its motors
	 *
	 * @param hardwareMap Hardware map to use with Robot Controller
	 * @param telemetry Telemetry Object passed from Op Mode
	 */
	public DeepRobot(HardwareMap hardwareMap, Telemetry telemetry){
		this.hardwareMap = hardwareMap;
		this.telemetry = telemetry;
		this.beginPose = new Pose2d(0, 0, 0);
		this.drive = new MecanumDrive(hardwareMap,  beginPose);
		grabberArm = new GrabberArm(hardwareMap);
		linearSlides = new LinearSlides(hardwareMap);
		setDrive();
	}

	/**
	 * Updates values on the robot such as telemetry
	 */
	public void update(){
		telemetry();
	}

	private void setDrive(){
		frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
		backLeft = hardwareMap.get(DcMotorEx.class, "rearLeft");
		frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
		backRight = hardwareMap.get(DcMotorEx.class, "rearRight");

		frontLeft.setDirection(DcMotor.Direction.REVERSE);
		backLeft.setDirection(DcMotor.Direction.REVERSE);
		frontRight.setDirection(DcMotor.Direction.FORWARD);
		backRight.setDirection(DcMotor.Direction.FORWARD);

		frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		frontLeft.setTargetPositionTolerance(3);
		frontRight.setTargetPositionTolerance(3);
		backRight.setTargetPositionTolerance(3);
		backLeft.setTargetPositionTolerance(3);
	}

	public double[] drive(double axial, double lateral, double yaw){
		double max;

		double leftFrontPower = axial + lateral + yaw;
		double rightFrontPower = axial - lateral - yaw;
		double leftBackPower = axial - lateral + yaw;
		double rightBackPower = axial + lateral - yaw;

		// Normalize the values so no wheel power exceeds 100%
		// This ensures that the robot maintains the desired motion.
		max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
		max = Math.max(max, Math.abs(leftBackPower));
		max = Math.max(max, Math.abs(rightBackPower));

		if (max > 1.0) {
			leftFrontPower /= max;
			rightFrontPower /= max;
			leftBackPower /= max;
			rightBackPower /= max;
		}

		// check state of mechanism and set speed limit
		switch (mechanismState) {
			case PICKUP:
				currentSpeedLimit = LOW_SPEED;
			case TOP_POSITION:
				currentSpeedLimit = LOW_SPEED;
			case DROP_POSITION:
				currentSpeedLimit = LOW_SPEED;
			default:
				currentSpeedLimit = HIGH_SPEED;

		}

		frontLeft.setPower(leftFrontPower * currentSpeedLimit);
		frontRight.setPower(rightFrontPower * currentSpeedLimit);
		backLeft.setPower(leftBackPower * currentSpeedLimit);
		backRight.setPower(rightBackPower * currentSpeedLimit);
        return new double[]{leftFrontPower, rightFrontPower, leftBackPower, rightBackPower};
	}

	/**
	 * Drive forwards for targetSeconds
	 */
	public void driveForward(){
		drive(1, 0 ,0);

	}

	/**
	 * Drive backwards for targetSeconds
	 */
	public void driveBack(){
		drive(-1, 0, 0);
	}

	/**
	 * Rotate left for targetSeconds
	 */
	public void turnRight(){
		drive(0, 0, 1);
	}

	/**
	 * rotate right for targetSeconds
	 */
	public void turnLeft(){
		drive(0, 0, -1);
	}

	/**
	 * Strafe left for targetSeconds
	 */
	public void strafeLeft(){
		drive(0, -0.5, 0.021);
	}

	/**
	 * Strafe right for targetSeconds
	 */
	public void strafeRight(){
		drive(0.13, 0.5, 0);
	}

	/**
	 * Stop drive
	 */
	public void driveStop(){
		drive(0, 0, 0);
	}

	/**
	 * position of robot before game has begun
	 */
	public void startPosition(){
		driveStop();
		grabberArm.closeClaw();
		// TODO Start Position of Robot

	}

	/**
	 * Mechanism position when drive,
	 */
	public void drivePosition(){
		driveStop();
		// TODO Other logic here
	}

	/**
	 * bring actuator in
	 */
	public void retractActuator(){
		grabberArm.extendActuator(0);
	}

	/**
	 * bring actuator in
	 */
	public void extendActuator(){
		grabberArm.extendActuator(1);
	}

	/**
	 * update Telemetry
	 */
	private void telemetry(){

		telemetry.addData("Status", "Run Time: " + runtime.toString());
		// TODO add telemetry here
		telemetry.update();
	}


}
