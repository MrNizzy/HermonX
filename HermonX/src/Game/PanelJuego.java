package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Nizzy
 */
public class PanelJuego extends JFrame implements KeyListener {

    //Variables
    private int X = 0;
    private int Y = 50;
    private int OrientacionGhost1, OrientacionGhost2,OrientacionGhost3;
    private int movimientos = 20;
    private String NPlayer;
    private final Image Favicon;
    private final Image Player;
    private final Image Ghost_1;
    private final Image Ghost_2;
    private final Image Ghost_3;
    private final Image Bomb;
    private final Image Fruit;
    private final Resources resources;
    private int XG1 = 0, YG1 = 50;
    private int XG2 = 0, YG2 = 50;
    private int XG3 = 0, YG3 = 50;
    private int XF = 0, YF = 50;
    Timer timer;
    private int Tiempo, TimeOut = 15, TiempoF = 0, A = 0;

    public PanelJuego() {

        resources = new Resources();
        resources.setRuta("/Images/");
        Player = new ImageIcon(getClass().getResource("/Images/Player.png")).getImage();
        Favicon = new ImageIcon(getClass().getResource("/Images/icon.png")).getImage();
        Ghost_1 = new ImageIcon(getClass().getResource(resources.getRuta() + "Ghost_1.png")).getImage();
        Ghost_2 = new ImageIcon(getClass().getResource(resources.getRuta() + "Ghost_2.png")).getImage();
        Ghost_3 = new ImageIcon(getClass().getResource(resources.getRuta() + "Ghost_3.png")).getImage();
        Bomb = new ImageIcon(getClass().getResource(resources.getRuta() + "bomb.png")).getImage();
        Fruit = new ImageIcon(getClass().getResource(resources.getRuta() + "fruit.png")).getImage();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hermon-X");
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null); // Centrar ventana
        setResizable(false);
        setIconImage(Favicon);
        //Creación de Objetos
        addKeyListener(this);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tiempo++;
                repaint();
                if (TimeOut > 0) {
                    TimeOut--;
                } else {
                    TimeOut = 0;
                }

