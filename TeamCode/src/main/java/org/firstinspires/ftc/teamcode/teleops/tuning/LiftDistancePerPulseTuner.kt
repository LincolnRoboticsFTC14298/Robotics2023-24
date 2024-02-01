package org.firstinspires.ftc.teamcode.teleops.tuning

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.LiftKF
import org.firstinspires.ftc.teamcode.subsystems.VoltageSensor

@TeleOp
class LiftDistancePerPulseTuner() : OpMode() {

    private lateinit var lift: LiftKF
    lateinit var voltageSensor: VoltageSensor

    override fun init() {
        voltageSensor = VoltageSensor(hardwareMap)

        lift = LiftKF(hardwareMap, voltageSensor)
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
    }

    override fun loop() {
        val power = -gamepad1.left_stick_y * 0.6 //TODO find out why the acceleration is instantaneous
        voltageSensor.periodic()

        lift.checkLimit = true
        lift.checkEncoder()
        lift.setPower(power)
        lift.updateFilter(null)

        if (gamepad1.a) {
            lift.resetEncoders()
        }

        val p = TelemetryPacket()
        p.put("power", power)
        lift.fetchTelemetry(p)
        FtcDashboard.getInstance().sendTelemetryPacket(p)
    }

}