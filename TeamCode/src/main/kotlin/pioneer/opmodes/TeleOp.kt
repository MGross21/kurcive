package pioneer.opmodes

import com.qualcomm.robotcore.eventloop.opmode.OpMode

open class TeleOp : OpMode() {

    override fun getRuntime(): Double {
        return super.getRuntime()
    }

    override fun init_loop() {
        telemetry.addData("Status", "Init Loop")
    }

    override fun init() {
        telemetry.addData("Status", "Initialized")
    }

    override fun internalPostInitLoop() {
        telemetry.addData("Status", "Post Init Loop")
    }

    override fun internalPostLoop() {
        telemetry.addData("Status", "Post Loop")
    }

    override fun internalPreInit() {
        telemetry.addData("Status", "Pre Init")
    }

    override fun loop() {
        telemetry.addData("Status", "Running")
    }

    fun requestOpModeStop() {
        super.requestOpModeStop()
    }

    fun resetStartTime() {
        super.resetStartTime()
    }

    override fun start() {
        telemetry.addData("Status", "Started")
    }

    override fun stop() {
        telemetry.addData("Status", "Stopped")
    }
    /*
    *Requests that this OpMode be shut down if it the currently active opMode, much as if the stop button had been pressed on the driver station; if this is not the currently active OpMode, then this function has no effect.
    */
    override fun requestOpModeStop() {
        telemetry.addData("Status", "Requested Stop")
        super.requestOpModeStop()
    }

    fun updateTelemetry(telemetry: Telemetry) {
        super.updateTelemetry(telemetry)
    }

}