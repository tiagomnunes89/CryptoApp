package br.dev.tmn.cryptocurrency.ui.utils

data class Data<RequestData>(
    var responseType: Status,
    var data: RequestData? = null,
    var error: Exception? = null
)

enum class Status { SUCCESSFUL, ERROR, LOADING }