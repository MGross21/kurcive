package pioneer.tools.utils

/**
 * A class that toggles a boolean state when a button is pressed.
 */
class Toggle(initialState: Boolean = false) {
    var state: Boolean = initialState
        private set

    private var prevButtonState: Boolean = false
    private var justChanged: Boolean = false

    /**
     * Toggles the state if the button is pressed.
     * @param buttonPressed the current button state
     * @return the current state after toggling
     */
    fun update(buttonPressed: Boolean): Boolean {
        justChanged = false
        if (buttonPressed && !prevButtonState) {
            state = !state
            justChanged = true
        }
        prevButtonState = buttonPressed
        return state
    }

    /**
     * Sets the state.
     * @param newState the new state
     */
    fun setState(newState: Boolean) {
        justChanged = newState != state
        state = newState
    }

    /**
     * Checks if the state just changed.
     * @return true if the state just changed, false otherwise
     */
    fun justChanged(): Boolean = justChanged

    /**
     * Resets the toggle to its initial state.
     * @param resetState the state to reset to (default is false)
     */
    fun reset(resetState: Boolean = false) {
        state = resetState
        prevButtonState = false
        justChanged = false
    }
}
