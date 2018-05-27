package dots.controllers

import dots.model.player.Player

case class GameSettings(playerA: Player, playerB: Player, columns: Int, rows: Int)

