package telas;

import banco.DAO;
import encapsulamentos.Questionario;
import encapsulamentos.Respostas;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Apuracao extends javax.swing.JFrame {

    JTabbedPane tabsResp = new javax.swing.JTabbedPane();
    private ArrayList<Questionario> Perguntas = new ArrayList();
    private ArrayList<Respostas> Alternativas = new ArrayList();
    private final ArrayList<JPanel> panel = new ArrayList();
    ArrayList<javax.swing.JPanel> painelPerguntas = new ArrayList();
    ArrayList<String> resposta = new ArrayList();
    ArrayList<String> votosFinais = new ArrayList();
    int contPergunta;
    int alturaResposta = 70;
    int espacamentoResposta = 30;
    int colocacao;
    int alturaSeparador = 93;
    int espacamentoSeparador = 20;

    /**
     * @throws java.sql.SQLException
     */
    public Apuracao() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().add(tabsResp);
        carregaAbas();
    }

    private void carregaAbas() throws SQLException {
        painelPerguntas = carregaPergunta();
        int cont = 0;
        while (cont < painelPerguntas.size()) {
            tabsResp.addTab(String.valueOf(cont + 1), painelPerguntas.get(cont));
            cont++;
        }
        jPanel1.add(tabsResp);
        tabsResp.setBounds(0, 0, 1200, 600);
    }

    public ArrayList<JPanel> carregaPergunta() throws SQLException {
        
        DAO pega = new DAO();
        contPergunta = 0;
        Perguntas = pega.questionario();
        Perguntas.forEach((Questionario questao) -> {
            JPanel PanelPergunta = new javax.swing.JPanel();
            JLabel txtPergunta = new javax.swing.JLabel();
            PanelPergunta.setLayout(null);
            txtPergunta.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
            txtPergunta.setText(questao.getPergunta());
            PanelPergunta.add(txtPergunta);
            txtPergunta.setBounds(5, 5, 900, 30);

            try {
                
                Alternativas = pega.apuracao(pega.retornaIntQuestao(contPergunta));
                Alternativas.forEach((Respostas alternativa) -> {
                    colocacao = 1;
                    resposta = alternativa.getVotados();
                    resposta.forEach((String resp) -> {
                        if (colocacao <= 5) {

                            javax.swing.JLabel votado = new JLabel();
                            votado.setText(colocacao + "º) " + resp);
                            votado.setFont(new java.awt.Font("Times New Roman", 1, 14)); //NOI18N
                            votado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                            PanelPergunta.add(votado);
                            votado.setBounds(espacamentoResposta, alturaResposta, 260, 23);
                            colocacao++;
                            javax.swing.JSeparator separadorH = new JSeparator();
                            PanelPergunta.add(separadorH);
                            separadorH.setBounds(espacamentoSeparador, alturaSeparador, 400, 50);
                            alturaResposta += 30;
                            alturaSeparador += 30;
                        }
                    });

                    votosFinais = alternativa.getRespostas();
                    alturaResposta = 70;
                    alturaSeparador = 93;
                    colocacao = 1;
                    votosFinais.forEach((String votoFinal) -> {
                        if (colocacao <= 10) {

                            javax.swing.JLabel votos = new JLabel();
                            votos.setText(votoFinal + " Votos");
                            votos.setFont(new java.awt.Font("Times New Roman", 1, 14)); //NOI18N
                            votos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                            PanelPergunta.add(votos);
                            votos.setBounds(espacamentoResposta + 280, alturaResposta, 300, 23);
                            alturaResposta += 30;
                            alturaSeparador += 30;
                            colocacao++;
                        }
                    });

                    alturaResposta = 70;
                    alturaSeparador = 93;

                });

            } catch (SQLException ex) {
                Logger.getLogger(Apuracao.class.getName()).log(Level.SEVERE, null, ex);
            }

            javax.swing.JLabel lblClassificao = new JLabel();
            lblClassificao.setText("NOME");
            lblClassificao.setFont(new java.awt.Font("Times New Roman", 1, 15)); //NOI18N
            PanelPergunta.add(lblClassificao);
            lblClassificao.setBounds(130, 40, 93, 24);

            javax.swing.JLabel lblVotos = new JLabel();
            lblVotos.setText("VOTOS");
            lblVotos.setFont(new java.awt.Font("Times New Roman", 1, 15)); //NOI18N
            PanelPergunta.add(lblVotos);
            lblVotos.setBounds(340, 40, 93, 24);

            javax.swing.JSeparator separadorV = new JSeparator(1);
            PanelPergunta.add(separadorV);
            separadorV.setBounds(300, 50, 50, 680);

            panel.add(PanelPergunta);
            contPergunta++;

        });
        return panel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        jButton1.setText("SAIR");
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton1ActionPerformed(evt);
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(1240, 5, 120, 50);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("APURAÇÃO DOS VOTOS");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 10, 1240, 80);

        jPanel1.setLayout(null);
        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 90, 1200, 620);

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        DAO iniciar = new DAO();
        try {
            iniciar.iniciar(0);
            Principal principal = new Principal();
            principal.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Apuracao.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }                                        

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration                   
}
