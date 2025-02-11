package pioneer.core.servo

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo as FtcServo

/**
 * Core Servo class with basic functionalities, including FTC hardware integration.
 * Extend this class to create custom servo classes and States.
 */
open class Servo(
    private val minPos: Double = 0.0,
    private val maxPos: Double = 1.0,
    private val hardwareMap: HardwareMap? = null, // HardwareMap for FTC hardware
    private val servoName: String, // Name of the servo in the hardware map
    private val mode: Mode = Mode.SERVO // Default mode is position control (SERVO mode)
) {

    // Validate position ranges
    init {
        require(minPos >= 0 && minPos < 1) { "Minimum position must be between 0 and 1" }
        require(maxPos > 0 && maxPos <= 1) { "Maximum position must be between 0 and 1" }
        require(minPos < maxPos) { "Minimum position must be less than maximum position" }
    }

    // FTC hardware servo instance
    private val ftcServo: FtcServo? = hardwareMap?.get(FtcServo::class.java, servoName)

    // Enum for controlling the state of the servo
    enum class State(val position: Double) {
        OPEN(maxPos),
        CLOSE(minPos),
    }

    // Enum for the mode of operation (Position control or Continuous)
    enum class Mode {
        SERVO,       // Standard position-controlled servo
        CONTINUOUS  // Continuous mode (speed-controlled)
    }

    // Internal state of the servo
    private var _state: State = State.CLOSE
    private var _position: Double = _state.position

    // Read-only property for current position
    val position: Double
        get() = _position

    // Method to set the position or speed of the servo
    open fun setState(state: State) {
        setPosition(state.position)
    }

    open fun setPosition(pos: Double) {
        when (mode) {
            Mode.SERVO -> {
                // For SERVO mode, ensure the position is within the valid range
                require(pos in minPos..maxPos) { "Position must be between $minPos and $maxPos" }
                _position = pos
                ftcServo?.position = pos // Set the servo position in the FTC hardware
            }
            Mode.CONTINUOUS -> {
                // For CONTINUOUS mode, accept a value between -1.0 (full speed one way) and 1.0 (full speed the other way)
                ftcServo?.position = when {
                    pos > 0 -> 1.0 // Full speed forward
                    pos < 0 -> -1.0 // Full speed backward
                    else -> 0.0 // Stop
                }
                _position = pos // For continuous mode, the position isn't meaningful, but we still store it
            }
        }
    }

    // Open the servo to its maximum position (for SERVO mode)
    fun open() {
        if (mode == Mode.SERVO) {
            setPosition(maxPos)
        }
    }

    // Close the servo to its minimum position (for SERVO mode)
    fun close() {
        if (mode == Mode.SERVO) {
            setPosition(minPos)
        }
    }
}
