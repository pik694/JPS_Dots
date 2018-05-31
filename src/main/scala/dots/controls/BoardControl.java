package dots.controls;

import dots.model.Point;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Represents board's view
 */
public class BoardControl extends Pane {

    private IntegerProperty rows = new SimpleIntegerProperty();
    private IntegerProperty columns = new SimpleIntegerProperty();

    private ArrayList<Line> rowLines = new ArrayList<>();
    private ArrayList<Line> columnLines = new ArrayList<>();
    private ArrayList<DotView> circles = new ArrayList<>();
    private ArrayList<Line> connectionLines = new ArrayList<>();

    /**
     * Constructor
     */
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

    /**
     * Constructor
     * @param children
     */
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

    /**
     * Getter
     * @return number of rows
     */
    public int getRows() {
        return rows.get();
    }

    /**
     * Getter
     * @return rows property
     */
    public IntegerProperty rowsProperty() {
        return rows;
    }

    /**
     * Getter
     * @return number of columns
     */
    public int getColumns() {
        return columns.get();
    }

    /**
     * Getter
     * @return columns property
     */
    public IntegerProperty columnsProperty() {
        return columns;
    }

    /**
     * Setter
     * @param columns number of columns
     */
    public void setColumns(int columns) {
        this.columns.set(columns);
    }

    /**
     * Setter
     * @param rows number of rows
     */
    public void setRows(int rows) {
        this.rows.set(rows);
    }

    /**
     * Draws dot
     * @param dot to be drawn
     */
    public void drawDot(DotView dot) {

        double y = heightProperty().doubleValue() / (rowLines.size() + 1) * (dot.row() + 1);
        double x = widthProperty().doubleValue() / (columnLines.size() + 1) * (dot.column() + 1);

        dot.centerX().value_$eq(x);
        dot.centerY().value_$eq(y);

        getChildren().add(dot.delegate());
        circles.add(dot);

    }

    /**
     * Draws line
     * @param start point
     * @param end point
     * @param color of that line
     */
    public void drawLine(Point start, Point end, Color color) {

        ArrayList<Point> array = new ArrayList<>();

        array.add(start);
        array.add(end);

        Line line = new Line();
        line.setStrokeWidth(4);
        line.setStroke(color);

        this.getChildren().add(line);
        this.connectionLines.add(line);
        line.setUserData(array);

        double startY = heightProperty().doubleValue() / (rowLines.size() + 1) * (start.row() + 1);
        double startX = widthProperty().doubleValue() / (columnLines.size() + 1) * (start.column() + 1);

        double endY = heightProperty().doubleValue() / (rowLines.size() + 1) * (end.row() + 1);
        double endX = widthProperty().doubleValue() / (columnLines.size() + 1) * (end.column() + 1);

        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);

    }

    /**
     * Transforms mouse coordinates to board coordinates
     * @param event mouse click event
     * @return transformed coordinate
     */
    public Point translate(MouseEvent event) {

        double xDistance = widthProperty().doubleValue() / (columnLines.size() + 1);
        double yDistance = heightProperty().doubleValue() / (rowLines.size() + 1);

        double x = event.getX() / xDistance;
        double y = event.getY() / yDistance;

        Integer column = getCoordinate(x);
        Integer row = getCoordinate(y);

        if (row == null || column == null
                || row < 0
                || row >= rowLines.size()
                || column < 0
                || column >= columnLines.size())

            return null;


        return new Point(row, column);
    }


    /**
     * @param a
     * @return coordinate
     */
    private Integer getCoordinate(double a) {
        int a_ = ((int) (a * 10)) % 10;
        if (a_ < 4) {
            return ((int) a) - 1;
        } else if (a_ > 6) {
            return (int) a;
        } else return null;
    }

    /**
     * Updates view
     */
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

        for (DotView dot : circles) {
            double x = widthProperty().doubleValue() / (columnLines.size() + 1) * (dot.column() + 1);
            double y = heightProperty().doubleValue() / (rowLines.size() + 1) * (dot.row() + 1);

            dot.centerX().value_$eq(x);
            dot.centerY().value_$eq(y);
        }

        for (Line line : connectionLines) {

            ArrayList<Point> array = (ArrayList<Point>) line.getUserData();

            line.fillProperty().setValue(Color.RED);

            Point start = array.get(0);
            Point end = array.get(1);

            double startY = heightProperty().doubleValue() / (rowLines.size() + 1) * (start.row() + 1);
            double startX = widthProperty().doubleValue() / (columnLines.size() + 1) * (start.column() + 1);

            double endY = heightProperty().doubleValue() / (rowLines.size() + 1) * (end.row() + 1);
            double endX = widthProperty().doubleValue() / (columnLines.size() + 1) * (end.column() + 1);

            line.setStartX(startX);
            line.setStartY(startY);

            line.setEndX(endX);
            line.setEndY(endY);

        }

    }

    /**
     * Draw rows
     * @param rows number of rows to draw
     */
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

    /**
     * Draw columns
     * @param columns number of columns to draw
     */
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
