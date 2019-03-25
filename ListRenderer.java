import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ListRenderer extends JLabel implements ListCellRenderer {
    public ListRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        int selectedIndex = ((Integer) value).intValue();
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(Color.WHITE);
            setForeground(list.getForeground());
        }

        String images[] = {"images/thickness_1.png",
                "images/thickness_2.png",
                "images/thickness_3.png",
                "images/thickness_4.png",
                "images/thickness_5.png"};
        ImageIcon icon = new ImageIcon(images[selectedIndex]);
        setIcon(icon);
        return this;
    }
    public void addLineListener(JList<Integer> thicknessList, Model model) {
        thicknessList.setCellRenderer(this);
        thicknessList.setSelectedIndex(0);
        thicknessList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();
                int selectedIndex = list.getSelectedIndex();
                model.setCurrentThickness(selectedIndex);
                if(model.getCurrentTool() == Tool.SELECT) {
                    model.setSelectedShapeThickness(selectedIndex);
                }
            }
        });

    }
}