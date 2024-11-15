package org.firstinspires.ftc.teamcode.Autonomous.Actions;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import org.firstinspires.ftc.teamcode.SubSystems.GrabberArm;
import org.firstinspires.ftc.teamcode.Utils.Constant;

public class ArmActions {
    private final int UNCERTAINTY = 20; // TODO change if needed
    GrabberArm arm;

    public ArmActions(HardwareMap hw){
        arm = new GrabberArm(hw);
    }

    public class ArmStart implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.ArmStartPosition;
            arm.setArmPosition(targetPos);
            telemetryPacket.put("armPos", "start position");

            if(!(arm.getArm() < targetPos - UNCERTAINTY || arm.getArm() > targetPos + UNCERTAINTY)) {
                return true;
            } else {
                arm.setArmPosition(targetPos);
                return false;
            }
        }
    }

    public Action armStart(){
        return new ArmStart();
    }

    public class ArmCollect implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.ArmCollectPosition;
            arm.setArmPosition(targetPos);
            telemetryPacket.put("armPos", "collect position");

            if(!(arm.getArm() < targetPos - UNCERTAINTY || arm.getArm() > targetPos + UNCERTAINTY)) {
                return true;
            } else {
                arm.setArmPosition(targetPos);
                return false;
            }
        }
    }

    public Action armCollect(){
        return new ArmCollect();
    }

    public class ArmChamberScore implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.ArmChamberScorePosition;
            arm.setArmPosition(targetPos);
            telemetryPacket.put("armPos", "chamber score position");

            if(!(arm.getArm() < targetPos - UNCERTAINTY || arm.getArm() > targetPos + UNCERTAINTY)) {
                return true;
            } else {
                arm.setArmPosition(targetPos);
                return false;
            }
        }
    }

    public Action armChamberScore(){
        return new ArmChamberScore();
    }

    public class ArmVertical implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.ArmVerticalPosition;
            arm.setArmPosition(targetPos);
            telemetryPacket.put("armPos", "vertical position");

            if(!(arm.getArm() < targetPos - UNCERTAINTY || arm.getArm() > targetPos + UNCERTAINTY)) {
                return true;
            } else {
                arm.setArmPosition(targetPos);
                return false;
            }
        }
    }

    public Action armVerticle(){
        return new ArmVertical();
    }

    public class ArmFloorScore implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.ArmFloorScorePosition;
            arm.setArmPosition(targetPos);
            telemetryPacket.put("armPos", "floor score position");

            if(!(arm.getArm() < targetPos - UNCERTAINTY || arm.getArm() > targetPos + UNCERTAINTY)) {
                return true;
            } else {
                arm.setArmPosition(targetPos);
                return false;
            }
        }
    }

    public Action armFloorScore(){
        return new ArmFloorScore();
    }

    public class ArmBasketScore implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.ArmBasketScorePosition;
            arm.setArmPosition(targetPos);
            telemetryPacket.put("armPos", "basket score position");

            if(!(arm.getArm() < targetPos - UNCERTAINTY || arm.getArm() > targetPos + UNCERTAINTY)) {
                return true;
            } else {
                arm.setArmPosition(targetPos);
                return false;
            }
        }
    }

    public Action armBasketScore(){
        return new ArmBasketScore();
    }

    public class ArmClimb implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.ArmClimbPosition;
            arm.setArmPosition(targetPos);
            telemetryPacket.put("armPos", "climb position");

            if(!(arm.getArm() < targetPos - UNCERTAINTY || arm.getArm() > targetPos + UNCERTAINTY)) {
                return true;
            } else {
                arm.setArmPosition(targetPos);
                return false;
            }
        }
    }

    public Action armClimb(){
        return new ArmClimb();
    }
}