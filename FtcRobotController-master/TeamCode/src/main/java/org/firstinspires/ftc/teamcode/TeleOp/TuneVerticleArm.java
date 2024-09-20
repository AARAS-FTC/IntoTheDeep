package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;


//https://www.youtube.com/watch?v=E6H6Nqe6qJo
@Config
public class TuneVerticleArm extends OpMode {
    private PIDFController controller;

    public static double p = 0, i = 0, d = 0, f = 0;
    public static int target = 0;

    private final double ticks_in_degree = 3895.9 / 360.0;

    private DcMotorEx arm_motor;

    @Override
    public void init() {
        controller = new PIDFController(p, i, d);
    }

    /**
     * User-defined loop method
     * <p>
     * This method will be called repeatedly during the period between when
     * the play button is pressed and when the OpMode is stopped.
     */
    @Override
    public void loop() {

    }
}
