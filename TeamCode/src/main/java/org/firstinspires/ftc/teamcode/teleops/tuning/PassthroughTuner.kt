package org.firstinspires.ftc.teamcode.teleops.tuning

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.FieldConfig
import org.firstinspires.ftc.teamcode.subsystems.Passthrough
import org.firstinspires.ftc.teamcode.subsystems.Passthrough.Companion.passthroughDepositPosition
import org.firstinspires.ftc.teamcode.subsystems.Passthrough.Companion.passthroughJunctionAngle
import org.firstinspires.ftc.teamcode.subsystems.Passthrough.Companion.passthroughPickUpPosition

@TeleOp
class PassthroughTuner() : OpMode() {

    lateinit var passthrough: Passthrough

    override fun init() {
        passthrough = Passthrough(hardwareMap)
    }

    override fun loop() {

        if (gamepad1.dpad_down) {
            passthrough.pickUp() //TODO FIX it doesnt work idfk why
        }

        if (gamepad1.dpad_right) {
            passthrough.junctionDeposit()
        }

        if (gamepad1.dpad_up) {
            passthrough.deposit()
        }

        if (gamepad1.a) {
            passthrough.setPosition(0.0)
        }

        val p = TelemetryPacket()

        p.put("Time to deposit", passthrough.timeToTarget(passthroughDepositPosition))
        //p.put("Time to junction deposit", passthrough.timeToTarget(passthroughJunctionAngle))
        p.put("Time to pick up", passthrough.timeToTarget(passthroughPickUpPosition))
        passthrough.fetchTelemetry(p)
        FtcDashboard.getInstance().sendTelemetryPacket(p)
    }
}