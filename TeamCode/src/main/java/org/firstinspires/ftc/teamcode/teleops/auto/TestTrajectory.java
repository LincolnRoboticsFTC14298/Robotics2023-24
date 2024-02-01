package org.firstinspires.ftc.teamcode.teleops.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Disabled
public class TestTrajectory extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDriveRR drive = new MecanumDriveRR(hardwareMap, new Pose2d(0, 0, 0));


        Action test = drive.actionBuilder(new Pose2d(0, 0, 0))
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(0, 24), 0)
                .build();


        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(test);
    }
}