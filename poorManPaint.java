
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class PoorMans_Paint {

    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.setVisible(true);
    }
}

class MyFrame extends JFrame {

    private Color color;
    private String colorString;
    private static MyPanel p = new MyPanel();

    public MyFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(true);
        p.setFocusable(true);
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        JMenu colorMenu = new JMenu("Color");
        menu.add(colorMenu);
        JMenu exitMenu = new JMenu("Exit");
        menu.add(exitMenu);
        final JMenuItem exit = new JMenuItem("Exit program");
        exitMenu.add(exit);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        final JMenuItem red = new JMenuItem("Red");
        colorMenu.add(red);
        red.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = Color.RED;
                colorString = "Red";
                p.changeColor(color, colorString);
            }
        });
        final JMenuItem green = new JMenuItem("Green");
        colorMenu.add(green);
        green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = Color.GREEN;
                colorString = "Green";
                p.changeColor(color, colorString);
            }
        });
        final JMenuItem blue = new JMenuItem("Blue");
        colorMenu.add(blue);
        blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                color = Color.BLUE;
                colorString = "Blue";
                p.changeColor(color, colorString);
            }
        });
        JPanel buttons = new JPanel();
        JButton changeRed = new JButton("CHANGE TO RED");
        changeRed.setFocusable(false);
        changeRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p.changeShapeColor(Color.RED, "Red");
            }
        });
        JButton changeGreen = new JButton("CHANGE TO GREEN");
        changeGreen.setFocusable(false);
        changeGreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p.changeShapeColor(Color.GREEN, "Green");
            }
        });
        JButton changeBlue = new JButton("CHANGE TO BLUE");
        changeBlue.setFocusable(false);
        changeBlue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p.changeShapeColor(Color.BLUE, "Blue");
            }
        });
        p.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char shortcut = e.getKeyChar();
                if (shortcut == 'r') {
                    color = Color.RED;
                    colorString = "Red";
                    p.changeColor(color, colorString);
                } else if (shortcut == 'g') {
                    color = Color.GREEN;
                    colorString = "Green";
                    p.changeColor(color, colorString);
                } else if (shortcut == 'b') {
                    color = Color.BLUE;
                    colorString = "Blue";
                    p.changeColor(color, colorString);
                }
            }
        });
        buttons.add(changeRed);
        buttons.add(changeGreen);
        buttons.add(changeBlue);
        add(buttons, BorderLayout.SOUTH);
        add(p);
    }
}

class MyPanel extends JPanel {

    private ArrayList<MyShape> shapes = new ArrayList<>();

    private Color currentColor = Color.BLUE;
    private String currentColorString = "Blue";

    public MyPanel() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) { // left mouse button
                    shapes = new ArrayList<>();
                    shapes.add(new MyShape(e.getPoint()));
                    repaint();
                }
                if (e.getButton() == 3) { // right mouse button
                    shapes = new ArrayList<>();
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if ((e.getModifiersEx()
                        & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                    shapes.get(
                            shapes.size() - 1).addPoint(e.getPoint());
                    repaint();
                }
            }
        });
    }

    public void changeColor(Color color, String colorString) {
        currentColor = color;
        currentColorString = colorString;
    }

    public void changeShapeColor(Color color, String colorString) {
        currentColor = color;
        currentColorString = colorString;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawString(currentColorString, 10, 15);
        for (MyShape s : shapes) {
            s.drawShape(g2, currentColor);
        }
    }
}

class MyShape {

    private ArrayList<Point2D> points = new ArrayList<>();

    public MyShape() {

    }

    public MyShape(Point2D point) {
        points.add(point);
    }

    public void addPoint(Point2D point) {
        points.add(point);
    }

    public void drawShape(Graphics2D g, Color color) {
        g.setColor(color);
        if (points.size() == 0) {
            return;
        }
        Point2D start = points.get(0);
        for (Point2D end : points) {
            g.draw(new Line2D.Double(start, end));
            start = end;
        }
    }
}
