package pioneer.core.motor

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.configuration.annotations.MotorType
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties

open class Motor(private val hardwareMap: HardwareMap, private val name: String) {

    private val motor: DcMotor = hardwareMap.get(DcMotor::class.java, name)

    init {
        motor.power = 0.0  // Ensure motor starts with zero power
        motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE  // Default to brake mode
    }

    // Set motor direction (FORWARD or REVERSE)
    fun setDirection(direction: DcMotorSimple.Direction) {
        motor.direction = direction
    }

    // Set motor power (-1.0 to 1.0)
    fun setPower(power: Double) {
        motor.power = power.coerceIn(-1.0, 1.0) // Clamp power to valid range
    }

    // Set motor mode (e.g., RUN_USING_ENCODER, RUN_WITHOUT_ENCODER)
    fun setMode(mode: DcMotor.RunMode) {
        motor.mode = mode
    }

    // Set behavior when power is set to zero (BRAKE or FLOAT)
    fun setZeroPowerBehavior(behavior: DcMotor.ZeroPowerBehavior) {
        motor.zeroPowerBehavior = behavior
    }

    // Set motor target position (for encoder-based movement)
    fun setTargetPosition(position: Int) {
        motor.targetPosition = position
    }

    // Check if motor is still running to the target position
    fun isBusy(): Boolean {
        return motor.isBusy
    }

    // Stop motor safely
    fun stop() {
        motor.power = 0.0
    }

    // Get the current position of the encoder
    fun getCurrentPosition(): Int {
        return motor.currentPosition
    }

    // Reset encoder to zero
    fun resetEncoder() {
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    }

    // Get current motor power
    fun getPower(): Double {
        return motor.power
    }

    // Get current motor direction
    fun getDirection(): DcMotorSimple.Direction {
        return motor.direction
    }

    // Get current motor mode
    fun getMode(): DcMotor.RunMode {
        return motor.mode
    }

    // Get motor zero power behavior
    fun getZeroPowerBehavior(): DcMotor.ZeroPowerBehavior {
        return motor.zeroPowerBehavior
    }
}
