package org.firstinspires.ftc.teamcode.teleops.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class LauncherTestTeleOp extends LinearOpMode{
    double position;
    Servo servo;
    @Override
    public void runOpMode() {

        servo = hardwareMap.get(Servo.class, "launcher");
        position = 0.9;

        waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.a) {
                position = 0.4;
            }
            if (gamepad1.b) {
                position = 0.9;
            }
            servo.setPosition(position);
            telemetry.addData("Position", position);
            telemetry.update();
        }
    }
}