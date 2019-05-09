package main;

import banco.DAO;
import telas.Principal;
import telas.login;
import telas.telaFundo;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Main {

    public static void main(String arg[]) throws Exception {

        DAO iniciar = new DAO();
        if (iniciar.iniciar() == 0) {
            Principal principal = new Principal();
            principal.setVisible(true);
        } else {
            telaFundo tela = new telaFundo();
            tela.setVisible(true);
            
            login log = new login(tela);
            log.setVisible(true);
        }
    }
}