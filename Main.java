import javax.swing.*;
import java.awt.*;

public class Main  {
    public static void main(String[] args) {


        JFrame frame = new JFrame("JSketch by Huiting Luo");

        Model model = new Model(frame);
        ToolbarView toolbarView = new ToolbarView(model);
        CanvasView canvasView = new CanvasView(model);
        model.addView(toolbarView);
        model.addView(canvasView);


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        FileView fview = new FileView(model, frame, fileMenu);
        fileMenu.add(fview);
        JMenu viewMenu = new JMenu("View");
        View_View vview = new View_View(model, canvasView, viewMenu);
        viewMenu.add(vview);
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);

        frame.setJMenuBar(menuBar);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        contentPane.add(toolbarView);
        contentPane.add(canvasView);

        frame.setMinimumSize(new Dimension(900,800));
        frame.setMaximumSize(new Dimension(1600, 1200));
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setVisible(true);

    }

}