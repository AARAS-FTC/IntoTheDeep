package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.SubSystems.GrabberArm;
import org.firstinspires.ftc.teamcode.SubSystems.GrabberState;
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

	private WebcamName camera;
	private VisionPortal visionPortal;
	private Pose2d beginPose;
	public MecanumDrive drive;


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
	}

	/**
	 * Updates values on the robot such as telemetry
	 */
	public void update(){
		telemetry();
	}

	private void setDrive(){
		frontLeft = hardwareMap.get(DcMotorEx.class, "left_front");
		backLeft = hardwareMap.get(DcMotorEx.class, "left_back");
		frontRight = hardwareMap.get(DcMotorEx.class, "right_front");
		backRight = hardwareMap.get(DcMotorEx.class, "right_back");

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
	 * update Telemetry
	 */
	private void telemetry(){

		telemetry.addData("Status", "Run Time: " + runtime.toString());
		// TODO add telemetry here
		telemetry.update();
	}


}
