import java.awt.*;
import java.io.Serializable;

public class MyShape implements Serializable {
    Shape shape;
    Color strokeColor;
    Color fillColor = null;
    Integer thickness;

    MyShape(Shape newShape, Color sColor, Integer newThickness) {
        shape = newShape;
        strokeColor = sColor;
        thickness = newThickness;
    };
}
