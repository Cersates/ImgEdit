package imgedit;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class Edit {

    String fileName; //название файла
    public Paint pai;
    public static BufferedImage imag;
    int mode = 0;
    public Color maincolor;
    public JButton colorbutton;
    public JColorChooser choosColor;
    public boolean pressed = false;
    public boolean loading;
    public int xPad;
    public int yPad;
    public int xf;
    public int yf;

    public Edit() {

        final Window progWindow = new Window("Графический редактор");

        progWindow.setSize(700, 700);
        progWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maincolor = java.awt.Color.black;

        pai = new Paint();
        pai.setBounds(30, 30, 260, 260);
        pai.setBackground(java.awt.Color.white);
        pai.setOpaque(true);
        progWindow.add(pai);

        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);

        JButton penbutton = new JButton(new ImageIcon("pen.png"));
        penbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 0;
            }
        });
        toolbar.add(penbutton);
        JButton brushbutton = new JButton(new ImageIcon("brush.png"));
        brushbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 1;
            }
        });
        toolbar.add(brushbutton);

        JButton lasticbutton = new JButton(new ImageIcon("lastic.png"));
        lasticbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 2;
            }
        });
        toolbar.add(lasticbutton);

        JButton textbutton = new JButton(new ImageIcon("text.png"));
        textbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 3;
            }
        });
        toolbar.add(textbutton);

        JButton linebutton = new JButton(new ImageIcon("line.png"));
        linebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 4;
            }
        });
        toolbar.add(linebutton);

        JButton elipsbutton = new JButton(new ImageIcon("elips.png"));
        elipsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 5;
            }
        });
        toolbar.add(elipsbutton);

        JButton rectbutton = new JButton(new ImageIcon("rect.png"));
        rectbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 6;
            }
        });
        toolbar.add(rectbutton);

        toolbar.setBounds(0, 0, 30, 300);
        progWindow.add(toolbar);

        choosColor = new JColorChooser(maincolor);
        choosColor.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                maincolor = choosColor.getColor();
                colorbutton.setBackground(maincolor);
            }
        });
        pai.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (pressed == true) {
                    Graphics g = imag.getGraphics();
                    Graphics2D g2 = (Graphics2D) g;
                    // установка цвета
                    g2.setColor(maincolor);
                    switch (mode) {
                        // карандаш
                        case 0:
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                        // кисть
                        case 1:
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                        // ластик
                        case 2:
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.setColor(java.awt.Color.WHITE);
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                            break;
                    }
                    xPad = e.getX();
                    yPad = e.getY();
                }
                pai.repaint();
            }
        });
        pai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Graphics g = imag.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                // установка цвета
                g2.setColor(maincolor);
                switch (mode) {
                    // карандаш
                    case 0:
                        g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                        break;
                    // кисть
                    case 1:
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                        break;
                    // ластик
                    case 2:
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.setColor(java.awt.Color.WHITE);
                        g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                        break;
                    // текст
                    case 3:
                        // устанавливаем фокус для панели,
                        // чтобы печатать на ней текст
                        pai.requestFocus();
                        break;
                }
                xPad = e.getX();
                yPad = e.getY();

                pressed = true;
                pai.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                xPad = e.getX();
                yPad = e.getY();
                xf = e.getX();
                yf = e.getY();
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                Graphics g = imag.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                // установка цвета
                g2.setColor(maincolor);
                // Общие рассчеты для овала и прямоугольника
                int x1 = xf, x2 = xPad, y1 = yf, y2 = yPad;
                if (xf > xPad) {
                    x2 = xf;
                    x1 = xPad;
                }
                if (yf > yPad) {
                    y2 = yf;
                    y1 = yPad;
                }
                switch (mode) {
                    // линия
                    case 4:
                        g.drawLine(xf, yf, e.getX(), e.getY());
                        break;
                    // круг
                    case 5:
                        g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
                        break;
                    // прямоугольник
                    case 6:
                        g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
                        break;
                }
                xf = 0;
                yf = 0;
                pressed = false;
                pai.repaint();
            }
        });
        pai.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // устанавливаем фокус для панели,
                // чтобы печатать на ней текст
                pai.requestFocus();
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (mode == 3) {
                    Graphics g = imag.getGraphics();
                    Graphics2D g2 = (Graphics2D) g;
                    // установка цвета
                    g2.setColor(maincolor);
                    g2.setStroke(new BasicStroke(2.0f));

                    String str = "";
                    str += e.getKeyChar();
                    g2.setFont(new Font("Arial", 0, 15));
                    g2.drawString(str, xPad, yPad);
                    xPad += 10;
                    // устанавливаем фокус для панели,
                    // чтобы печатать на ней текст
                    pai.requestFocus();
                    pai.repaint();
                }
            }
        });
        progWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // если делаем загрузку, то изменение размеров формы
                // отрабатываем в коде загрузки
                if (loading == false) {
                    pai.setSize(progWindow.getWidth() - 40, progWindow.getHeight() - 80);
                    BufferedImage tempImage = new BufferedImage(pai.getWidth(), pai.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D d2 = (Graphics2D) tempImage.createGraphics();
                    d2.setColor(java.awt.Color.white);
                    d2.fillRect(0, 0, pai.getWidth(), pai.getHeight());
                    tempImage.setData(imag.getRaster());
                    imag = tempImage;
                    pai.repaint();
                }
                loading = false;
            }
        });
        progWindow.setLayout(null);
        progWindow.setVisible(true);
    }

    class Paint extends JPanel { //рисование

        @Override
        public void paintComponent(Graphics g) {
            if (imag == null) {
                imag = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = (Graphics2D) imag.createGraphics();
                d2.setColor(java.awt.Color.white);
                d2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            super.paintComponent(g);
            g.drawImage(imag, 0, 0, this);
        }

        public Paint() {
        }

    }

}
