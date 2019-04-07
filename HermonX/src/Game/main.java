package Game;

import javax.swing.JOptionPane;

/**
 *
 * @author Nizzy
 */
public class main {
    
    public static void main(String[] args) {
        
        String Jugador;
        
        Jugador = JOptionPane.showInputDialog(null, "Digite el Nombre del jugador: ");
        
        PanelJuego Game=new PanelJuego();
        Game.Name(Jugador);
        Resources Sound=new Resources();
        Sound.Audio("/Audios/", "pacman_beginning", ".wav");
        Sound.AudioGame("/Audios/", "pacman_chomp", ".wav");
    }
}
