import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Actuator Test", group = "Sample")
public class ServoAutonomousTest extends LinearOpMode {

    private Servo myServo;

    @Override
    public void runOpMode() {
        myServo = hardwareMap.get(Servo.class, "servoName");

        waitForStart();

        if (opModeIsActive()) {
            myServo.setPosition(1.0);
            telemetry.addData("Servo Position", myServo.getPosition());
            telemetry.update();
            sleep(1000);

            myServo.setPosition(0.0);
            telemetry.addData("Servo Position", myServo.getPosition());
            telemetry.update();
            sleep(1000);

            myServo.setPosition(0.5);
            telemetry.addData("Servo Position", myServo.getPosition());
            telemetry.update();
            sleep(1000);
        }

    }
}
