package tcs.familytree.views.plane;

import javafx.scene.shape.Line;

public class SimpleLineOnPlane implements LineOnPlane {
    private final PersonOnPlane parent;
    private final PersonOnPlane child;
    private final int labelWidth = 120;
    private final int labelHeight = 75;

    public SimpleLineOnPlane(PersonOnPlane parent, PersonOnPlane child){
        this.parent = parent;
        this.child = child;
    }

    @Override
    public PersonOnPlane parent() {
        return parent;
    }

    @Override
    public PersonOnPlane child() {
        return child;
    }

    @Override
    public Line build() {
        return new Line(parent.x(), parent.y(), child.x(), child.y());
    }

    @Override
    public Line build(int x, int y) {
        return new Line(parent.x() + x + (double) labelWidth /2, parent.y() + y + (double) labelHeight /2, child.x() + x + (double) labelWidth /2, child.y() + y + (double) labelHeight /2);
    }
}
