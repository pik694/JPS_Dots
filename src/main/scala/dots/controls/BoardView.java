package dots.controls;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class BoardView extends Pane {
    public BoardView() {

        rows.addListener((observable, oldValue, newValue) -> {
            this.getChildren().removeAll(rowLines);
            this.generateRows(newValue.intValue());
        });

        columns.addListener((arg, oldVal, newVal) -> {
            this.getChildren().removeAll(columnLines);
            this.generateColumns(newVal.intValue());
        });

        generateColumns(columns.get());
        generateRows(rows.get());
    }

    public BoardView(Node... children) {
        super(children);
        rows.addListener((observable, oldValue, newValue) -> {
            this.getChildren().removeAll(rowLines);
            this.generateRows(newValue.intValue());
        });

        columns.addListener((arg, oldVal, newVal) -> {
            this.getChildren().removeAll(columnLines);
            this.generateColumns(newVal.intValue());
        });

        generateColumns(columns.get());
        generateRows(rows.get());

    }

    private IntegerProperty rows = new SimpleIntegerProperty();
    private IntegerProperty columns = new SimpleIntegerProperty();

    public int getRows() {
        return rows.get();
    }

    public IntegerProperty rowsProperty() {
        return rows;
    }

    public int getColumns() {
        return columns.get();
    }

    public IntegerProperty columnsProperty() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns.set(columns);
    }

    public void setRows(int rows) {
        this.rows.set(rows);
    }

    private ArrayList<Line> rowLines = new ArrayList<>();
    private ArrayList<Line> columnLines = new ArrayList<>();

    private void generateRows(Integer rows) {
        for (int i = 0; i < rows; ++i) {
            Line line = new Line();
            line.setStrokeWidth(2);

            double y = heightProperty().doubleValue() / rows * i;

            line.startYProperty().set(y);
            line.endYProperty().set(y);

            line.startXProperty().set(0);
            line.endXProperty().set(widthProperty().doubleValue());

            this.rowLines.add(line);
            this.getChildren().add(line);
        }
    }

    private void generateColumns(Integer columns) {

        for (int i = 0; i < columns; ++i) {
            Line line = new Line();
            line.setStrokeWidth(2);

            double x = widthProperty().doubleValue() / columns * i;

            line.startXProperty().set(x);
            line.endXProperty().set(x);

            line.startYProperty().set(0);
            line.endYProperty().set(heightProperty().doubleValue());

            this.columnLines.add(line);
            this.getChildren().add(line);

        }

    }


}
