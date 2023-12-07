package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ButtonLiftTest extends LinearOpMode
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

            motorReft.setPower(1.0 * isGo);
            motorLight.setPower(1.0 * isGo);
        }
    }
}