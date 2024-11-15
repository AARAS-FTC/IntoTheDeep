package org.firstinspires.ftc.teamcode.Autonomous.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Autonomous.enums.LiftPositions;
import org.firstinspires.ftc.teamcode.SubSystems.LinearSlides;
import org.firstinspires.ftc.teamcode.Utils.Constant;

public class LiftActions {
    LinearSlides lift;
    LiftPositions status = LiftPositions.START_POSITION;
    private final int UNCERTAINTY = 20; // TODO change if needed
    public LiftActions(HardwareMap hm){
        lift = new LinearSlides(hm);
    }

    public class LiftStart implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.LiftStartPosition;

            lift.setPosition(targetPos);
            telemetryPacket.put("liftPos", "start position");

            if(!(lift.getPosition()[0] < targetPos - UNCERTAINTY ||
                    lift.getPosition()[0] > targetPos + UNCERTAINTY)) {
                return true;
            }else {
                lift.setPosition(targetPos);
                return false;
            }
        }
    }

    public Action liftStart(){
        return new LiftStart();
    }

    public class LiftLowChamber implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.LiftLowChamberPosition; // todo get position for low rung
            lift.setPosition(targetPos);
            telemetryPacket.put("liftPos", "Low Rung");

            if(!(lift.getPosition()[0] < targetPos - UNCERTAINTY ||
                    lift.getPosition()[0] > targetPos + UNCERTAINTY)) {
                return true;
            }else{
                lift.setPosition(targetPos);
                return false;
            }
        }
    }

    public Action liftLowChamber(){
        return new LiftLowChamber();
    }

    public class LiftHighChamber implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.LiftHighChamberPosition; // todo get position for low rung

            lift.setPosition(targetPos);
            telemetryPacket.put("liftPos", "high Rung");

            if(!(lift.getPosition()[0] < targetPos - UNCERTAINTY ||
                    lift.getPosition()[0] > targetPos + UNCERTAINTY)) {
                return true;
            }else {
                lift.setPosition(targetPos);
                return false;
            }
        }
    }

    public Action liftHighChamber(){
        return new LiftHighChamber();
    }

    public class LiftLowBasket implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.LiftLowBasketPosition; // todo get position for low basket

            lift.setPosition(targetPos);
            telemetryPacket.put("liftPos", "Low Basket");

            if(!(lift.getPosition()[0] < targetPos - UNCERTAINTY ||
                    lift.getPosition()[0] > targetPos + UNCERTAINTY)) {
                return true;
            }else {
                lift.setPosition(targetPos);
                return false;
            }
        }
    }

    public Action liftLowBasket(){
        return new LiftLowBasket();
    }

    public class LiftHighBasket implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.LiftHighBasketPosition; // todo get position for low rung

            lift.setPosition(targetPos); // todo get position for high rung
            telemetryPacket.put("liftPos", "High Basket");

            if(!(lift.getPosition()[0] < targetPos - UNCERTAINTY ||
                    lift.getPosition()[0] > targetPos + UNCERTAINTY)) {
                return true;
            }else {
                lift.setPosition(targetPos);
                return false;
            }
        }
    }

    public Action liftHighBasket(){
        return new LiftHighBasket();
    }

    public class LiftLowRung implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.LiftLowRungPosition; // todo get position for low rung

            lift.setPosition(targetPos); // todo get position for high rung
            telemetryPacket.put("liftPos", "low rung");

            if(!(lift.getPosition()[0] < targetPos - UNCERTAINTY ||
                    lift.getPosition()[0] > targetPos + UNCERTAINTY)) {
                return true;
            }else {
                lift.setPosition(targetPos);
                return false;
            }
        }
    }

    public Action liftLowRung(){
        return new LiftLowRung();
    }

    public class LiftHighRung implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            int targetPos = Constant.LiftHighRungPosition; // todo get position for low rung

            lift.setPosition(targetPos); // todo get position for high rung
            telemetryPacket.put("liftPos", "High rung");

            if(!(lift.getPosition()[0] < targetPos - UNCERTAINTY ||
                    lift.getPosition()[0] > targetPos + UNCERTAINTY)) {
                return true;
            }else {
                lift.setPosition(targetPos);
                return false;
            }
        }
    }

    public Action liftHighRung(){
        return new LiftHighRung();
    }
}
