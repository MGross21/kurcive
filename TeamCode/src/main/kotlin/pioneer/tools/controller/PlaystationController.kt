package pioneer.tools.controller

import com.qualcomm.robotcore.hardware.Gamepad

open class PlaystationController(gamepad: Gamepad) : Gamepad() {

    private val debounceInterval = 300 // milliseconds
    private var lastPressTime = mutableMapOf<String, Long>()

    init {
        this.copy(gamepad)
        this.joystickDeadzone = 0.1
    }

    private fun isDebounced(button: String): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastTime = lastPressTime[button] ?: 0
        return if (currentTime - lastTime > debounceInterval) {
            lastPressTime[button] = currentTime
            true
        } else {
            false
        }
    }

    val cross get() = a && isDebounced("cross")
    val circle get() = b && isDebounced("circle")
    val triangle get() = y && isDebounced("triangle")
    val square get() = x && isDebounced("square")

    val dpadUp get() = dpad_up && isDebounced("dpadUp")
    val dpadDown get() = dpad_down && isDebounced("dpadDown")
    val dpadLeft get() = dpad_left && isDebounced("dpadLeft")
    val dpadRight get() = dpad_right && isDebounced("dpadRight")

    val leftStickButton get() = left_stick_button && isDebounced("leftStickButton")
    val rightStickButton get() = right_stick_button && isDebounced("rightStickButton")

    val l1 get() = left_bumper && isDebounced("l1")
    val r1 get() = right_bumper && isDebounced("r1")

    val l2 get() = left_trigger
    val l2Pressed get() = leftTrigger > triggerDeadzone && isDebounced("l2Pressed")
    val r2 get() = right_trigger
    val r2Pressed get() = rightTrigger > triggerDeadzone && isDebounced("r2Pressed")

    val leftStickX get() = left_stick_x
    val leftStickY get() = left_stick_y
    val leftStickMagnitude get() = Math.hypot(leftStickX, leftStickY)
    val leftStickAngle get() = Math.atan2(leftStickY, leftStickX)
    val leftStickDirection get() = Math.round(leftStickAngle / (Math.PI / 4)).toInt()
    val l3 get() = left_stick_button && isDebounced("l3")

    val rightStickX get() = right_stick_x
    val rightStickY get() = right_stick_y
    val rightStickMagnitude get() = Math.hypot(rightStickX, rightStickY)
    val rightStickAngle get() = Math.atan2(rightStickY, rightStickX)
    val rightStickDirection get() = Math.round(rightStickAngle / (Math.PI / 4)).toInt()
    val r3 get() = right_stick_button && isDebounced("r3")

    var triggerDeadzone: Double
        get() = this.triggerDeadzone
        set(value) {
            require(value in 0.0..1.0) { "Deadzone must be between 0 and 1" }
            this.triggerDeadzone = value
        }

    fun rumble(milliseconds: Int) = rumble(milliseconds)
}