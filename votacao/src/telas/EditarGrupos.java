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
public class EditarGrupos extends javax.swing.JDialog {

    javax.swing.JScrollPane scroll;
    javax.swing.JTable tabela;
    Cadastro painelCadastro;
    ArrayList<NovasPerguntas> arrayNovo = new ArrayList();
    ArrayList<Candidatos> arrayCandidatos = new ArrayList();

    /**
     * Creates new form EditarOpcoes
     *
     * @param painel
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public EditarGrupos(Cadastro painel) throws SQLException, ClassNotFoundException {
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
        setMinimumSize(new java.awt.Dimension(400, 500));
        setModal(true);
        setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        setPreferredSize(new java.awt.Dimension(400, 500));
        getContentPane().setLayout(null);

        pack();
    }// </editor-fold>                        

    private void carregaTabela() throws SQLException, ClassNotFoundException {
        DAO dao = new DAO();
        ResultSet rs = dao.editarGrupo();
        scroll.setViewportView(tabela);
        tabela.setModel(new ResultSetTableModel(rs));
        tabela.getColumnModel().getColumn(0).setPreferredWidth(355);
        tabela.getColumnModel().getColumn(0).setHeaderValue("GRUPOS");
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().resizeAndRepaint();

    }

    public void novosComponente() throws SQLException, ClassNotFoundException {
        javax.swing.JPanel painel1 = new javax.swing.JPanel();
        painel1.setLayout(null);

        javax.swing.JLabel titulo = new javax.swing.JLabel();
        titulo.setText("EDIÇÃO DOS GRUPOS");
        titulo.setFont(new java.awt.Font("TIMES NEW ROMAN", 1, 24));
        titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(titulo);
        titulo.setBounds(0, 10, 400, 30);

        scroll = new javax.swing.JScrollPane();

        tabela = new javax.swing.JTable();
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        painel1.add(scroll);
        painel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        getContentPane().add(painel1);
        painel1.setBounds(20, 50, 360, 340);
        scroll.setBounds(0, 0, 360, 340);

        javax.swing.JButton BtnEditar = new javax.swing.JButton();
        javax.swing.JButton BtnExcluir = new javax.swing.JButton();
        javax.swing.JButton BtnFechar = new javax.swing.JButton();

        BtnEditar.setText("EDITAR");
        BtnEditar.addActionListener((ActionEvent evt) -> {
            if (tabela.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Nenhum grupo selecionado");

            } else if (tabela.getValueAt(tabela.getSelectedRow(), 0).toString().equals("TODOS")) {
                JOptionPane.showMessageDialog(this, "Grupo TODOS não pode ser alterado");
            } else {

                String selecionado = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                String novo = JOptionPane.showInputDialog(this, "Atualize o nome do grupo:");

                if (novo == null || "".equals(novo)) {
                } else {

                    try {
                        DAO dao = new DAO();
                        dao.updateGrupo(novo, selecionado);
                        painelCadastro.atualizaCombo();
                        dispose();
                    } catch (SQLException ex) {
                        Logger.getLogger(EditarGrupos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        BtnExcluir.setText("EXCLUIR");
        BtnExcluir.addActionListener((ActionEvent evt) -> {

            try {
                DAO dao = new DAO();
                String grupo = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                int idGrupo = dao.retornaIDGrupo(grupo);
                if (tabela.getSelectedRow() < 0) {
                    if (dao.verificaGrupos(idGrupo)) {
                        dao.excluirGrupo(idGrupo);
                        painelCadastro.atualizaCombo();
                        JOptionPane.showMessageDialog(this, "Excluido com sucesso!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Não é possivel excluir o grupo já usada em pergunta(s)");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhum grupo selecionado");
                }

            } catch (SQLException ex) {
                Logger.getLogger(EditarGrupos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArrayIndexOutOfBoundsException ab) {

            }
        });

        BtnFechar.setText("FECHAR");
        BtnFechar.addActionListener((ActionEvent e) -> {
            dispose();
        });

        getContentPane().add(BtnExcluir);
        getContentPane().add(BtnEditar);
        getContentPane().add(BtnFechar);
        BtnExcluir.setBounds(20, 400, 90, 40);
        BtnEditar.setBounds(130, 400, 120, 40);
        BtnFechar.setBounds(280, 400, 90, 40);

    }

}
