package ir.mrhib.trixcounter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("game_id")
    val id: Int = 0,
    @ColumnInfo(name = "match_id")
    val matchId: Int,
    @ColumnInfo("game_king")
    var king: Int,
    @ColumnInfo("game_contract")
    var contract: Int,
    @ColumnInfo("game_p1")
    var pointP1: Int,
    @ColumnInfo("game_p2")
    var pointP2: Int,
    @ColumnInfo("game_p3")
    var pointP3: Int,
    @ColumnInfo("game_p4")
    var pointP4: Int,
) {
}