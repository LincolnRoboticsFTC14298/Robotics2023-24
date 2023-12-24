package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class PassThroughTest extends LinearOpMode
{
    double position;
    double MAX_POS = 0.6;
    double MIN_POS = 0.01;
    Servo leftServo;
    Servo rightServo;
    @Override
    public void runOpMode() throws InterruptedException {
        leftServo = hardwareMap.get(Servo.class, "left");
        rightServo = hardwareMap.get(Servo.class, "right");
        rightServo.setDirection(Servo.Direction.REVERSE);

        waitForStart();
        while(opModeIsActive()){
            position = (((-gamepad1.left_stick_y + 1) * (MAX_POS - MIN_POS)) / 2) + MIN_POS;
            //position = -gamepad1.left_stick_y;
            leftServo.setPosition(position);
            rightServo.setPosition(position);
            telemetry.addData("Position", position);
            telemetry.update();
        }
    }
}