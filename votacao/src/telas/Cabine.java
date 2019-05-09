package telas;

import banco.DAO;
import encapsulamentos.Questionario;
import encapsulamentos.Respondendo;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import metodos.Som;
import metodos.TocaSom;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Cabine extends javax.swing.JFrame {

    JTabbedPane tabs = new javax.swing.JTabbedPane();
    private ArrayList<javax.swing.JPanel> painelPergunta = new ArrayList();
    private ArrayList<Questionario> Perguntas = new ArrayList();
    private final ArrayList<Respondendo> arrayRespondendo = new ArrayList();
    private ArrayList<String> Alternativas = new ArrayList();
    private final ArrayList<JPanel> panel = new ArrayList();
    int alturaAlternativas = 50;
    int espacamentoAlternativas = 20;
    boolean todasAlternativas = false;
    int idGrupoGeral;

    public Cabine(int idGrupo) throws SQLException {
        idGrupoGeral = idGrupo;
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        painelPergunta = carregaPergunta(idGrupo);
        iniciaQuestionario();
    }

    private void iniciaQuestionario() {
        int cont = 0;
        while (cont < painelPergunta.size()) {
            tabs.addTab(String.valueOf(cont + 1), painelPergunta.get(cont));
            cont++;
        }

        jPanel2.add(tabs);
        tabs.setBounds(10, 10, 1305, 550);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("FECHAR");
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton1ActionPerformed(evt);
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(1255, 10, 90, 40);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("VOTAÇÃO BASQUETE ROSINA 2016");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 10, 1366, 40);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);
        jPanel1.add(jPanel2);
        jPanel2.setBounds(20, 60, 1330, 580);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(null);

        jButton2.setText("ANTERIOR");
        jButton2.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton2ActionPerformed(evt);
        });
        jPanel3.add(jButton2);
        jButton2.setBounds(300, 20, 191, 64);

        jButton3.setText("PROXIMA");
        jButton3.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton3ActionPerformed(evt);
        });
        jPanel3.add(jButton3);
        jButton3.setBounds(587, 20, 191, 64);

        jButton4.setText("FINALIZAR");
        jButton4.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                jButton4ActionPerformed(evt);
            } catch (MalformedURLException | URISyntaxException ex) {
                Logger.getLogger(Cabine.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jPanel3.add(jButton4);
        jButton4.setBounds(875, 20, 191, 64);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(20, 650, 1330, 100);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1366, 768);

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*');

        JLabel rotulo = new JLabel("Entre com a senha:");

        JPanel entUsuario = new JPanel();
        entUsuario.add(rotulo);
        entUsuario.add(password);

        JOptionPane.showMessageDialog(null, entUsuario, "Acesso restrito", JOptionPane.PLAIN_MESSAGE);

        char[] senha = password.getPassword();
        String senhaString = String.valueOf(senha);

        if (senhaString.equals("fechar")) {
            DAO dao = new DAO();
            try {
                dao.iniciar(0);
                Principal principal = new Principal();
                principal.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Cabine.class.getName()).log(Level.SEVERE, null, ex);
            }
            dispose();
        }
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int totaltabs = tabs.getTabCount();
        int atualtabs = tabs.getSelectedIndex();

        if ((atualtabs + 1) < totaltabs) {
            tabs.setSelectedIndex(atualtabs + 1);
        }
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int atualtabs = tabs.getSelectedIndex();

        if (atualtabs > 0) {
            tabs.setSelectedIndex(atualtabs - 1);
        }
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) throws MalformedURLException, URISyntaxException {                                         
        DAO dao = new DAO();
        try{
            int quantidade = dao.somaRespostas(idGrupoGeral);
            int temQueSer = arrayRespondendo.size();
            
            if (quantidade == temQueSer){

                TocaSom toca = new TocaSom();
                toca.finalizado();
                Thread.sleep(1700);

                salvarRespostas();
                String comando = "java -jar " + new File("").getAbsolutePath() + "\\votacao.jar";
                Process Processo = Runtime.getRuntime().exec(comando);
                arrayRespondendo.clear();
                System.exit(0);
            }else{
                JOptionPane.showMessageDialog(this, "Nenhuma resposta pode ficar em branco");
            }
        }catch (SQLException | InterruptedException | IOException ex) {
            Logger.getLogger(Cabine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                        

    public ArrayList<JPanel> carregaPergunta(int idGrupo) throws SQLException {
        DAO pega = new DAO();

        Perguntas = pega.questionario(idGrupo);
        Perguntas.forEach((Questionario questao) -> {
            try {
                if (pega.isMultipla(questao.getPergunta())) {
                    multiplaEscolha(questao);
                } else {
                    unicaEscolha(questao);

                }
            } catch (SQLException ex) {
                Logger.getLogger(Cabine.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        return panel;
    }

    private void unicaEscolha(Questionario questao) {
        ButtonGroup grupoBotao = new javax.swing.ButtonGroup();
        JPanel PanelPergunta = new javax.swing.JPanel();
        JLabel txtPergunta = new javax.swing.JLabel();
        PanelPergunta.setLayout(null);
        txtPergunta.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
        txtPergunta.setText(questao.getPergunta());
        PanelPergunta.add(txtPergunta);
        txtPergunta.setBounds(5, 5, 1280, 30);
        Alternativas = questao.getAlternativas();
        Collections.sort(Alternativas);
        Alternativas.forEach((String alter) -> {
            JRadioButton radioAlternativa = new javax.swing.JRadioButton();
            radioAlternativa.setText(alter);

            PanelPergunta.add(radioAlternativa);
            if (alturaAlternativas > 460) {
                espacamentoAlternativas = 200;
                alturaAlternativas = 50;
            } else if (alturaAlternativas > 461) {
                espacamentoAlternativas = 450;
                alturaAlternativas = 50;
            }
            radioAlternativa.setBounds(espacamentoAlternativas, alturaAlternativas, 175, 23);

            radioAlternativa.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    Respondendo responda = new Respondendo();
                    DAO dao = new DAO();
                    try {
                        int idPergunta = dao.retornaIdPergunta(questao.getPergunta());
                        String alternativa = radioAlternativa.getText();
                        if (arrayRespondendo.size() > 0) {
                            int index = -1;
                            boolean remover = false;
                            for (Respondendo item : arrayRespondendo) {
                                if (item.getIdPergunta() == idPergunta) {
                                    index = arrayRespondendo.indexOf(item);
                                    remover = true;
                                }
                            }
                            if (remover) {
                                arrayRespondendo.remove(index);
                            }

                        }

                        responda.setIdPergunta(idPergunta);
                        responda.setEscolha(alternativa);
                        arrayRespondendo.add(responda);
                    } catch (SQLException ex) {
                        Logger.getLogger(Cabine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            alturaAlternativas = alturaAlternativas + 20;
            grupoBotao.add(radioAlternativa);
        });
        alturaAlternativas = 50;
        espacamentoAlternativas = 20;
        panel.add(PanelPergunta);
    }

    private void multiplaEscolha(Questionario questao) {

        JPanel PanelPergunta = new javax.swing.JPanel();
        JLabel txtPergunta = new javax.swing.JLabel();
        PanelPergunta.setLayout(null);
        txtPergunta.setFont(new java.awt.Font("Times New Roman", 1, 25)); // NOI18N
        txtPergunta.setText(questao.getPergunta());
        PanelPergunta.add(txtPergunta);
        txtPergunta.setBounds(5, 5, 1280, 30);
        Alternativas = questao.getAlternativas();
        Collections.sort(Alternativas);
        Alternativas.forEach((String alter) -> {
            JRadioButton radioAlternativa = new javax.swing.JRadioButton();
            radioAlternativa.setText(alter);

            PanelPergunta.add(radioAlternativa);
            if (alturaAlternativas > 460) {
                espacamentoAlternativas = 200;
                alturaAlternativas = 50;
            } else if (alturaAlternativas > 461) {
                espacamentoAlternativas = 450;
                alturaAlternativas = 50;
            }
            radioAlternativa.setBounds(espacamentoAlternativas, alturaAlternativas, 175, 23);

            radioAlternativa.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    Respondendo responda = new Respondendo();
                    DAO dao = new DAO();
                    int idPergunta = 0;
                    int contAlter = 0;
                    String alternativa = radioAlternativa.getText();
                    try {
                        idPergunta = dao.retornaIdPergunta(questao.getPergunta());

                    } catch (SQLException ex) {
                        Logger.getLogger(Cabine.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (radioAlternativa.isSelected()) {

                        for (Respondendo respondendo : arrayRespondendo) {
                            if (respondendo.getIdPergunta() == idPergunta) {
                                contAlter++;
                            }
                        }
                        if (contAlter >= questao.getQntAlternativas()) {
                            radioAlternativa.setSelected(false);
                        } else {
                            responda.setIdPergunta(idPergunta);
                            responda.setEscolha(alternativa);
                            responda.setQntAlternativas(questao.getQntAlternativas());
                            arrayRespondendo.add(responda);
                        }

                    } else {
                        contAlter--;
                        int index = -1;
                        boolean remover = false;
                        for (Respondendo item : arrayRespondendo) {

                            try {
                                idPergunta = dao.retornaIdPergunta(questao.getPergunta());
                                if (item.getIdPergunta() == idPergunta) {
                                    index = arrayRespondendo.indexOf(item);
                                    remover = true;
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Cabine.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        if (remover) {
                            arrayRespondendo.remove(index);
                        }
                    }
                }
            });

            alturaAlternativas = alturaAlternativas + 20;
        });
        alturaAlternativas = 50;
        espacamentoAlternativas = 20;
        panel.add(PanelPergunta);
    }

    private void salvarRespostas() throws SQLException {
        DAO dao = new DAO();
        arrayRespondendo.forEach((Respondendo respondendo) -> {
            respondendo.getEscolha().forEach((String resposta) -> {
                try {
                    dao.salvarRespostas(respondendo.getIdPergunta(), resposta);
                } catch (SQLException ex) {
                    Logger.getLogger(Cabine.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration                   
}
