package org.firstinspires.ftc.teamcode.teleops.tuning

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.VoltageSensor

@TeleOp
class ManualLiftFeedforwardTuner : OpMode() {

    lateinit var lift: Lift

    lateinit var voltageSensor: VoltageSensor
    override fun init() {
        voltageSensor = VoltageSensor(hardwareMap)
        lift = Lift(hardwareMap, voltageSensor)
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
    }

    var up = false
    override fun loop() {
        if (lift.timeFromTarget() < 0 ) {
            if(!up) {
                lift.setpoint = 23.0
                up = true
            }
            else {
                lift.setpoint = 5.0
                up = false
            }
        }

        voltageSensor.periodic()

        lift.updateController()
        lift.tuningPeriodic()

        val p = TelemetryPacket()
        lift.fetchTelemetry(p)
        FtcDashboard.getInstance().sendTelemetryPacket(p)
    }
}