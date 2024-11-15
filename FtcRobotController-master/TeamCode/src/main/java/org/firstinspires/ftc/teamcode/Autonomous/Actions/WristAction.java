package org.firstinspires.ftc.teamcode.Autonomous.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.SubSystems.GrabberArm;
import org.firstinspires.ftc.teamcode.Utils.Constant;

public class WristAction {
    private final double UNCERTAINTY = 0.1; // TODO change if needed
    GrabberArm arm;

    public WristAction(HardwareMap hw){
        arm = new GrabberArm(hw);
    }

    public class WristStart implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double targetPos = Constant.WristStartPosition;

            arm.setWristPosition(targetPos);
            telemetryPacket.put("armPos", "climb position");

            if(arm.getWrist() < targetPos - UNCERTAINTY ||
                arm.getWrist() > targetPos + UNCERTAINTY){
                arm.setWristPosition(targetPos);
                return false;
            }else{
                return true;
            }
        }
    }

    public class WristCollect implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double targetPos = Constant.WristCollectPosition;

            arm.setWristPosition(targetPos);
            telemetryPacket.put("armPos", "climb position");

            if(arm.getWrist() < targetPos - UNCERTAINTY ||
                    arm.getWrist() > targetPos + UNCERTAINTY){
                arm.setWristPosition(targetPos);
                return false;
            }else{
                return true;
            }
        }
    }

    public class WristChamberScore implements Action{


        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double targetPos = Constant.WristChamberScorePosition;

            arm.setWristPosition(targetPos);
            telemetryPacket.put("armPos", "Chamber position");

            if(arm.getWrist() < targetPos - UNCERTAINTY ||
                    arm.getWrist() > targetPos + UNCERTAINTY){
                arm.setWristPosition(targetPos);
                return false;
            }else{
                return true;
            }
        }
    }

    public class WristBasketScore implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double targetPos = Constant.WristBasketScorePosition;

            arm.setWristPosition(targetPos);
            telemetryPacket.put("armPos", "Basket position");

            if(arm.getWrist() < targetPos - UNCERTAINTY ||
                    arm.getWrist() > targetPos + UNCERTAINTY){
                arm.setWristPosition(targetPos);
                return false;
            }else{
                return true;
            }
        }
    }

    public class WristFloorScore implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double targetPos = Constant.WristStartPosition;

            arm.setWristPosition(targetPos);
            telemetryPacket.put("armPos", "Floor position");

            if(arm.getWrist() < targetPos - UNCERTAINTY ||
                    arm.getWrist() > targetPos + UNCERTAINTY){
                arm.setWristPosition(targetPos);
                return false;
            }else{
                return true;
            }
        }
    }
}
