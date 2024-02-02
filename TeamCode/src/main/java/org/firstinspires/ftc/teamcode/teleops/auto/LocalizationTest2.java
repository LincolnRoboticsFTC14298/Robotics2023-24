package org.firstinspires.ftc.teamcode.teleops.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.VoltageSensor;
import org.firstinspires.ftc.teamcode.subsystems.localization.OdometryLocalizer;

@TeleOp
public class LocalizationTest2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        OdometryLocalizer localizer = new OdometryLocalizer(hardwareMap);
        VoltageSensor voltageSensor = new VoltageSensor(hardwareMap);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0), localizer, voltageSensor, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x
            ));

            drive.periodic();

            telemetry.addData("x", drive.getPose().position.x);
            telemetry.addData("y", drive.getPose().position.y);
            telemetry.addData("heading", drive.getPose().heading);
            telemetry.update();
        }

    }
}
