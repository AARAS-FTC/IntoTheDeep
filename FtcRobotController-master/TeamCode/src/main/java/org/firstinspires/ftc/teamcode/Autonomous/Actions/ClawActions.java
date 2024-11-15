package org.firstinspires.ftc.teamcode.Autonomous.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.SubSystems.GrabberArm;
import org.firstinspires.ftc.teamcode.Utils.Constant;

public class ClawActions {
    GrabberArm arm;

    public ClawActions(HardwareMap hw){
        arm = new GrabberArm((hw));
    }

    public class ClawOpen implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double targetPos = Constant.ClawOpen;
            arm.openClaw();
            telemetryPacket.put("clawPos", "Open position");

            if(arm.getClaw() <= Constant.ClawOpen){
                return true;
            }else {
                arm.openClaw();
                return false;
            }
        }
    }

    public Action clawOpen(){
        return new ClawOpen();
    }

    public class ClawClose implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double targetPos = Constant.ClawClose;
            arm.closeClaw();
            telemetryPacket.put("ClawPos", "Close position");


            if(arm.getClaw() <= Constant.ClawClose){
                return true;
            }else {
                arm.closeClaw();
                return false;
            }
        }
    }

    public Action clawClose(){
        return new ClawClose();
    }
}
