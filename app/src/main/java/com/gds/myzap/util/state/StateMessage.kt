package com.gds.myzap.util.state

import com.gds.myzap.data.model.Mensagem

sealed class StateMessage(
    var messageValue: Mensagem? = null,
    var messageError: String? = null
) {
    class Success(messageValue: Mensagem) : StateMessage(messageValue)
    class Error(messageValue: Mensagem? = null, messageError: String) : StateMessage(messageValue, messageError)
    class Empty() : StateMessage()
    class Loading() : StateMessage()
}
