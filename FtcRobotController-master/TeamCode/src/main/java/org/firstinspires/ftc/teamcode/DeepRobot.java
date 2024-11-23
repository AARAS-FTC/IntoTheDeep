package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.GrabberArm;
import org.firstinspires.ftc.teamcode.SubSystems.LinearSlides;
import org.firstinspires.ftc.teamcode.Utils.Constant;

public class DeepRobot {
	//contains all the code for interacting with the robots functions
	private ElapsedTime runtime = new ElapsedTime();
	private DcMotorEx frontLeft = null;
	private DcMotorEx backLeft = null;
	private DcMotorEx frontRight = null;
	private DcMotorEx backRight = null;
	private HardwareMap hardwareMap;
	private Telemetry telemetry;
	public GrabberArm grabberArm;
	public LinearSlides linearSlides;
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

	/**
	 *
	 * @param axial Forwards and Backwards
	 * @param lateral Left and Right Strafe
	 * @param yaw Left and Right rotation
	 * @return
	 */
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

		// TODO check state of mechanism and set speed limit
		frontLeft.setPower(leftFrontPower * currentSpeedLimit);
		frontRight.setPower(rightFrontPower * currentSpeedLimit);
		backLeft.setPower(leftBackPower * currentSpeedLimit);
		backRight.setPower(rightBackPower * currentSpeedLimit);
        return new double[]{leftFrontPower, rightFrontPower, leftBackPower, rightBackPower};
	}

	// DRIVE COMMANDS TO BE USED BY NON RR AUTONOMOUS
	/**
	 * Drive forwards for targetSeconds
	 */
	public void driveForward(){
		drive(0.6, 0 ,0);

	}

	/**
	 * Drive backwards for targetSeconds
	 */
	public void driveBack(){
		drive(-0.6, 0, 0);
	}

	/**
	 * Rotate left for targetSeconds
	 */
	public void turnRight(){
		drive(0, 0, 0.6);
	}

	/**
	 * rotate right for targetSeconds
	 */
	public void turnLeft(){
		drive(0, 0, -0.6);
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


	// CODE FOR ROBOT MECHANISM CONTROL
	/**
	 * position of robot before game has begun. Ensure that all motors have been rest and put in the
	 * right position
	 * THe arm and linear slides should already be in the right position at this point.
	 */
	public void startPosition(){
		driveStop();
		grabberArm.closeClaw();
		grabberArm.setArmPosition(Constant.ArmStartPosition);
		linearSlides.setPosition(Constant.LiftStartPosition);
//		grabberArm.setWristPosition(Constant.WristStartPosition); // TODO check this is the right position, change if otherwise
	}

	/**
	 * Mechanism position when driving, mainly for autonomous.
	 */
	public void driveWithSamplePosition(){
		startPosition();
	}

	/**
	 * Mechanism position when attempting to collect a scoring element
	 */
	public void collectSamplePosition(){
		driveStop();
//		grabberArm.setArmPosition(Constant.ArmVerticalPosition); // TODO change this so that the arm is completely vertical
		linearSlides.setPosition(Constant.LiftCollectPosition);
		grabberArm.openClaw();
//		grabberArm.setWristPosition(Constant.WristCollectPosition); // TODO change this the right position
		grabberArm.setArmPosition(Constant.ArmCollectPosition); //TODO value for arm to the ground

	}

	/**
	 * Mechanism Position when getting into a position to place sample on the ground.
	 */
	public void placeSampleGround(){
		driveStop();
//		grabberArm.setArmPosition(Constant.ArmVerticalPosition); // TODO change this so that the arm is completely vertical
		linearSlides.setPosition(Constant.LiftGroundScorePosition);
		grabberArm.openClaw();
		grabberArm.setArmPosition(Constant.ArmFloorScorePosition); //TODO value for arm to the ground
//		grabberArm.setWristPosition(Constant.WristFloorScorePosition); // TODO change this the right position
	}

	/**
	 * Mechinsism position when getting into a position to score a sample on the low rung.This
	 * 	 * should be slightly higher than the run. The operator will then drive into position
	 * 	 * and lower the slides and then open the claw
	 */
	public void scoreSampleLowChamber(){
		driveStop();
		linearSlides.setPosition(Constant.LiftLowChamberPosition); //TODO add height for low rung
		grabberArm.setArmPosition(Constant.ArmChamberScorePosition); //TODO desired arm position
//		grabberArm.setWristPosition(Constant.WristChamberScorePosition);
	}

	/**
	 * Mechanism position when getting into a position to score a specimen on the high rung. This
	 * should be slightly higher than the run. The operator will then drive into position
	 * and lower the slides and then open the claw
	 */
	public void scoreSampleHighChamber(){
		driveStop();
		linearSlides.setPosition(Constant.LiftHighChamberPosition); //TODO add height for low rung
		grabberArm.setArmPosition(Constant.ArmChamberScorePosition); //TODO desired arm position
//		grabberArm.setWristPosition(Constant.WristChamberScorePosition);
	}

	/**
	 * Mech position when scoring in the low basket. See above
	 */
	public void scoreSampleLowBasket(){
		driveStop();
		linearSlides.setPosition(Constant.LiftLowBasketPosition);
		grabberArm.setArmPosition(Constant.ArmBasketScorePosition);
//		grabberArm.setWristPosition(Constant.WristBasketScorePosition);
	}

	/**
	 * Mech position when scoring in the high basket. See above for extra details.
	 */
	public void scoreSampleHighBasket(){
		driveStop();
		linearSlides.setPosition(Constant.LiftHighBasketPosition);
		grabberArm.setArmPosition(Constant.ArmBasketScorePosition);
//		grabberArm.setWristPosition(Constant.WristBasketScorePosition);
	}

	/**
	 * code for climb
	 */
	public void raiseToLowRung(){
		driveStop();
		grabberArm.setArmPosition(Constant.ArmClimbPosition);
		linearSlides.setPosition(Constant.LiftLowRungPosition);

	}

	public void raiseToHighRung(){
		driveStop();
		grabberArm.setArmPosition(Constant.ArmClimbPosition);
		linearSlides.setPosition(Constant.LiftHighRungPosition);
	}

	public void climb(){
		linearSlides.climb(0.6);
	}

	// INTAKE MECHANISM CODE. INCLUDES THE ARM MOTOR, LINEAR ACTUATOR, AND CLAW SERVOS.

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
