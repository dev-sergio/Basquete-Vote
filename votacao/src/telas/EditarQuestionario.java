package telas;

import banco.DAO;
import encapsulamentos.Candidatos;
import encapsulamentos.NovasPerguntas;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import metodos.ResultSetTableModel;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class EditarQuestionario extends javax.swing.JDialog {

    javax.swing.JScrollPane scroll;
    javax.swing.JTable tabela;
    Cadastro painelCadastro;
    ArrayList<NovasPerguntas> arrayNovo = new ArrayList();
    ArrayList<Candidatos> arrayCandidatos = new ArrayList();

    /**
     * Creates new form Editar
     *
     * @param painel
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public EditarQuestionario(Cadastro painel) throws SQLException, ClassNotFoundException {
        initComponents();
        novosComponente();
        carregaTabela();
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        painelCadastro = painel;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(660, 500));
        setModal(true);
        setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        getContentPane().setLayout(null);

        pack();
    }// </editor-fold>                        

    private void carregaTabela() throws SQLException, ClassNotFoundException {
        DAO dao = new DAO();
        ResultSet rs = dao.editar();
        scroll.setViewportView(tabela);
        tabela.setModel(new ResultSetTableModel(rs));
        tabela.getColumnModel().getColumn(0).setPreferredWidth(475);
        tabela.getColumnModel().getColumn(0).setHeaderValue("PERGUNTA");
        tabela.getColumnModel().getColumn(1).setPreferredWidth(122);
        tabela.getColumnModel().getColumn(1).setHeaderValue("QTD ALTERNATIVAS");
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().resizeAndRepaint();

    }

    public void novosComponente() throws SQLException, ClassNotFoundException {
        javax.swing.JPanel painel1 = new javax.swing.JPanel();
        painel1.setLayout(null);

        javax.swing.JLabel titulo = new javax.swing.JLabel();
        titulo.setText("EDIÇÃO DAS PERGUNTAS");
        titulo.setFont(new java.awt.Font("TIMES NEW ROMAN", 1, 24));
        titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(titulo);
        titulo.setBounds(0, 10, 660, 30);

        scroll = new javax.swing.JScrollPane();

        tabela = new javax.swing.JTable();
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        painel1.add(scroll);
        painel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        getContentPane().add(painel1);
        painel1.setBounds(20, 50, 600, 340);
        scroll.setBounds(0, 0, 600, 340);

        javax.swing.JButton BtnEditar = new javax.swing.JButton();
        javax.swing.JButton BtnExcluir = new javax.swing.JButton();
        javax.swing.JButton BtnFechar = new javax.swing.JButton();

        BtnEditar.setText("EDITAR");
        BtnEditar.addActionListener((ActionEvent evt) -> {
            if (tabela.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Nenhuma pergunta selecionada");

            } else {

                try {
                    painelCadastro.limparCadastro();

                    String texto = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();

                    painelCadastro.caixa.setText(texto);
                    DAO dao = new DAO();
                    int id_pergunta = dao.retornaIdPergunta(texto);
                    arrayNovo = dao.editando(id_pergunta);

                    arrayNovo.forEach((NovasPerguntas cand) -> {
                        Candidatos candidatos = new Candidatos();
                        candidatos.setIndexCand(cand.getId_alternativa());
                        candidatos.setCandidato(cand.getAlternativa());
                        arrayCandidatos.add(candidatos);
                    });

                    painelCadastro.arrayCandidatos = arrayCandidatos;
                    painelCadastro.carregaAlternativas(false);
                    painelCadastro.perguntaVinda = texto;
                    if (!dao.isMultipla(texto)) {
                        painelCadastro.checkMultipla.setSelected(false);
                    } else {
                        painelCadastro.checkMultipla.setSelected(true);
                        painelCadastro.qtdMultipla.setEnabled(true);
                        painelCadastro.qtdMultipla.setText(String.valueOf(dao.qntMultipla(texto)));
                    }

                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(EditarQuestionario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        BtnExcluir.setText("EXCLUIR");

        BtnFechar.setText("FECHAR");
        BtnFechar.addActionListener((ActionEvent e) -> {
            dispose();
        });

        getContentPane().add(BtnExcluir);
        getContentPane().add(BtnEditar);
        getContentPane().add(BtnFechar);
        BtnExcluir.setBounds(20, 400, 90, 40);
        BtnEditar.setBounds(270, 400, 120, 40);
        BtnFechar.setBounds(500, 400, 90, 40);

    }                  
}
