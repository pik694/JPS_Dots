package dots.controls;

import dots.model.Dot;
import dots.model.DotView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class BoardControl extends Pane {

    private IntegerProperty rows = new SimpleIntegerProperty();
    private IntegerProperty columns = new SimpleIntegerProperty();

    private ArrayList<Line> rowLines = new ArrayList<>();
    private ArrayList<Line> columnLines = new ArrayList<>();
    private ArrayList<Circle> circles = new ArrayList<>();

    public BoardControl() {

        rows.addListener((observable, oldValue, newValue) -> {
            this.getChildren().removeAll(rowLines);
            this.rowLines.clear();
            this.generateRows(newValue.intValue());
        });

        columns.addListener((arg, oldVal, newVal) -> {
            this.getChildren().removeAll(columnLines);
            this.columnLines.clear();
            this.generateColumns(newVal.intValue());
        });

        heightProperty().addListener(observable -> {
            update();
        });

        widthProperty().addListener((observable -> {
            update();
        }));

    }

    public BoardControl(Node... children) {
        super(children);
        rows.addListener((observable, oldValue, newValue) -> {
            this.getChildren().removeAll(rowLines);
            this.generateRows(newValue.intValue());
        });

        columns.addListener((arg, oldVal, newVal) -> {
            this.getChildren().removeAll(columnLines);
            this.generateColumns(newVal.intValue());
        });
    }

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

    public void drawDot(DotView dot) {
        double y = heightProperty().doubleValue() / (rowLines.size() + 1) * (dot.point().row() + 1);
        double x = widthProperty().doubleValue() / (columnLines.size() + 1) * (dot.point().column() + 1);

        Circle circle = new Circle();

        circle.fillProperty().setValue(dot.color().delegate());
        circle.centerXProperty().set(x);
        circle.centerYProperty().set(y);

        circle.setUserData(dot);
        circle.setRadius(6);

        getChildren().add(circle);
        circles.add(circle);

    }

    private void update() {

        for (int i = 0; i < rowLines.size(); ++i) {
            Line line = rowLines.get(i);
            double y = heightProperty().doubleValue() / (rowLines.size() + 1) * (i + 1);
            line.startYProperty().set(y);
            line.endYProperty().set(y);
        }
        for (Line line : columnLines) line.endYProperty().set(heightProperty().doubleValue());

        for (int i = 0; i < columnLines.size(); ++i) {
            Line line = columnLines.get(i);
            double x = widthProperty().doubleValue() / (columnLines.size() + 1) * (i + 1);

            line.startXProperty().set(x);
            line.endXProperty().set(x);
        }

        for (Line line : rowLines) line.endXProperty().set(widthProperty().doubleValue());

        for(Circle circle : circles){
            DotView dot = (DotView) circle.getUserData();
            double x = widthProperty().doubleValue() / (columnLines.size() + 1) * ( dot.point().column() + 1);
            double y = heightProperty().doubleValue() / (rowLines.size() + 1) * (dot.point().row() + 1);

            circle.centerYProperty().set(y);
            circle.centerXProperty().set(x);
        }

    }

    private void generateRows(Integer rows) {
        for (int i = 0; i < rows; ++i) {
            Line line = new Line();

            this.rowLines.add(line);
            this.getChildren().add(line);

            line.setStrokeWidth(2);

            double y = heightProperty().doubleValue() / rows * i;

            line.startYProperty().set(y);
            line.endYProperty().set(y);

            line.startXProperty().set(0);
            line.endXProperty().set(widthProperty().doubleValue());

        }
    }

    private void generateColumns(Integer columns) {

        for (int i = 0; i < columns; ++i) {
            Line line = new Line();
            this.columnLines.add(line);
            this.getChildren().add(line);

            line.setStrokeWidth(2);

            double x = widthProperty().doubleValue() / columns * i;

            line.startXProperty().set(x);
            line.endXProperty().set(x);

            line.startYProperty().set(0);
            line.endYProperty().set(heightProperty().doubleValue());

        }

    }


}