                //System.out.println("Tiempo Vivo: " + Tiempo + "\nTiempo Para morir: " + TimeOut);
                repaint();
            }
        });
        timer.start();

        //Posiciones de Los Fantasmas Aleatorios
        //Ghost_1
        XG1 = XG1 + (RandomX() * 50);
        YG1 = YG1 + (RandomY() * 50);
        //Ghost_2
        XG2 = XG2 + (RandomX() * 50);
        YG2 = YG2 + (RandomY() * 50);
        //Ghost_3
        XG3 = XG3 + (RandomX() * 50);
        YG3 = YG3 + (RandomY() * 50);
        //Fruit
        XF = XF + (RandomX() * 50);
        YF = YF + (RandomY() * 50);

    }

    public int RandomP() {
        return (int) (Math.random() * 4) + 1;
    }

    public int RandomX() {
        return (int) (Math.random() * 10);
    }

    public int RandomY() {
        return (int) (Math.random() * 8) + 1;
    }

    @Override
    public void paint(Graphics g) {

        //fondo
        g.setColor(Color.black);
        g.fillRect(0, 0, 500, 500);
        g.setColor(Color.white);
        g.setFont(new Font("DFGothic-EB", Font.PLAIN, 20));
        g.drawString("Tiempo: " + Tiempo, 30, 45);
        g.drawString("Mueres en: " + TimeOut, 350, 45);
        g.setColor(Color.decode("#ffff33"));
        g.drawString("Movimientos: " + movimientos, 150, 45);

        if (X == XF && Y == YF) {
            movimientos = movimientos + 10;

            //Evita que La Fruta salga encima de Una Bomba
            //Fruit
            XF = (XF = 0) + (RandomX() * 50);
            YF = (YF = 50) + (RandomY() * 50);
            while ((XF == 400 && YF == 200) || (XF == 100 && YF == 350)) {
                XF = (XF = 0) + (RandomX() * 50);
                YF = (YF = 50) + (RandomY() * 50);

            }
            repaint();
        }

        //Verifica si la intercepcion entre el Jugador y algún fantasma o Mina colisionó, de ser así se termina el juego
        if (TimeOut == 0 || ((X == XG1) && (Y == YG1)) || ((X == XG2) && (Y == YG2))|| ((X == XG3) && (Y == YG3)) || ((X == 400) && (Y == 200)) || ((X == 100) && (Y == 350)) || (movimientos == 0)) {
            g.setFont(new Font("DFGothic-EB", Font.PLAIN, 60));
            resources.Audio("/Audios/", "pacman_death", ".wav");
            g.drawString("GAME OVER! ", 100, 300);
            TiempoF = Tiempo;
            A = 1;
            EscribirPuntaje();
        }

        g.drawImage(Player, X, Y, null);
        g.drawImage(Ghost_1, XG1, YG1, null);
        g.drawImage(Ghost_2, XG2, YG2, null);
        g.drawImage(Ghost_3, XG3, YG3, null);
        g.drawImage(Bomb, 400, 200, null);
        g.drawImage(Bomb, 100, 350, null);
        g.drawImage(Fruit, XF, YF, null);

        
        //Lineas Verticales
        g.drawLine(50, 50, 50, 500);
        g.drawLine(100, 50, 100, 500);
        g.drawLine(150, 50, 150, 500);
        g.drawLine(200, 50, 200, 500);
        g.drawLine(250, 50, 250, 500);
        g.drawLine(300, 50, 300, 500);
        g.drawLine(350, 50, 350, 500);
        g.drawLine(400, 50, 400, 500);
        g.drawLine(450, 50, 450, 500);
        //Lineas Horizontales
        g.drawLine(0, 50, 500, 50);
        g.drawLine(0, 100, 500, 100);
        g.drawLine(0, 150, 500, 150);
        g.drawLine(0, 200, 500, 200);
        g.drawLine(0, 250, 500, 250);
        g.drawLine(0, 300, 500, 300);
        g.drawLine(0, 350, 500, 350);
        g.drawLine(0, 400, 500, 400);
        g.drawLine(0, 450, 500, 450);

        //Termina el juego si se acaba el tiempo
        if (TimeOut == 0 && A == 0) {
            TiempoF = Tiempo;
            A = 1;
            EscribirPuntaje();

        }
        //Cierra la ventana después de 3 seg
        if (A == 1) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {

            }
            dispose();
        }

    }

    public void EscribirPuntaje() {
        try {
            FileWriter fstream = new FileWriter("Puntajes.txt", true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(NPlayer + ": " + TiempoF+" seg");
            out.newLine();
            out.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void PosicionXadd() {
        X = X + 50;
    }

    public void PosicionXsub() {
        X = X - 50;
    }

    public void PosicionYadd() {
        Y = Y + 50;
    }

    public void PosicionYsub() {
        Y = Y - 50;
    }

    public void Name(String a) {
        NPlayer = a;
    }

    public int PosicionGXadd(int a) {
        a = a + 50;
        return a;
    }

    public int PosicionGXsub(int a) {
        a = a - 50;
        return a;
    }

    public int PosicionGYadd(int a) {
        a = a + 50;
        return a;
    }

    public int PosicionGYsub(int a) {
        a = a - 50;
        return a;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        TimeOut = 15;
        movimientos--;
        switch (e.getExtendedKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (X == 50) {
                    X = 0;
                    repaint();
                } else if (X > 50) {
                    PosicionXsub();
                    repaint();
                } else {
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (X == 450) {
                    X = 450;
                    repaint();
                } else if (X < 450) {
                    PosicionXadd();
                    repaint();
                }
                break;
            case KeyEvent.VK_UP:
                if (Y == 50) {
                    Y = 50;
                    repaint();
                } else if (Y >= 50) {
                    PosicionYsub();
                    repaint();
                } else {
                }
                break;
            case KeyEvent.VK_DOWN:
                if (Y == 450) {
                    Y = 450;
                    repaint();
                } else if (Y >= 50) {
                    PosicionYadd();
                    repaint();
                } else {
                }
                break;
            default:
                break;
        }

        OrientacionGhost1 = RandomP();
        OrientacionGhost2 = RandomP();
        OrientacionGhost3 = RandomP();

        //GHOST_1
        if (OrientacionGhost1 == 1) {
            if (XG1 == 50) {
                XG1 = 0;
                repaint();
            } else if (XG1 > 50) {
                XG1 = XG1 - 50;
                repaint();
            }
        }
        if (OrientacionGhost1 == 2) {
            if (XG1 == 450) {
                XG1 = 450;
                repaint();
            } else if (XG1 < 450) {
                XG1 = XG1 + 50;
                repaint();
            }
        }
        if (OrientacionGhost1 == 3) {
            if (YG1 == 50) {
                YG1 = 50;
                repaint();
            } else if (YG1 >= 50) {
                YG1 = YG1 - 50;
                repaint();
            }
        }
        if (OrientacionGhost1 == 4) {
            if (YG1 == 450) {
                YG1 = 450;
                repaint();
            } else if (YG1 >= 50) {
                YG1 = YG1 + 50;
                repaint();
            }
        }

        //GHOST_2
        if (OrientacionGhost2 == 1) {
            if (XG2 == 50) {
                XG2 = 0;
                repaint();
            } else if (XG2 > 50) {
                XG2 = XG2 - 50;
                repaint();
            }
        }
        if (OrientacionGhost2 == 2) {
            if (XG2 == 450) {
                XG2 = 450;
                repaint();
            } else if (XG2 < 450) {
                XG2 = XG2 + 50;
                repaint();
            }
        }
        if (OrientacionGhost2 == 3) {
            if (YG2 == 50) {
                YG2 = 50;
                repaint();
            } else if (YG2 >= 50) {
                YG2 = YG2 - 50;
                repaint();
            }
        }
        if (OrientacionGhost2 == 4) {
            if (YG2 == 450) {
                YG2 = 450;
                repaint();
            } else if (YG2 >= 50) {
                YG2 = YG2 + 50;
                repaint();
            }
        }
        
        //GHOST_3
        if (OrientacionGhost3 == 1) {
            if (XG3 == 50) {
                XG3 = 0;
                repaint();
            } else if (XG3 > 50) {
                XG3 = XG3 - 50;
                repaint();
            }
        }
        if (OrientacionGhost3 == 2) {
            if (XG3 == 450) {
                XG3 = 450;
                repaint();
            } else if (XG3 < 450) {
                XG3 = XG3 + 50;
                repaint();
            }
        }
        if (OrientacionGhost3 == 3) {
            if (YG3 == 50) {
                YG3 = 50;
                repaint();
            } else if (YG3 >= 50) {
                YG3 = YG3 - 50;
                repaint();
            }
        }
        if (OrientacionGhost3 == 4) {
            if (YG3 == 450) {
                YG3 = 450;
                repaint();
            } else if (YG3 >= 50) {
                YG3 = YG3 + 50;
                repaint();
            }
        }

        //System.out.println("Posiciones: "+X+", "+Y);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
