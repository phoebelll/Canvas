import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


public class FileView  extends JMenuItem implements IView {
    public static Model model;

    FileView(Model model_, Frame frame, JMenu fileMenu) {
        model = model_;
        JMenuItem newImage = new JMenuItem("New");
        newImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.newDraw();
            }
        });

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        file = new File(file.getAbsolutePath() + ".jsd");
                        FileOutputStream fileStream = new FileOutputStream(file);
                        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
                        objectStream.writeObject(model.getShapeList());
                        objectStream.close();
                    }
                }catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                } catch (IOException e1) {
                        e1.printStackTrace();
                }
            }
        });
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        FileInputStream fileStream = new FileInputStream(fileChooser.getSelectedFile());
                        ObjectInputStream objectStream = new ObjectInputStream(fileStream);
                        ArrayList<MyShape> result = (ArrayList<MyShape>) objectStream.readObject();
                        model.loadShapes(result);
                        objectStream.close();
                    }
                }catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                } catch (IOException ex) {
                            ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                }
            }
        });
        fileMenu.add(newImage);
        fileMenu.add(save);
        fileMenu.add(load);

    }
    public void updateView() {
        System.out.println("Update view menu item");

    }
}
