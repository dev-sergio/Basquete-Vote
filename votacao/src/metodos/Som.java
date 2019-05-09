package metodos;

import java.io.IOException;
import java.io.InputStream;  
  
import javax.sound.sampled.AudioFormat;  
import javax.sound.sampled.AudioInputStream;  
import javax.sound.sampled.AudioSystem;  
import javax.sound.sampled.Clip;  
import javax.sound.sampled.DataLine;  
import javax.sound.sampled.LineEvent;  
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */

  
public class Som {  
  
    // Toca um som  
    public void tocar(final InputStream arquivo, final boolean repetir) {  
        try {  
            // Obtém os dados sonoros  
            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);  
  
            // Carrega o formato do audio e cria uma linha  
            final AudioFormat audioFormat = audioInputStream.getFormat();  
            final DataLine.Info dataLineInfo = new DataLine.Info(Clip.class,  
                                                                 audioInputStream.getFormat(),  
                                                                 ((int) audioInputStream.getFrameLength() * audioFormat.getFrameSize()));  
  
            // Carrega o som para o dispositivo  
            final Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);  
            clip.addLineListener((final LineEvent e) -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    e.getLine().close();
                }
            } // Evento do LineListener
            );  
            clip.open(audioInputStream);  
  
            // Toca o som  
            if (repetir) {  
                clip.loop(Clip.LOOP_CONTINUOUSLY);  
            } else {  
                clip.loop(0);  
            }  
        } catch (final IOException | LineUnavailableException | UnsupportedAudioFileException e) {  
        }  
    }  
} 