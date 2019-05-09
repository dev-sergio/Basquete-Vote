package encapsulamentos;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Candidatos {

    /**
     * @return the indexCand
     */
    public int getIndexCand() {
        return indexCand;
    }

    /**
     * @param indexCand the index to set
     */
    public void setIndexCand(int indexCand) {
        this.indexCand = indexCand;
    }

    /**
     * @return the candidato
     */
    public String getCandidato() {
        return candidato;
    }

    /**
     * @param candidato the candidato to set
     */
    public void setCandidato(String candidato) {
        this.candidato = candidato;
    }
    private int indexCand;
    private String candidato;
}
