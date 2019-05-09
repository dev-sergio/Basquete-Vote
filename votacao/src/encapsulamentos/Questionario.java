package encapsulamentos;

import java.util.ArrayList;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Questionario {

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

    public ArrayList getPessoas() {
        return pessoas;
    }

    public void setPessoas(ArrayList pessoas) {
        this.pessoas = pessoas;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public ArrayList getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(String alternativas) {
        
        this.alternativas.add(alternativas);
    }
    
    private String pergunta;
    private final ArrayList alternativas = new ArrayList();
    private ArrayList pessoas = new ArrayList();
    private int qntAlternativas;
}