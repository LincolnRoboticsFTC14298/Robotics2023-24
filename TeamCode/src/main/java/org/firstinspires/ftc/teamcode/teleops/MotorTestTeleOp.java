package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp
public class MotorTestTeleOp extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        //motors
        DcMotor motorReft = hardwareMap.dcMotor.get("liftLeft");
        DcMotor motorLight = hardwareMap.dcMotor.get("liftRigt");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive())
        {
            int isGo = (gamepad1.a?1:0) - (gamepad1.b?1:0);

            if (isGo != 0)
            {
                motorReft.setPower(0.5 * isGo);
                motorLight.setPower(0.5 * isGo);
            }
        }
    }
}