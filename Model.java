import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class Model {

    private MyShape drawShape;
    private MyShape selectedShape;
    private Tool currTool;
    private Color currColor;
    private Integer currThickness;

    private int minx,miny;
    private double offsetX;
    private double offsetY;
    private ArrayList<MyShape> shapeList;
    private ArrayList<IView> viewList;
    public Frame jframe;


    Model(Frame f) {

        jframe = f;
        shapeList = new ArrayList<MyShape>();
        viewList = new ArrayList<IView>();
        currTool = Tool.SELECT;
        currColor = Color.RED;
        currThickness = 1;
        drawShape =null;
        selectedShape = null;

    }

    public void addView(IView view) {
        viewList.add(view);
        view.updateView();
    }

    public void setCurrentThickness(int thickness) {
        currThickness = thickness;
        notifyObservers();
    }

    public void setSelectedShapeThickness(int thickness) {
        while (true) {
            if (selectedShape == null) break;
            selectedShape.thickness = thickness;
            break;
        }
        notifyObservers();
    }

    public void setSelectedShapeColor(Color color) {
        while (true) {
            if (selectedShape == null) break;
            selectedShape.strokeColor = color;
            break;
        }
        notifyObservers();
    }

    public void setCurrentColor(Color color) {
        currColor = color;
        notifyObservers();
    }

    public void setCurrentTool(Tool Name) {
        currTool = Name;
        notifyObservers();
    }

    public void newDraw() {
        shapeList = new ArrayList<MyShape>();
        notifyObservers();
    }

    public void clearSelectedShape() {
        selectedShape = null;
        notifyObservers();
    }

    public void drawingShape(int x1, int y1, int x2, int y2) {
        minx = Math.min(x1, x2);
        miny = Math.min(y1, y2);
        switch (currTool) {
            case RECTANGLE:
                Rectangle2D.Float box = new Rectangle2D.Float(minx,miny, (int) Math.abs(x1 - x2) ,(int) Math.abs(y1 - y2));
                drawShape = new MyShape(box, currColor, currThickness);
                break;
            case CIRCLE:
                Ellipse2D.Float box1 = new Ellipse2D.Float(minx,miny,(int) Math.abs(x1 - x2) ,(int) Math.abs(y1 - y2));
                drawShape = new MyShape(box1 , currColor, currThickness);
                break;
            case LINE:
                Line2D.Float box2 = new Line2D.Float(x1,y1,x2,y2);
                drawShape = new MyShape(box2, currColor, currThickness);
                break;
        }
        notifyObservers();
    }

    public void addShape() {
        shapeList.add(drawShape);
        drawShape = null;
        notifyObservers();
    }
    public void loadShapes(ArrayList<MyShape> loadedSList) {
        shapeList = loadedSList;
        notifyObservers();
    }
    public void selectShape(int x, int y) {
        int i = shapeList.size()-1;
        while (i>=0) {
            selectedShape = shapeList.get(i);
            if (selectedShape.shape.contains(x, y) || selectedShape.shape.intersects(x-5,y-5,4,4)) {
                selectedShape = null;
                notifyObservers();
                selectedShape = shapeList.get(i);
                currThickness = selectedShape.thickness;
                currColor = selectedShape.strokeColor;
                break;
            }
            i--;
        }
        if(selectedShape != null) {
            offsetX = selectedShape.shape.getBounds().getX() - x;
            offsetY = selectedShape.shape.getBounds().getY() - y;
        }

        notifyObservers();
    }

    public void moveSelectedShape(int x, int y) {
        if(selectedShape != null) {
            double oldShapeX = selectedShape.shape.getBounds().getX();
            double oldShapeY = selectedShape.shape.getBounds().getY();

            AffineTransform tx = new AffineTransform();
            tx.translate(x - oldShapeX + offsetX, y - oldShapeY + offsetY);
            selectedShape.shape = tx.createTransformedShape(selectedShape.shape);
        }
        notifyObservers();
    }
    public void fillShape(int x, int y) {
        int i = shapeList.size()-1;
        while (i>=0) {
            selectedShape = shapeList.get(i);
            if (selectedShape.shape.contains(x, y)) {
                selectedShape.fillColor = currColor;
                break;
            }
            i--;
        }
        notifyObservers();
    }

    public void eraseShape(int x, int y) {
        int i = shapeList.size()-1;
        while (i>=0) {
            selectedShape = shapeList.get(i);
            if (selectedShape.shape.contains(x, y) ||
                    selectedShape.shape.intersects(x-2,y-2,4,4))
            {
                shapeList.remove(i);
                break;
            }
            i--;
        }
        notifyObservers();
    }

    public MyShape getDrawingShape() {
        return drawShape;
    }
    public MyShape getSelectedShape() {
        return selectedShape;
    }
    public ArrayList<MyShape> getShapeList() {
        return shapeList;
    }
    public Tool getCurrentTool() {
        return currTool;
    }
    public Color getCurrentColor() {
        return currColor;
    }
    public Integer getCurrentThickness() {
        return currThickness;
    }

    private void notifyObservers() {
        for (IView view : this.viewList) {
            view.updateView();
        }
    }
}