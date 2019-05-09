package encapsulamentos;

import java.util.ArrayList;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Respondendo {

    /**
     * @return the qntAlternativas
     */
    public int getQntAlternativas() {
        return qntAlternativas;
    }

    /**
     * @param qntAlternativas the qntAlternativas to set
     */
    public void setQntAlternativas(int qntAlternativas) {
        this.qntAlternativas = qntAlternativas;
    }

    /**
     * @return the escolha
     */
    public ArrayList<String> getEscolha() {
        return escolha;
    }

    /**
     * @param escolha the escolha to set
     */
    public void setEscolha(String escolha) {
        this.escolha.add(escolha);
    }

    /**
     * @return the idPergunta
     */
    public int getIdPergunta() {
        return idPergunta;
    }

    /**
     * @param idPergunta the idPergunta to set
     */
    public void setIdPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
    }


    private int idPergunta;
    private int qntAlternativas;
    private final ArrayList<String> escolha = new ArrayList();
    
    
}
