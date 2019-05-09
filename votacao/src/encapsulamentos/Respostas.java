package encapsulamentos;

import java.util.ArrayList;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Respostas {

    public ArrayList getRespostas() {
        return respostas;
    }

    public void setRespostas(String respostas) {
        this.respostas.add(respostas);
    }

    public ArrayList getVotados() {
        return votados;
    }
    
    public void setVotados(String votados) {
        this.votados.add(votados);
    }
    private final ArrayList respostas = new ArrayList();
    private final ArrayList votados = new ArrayList();

}
