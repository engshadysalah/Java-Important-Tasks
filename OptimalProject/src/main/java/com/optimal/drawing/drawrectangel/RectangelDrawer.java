/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.drawing.drawrectangel;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class RectangelDrawer extends JFrame {

    JPanel jPanel;

    private ArrayList<Rectangel> rectangels = new ArrayList<Rectangel>();

    int Removed = 0;

    Point pointStart = null;
    Point pointsEnd = null;

    public RectangelDrawer() {

        jPanel = new JPanel();

        add(jPanel);
        //jPanel.setBackground(Color.white);

        setTitle("Drawer");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        jPanel.addMouseListener(mouseHandler);
        jPanel.addMouseMotionListener(mouseMotionHandler);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_DELETE) {

                    System.out.println("deleeeeeeeeeeeeeet");

                    pointStart = new Point(0, 0);
                    pointsEnd = new Point(0, 0);

                    if (rectangels.isEmpty()) {

                    } else {

                        rectangels.remove(rectangels.size() - 1);

                        // jPanel.getGraphics().clearRect(0,0,1000, (int) pointsEnd.distance(pointsEnd.x, pointStart.y));
                        repaint();

                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    public void addRectangel(double xbegin, double ybegin, double width, double hight) {

        // jPanel.getGraphics().clearRect((int) xbegin, (int) ybegin, (int) width, (int) hight);
        rectangels.add(new Rectangel(xbegin, ybegin, width, hight));

        System.out.println("===========> Done ");

    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        //  jPanel.getGraphics().clearRect((int) rectangels.get(rectangels.size() - 1).x, (int) rectangels.get(rectangels.size() - 1).y, (int) rectangels.get(rectangels.size() - 1).width, (int) rectangels.get(rectangels.size() - 1).hight);
        if (pointStart == null) {
        } else {

            // to show pointes of rectangel while drawing
            jPanel.getGraphics().drawRect((int) pointStart.x, (int) pointStart.y, (int) pointsEnd.distance(pointStart.x, pointsEnd.y), (int) pointsEnd.distance(pointsEnd.x, pointStart.y));

            //  jPanel.getGraphics().clearRect((int) rectangels.get(0).x, (int) rectangels.get(0).y, (int) rectangels.get(0).width, (int) rectangels.get(0).hight);
            //  graphics.clearRect((int) pointStart.x, (int) pointStart.y, (int) pointsEnd.distance(pointStart.x, pointsEnd.y), (int) pointsEnd.distance(pointsEnd.x, pointStart.y));
            if (rectangels.isEmpty()) {

                System.out.println("list is Empty ");

            } else {

                for (final Rectangel r : rectangels) {

                    r.paint(jPanel.getGraphics());

                    System.out.println("List size : " + rectangels.size());

                }

            }
        }

    }

    public MouseListener mouseHandler = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {

            pointStart = e.getPoint();

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            pointsEnd = e.getPoint();

            if (e.getPoint().distance(pointStart.x, pointsEnd.y) == 0.0) {

                System.out.println("Cann't paint");

            } else {

                System.out.println("x" + e.getPoint().x);

                addRectangel(pointStart.x, pointStart.y, e.getPoint().distance(pointStart.x, pointsEnd.y), e.getPoint().distance(pointsEnd.x, pointStart.y));

                System.out.println("===>  Rec Area = " + e.getPoint().distance(pointStart.x, pointsEnd.x) * e.getPoint().distance(pointsEnd.x, pointStart.y));

                repaint();

            }
        }

    };

    public MouseMotionListener mouseMotionHandler = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {

            pointsEnd = e.getPoint();

            repaint();
        }

    };

    public static void main(String[] args) {

        new RectangelDrawer();

    }

}
