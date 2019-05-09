package telas;

import banco.DAO;
import encapsulamentos.NovasPerguntas;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import encapsulamentos.Candidatos;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import metodos.JtextFieldSomenteNumeros;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Cadastro extends javax.swing.JFrame {

    JTextField caixa = new JTextField();
    JTextField qtdMultipla = new JtextFieldSomenteNumeros();
    ArrayList<NovasPerguntas> arrayAlternativas = new ArrayList();
    JCheckBox checkMultipla = new JCheckBox();
    JPanel panel = new javax.swing.JPanel();
    JPanel painelAlter;
    JComboBox<String> combo = new JComboBox();
    int yCheck = 10;
    int xCheck = 10;
    boolean salvar = true;
    String perguntaVinda;
    int Multipla = 0;

    ArrayList<Candidatos> arrayCandidatos = new ArrayList();
    ArrayList<Candidatos> arrayCandidatosNovo = new ArrayList();
    ArrayList<String> arrayGrupos = new ArrayList();

    String[] opcao = new String[1];
    String[] opcaoSelecionada;
    int idQuestao;

    public Cadastro() throws SQLException, ClassNotFoundException {
        initComponents();

        this.setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        caixa.setFont(new java.awt.Font("Times New Roman", 1, 24));
        caixa.setToolTipText("Digite a PERGUNTA em no maximo 96 caracteres!");
        jPanel2.add(caixa);
        caixa.setBounds(20, 50, 990, 50);

        caixa.addKeyListener(new KeyListener() {
            String textoAtual;

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String texto = caixa.getText().toUpperCase();
                if (texto.length() >= 97) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Ultrapassou a quantidade de caracteres");
                    caixa.setText(texto.substring(0, 96).toUpperCase());
                }
            }
        }
        );
        caixa.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                caixa.setText(caixa.getText().toUpperCase());
            }
        });

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.setLayout(null);

        JButton botaoNovaAlternativa = new JButton();
        botaoNovaAlternativa.setText("NOVA OPÇÃO");
        botaoNovaAlternativa.addActionListener((ActionEvent evt) -> {
            String novo = JOptionPane.showInputDialog("Digite o nome do novo candidato:");

            if (novo == null || "".equals(novo)) {
            } else {
                DAO dao = new DAO();
                try {
                    dao.salvarAlternativa(novo.toUpperCase());
                    carregaAlternativas(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        jPanel2.add(botaoNovaAlternativa);
        botaoNovaAlternativa.setBounds(1025, 50, 140, 50);

        carregaAlternativas(true);

        JButton botaoSalvar = new JButton();
        botaoSalvar.setText("SALVAR");
        botaoSalvar.addActionListener((ActionEvent evt) -> {

            int qnt = 0;
            int qntEscolhas = 1;
            if ("".equals(caixa.getText())) {
                JOptionPane.showMessageDialog(null, "Digite a pergunta da votação");
                caixa.requestFocus();
            } else if (vericaSalvar()) {
                if (arrayCandidatos.size() > 1) {
                    DAO dao = new DAO();
                    if (checkMultipla.isSelected()) {
                        Multipla = 1;
                        qntEscolhas = Integer.parseInt(qtdMultipla.getText()); //LIMITAR ENTRADA DE DADOS PARA APENAS NUMEROS
                    } else {
                        Multipla = 0;
                    }
                    if (salvar) {

                        int idGrupo = 1;

                        try {
                            idGrupo = dao.retornaIDGrupo(combo.getSelectedItem().toString());
                        } catch (SQLException ex) {
                            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        try {
                            idQuestao = dao.salvarQuestao(caixa.getText().toUpperCase(), Multipla, qntEscolhas, idGrupo);
                        } catch (SQLException ex) {
                            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        arrayCandidatos.forEach((Candidatos candidatos) -> {
                            try {

                                dao.salvarQuestionario(idQuestao, candidatos.getIndexCand());
                            } catch (SQLException ex) {
                                Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                    } else {
                        try {
                            int idGrupo = dao.retornaIDGrupo(combo.getSelectedItem().toString());
                            int id_pergunta = dao.retornaIdPergunta(perguntaVinda);
                            dao.excluirQuestao(id_pergunta);
                            dao.salvarQuestaoEditada(id_pergunta, caixa.getText().toUpperCase(), Multipla, qntEscolhas, idGrupo);

                            arrayCandidatos.forEach((Candidatos candidatos) -> {
                                try {
                                    dao.salvarQuestionario(id_pergunta, candidatos.getIndexCand());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            });
                        } catch (SQLException ex) {
                            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    JOptionPane.showMessageDialog(null, "SALVO COM SUCESSO!!");
                    try {
                        limparCadastro();
                    } catch (SQLException ex) {
                        Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Selecione ao menos 2 opções");
                }

            }

        });

        jPanel1.add(botaoSalvar);
        botaoSalvar.setBounds(1210, 30, 140, 60);

        jPanel2.add(panel);
        panel.setBounds(20, 150, 1140, 510);

        JButton editarPergunta = new JButton();
        editarPergunta.setText("<html><center>EDITAR<br> PERGUNTA");
        editarPergunta.addActionListener((ActionEvent evt) -> {
            EditarQuestionario edit;
            try {
                edit = new EditarQuestionario(this);
                edit.setVisible(true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        jPanel1.add(editarPergunta);
        editarPergunta.setBounds(1210, 280, 140, 60);

        JButton editaOpcao = new JButton();
        editaOpcao.setText("<html><center>EDITAR<br> OPÇÕES");
        jPanel1.add(editaOpcao);
        editaOpcao.setBounds(1210, 350, 140, 60);

        editaOpcao.addActionListener((ActionEvent evt) -> {
            try {
                EditarOpcoes eo = new EditarOpcoes(this);
                eo.setVisible(true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        JButton sair = new JButton();
        sair.setText("SAIR");
        sair.addActionListener((ActionEvent act) -> {
            dispose();
            Principal principal = new Principal();
            principal.setVisible(true);
            DAO dao = new DAO();
            try {
                dao.iniciar(0);
            } catch (SQLException ex) {
                Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jPanel1.add(sair);
        sair.setBounds(1210, 650, 140, 60);

        checkMultipla.setText("MULTIPLA ESCOLHA");
        jPanel2.add(checkMultipla);
        checkMultipla.setBounds(230, 120, 150, 23);

        JLabel lblQtdEscolhas = new JLabel();
        lblQtdEscolhas.setText("<html><center>QUANTIDADE DE <br> ESCOLHAS");
        jPanel2.add(lblQtdEscolhas);
        lblQtdEscolhas.setBounds(430, 115, 100, 30);
        qtdMultipla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        qtdMultipla.setFont(new java.awt.Font("Times New Romam", 1, 15));
        jPanel2.add(qtdMultipla);
        qtdMultipla.setBounds(530, 115, 50, 30);
        qtdMultipla.setEnabled(false);

        checkMultipla.addActionListener((ActionEvent event) -> {
            JCheckBox cb = (JCheckBox) event.getSource();
            if (cb.isSelected()) {
                qtdMultipla.setEnabled(true);
            } else {
                qtdMultipla.setEnabled(false);
            }
        });

        JLabel lblCombo = new JLabel();
        lblCombo.setText("<html><center>GRUPO DAS <br>ALTERNATIVAS");
        jPanel2.add(lblCombo);
        lblCombo.setBounds(630, 115, 100, 30);

        jPanel2.add(combo);
        combo.setBounds(720, 120, 160, 20);

        JButton botaoCombo = new JButton();
        botaoCombo.setText("ADD GRUPO");
        jPanel2.add(botaoCombo);
        botaoCombo.setBounds(895, 115, 110, 30);

        JButton editaGrupo = new JButton();
        editaGrupo.setText("EDITAR GRUPO");
        editaGrupo.setFont(new java.awt.Font("Times New Romam", 1, 10));
        jPanel2.add(editaGrupo);
        editaGrupo.setBounds(1015, 115, 110, 25);

        editaGrupo.addActionListener((ActionEvent evt) -> {
            EditarGrupos EG;
            try {
                EG = new EditarGrupos(this);
                EG.setVisible(true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        combo.removeAllItems();
        arrayGrupos.clear();

        DAO dao = new DAO();
        try {
            arrayGrupos = dao.retornaGrupos();
        } catch (SQLException ex) {
            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
        arrayGrupos.forEach((String teste) -> {
            combo.addItem(teste);
        });

        botaoCombo.addActionListener((ActionEvent evt) -> {
            String novoGrupo;
            novoGrupo = JOptionPane.showInputDialog("Digite o nome do novo candidato:");
            if (novoGrupo == null || "".equals(novoGrupo)) {
            } else {
                combo.removeAllItems();
                arrayGrupos.clear();
                try {
                    dao.addGrupo(novoGrupo.toUpperCase());
                } catch (SQLException ex) {
                    Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
                }
                atualizaCombo();
            }
        });

        DAO dao2 = new DAO();
        boolean permitir = dao2.isEditavel();
        if (!permitir) {
            editaOpcao.setEnabled(false);
            editarPergunta.setEnabled(false);
            editaGrupo.setEnabled(false);
            editaOpcao.setToolTipText("NÃO É PERMITIDO EDITAR QUANDO JÁ HÁ RESPOSTAS");
            editarPergunta.setToolTipText("NÃO É PERMITIDO EDITAR QUANDO JÁ HÁ RESPOSTAS");
            editaGrupo.setToolTipText("NÃO É PERMITIDO EDITAR QUANDO JÁ HÁ RESPOSTAS");
        } else {
            editaOpcao.setEnabled(true);
            editarPergunta.setEnabled(true);
        }

    }

    public void atualizaCombo() {
        combo.removeAllItems();
        arrayGrupos.clear();
        DAO dao = new DAO();

        try {
            arrayGrupos = dao.retornaGrupos();
        } catch (SQLException ex) {
            Logger.getLogger(Cadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
        arrayGrupos.forEach((String teste) -> {
            combo.addItem(teste.toUpperCase());
        });
    }

    private boolean vericaSalvar() {
        if (combo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um grupo");
            combo.requestFocus();
            return false;
        } else if (checkMultipla.isSelected()) {
            if (qtdMultipla.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Digite a quantidade de escolhas possiveis");
                qtdMultipla.requestFocus();
                return false;
            }
        }

        return true;

    }

    public void carregaAlternativas(boolean salvarAlter) throws SQLException {
        salvar = salvarAlter;

        panel.removeAll();
        panel.repaint();
        yCheck = 10;
        xCheck = 10;
        DAO dao = new DAO();
        arrayAlternativas = dao.alternativas();
        if (arrayAlternativas.size() > 0) {
            arrayCandidatosNovo = (ArrayList) arrayCandidatos.clone();

            arrayAlternativas.forEach((NovasPerguntas alternativas) -> {
                JCheckBox check = new JCheckBox();

                check.setText(alternativas.getAlternativa());
                check.setFont(new java.awt.Font("Times New Roman", 1, 14));

//===================================================================================================================//
                // DEIXA TUDO COLORIDO.. BEM GAY
//            Random rnd = new Random();            
//            check.setBackground(new java.awt.Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
//            check.setForeground(new java.awt.Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
//==================================================================================================================//
                panel.add(check);
                check.setBounds(xCheck, yCheck, 230, 25);
                yCheck += 25;
                if (yCheck > 480) {
                    xCheck += 280;
                    yCheck = 10;
                }

                check.addActionListener((ActionEvent event) -> {
                    JCheckBox cb = (JCheckBox) event.getSource();
                    if (cb.isSelected()) {
                        Candidatos candidatos = new Candidatos();
                        candidatos.setIndexCand(alternativas.getId_alternativa());
                        candidatos.setCandidato(cb.getText());
                        arrayCandidatos.add(candidatos);
                    } else {
                        int index = 9999;
                        for (Candidatos item : arrayCandidatos) {
                            if (item.getCandidato().equalsIgnoreCase(cb.getText())) {
                                index = arrayCandidatos.indexOf(item);
                            }
                        }
                        arrayCandidatos.remove(index);
                    }
                });
                if (arrayCandidatosNovo.size() > 0) {
                    arrayCandidatosNovo.forEach((Candidatos candidatosNovo) -> {
                        if (candidatosNovo.getCandidato().equalsIgnoreCase(alternativas.getAlternativa())) {
                            check.setSelected(true);
                        }
                    });
                }
            });
        }
    }

    public void limparCadastro() throws SQLException {
        caixa.setText("");
        caixa.requestFocus();
        arrayCandidatos.clear();
        arrayCandidatosNovo.clear();
        qtdMultipla.setText("");
        qtdMultipla.setEnabled(false);
        checkMultipla.setSelected(false);
        carregaAlternativas(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(600, 700));
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CADASTRO QUESTIONÁRIO");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 10, 1360, 50);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("ALTERNATIVAS:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 120, 170, 22);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("NOVA PERGUNTA:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 20, 610, 22);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(20, 60, 1180, 690);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1366, 768);

        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration                   
}
