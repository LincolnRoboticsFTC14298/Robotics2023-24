package org.firstinspires.ftc.teamcode.teleops.testing;
import static org.opencv.core.Core.findFileOrKeep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ClawTestTeleOp extends LinearOpMode{
    double MAX_POS = 1.0;
    double MIN_POS = 0.30; //0.071
    double position;
    Servo servo;
    @Override
    public void runOpMode() {

        servo = hardwareMap.get(Servo.class, "claw");

        waitForStart();

        while(opModeIsActive()) {
            position = (((-gamepad1.left_stick_y + 1) * (MAX_POS - MIN_POS)) / 2) + MIN_POS;
            servo.setPosition(position);
            telemetry.addData("Position", position);
            telemetry.update();
        }
    }
}