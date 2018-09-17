package com.memtrip.eos.http.rpc.model.history.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoricAccountActionParent(
    val actions: List<HistoricAccountAction>,
    val last_irreversible_block: Int
)