package Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Resources {

    public Clip clip;
    public String ruta;
    public int random=1999999999;
    public int microSeg;

    public Resources() {
    }

    
    
    public void Audio(String Ruta, String audio, String Ext) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(Ruta + audio + Ext)));
            clip.start();
        } catch (Exception e) {
        }
    }
    
    public void AudioGame(String Ruta, String audio, String Ext) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(Ruta + audio + Ext)));
            clip.loop(random);
            clip.start();
        } catch (Exception e) {
        }
    }
    
    
    
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}
