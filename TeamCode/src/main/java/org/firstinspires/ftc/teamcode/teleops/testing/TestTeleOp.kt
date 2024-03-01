package org.firstinspires.ftc.teamcode.teleops.testing

import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitUntilCommand
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.VoltageSensor

@TeleOp
class TestTeleOp  : CommandOpMode() {
    override fun initialize() {
        /****************************************************
         * Initialize hardware                              *
         ****************************************************/

        val voltageSensor = VoltageSensor(hardwareMap)
        val lift = Lift(hardwareMap, voltageSensor)


        val hubs: List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)

        for (hub in hubs) {
            hub.bulkCachingMode = LynxModule.BulkCachingMode.AUTO
        }

        register(voltageSensor, lift)

        /****************************************************
         * Driver 1 Controls                                *
         * Driving and semi autonomous control              *
         ****************************************************/

        val driver1 = GamepadEx(gamepad1)


        //Pickup/Desposit
        driver1
            .getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
            .whenPressed(
                SequentialCommandGroup(
                    InstantCommand({ lift.setHeightLastPos() }, lift)
                )
            )
        driver1
            .getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER) //TODO Change to just passthrough/claw control
            .whenPressed(
                SequentialCommandGroup(
                    InstantCommand(lift::retract, lift),
                    WaitUntilCommand(lift::atTarget),
                )
            )

        //lift heights
        driver1
            .getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
            .whenPressed(
                    InstantCommand({ lift.setHeight(Lift.LiftPosition.LOW) }, lift)
            )

        driver1
            .getGamepadButton(GamepadKeys.Button.DPAD_UP)
            .whenPressed(
                    InstantCommand({ lift.setHeight(Lift.LiftPosition.MIDDLE) }, lift)
            )

        driver1
            .getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
            .whenPressed(
                    InstantCommand({ lift.setHeight(Lift.LiftPosition.HIGH) }, lift)
            )

    }

}
