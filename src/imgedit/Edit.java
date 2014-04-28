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
    public String inpStrB;

    public Edit() {

        final Window progWindow = new Window("Графический редактор");

        progWindow.setSize(1000, 700);
        progWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maincolor = java.awt.Color.black;

        JMenuBar menuBar = new JMenuBar();
        progWindow.setJMenuBar(menuBar);
        menuBar.setBounds(0, 0, 350, 30);
        JMenu fileMenu = new JMenu("Файл");
        JMenu fileMenu2 = new JMenu("Цвет");
        JMenu fileMenu3 = new JMenu("О программе");
        menuBar.add(fileMenu);
        menuBar.add(fileMenu2);
        menuBar.add(fileMenu3);

        // Меню цвет
        Action selectAction = new AbstractAction("Выбор цвета") {
            @Override
            public void actionPerformed(ActionEvent event) {
                maincolor = JColorChooser.showDialog(null, "Выберите цвет", maincolor);
                colorbutton.setBackground(maincolor);
            }
        };
        JMenuItem selectMenu = new JMenuItem(selectAction);
        fileMenu2.add(selectMenu);
////////////////////////////////////////////////////////////////////////////////

        // Меню о программе
        Action aboutAction = new AbstractAction("О программе") {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Выподнила: Фицай Инна\n\nОдесса 2014");
            }
        };
        JMenuItem aboutMenu = new JMenuItem(aboutAction);
        fileMenu3.add(aboutMenu);
////////////////////////////////////////////////////////////////////////////////

        // Меню файл
        Action loadAction = new AbstractAction("Загрузить") {
            @Override
            public void actionPerformed(ActionEvent event) {
                JFileChooser jf = new JFileChooser();
                int result = jf.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        //при выборе изображения подстраиваем размеры формы и панели под размеры данного изображения
                        fileName = jf.getSelectedFile().getAbsolutePath();
                        File iF = new File(fileName);
                        jf.addChoosableFileFilter(new Filter(".png"));
                        jf.addChoosableFileFilter(new Filter(".jpg"));
                        imag = ImageIO.read(iF);
                        loading = true;
                        progWindow.setSize(imag.getWidth() + 40, imag.getWidth() + 80);
                        pai.setSize(imag.getWidth(), imag.getWidth());
                        pai.repaint();
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(progWindow, "Файл не существует");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(progWindow, "Ошибка ввода-вывода");
                    }
                }
            }
        };
        JMenuItem loadMenu = new JMenuItem(loadAction);
        fileMenu.add(loadMenu);

        Action saveAction = new AbstractAction("Сохранить") {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser jf = new JFileChooser();
                    // Создаем фильтры  файлов
                    Filter pngFilter = new Filter(".png");
                    Filter jpgFilter = new Filter(".jpg");
                    if (fileName == null) {
                        // Добавляем фильтры
                        jf.addChoosableFileFilter(pngFilter);
                        jf.addChoosableFileFilter(jpgFilter);
                        int result = jf.showSaveDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            fileName = jf.getSelectedFile().getAbsolutePath();
                        }
                    }
                    // Смотрим какой фильтр выбран
                    if (jf.getFileFilter() == pngFilter) {
                        ImageIO.write(imag, "png", new File(fileName + ".png"));
                    } else {
                        ImageIO.write(imag, "jpeg", new File(fileName + ".jpg"));
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(progWindow, "Ошибка ввода-вывода");
                }
            }
        };
        JMenuItem saveMenu = new JMenuItem(saveAction);
        fileMenu.add(saveMenu);

        Action saveasAction = new AbstractAction("Сохранить как...") {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser jf = new JFileChooser();
                    // Создаем фильтры для файлов
                    Filter pngFilter = new Filter(".png");
                    Filter jpgFilter = new Filter(".jpg");
                    // Добавляем фильтры
                    jf.addChoosableFileFilter(pngFilter);
                    jf.addChoosableFileFilter(jpgFilter);
                    int result = jf.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        fileName = jf.getSelectedFile().getAbsolutePath();
                    }
                    // Смотрим какой фильтр выбран
                    if (jf.getFileFilter() == pngFilter) {
                        ImageIO.write(imag, "png", new File(fileName + ".png"));
                    } else {
                        ImageIO.write(imag, "jpeg", new File(fileName + ".jpg"));
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(progWindow, "Ошибка ввода-вывода");
                }
            }
        };
        JMenuItem saveasMenu = new JMenuItem(saveasAction);
        fileMenu.add(saveasMenu);

        pai = new Paint();
        pai.setBounds(30, 30, 260, 260);
        pai.setBackground(java.awt.Color.white);
        pai.setOpaque(true);
        progWindow.add(pai);

        // Тулбар для кнопок
        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);

        JButton penbutton = new JButton(new ImageIcon("Img/pen.png"));
        penbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 0;
            }
        });
        toolbar.add(penbutton);
        JButton brushbutton = new JButton(new ImageIcon("Img/brush.png"));
        brushbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 1;
            }
        });
        toolbar.add(brushbutton);

        JButton lasticbutton = new JButton(new ImageIcon("Img/lastic.png"));
        lasticbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 2;
            }
        });
        toolbar.add(lasticbutton);

        JButton textbutton = new JButton(new ImageIcon("Img/text.png"));
        textbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 3;

            }
        });
        toolbar.add(textbutton);

        JButton linebutton = new JButton(new ImageIcon("Img/line.png"));
        linebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 4;
            }
        });
        toolbar.add(linebutton);

        JButton elipsbutton = new JButton(new ImageIcon("Img/elips.png"));
        elipsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 5;
            }
        });
        toolbar.add(elipsbutton);

        JButton rectbutton = new JButton(new ImageIcon("Img/rect.png"));
        rectbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mode = 6;
            }
        });
        toolbar.add(rectbutton);

        toolbar.setBounds(0, 0, 30, 300);
        progWindow.add(toolbar);

        // Тулбар для кнопок
        JToolBar colorbar = new JToolBar("Colorbar", JToolBar.HORIZONTAL);
        colorbar.setBounds(30, 0, 50, 30);
        colorbutton = new JButton();
        colorbutton.setBackground(maincolor);
        colorbutton.setBounds(15, 5, 20, 20);
        colorbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                maincolor = JColorChooser.showDialog(null, "Выберите цвет", maincolor);
                colorbutton.setBackground(maincolor);
            }
        });

        colorbar.add(colorbutton);

        colorbar.setLayout(null);
        progWindow.add(colorbar);

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

//                    String str = "";
                    String str = JOptionPane.showInputDialog(null, "Введите строку", "Ввод строки", JOptionPane.QUESTION_MESSAGE);
//                    str += e.getKeyChar();
                    g2.setFont(new Font("Arial", 0, 15));
                    if (str != null) {
                        g2.drawString(str, xPad, yPad);
                        xPad += 10;
                    // устанавливаем фокус для панели,
                        // чтобы печатать на ней текст
                        pai.requestFocus();
                        pai.repaint();
                    }
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
        progWindow.setLocationRelativeTo(null);

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
