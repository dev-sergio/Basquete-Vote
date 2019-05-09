package telas;

import banco.DAO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

public class login extends javax.swing.JDialog {
    
    JFrame tf;
    
    public final JDialog getInstance() { // para remover (vazamento de construtor) - segurança
        return this;
    }
    
    public login(JFrame tF) throws IOException, Exception {
        tf = tF;
        inicializa();
        SwingUtilities.updateComponentTreeUI(getInstance());
        setAlwaysOnTop(true); //deixar a tela sempre por cima das outras
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        // Evento para botão fechar da propria janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               // System.exit(0);
                
            }
            
            @Override
            public void windowClosed(WindowEvent e) {
                dispose();
            }
            
        });
        
        carregaTela();
        setModal(true);
        setVisible(true);
        
    }
    
    public void inicializa() {
        setMinimumSize(new java.awt.Dimension(416, 330));
        setPreferredSize(new java.awt.Dimension(416, 330));
        getContentPane().setLayout(null);        
    }
    
    private void carregaTela() {
        
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        btnContinuar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        
        jLabel2.setText("jLabel2");
        
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LOGIN");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 400, 50);
        
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(null);
        
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("INSIRA O SEU NUMERO DO TITULO");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 20, 360, 17);
        
        txtTitulo.setFont(new java.awt.Font("Times New Roman", 1, 50)); // NOI18N
        txtTitulo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTitulo.setText("");
        jPanel1.add(txtTitulo);
        txtTitulo.setBounds(20, 50, 360, 100);
        txtTitulo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    verificaTitulo();
                }
            }
            
        });
        
        btnContinuar.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnContinuar.setText("CONTINUAR");
        jPanel1.add(btnContinuar);
        btnContinuar.setBounds(130, 170, 250, 50);
        btnContinuar.addActionListener((ActionEvent evt) -> {
            verificaTitulo();
        });
        
        btnCancelar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setFocusable(false);
        jPanel1.add(btnCancelar);
        btnCancelar.setBounds(20, 170, 100, 50);
        btnCancelar.addActionListener((ActionEvent evt) -> {
            JPasswordField password = new JPasswordField(10);
            password.setEchoChar('*');
            
            JLabel rotulo = new JLabel("Entre com a senha:");
            
            JPanel entUsuario = new JPanel();
            entUsuario.add(rotulo);
            entUsuario.add(password);
            
            JOptionPane.showMessageDialog(this, entUsuario, "Acesso restrito", JOptionPane.PLAIN_MESSAGE);
            
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
                tf.dispose();
            }
            
        });
        
        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 50, 400, 240);
        pack();
    }
    
    private void verificaTitulo() {
        try {
            
            if (!txtTitulo.getText().equals("")) {
                DAO dao = new DAO();
                int grupo = dao.retornaGrupoTitulo(txtTitulo.getText());
                if (grupo != 0) {
                    Cabine cab = new Cabine(grupo);
                    cab.setVisible(true);
                    this.dispose();
                    tf.dispose();
                    dao.excluirTitulo(Integer.valueOf(txtTitulo.getText()));
                } else {
                    JOptionPane.showMessageDialog(this, "TITULO INVALIDO");
                    txtTitulo.setText("");
                    txtTitulo.requestFocus();
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private javax.swing.JButton btnContinuar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtTitulo;
    
}
