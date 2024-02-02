package org.firstinspires.ftc.teamcode.teleops

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.PoseVelocity2d
import com.acmerobotics.roadrunner.Vector2d
import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.commands.drive.SimpleJoystickDrive
import org.firstinspires.ftc.teamcode.subsystems.DualClaw
import org.firstinspires.ftc.teamcode.subsystems.LiftKF
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive
import org.firstinspires.ftc.teamcode.subsystems.Passthrough
import org.firstinspires.ftc.teamcode.subsystems.VoltageSensor
import org.firstinspires.ftc.teamcode.subsystems.localization.OdometryLocalizer

@TeleOp
class OldMainTeleOp  : CommandOpMode() {
    override fun initialize() {
        /****************************************************
         * Initialize hardware                              *
         ****************************************************/

        val voltageSensor = VoltageSensor(hardwareMap)
        val lift = LiftKF(hardwareMap, voltageSensor)
        val claw = DualClaw(hardwareMap)
        val passthrough = Passthrough(hardwareMap)
        //val vision = Vision(hardwareMap)
        //val localizer = MecanumMonteCarloLocalizer(hardwareMap, vision, Pose2d(), arrayToRowMatrix(doubleArrayOf()))
        val localizer = OdometryLocalizer(hardwareMap)
        val mecanum = MecanumDrive(hardwareMap, Pose2d(0.0, 0.0, Math.toRadians(90.0)), localizer, voltageSensor)

        //register(lift, claw, passthrough, mecanum, vision)

        val hubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)

        for (hub in hubs) {
            hub.bulkCachingMode = LynxModule.BulkCachingMode.AUTO
        }

        register(voltageSensor, mecanum, claw, passthrough)

        /****************************************************
         * Driver 1 Controls                                *
         * Driving and semi autonomous control              *
         ****************************************************/

        val driver1 = GamepadEx(gamepad1)

        /**
         * Drive
         */
        val input = { PoseVelocity2d(Vector2d(driver1.leftY, -driver1.leftX), -driver1.rightX) }

        var fieldCentric = false //TODO find out what it does when true
        val fieldCentricProvider = { fieldCentric }

        mecanum.defaultCommand = SimpleJoystickDrive(mecanum, input, fieldCentricProvider)

        /**
         * Claw
         */
        driver1
            .getGamepadButton(GamepadKeys.Button.A)
            .whenPressed(
                InstantCommand(claw::incramentOpen, claw)
            )

        driver1
            .getGamepadButton(GamepadKeys.Button.B)
            .whenPressed(
                InstantCommand(claw::incramentClosed, claw)
            )


        //drive mode
        driver1
            .getGamepadButton(GamepadKeys.Button.Y)
            .whenPressed(
                InstantCommand(passthrough::halfway, passthrough)
            )
        driver1
            .getGamepadButton(GamepadKeys.Button.X)
            .whenPressed(
                InstantCommand(passthrough::pickUp, passthrough)
            )


        //Pickup/Desposit
        driver1
            .getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
            .whenPressed(
                SequentialCommandGroup(
                    InstantCommand(claw::close, claw),
                    WaitCommand(200),
                    InstantCommand(passthrough::deposit, passthrough),
                    WaitCommand(300),
                    InstantCommand({ lift.setHeightLastPos() }, lift)
                )
            )
        driver1
            .getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
            .whenPressed(
                SequentialCommandGroup(
                    InstantCommand(claw::close, claw),
                    WaitCommand(200),
                    InstantCommand(passthrough::halfway, passthrough),
                    WaitCommand(500),
                    InstantCommand(lift::retract, lift)
                )
            )

        //lift heights
        driver1
            .getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
            .whenPressed(InstantCommand({ lift.setHeight(LiftKF.LiftPosition.LOW) }, lift))

        driver1
            .getGamepadButton(GamepadKeys.Button.DPAD_UP)
            .whenPressed(InstantCommand({ lift.setHeight(LiftKF.LiftPosition.MIDDLE) }, lift))

        driver1
            .getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
            .whenPressed(InstantCommand({ lift.setHeight(LiftKF.LiftPosition.HIGH) }, lift))

        //release
        Trigger{ driver1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) >= 0.5 }
            .whenActive(InstantCommand(claw::releaseFirst, claw))

        Trigger { driver1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >= 0.5 }//TriggerReader(driver1, GamepadKeys.Trigger.RIGHT_TRIGGER)::wasJustPressed)
            .whenActive(InstantCommand(claw::releaseSecond, claw))





    }

}