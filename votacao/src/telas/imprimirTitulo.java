package telas;

import banco.DAO;
import encapsulamentos.Candidatos;
import encapsulamentos.NovasPerguntas;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import metodos.Imprimir;
import metodos.ResultSetTableModel;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class imprimirTitulo extends javax.swing.JDialog {

    javax.swing.JScrollPane scroll;
    javax.swing.JTable tabela;
    ArrayList<NovasPerguntas> arrayNovo = new ArrayList();
    ArrayList<Candidatos> arrayCandidatos = new ArrayList();

    public final JDialog getInstance() { // para remover (vazamento de construtor) - segurança
        return this;
    }

    /**
     * Creates new form EditarOpcoes
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public imprimirTitulo() throws SQLException, ClassNotFoundException {
        initComponents();
        novosComponente();
        carregaTabela();
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        setModal(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 370));
        setModal(true);
        setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        setPreferredSize(new java.awt.Dimension(400, 370));
        getContentPane().setLayout(null);

        pack();
    }// </editor-fold>                        

    private void carregaTabela() throws SQLException, ClassNotFoundException {
        DAO dao = new DAO();
        ResultSet rs = dao.imprimirTitulo();
        tabela.setFont(new java.awt.Font("Times New Roman", 1, 20));
        scroll.setViewportView(tabela);
        tabela.setModel(new ResultSetTableModel(rs));
        tabela.getColumnModel().getColumn(0).setPreferredWidth(357);
        tabela.getColumnModel().getColumn(0).setHeaderValue("GRUPOS");
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().resizeAndRepaint();
    }

    public void novosComponente() throws SQLException, ClassNotFoundException {
        javax.swing.JPanel painel1 = new javax.swing.JPanel();
        painel1.setLayout(null);

        javax.swing.JLabel titulo = new javax.swing.JLabel();
        titulo.setText("IMPRESSÃO DE TITULO");
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
        painel1.setBounds(20, 50, 360, 200);
        scroll.setBounds(0, 0, 360, 200);

        javax.swing.JButton btnImprimir = new javax.swing.JButton();

        javax.swing.JButton BtnFechar = new javax.swing.JButton();

        btnImprimir.setText("IMPRIMIR");
        btnImprimir.addActionListener((ActionEvent evt) -> {
            if (tabela.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Nenhum grupo selecionado");
            } else {
                try {
                    imprimir();
                } catch (SQLException ex) {
                    Logger.getLogger(imprimirTitulo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        BtnFechar.setText("FECHAR");
        BtnFechar.addActionListener((ActionEvent e) -> {
            dispose();
        });

        getContentPane().add(btnImprimir);
        getContentPane().add(BtnFechar);
        btnImprimir.setBounds(200, 270, 120, 40);
        BtnFechar.setBounds(40, 270, 90, 40);
        BtnFechar.setFocusable(false);

    }

    private void imprimir() throws SQLException {
        DAO dao = new DAO();

        int cod = 0;
        
        do {
            LocalDateTime agora = LocalDateTime.now();
            int hora = agora.getHour();
            int min = agora.getMinute();
            int seg = agora.getSecond();
            int ano = agora.getYear();
            int nano = agora.getNano() / 100000;
            int dia = agora.getDayOfYear();
            int mes = agora.getMonthValue();
            
            cod = nano + hora + min + seg + dia + ano;
            lbldata.setText(dia + "//" + mes + "//" + ano + " - " + hora + ":" + min + ":" + seg);
            
        
        } while (dao.isTituloRepetido(cod));

        String selecionado = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();

        int idGrupo = dao.retornaIDGrupo(selecionado);
        dao.salvarTitulo(cod, idGrupo);
        Imprimir im = new Imprimir(cod, selecionado);
        //JOptionPane.showMessageDialog(this, "GERAR IMPRESSÃO DO GRUPO: " + selecionado + " ID: " + idGrupo + " Cod: (" + cod + ")");
    }
    javax.swing.JLabel lbldata = new javax.swing.JLabel();
}
