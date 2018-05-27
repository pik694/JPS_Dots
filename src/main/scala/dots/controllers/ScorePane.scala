//package dots.controllers
//
//import dots.controllers.ScorePane.alignment
//import dots.model.Game
//import scalafx.geometry.{Insets, Pos}
//import scalafx.scene.control.Separator
//import scalafx.scene.image.ImageView
//import scalafx.scene.layout.{GridPane, VBox}
//import scalafx.scene.text.Text
//
//object ScorePane extends VBox(MainController.SPACING) {
//  padding = Insets(MainController.SPACING)
//  children = Seq(
//    new Text {
//      text = "Wynik"
//      style = "-fx-font-size: 24; fx-font-weight: bold"
//    },
//    new Separator,
//    new GridPane {
//      vgap = MainController.SPACING / 2
//      hgap = MainController.SPACING / 2
//
//      add(new Text("Gracz A") {
//        fill = MainController.playerAColor
////        Game.move_.onChange { (_, _, newValue) => this.underline = newValue == Game.playerA_.value }
//      }, 0, 0)
//      add(new ImageView {
////        Game.playerA_.onChange { (_, _, newValue) => this.image = newValue.photo }
//      }, 1, 0)
//      add(new Text(": 100") {
//        alignment = Pos.BottomRight
////        Game.score_.onChange { (_, _, newValue) =>
////          text = s": ${newValue._1}"
////        }
//      }, 2,0)
//
//      add(new Text("Gracz B") {
//        fill = MainController.playerBColor
////        Game.move_.onChange { (_, _, newValue) => this.underline = newValue == Game.playerB_.value }
//      }, 0, 1)
//      add(new ImageView {
////        Game.playerB_.onChange { (_, _, newValue) => this.image = newValue.photo }
//      }, 1, 1)
//      add(new Text(": 0") {
//        alignment = Pos.BottomRight
////        Game.score_.onChange { (_, _, newValue) =>
////          text = s": ${newValue._2}"
////        }
//      }, 2,1)
//    }
//  )
//}
