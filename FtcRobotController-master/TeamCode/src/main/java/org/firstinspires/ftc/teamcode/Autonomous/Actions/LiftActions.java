package org.firstinspires.ftc.teamcode.Autonomous.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Autonomous.enums.LiftPositions;
import org.firstinspires.ftc.teamcode.SubSystems.LinearSlides;

public class LiftActions {
    LinearSlides lift;
    LiftPositions status = LiftPositions.START_POSITION;
    public LiftActions(HardwareMap hm){
        lift = new LinearSlides(hm);
    }
    public class LiftLowRungPosition implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            lift.setPosition(500); // todo get position for low rung
            telemetryPacket.put("liftPos", "Low Rung");
            return false;
        }
    }
}
