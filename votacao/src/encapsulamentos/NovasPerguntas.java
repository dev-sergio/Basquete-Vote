package encapsulamentos;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class NovasPerguntas implements Comparable<NovasPerguntas> {
    
    @Override
    public int compareTo(NovasPerguntas o) {
        return getAlternativa().compareTo(o.getAlternativa());
    }

    /**
     * @return the id_alternativa
     */
    public int getId_alternativa() {
        return id_alternativa;
    }

    /**
     * @param id_alternativa the id_alternativa to set
     */
    public void setId_alternativa(int id_alternativa) {
        this.id_alternativa = id_alternativa;
    }

    /**
     * @return the alternativa
     */
    public String getAlternativa() {
        return alternativa;
    }

    /**
     * @param alternativa the alternativa to set
     */
    public void setAlternativa(String alternativa) {
        this.alternativa = alternativa;
    }
    private int id_alternativa;
    private String alternativa;
    
}
