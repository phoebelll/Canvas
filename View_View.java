import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View_View extends JMenuItem implements IView {
    public static Model model;
    private void JradioAddListener (JRadioButtonMenuItem rb, CanvasView canvasView, String name) {
        rb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)(canvasView.getLayout());
                cl.show(canvasView, name);
            }
        });
    }
    View_View(Model model_, CanvasView canvasView, JMenu viewMenu) {
        model = model_;
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem fullSizeRB = new JRadioButtonMenuItem("Full Size");
        JRadioButtonMenuItem fitRB = new JRadioButtonMenuItem("Fit to Window");

        JradioAddListener(fullSizeRB, canvasView, "Full Size");
        JradioAddListener(fitRB, canvasView, "Fit to Window");
        group.add(fitRB);
        group.add(fullSizeRB);
        viewMenu.add(fitRB);
        viewMenu.add(fullSizeRB);
        fullSizeRB.setSelected(true); //default to Full Size
    }
    public void updateView() {
        System.out.println("Update view item");

    }
}