import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ToolbarView extends JPanel implements IView {


    private Model model;
    private JList thickLst;

    private JToggleButton color1Button;
    private JToggleButton color2Button;
    private JToggleButton color3Button;
    private JToggleButton color4Button;
    private JToggleButton color5Button;
    private JToggleButton color6Button;

    private void addColorListener (JToggleButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setCurrentColor(button.getBackground());
                if(model.getCurrentTool() == Tool.SELECT) {
                    model.setSelectedShapeColor(model.getCurrentColor());
                }
            }
        });
    }

    ToolbarView(Model model_) {
        model = model_;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1)));

        this.setPreferredSize(new Dimension(100,100));

        JToolBar toolsToolBar = new JToolBar(JToolBar.VERTICAL);
        this.add(toolsToolBar);
        JToolBar colorToolBar = new JToolBar(JToolBar.VERTICAL);
        this.add(colorToolBar);
        JToolBar thicknessToolBar = new JToolBar(JToolBar.VERTICAL);

        thicknessToolBar.setPreferredSize(new Dimension(100, 170));
        thicknessToolBar.setMinimumSize(new Dimension(100, 170));
        this.add(thicknessToolBar);

        JPanel tools = new JPanel(new GridLayout(3,2));
        tools.setPreferredSize(new Dimension(100, 150));
        ButtonGroup toolButtons = new ButtonGroup();

        JToggleButton selectTool = new JToggleButton(new ImageIcon("images/select.png"), true);
        selectTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clearSelectedShape();
                model.setCurrentTool(Tool.SELECT);
            }
        });

        JToggleButton eraseTool = new JToggleButton(new ImageIcon("images/erase.png"));
        eraseTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clearSelectedShape();
                model.setCurrentTool(Tool.ERASE);
            }
        });

        JToggleButton lineTool = new JToggleButton(new ImageIcon("images/line.png"));
        lineTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clearSelectedShape();
                model.setCurrentTool(Tool.LINE);
            }
        });
        JToggleButton circleTool = new JToggleButton(new ImageIcon("images/circle.png"));
        circleTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clearSelectedShape();
                model.setCurrentTool(Tool.CIRCLE);
            }
        });

        JToggleButton rectangleTool = new JToggleButton(new ImageIcon("images/rectangle.png"));
        rectangleTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clearSelectedShape();
                model.setCurrentTool(Tool.RECTANGLE);
            }
        });

        JToggleButton fillTool = new JToggleButton(new ImageIcon("images/fill.png"));
        fillTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clearSelectedShape();
                model.setCurrentTool(Tool.FILL);
            }
        });

        JToggleButton  toolsName[] = {selectTool,eraseTool, lineTool, circleTool, rectangleTool, fillTool};
        for (int i=0; i<toolsName.length; i++) {
            tools.add(toolsName[i]);
            toolButtons.add(toolsName[i]);
        }
        toolsToolBar.add(tools);


        //////color Palette
        JPanel colors = new JPanel(new GridLayout(3,2));
        colors.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        colors.setPreferredSize(new Dimension(100, 150));
        ButtonGroup colorButtons = new ButtonGroup();

        color1Button = new JToggleButton("");
        color1Button.setBackground(Color.BLACK);
        color1Button.setIcon(new ImageIcon("images/black.png"));
        color1Button.setSelectedIcon(new ImageIcon("images/black_s.png"));
        addColorListener(color1Button);

        color2Button = new JToggleButton("");
        color2Button.setBackground(Color.WHITE);
        color2Button.setIcon(new ImageIcon("images/white.png"));
        color2Button.setSelectedIcon(new ImageIcon("images/white_s.png"));
        addColorListener(color2Button);

        color3Button = new JToggleButton("");
        color3Button.setBackground(Color.RED);
        color3Button.setIcon(new ImageIcon("images/red.png"));
        color3Button.setSelectedIcon(new ImageIcon("images/red_s.png"));
        addColorListener(color3Button);

        color4Button = new JToggleButton("");
        color4Button.setBackground(Color.BLUE);
        color4Button.setIcon(new ImageIcon("images/blue.png"));
        color4Button.setSelectedIcon(new ImageIcon("images/blue_s.png"));
        addColorListener(color4Button);

        color5Button = new JToggleButton("");
        color5Button.setBackground(Color.YELLOW);
        color5Button.setIcon(new ImageIcon("images/yellow.png"));
        color5Button.setSelectedIcon(new ImageIcon("images/yellow_s.png"));
        addColorListener(color5Button);

        color6Button = new JToggleButton("");
        color6Button.setBackground(Color.GREEN);
        color6Button.setIcon(new ImageIcon("images/green.png"));
        color6Button.setSelectedIcon(new ImageIcon("images/green_s.png"));
        addColorListener(color6Button);

        JToggleButton  colorBotton[] = {color1Button,color2Button,color3Button,color4Button,color5Button,color6Button};
        for (int i=0; i<colorBotton.length; i++) {
            colors.add(colorBotton[i]);
            colorButtons.add(colorBotton[i]);
        }

        colorToolBar.add(colors);

        JButton chooserButton = new JButton("Chooser");
        chooserButton.setPreferredSize(new Dimension(100, 50));
        chooserButton.setMaximumSize(new Dimension(200, 50));
        chooserButton.setMinimumSize(new Dimension(100, 50));
        chooserButton.setAlignmentX(0.5F);

        chooserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setCurrentColor(JColorChooser.showDialog(null, "Pick a Color", Color.BLACK, true));
                if(model.getCurrentTool() == Tool.SELECT) {
                    model.setSelectedShapeColor(model.getCurrentColor());
                }
            }
        });

        colorToolBar.add(chooserButton);

        Integer intArr[] = {0,1,2,3,4};
        thickLst = new JList(intArr);
        thickLst.setBackground(Color.WHITE);
        ListRenderer renderer = new ListRenderer();
        renderer.setPreferredSize(new Dimension(120, 30));
        renderer.addLineListener(thickLst, model);
        thicknessToolBar.add(thickLst);
    }

    public void updateView() {
        if(model.getSelectedShape() != null) {
            thickLst.setSelectedIndex(model.getCurrentThickness());
            Color cr = model.getCurrentColor();
            if(cr.getRGB() == Color.BLACK.getRGB())
                color1Button.setSelected(true);
            else if(cr.getRGB() == Color.WHITE.getRGB())
                color2Button.setSelected(true);
            else if(cr.getRGB() == Color.RED.getRGB())
                color3Button.setSelected(true);
            else if(cr.getRGB() == Color.BLUE.getRGB())
                color6Button.setSelected(true);
            else if(cr.getRGB() == Color.YELLOW.getRGB())
                color4Button.setSelected(true);
            else if(cr.getRGB() == Color.GREEN.getRGB())
                color5Button.setSelected(true);
        }
    }
}