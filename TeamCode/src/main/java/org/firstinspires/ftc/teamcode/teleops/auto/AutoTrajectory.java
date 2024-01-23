package org.firstinspires.ftc.teamcode.teleops.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.VoltageSensor;
import org.firstinspires.ftc.teamcode.subsystems.localization.OdometryLocalizer;

public class AutoTrajectory extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        OdometryLocalizer localizer = new OdometryLocalizer(hardwareMap);
        VoltageSensor voltageSensor = new VoltageSensor(hardwareMap);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-45,-36,0), localizer, voltageSensor, telemetry);

        Action blueRight = (Action) drive.actionBuilder(new Pose2d(-45, -36, 0))
                .setTangent(180)
                .splineTo(new Vector2d(-60, 12), 90)
                .splineTo(new Vector2d(-36, 36), 90)
                .splineToConstantHeading(new Vector2d(-60, 48), 90)
                .splineToConstantHeading(new Vector2d(-60, 60), 90)
                .build();

        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(blueRight);
    }
}
