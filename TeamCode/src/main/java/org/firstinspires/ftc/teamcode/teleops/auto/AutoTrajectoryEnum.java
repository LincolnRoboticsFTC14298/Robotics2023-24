package org.firstinspires.ftc.teamcode.teleops.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


/**
 * Reference code for enum use. Change into Kotlin!
 */

@Autonomous
public class AutoTrajectoryEnum extends LinearOpMode {

    public enum StartingPose {
        BLUE_RIGHT(new Pose2d(-45, -36, 0)),
        BLUE_LEFT(new Pose2d(-45, 12, 0)),
        RED_LEFT(new Pose2d(45, -36, 180)),
        RED_RIGHT(new Pose2d(45, 12, 180));

        private final Pose2d pose;

        StartingPose(Pose2d pose) {
            this.pose = pose;
        }

        public Pose2d getPose() {
            return pose;
        }
    }

    MecanumDrive drive = new MecanumDrive(hardwareMap, StartingPose.BLUE_RIGHT.getPose());

    @Override
    public void runOpMode() throws InterruptedException {
        Action blueRight = drive.actionBuilder(drive.pose) //TODO maybe change back to the hardcoded starting pose if drive.pose causes issues
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-60, 12, -90), 90)
                .splineToLinearHeading(new Pose2d(-36, 36, -90), 90)
                .splineToConstantHeading(new Vector2d(-60, 48), 90)
                .splineToConstantHeading(new Vector2d(-60, 60), 90)
                .build();

        Action blueLeft = drive.actionBuilder(drive.pose)
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-36, 36, -90), 90)
                .splineToConstantHeading(new Vector2d(-60, 48), 90)
                .splineToConstantHeading(new Vector2d(-60, 60), 90)
                .build();

        Action redLeft = drive.actionBuilder(drive.pose)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(60, 12, -90), 90)
                .splineToLinearHeading(new Pose2d(36, 36, -90), 90)
                .splineToConstantHeading(new Vector2d(60, 48), 90)
                .splineToConstantHeading(new Vector2d(60, 60), 90)
                .build();

        Action redRight = drive.actionBuilder(drive.pose)
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(36, 36, -90), 90)
                .splineToConstantHeading(new Vector2d(60, 48), 90)
                .splineToConstantHeading(new Vector2d(60, 60), 90)
                .build();

        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(blueRight);
//        Actions.runBlocking(blueLeft);
//        Actions.runBlocking(redLeft);
//        Actions.runBlocking(redRight);
    }
}