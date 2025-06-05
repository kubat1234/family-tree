package tcs.familytree.views.plane;


import javafx.scene.shape.Line;

public interface LineOnPlane {
    Line build();
    Line build(int x, int y);
}