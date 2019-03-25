import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class CanvasView extends JPanel implements IView {

	private Model model;
	private  int x;
	private int y;
	private int startX;
	private int startY;
	private double canvasWidth = 1100;
	private double canvasHeight = 1000;
	private double scaleWidth;
	private double scaleHeight;
	private double scale;
	private float dash [] = {10.0f};
	private ArrayList<MyShape> shapeList;
	private static final String ESCAPE = "escape";


	private Action escape = new AbstractAction(ESCAPE) {
		public void actionPerformed(ActionEvent e) {
			System.out.println("pressed esc");
			repaint();
		}
	};

	private	JPanel canvasFull = new JPanel(){
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			scaleWidth = this.getWidth() / canvasWidth;
			scaleHeight = this.getHeight() / canvasHeight;
			scale = Math.min(scaleWidth, scaleHeight);
			g2.scale(scale, scale);

			shapeList = model.getShapeList();
			for (MyShape shape: shapeList) {
				int shapeThickness = (shape.thickness+1)*2;
				if (shape.fillColor != null) {
					g2.setColor(shape.fillColor);
					g2.fill(shape.shape);
				}

				g2.setStroke(new BasicStroke(shapeThickness));
				if(shape == model.getSelectedShape()) {
					g2.setStroke(new BasicStroke(shapeThickness,
							BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_MITER,
							10, dash, 0));
				}

				g2.setColor(shape.strokeColor);
				g2.draw(shape.shape);
				g2.setStroke(new BasicStroke(3));

			}
			if(model.getDrawingShape() != null) {
				g2.setStroke(new BasicStroke((model.getCurrentThickness()+1)*2));
				g2.setColor(model.getCurrentColor());
				g2.draw(model.getDrawingShape().shape);
			}
		}
	};

	private	JPanel canvasFit = new JPanel(){
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			scaleWidth = this.getWidth() / canvasWidth;
			scaleHeight = this.getHeight() / canvasHeight;
			scale = Math.min(scaleWidth, scaleHeight);
			g2.scale(scale, scale);

			shapeList = model.getShapeList();
			for (MyShape shape: shapeList) {
				int shapeThickness = (shape.thickness+1)*2;
				if (shape.fillColor != null) {
					g2.setColor(shape.fillColor);
					g2.fill(shape.shape);
				}

				g2.setStroke(new BasicStroke(shapeThickness));
				if(shape == model.getSelectedShape()) {
					g2.setStroke(new BasicStroke(shapeThickness,
							BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_MITER,
							10, dash , 0));
				}

				g2.setColor(shape.strokeColor);
				g2.draw(shape.shape);
				g2.setStroke(new BasicStroke(3));

			}
			if(model.getDrawingShape() != null) {
				g2.setStroke(new BasicStroke((model.getCurrentThickness()+1)*2));
				g2.setColor(model.getCurrentColor());
				g2.draw(model.getDrawingShape().shape);
			}

		}
	};

	CanvasView(Model model_) {
		model = model_;
		this.setLayout(new CardLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		canvasFull.setBackground(Color.WHITE);
		canvasFull.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));

		JPanel canvasContainer = new JPanel(new BorderLayout());
		canvasContainer.setPreferredSize(new Dimension(1100,1000));
		canvasContainer.setMaximumSize(new Dimension(1100,1000));
		canvasContainer.add(canvasFull, BorderLayout.CENTER);

		JPanel canvasVContainer = new JPanel();
		JPanel canvasHContainer = new JPanel();
		canvasVContainer.setLayout(new BoxLayout(canvasVContainer, BoxLayout.Y_AXIS));
		canvasHContainer.setLayout(new BoxLayout(canvasHContainer, BoxLayout.X_AXIS));
        canvasVContainer.setBackground(Color.LIGHT_GRAY);
        canvasHContainer.setBackground(Color.LIGHT_GRAY);
		canvasVContainer.add(canvasContainer);
		canvasHContainer.add(canvasVContainer);
		canvasHContainer.add(Box.createHorizontalGlue());
		canvasVContainer.add(Box.createVerticalGlue());


		JScrollPane scroller = new JScrollPane(canvasHContainer);
		scroller.setSize(new Dimension(640,500));
		this.add(scroller,"Fit to Window");

		canvasFit.setBackground(Color.WHITE);
		canvasFit.setPreferredSize(new Dimension(1100,1000));

		JPanel fitWindow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		fitWindow.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				scale =  Math.min(fitWindow.getWidth() / canvasWidth, fitWindow.getHeight() / canvasHeight);
				canvasFit.setPreferredSize(new Dimension((int)(canvasWidth*scale), (int)(canvasHeight*scale)));
				fitWindow.revalidate();
			}
		});

		fitWindow.setBackground(Color.LIGHT_GRAY);
		fitWindow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		fitWindow.add(canvasFit);
		this.add(fitWindow, "Fit to Window");


		canvasFull.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				scaleWidth = canvasFit.getWidth() / canvasWidth;
				scaleHeight = canvasFit.getHeight() / canvasHeight;
				startX = (int) (e.getX()/scaleWidth);
				startY = (int) (e.getY()/scaleHeight);
				x = (int) (e.getX()/scaleWidth);
				y =(int) (e.getY()/scaleHeight);
				Tool t = model_.getCurrentTool();
				switch (t) {
					case SELECT:
						model.selectShape(startX, startY);
						break;
					case ERASE:
						model.eraseShape(startX, startY);
						break;
					case FILL:
						model.fillShape(startX, startY);
						break;
					case CIRCLE:
						model.drawingShape(0, 0, 0, 0);
						break;
					case RECTANGLE:
						model.drawingShape(0, 0, 0, 0);
						break;
					case LINE:
						model.drawingShape(0, 0, 0, 0);
						break;
				}

			}
			public void mouseReleased(MouseEvent e) {
				Tool t = model_.getCurrentTool();
                switch (t) {
                    case CIRCLE:
                        model.addShape();
                        break;
                    case RECTANGLE:
                        model.addShape();
                        break;
                    case LINE:
                        model.addShape();
                        break;
                }
			}
		});
		canvasFull.addMouseMotionListener(new MouseAdapter() {
				public void mouseDragged(MouseEvent e) {
					Tool t = model_.getCurrentTool();

                    switch (t) {
                        case SELECT:
                            model.selectShape(x,y);
                            break;
                        case ERASE:
                            model.eraseShape(e.getX(), e.getY());
                            break;
                        case CIRCLE:
                            model.drawingShape(startX, startY, e.getX(), e.getY());
                            break;
                        case RECTANGLE:
                            model.drawingShape(startX, startY, e.getX(), e.getY());
                            break;
                        case LINE:
                            model.drawingShape(startX, startY, e.getX(), e.getY());
                            break;
                    }
				}
		});

		canvasFit.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				scaleWidth = canvasFit.getWidth() / canvasWidth;
				scaleHeight = canvasFit.getHeight() / canvasHeight;
				startX = (int) (e.getX()/scaleWidth);
				startY = (int) (e.getY()/scaleHeight);
				x = (int) (e.getX()/scaleWidth);
				y =(int) (e.getY()/scaleHeight);

				Tool t = model_.getCurrentTool();
				switch (t) {
					case SELECT:
						model.selectShape(x,y);
						break;
					case ERASE:
						model.eraseShape(x,y);
						break;
					case FILL:
						model.fillShape(x,y);
						break;
					case CIRCLE:
						model.drawingShape(0, 0, 0, 0);
						break;
					case RECTANGLE:
						model.drawingShape(0, 0, 0, 0);
						break;
					case LINE:
						model.drawingShape(0, 0, 0, 0);
						break;
				}
			}
			public void mouseReleased(MouseEvent e) {
				Tool t = model_.getCurrentTool();
                switch (t) {
                    case CIRCLE:
                        model.addShape();
                        break;
                    case RECTANGLE:
                        model.addShape();
                        break;
                    case LINE:
                        model.addShape();
                        break;
                }
			}
		});
		canvasFit.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				scaleWidth = canvasFit.getWidth() / canvasWidth;
				scaleHeight = canvasFit.getHeight() / canvasHeight;
				 x = (int) (e.getX()/scaleWidth);
				 y =(int) (e.getY()/scaleHeight);
				Tool t = model_.getCurrentTool();
				switch (t){
                    case SELECT:
                        model.moveSelectedShape(x,y);
                        break;
                    case CIRCLE:
                        model.drawingShape(startX,startY,x,y);
                        break;
                    case RECTANGLE:
                        model.drawingShape(startX,startY,x,y);
                        break;
                    case LINE:
                        model.drawingShape(startX,startY,x,y);
                        break;
                }

			}
		});
		this.setFocusable(true);
		this.requestFocus();
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ESCAPE);
		this.getActionMap().put(ESCAPE, escape);
	}
/// override abstract method of IView
	public void updateView() {
		canvasFull.repaint();
		canvasFit.repaint();
	}
}