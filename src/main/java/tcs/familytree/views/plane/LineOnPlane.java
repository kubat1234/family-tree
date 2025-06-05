package tcs.familytree.views.plane;


import javafx.scene.shape.Line;

public interface LineOnPlane {
    PersonOnPlane parent();
    PersonOnPlane child();
    Line build();
    Line build(int x, int y);

}
