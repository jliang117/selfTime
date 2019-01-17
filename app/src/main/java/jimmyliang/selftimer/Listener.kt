package jimmyliang.selftimer

sealed class ListenerType{
    class EditTimerListener():ListenerType()

    class ToggleTimerListener():ListenerType()
}

interface UpdateClickListener{
    fun onUpdateClicked(clicked:ListenerType)
}