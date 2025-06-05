package tcs.familytree.views.plane;

import javafx.scene.shape.Line;

public class SimpleLineOnPlane implements LineOnPlane {
    private double x1, y1, x2, y2;
    private final int labelWidth = 120;
    private final int labelHeight = 75;

    public SimpleLineOnPlane(PersonOnPlane parent, PersonOnPlane child){
        x1 = parent.x();
        y1 = parent.y();
        x2 = child.x();
        y2 = child.y();
    }

    public SimpleLineOnPlane(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void moveBy(double dx, double dy) {
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }

    @Override
    public Line build() {
        return new Line(x1, y1, x2, y2);
    }

    @Override
    public Line build(int x, int y) {
        return new Line(x1 + x + (double) labelWidth /2, y1 + y + (double) labelHeight /2, x2 + x + (double) labelWidth /2, y2 + y + (double) labelHeight /2);
    }
}
