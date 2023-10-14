package ir.mrhib.trixcounter.model

import ir.mrhib.trixcounter.ui.tableActivity.CONTRACTS

data class Player(
    val id: Int = 0,
    var name: String,
    var contracts: ArrayList<CONTRACTS>
)