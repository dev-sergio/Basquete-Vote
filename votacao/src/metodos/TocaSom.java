package metodos;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class TocaSom {
    
    public void finalizado() throws MalformedURLException{
        AudioClip clip = Applet.newAudioClip(new File("/sons/votou.wav").toURL());
        clip.play();
    }
}
