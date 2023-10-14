package ir.mrhib.trixcounter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_match")
data class Match(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "match_id") val matchId: Int = 0,
    @ColumnInfo(name = "player1") val player1: String,
    @ColumnInfo(name = "player2") val player2: String,
    @ColumnInfo(name = "player3") val player3: String,
    @ColumnInfo(name = "player4") val player4: String,
)