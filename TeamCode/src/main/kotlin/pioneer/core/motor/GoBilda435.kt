package pioneer.core.motor

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap

/**
 * GoBilda motor class with basic functionalities
 * THIS CLASS IS NOT COMPLETE AND IS FOR DEMONSTRATION PURPOSES ONLY
 */


class GoBilda435(hardwareMap: HardwareMap, name: String) : Motor(hardwareMap, name) {

    private val TICKS_PER_REVOLUTION = 383.6
    private val WHEEL_DIAMETER = 4.0
    private val MAX_RPM = 435.0

    fun setVelocity(velocity: Double) {
        val targetVelocity = velocity * TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI)
        setMode(DcMotor.RunMode.RUN_USING_ENCODER)
        setTargetPosition(targetVelocity.toInt())
        setPower(1.0)
    }
}