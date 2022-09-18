import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

class Snake extends KeyAdapter {

    Background background_for_frame = new Background();
    JPanel panel_for_main_menu = new JPanel();
    static int time_step;
    static Snake game;
    int apple_x_coordinate = 160;
    int apple_y_coordinate = 20;
    int snake_length_dots = 3;
    int[] x = new int[200];
    int[] y = new int[200];
    static BufferedImage body;
    static BufferedImage skull;
    static BufferedImage apple;
    static BufferedImage snake;
    static BufferedImage back;
    static BufferedImage turtle;
    static BufferedImage leopard;
    int dot_width = 20;
    boolean inGame = true;
    boolean direction_right = true;
    boolean direction_left = false;
    boolean direction_up = false;
    boolean direction_down = false;
    final static JFrame main_frame = getFrame();

    public Snake() {
        panel_for_main_menu.setLayout(null);
        background_for_frame.setLocation(0, 0);
        background_for_frame.setSize(400, 400);
        JButton play_button = new JButton("Play");
        JButton settings_button = new JButton("Settings");
        JButton about_button = new JButton("About");
        JButton quit_button = new JButton("Quit");
        play_button.setLocation(155, 75);
        play_button.setSize(70, 25);
        play_button.setBackground(Color.white);
        settings_button.setLocation(145, 110);
        settings_button.setSize(90, 25);
        settings_button.setBackground(Color.white);
        about_button.setLocation(155, 145);
        about_button.setSize(70, 25);
        about_button.setBackground(Color.white);
        quit_button.setLocation(155, 180);
        quit_button.setSize(70, 25);
        quit_button.setBackground(Color.white);
        panel_for_main_menu.add(play_button);
        panel_for_main_menu.add(settings_button);
        panel_for_main_menu.add(about_button);
        panel_for_main_menu.add(quit_button);
        panel_for_main_menu.add(background_for_frame);
        main_frame.add(panel_for_main_menu);
        play_button.addActionListener(e -> {
            main_frame.remove(panel_for_main_menu);
            main_frame.add(new MyComponent());
            main_frame.revalidate();
            main_frame.requestFocus();
        });
        settings_button.addActionListener(e -> {
            main_frame.remove(panel_for_main_menu);
            JButton return_button_1 = new JButton("Return");
            return_button_1.setLocation(145, 145);
            return_button_1.setSize(90, 25);
            return_button_1.setBackground(Color.white);
            JPanel panel_for_settings_menu = new JPanel();
            panel_for_settings_menu.setLayout(null);
            JSlider speed_bar = new JSlider();
            speed_bar.setSnapToTicks(true);
            speed_bar.setPaintTicks(true);
            speed_bar.setMinimum(50);
            speed_bar.setMaximum(250);
            speed_bar.setMajorTickSpacing(100);
            speed_bar.setMinorTickSpacing(20);
            speed_bar.addChangeListener(e12 -> time_step = speed_bar.getValue());
            speed_bar.setValue(time_step);
            speed_bar.setLocation(115, 70);
            speed_bar.setSize(150, 27);
            JComponent turtle = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    g.drawImage(Snake.turtle, 0, 0, 20, 20, null);
                }
            };
            JComponent leopard = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    g.drawImage(Snake.leopard, 0, 0, 30, 30, null);
                }
            };
            JComponent comment_string = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    g.setFont(new Font("Dialog", Font.BOLD, 11));
                    g.drawString("Set speed...", 10, 10);
                }
            };
            comment_string.setSize(100, 20);
            comment_string.setLocation(150, 110);
            panel_for_settings_menu.add(comment_string);
            turtle.setSize(20, 20);
            turtle.setLocation(267, 100);
            panel_for_settings_menu.add(turtle);
            leopard.setSize(30, 30);
            leopard.setLocation(80, 95);
            panel_for_settings_menu.add(leopard);
            panel_for_settings_menu.add(return_button_1);
            panel_for_settings_menu.add(speed_bar);
            panel_for_settings_menu.add(background_for_frame);
            main_frame.add(panel_for_settings_menu);
            main_frame.setVisible(true);
            main_frame.revalidate();
            return_button_1.addActionListener(e1 -> {
                main_frame.remove(panel_for_settings_menu);
                game = new Snake();
            });
        });
        about_button.addActionListener(e -> {
            main_frame.remove(panel_for_main_menu);
            JButton return_button_2 = new JButton("Return");
            return_button_2.setLocation(145, 145);
            return_button_2.setSize(90, 25);
            return_button_2.setBackground(Color.white);
            JPanel panel_for_about_menu = new JPanel();
            panel_for_about_menu.setLayout(null);
            panel_for_about_menu.add(return_button_2);
            JComponent string_about = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    g.setFont(new Font("Dialog", Font.BOLD, 11));
                    g.drawString("This is my first Java program... Further more!", 10, 10);
                }
            };
            string_about.setSize(260, 20);
            string_about.setLocation(65, 110);
            panel_for_about_menu.add(string_about);
            panel_for_about_menu.add(background_for_frame);
            main_frame.add(panel_for_about_menu);
            main_frame.revalidate();
            return_button_2.addActionListener(e1 -> {
                main_frame.remove(panel_for_about_menu);
                game = new Snake();
            });
        });
        quit_button.addActionListener(e -> System.exit(0));
        main_frame.setIconImage(snake);
        main_frame.revalidate();
        main_frame.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) && !direction_right) {
            direction_left = true;
            direction_down = false;
            direction_up = false;
        }
        if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) && !direction_left) {
            direction_right = true;
            direction_up = false;
            direction_down = false;
        }
        if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) && !direction_down) {
            direction_up = true;
            direction_left = false;
            direction_right = false;
        }
        if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) && !direction_up) {
            direction_down = true;
            direction_left = false;
            direction_right = false;
        }
    }

    public static JFrame getFrame() {
        JFrame jFrame = new JFrame("Snake");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width / 2 - 197, dimension.height / 2 - 198, 394, 397);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        return jFrame;
    }

    public static void main(String[] args) throws IOException {
        body = ImageIO.read(new File("body.png"));
        skull = ImageIO.read(new File("skull.png"));
        apple = ImageIO.read(new File("apple.png"));
        snake = ImageIO.read(new File("snake.png"));
        back = ImageIO.read(new File("back.png"));
        leopard = ImageIO.read(new File("leopard.png"));
        turtle = ImageIO.read(new File("turtle.png"));
        time_step = 100;
        game = new Snake();
    }

    class MyComponent extends JPanel {

        Timer timer;

        public MyComponent() {
            init();
            timer = new Timer(time_step, e -> {
                if (inGame) {
                    repaint();
                    move();
                    checkCollisions();
                } else {
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    timer.stop();
                    game = new Snake();
                }
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(back, 0, 0, 400, 400, null);
            if (inGame) {
                g.drawImage(apple, apple_x_coordinate, apple_y_coordinate, 20, 20, null);
                for (int i = 1; i < snake_length_dots - 1; i++) {
                    g.drawImage(body, x[i], y[i], 20, 20, null);
                }
                g.drawImage(skull, x[0], y[0], 20, 20, null);
                g.drawImage(skull, x[snake_length_dots - 1], y[snake_length_dots - 1], 20, 20, null);
                g.drawString(String.valueOf(snake_length_dots), 330, 20);
            } else {
                g.setFont(new Font("Dialog", Font.BOLD, 11));
                g.drawString("Game Over", 160, 145);
                g.drawString("Your score: " + snake_length_dots, 156, 165);
            }
        }

        public void init() {
            for (int i = 0; i < snake_length_dots; i++) {
                x[i] = 60 - i * dot_width;
                y[i] = 20;
            }
        }

        public void checkCollisions() {
            if (x[0] < 0 || x[0] > 374 || y[0] < 0 || y[0] > 357) {
                inGame = false;
            }
            for (int i = snake_length_dots; i > 0; i--) {
                if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                    inGame = false;
                    break;
                }
            }
            if (x[0] == apple_x_coordinate && y[0] == apple_y_coordinate) {
                ++snake_length_dots;
                createApple();
            }
        }

        public void move() {
            for (int i = snake_length_dots; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }
            if (direction_left) {
                x[0] -= dot_width;
            }
            if (direction_right) {
                x[0] += dot_width;
            }
            if (direction_up) {
                y[0] -= dot_width;
            }
            if (direction_down) {
                y[0] += dot_width;
            }
        }

        public void createApple() {
            apple_x_coordinate = new Random().nextInt(19) * dot_width;
            apple_y_coordinate = new Random().nextInt(18) * dot_width;
        }
    }

    static class Background extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(back, 0, 0, 400, 400, null);
        }
    }
}