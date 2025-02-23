package kurcive.opmodes

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar

// Import your OpMode classes
import kurcive.opmodes.Auto
import kurcive.opmodes.Teleop

object Register {
    @OpModeRegistrar
    @JvmStatic
    fun registerOpModes(manager: OpModeManager) {
        // Register the OpModes dynamically
        val opModes = listOf(Auto::class.java, Teleop::class.java)

        // Register each OpMode, using its simple class name as the OpMode name
        for (opModeClass in opModes) {
            val opModeName = opModeClass.simpleName // This gives the class name without the package
            manager.register(opModeName, opModeClass)
        }
    }
}
