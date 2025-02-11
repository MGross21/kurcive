package pioneer.subsystems.slide

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import pioneer.core.motor.Motor
import pioneer.tools.control.PID

open class Slide(
    hardwareMap: HardwareMap,
    private val motor1: Motor,
    private val motor2: Motor? = null,
    val type: Type = Type.HORIZONTAL,
    private val pid: PID? = null
    open val maxExtension: Double = 500, // Example max extension in ticks
    open val minExtension: Double = 0.0    // Fully retracted position
) {
    enum class State { IDLE, MOVING }
    enum class Type { HORIZONTAL, VERTICAL }
    enum class ExtensionState { RETRACTED, EXTENDED }

    private var _state = State.IDLE
    val state: State get() = _state

    private var extensionState = ExtensionState.RETRACTED
    val isExtended: Boolean get() = extensionState == ExtensionState.EXTENDED

    private val motors = listOfNotNull(motor1, motor2)

    init {
        motors.forEach { motor ->
            motor.apply {
                setMode(DcMotor.RunMode.RUN_USING_ENCODER)
                setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
            }
        }
    }

    var power: Double = 0.0
        set(value) {
            motors.forEach { it.power = value }
            field = value
        }

    fun setZeroPowerBehavior(behavior: DcMotor.ZeroPowerBehavior) =
        motors.forEach { it.zeroPowerBehavior = behavior }

    fun setMode(mode: DcMotor.RunMode) =
        motors.forEach { it.mode = mode }

    fun setTargetPosition(position: Int) =
        motors.forEach { it.targetPosition = position }
}
