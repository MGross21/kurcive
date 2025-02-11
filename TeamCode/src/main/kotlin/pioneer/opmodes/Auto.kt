package pioneer.opmodes

import com.qualcomm.robotcore.eventloop.opmode.OpMode

class Auto : OpMode() {

    init {
        register()
    }

    // This function directly registers the OpMode
    private fun registerOpMode() {
        // Get the OpModeManager from the system context (using hardwareMap.appContext)
        val manager = hardwareMap.appContext.getSystemService(OpModeManager::class.java)

        // Register this OpMode using its class and simple name
        manager?.let {
            it.register(this.javaClass.simpleName, this.javaClass)
        }
    }

    private var AUTO_TIME = 30 * 1000 // 30 seconds
    private var startTime = 0L

    override getRuntime() {
        return 0.0
    }

    override fun init_loop() {
        telemetry.addData("Status", "Initialized")
    }

    override fun internalPostInitLoop() {
        // Find all Instances of Threads and Coroutines and shutdown
        // Save data to file
    }

    override fun internalPostLoop() {
        telemetry.addData("Status", "Initialized")
    }

    override fun internalPostStart() {
        startTime = System.currentTimeMillis()
    }

    private fun sec(time:Long):Double {
        return time / 1000
    }

    fun requestExtension(time:Long){
        if (currentTime < 1.25) {
            val requiredTime = 1.25 - currentTime
            // Add requiredTime to the clock
        }
    }


}