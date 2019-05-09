package telas;

import banco.DAO;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class Principal extends javax.swing.JFrame {

    public Principal() {
        this.setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {

        javax.swing.JButton btnSair = new javax.swing.JButton();
        javax.swing.JPanel painelExterno = new javax.swing.JPanel();
        javax.swing.JButton btnCabine = new javax.swing.JButton();
        javax.swing.JButton btnApurar = new javax.swing.JButton();
        javax.swing.JButton btnCadastro = new javax.swing.JButton();
        javax.swing.JButton btnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        painelExterno.setLayout(null);

        btnSair.setText("SAIR");
        btnSair.addActionListener((java.awt.event.ActionEvent evt) -> {
            System.exit(0);
        });
        painelExterno.add(btnSair);
        btnSair.setBounds(1240, 5, 120, 50);

        btnCabine.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        btnCabine.setText("CABINE DE VOTAÇÃO");
        btnCabine.addActionListener((java.awt.event.ActionEvent evt) -> {
            DAO alternar = new DAO();

            try {
                alternar.iniciar(1);

                telaFundo tf = new telaFundo();
                tf.setVisible(true);
                login log = new login(tf);
                log.setVisible(true);
                dispose();
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        painelExterno.add(btnCabine);
        btnCabine.setBounds(10, 390, 1220, 180);

        btnApurar.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        btnApurar.setText("GERAR RESULTADOS");
        btnApurar.addActionListener((java.awt.event.ActionEvent evt) -> {
            javax.swing.JPasswordField password = new javax.swing.JPasswordField(10);
            password.setEchoChar('*');

            javax.swing.JLabel rotulo = new javax.swing.JLabel("Entre com a senha:");

            javax.swing.JPanel entUsuario = new javax.swing.JPanel();
            entUsuario.add(rotulo);
            entUsuario.add(password);

            javax.swing.JOptionPane.showMessageDialog(null, entUsuario, "Acesso restrito", javax.swing.JOptionPane.PLAIN_MESSAGE);

            char[] senha = password.getPassword();
            String senhaString = String.valueOf(senha);

            if (senhaString.equals("apurar")) {
                try {
                    Apuracao apurar = new Apuracao();
                    apurar.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        painelExterno.add(btnApurar);
        btnApurar.setBounds(10, 580, 1220, 180);

        btnCadastro.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        btnCadastro.setText("CADASTRAR PERGUNTAS");
        btnCadastro.addActionListener((java.awt.event.ActionEvent evt) -> {
            try {
                Cadastro cad = new Cadastro();
                cad.setVisible(true);
                dispose();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        painelExterno.add(btnCadastro);
        btnCadastro.setBounds(10, 10, 1220, 180);

        btnImprimir.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        btnImprimir.setText("IMPRIMIR TITULOS");
        btnImprimir.addActionListener((java.awt.event.ActionEvent evt) -> {
            imprimirTitulo imprime;
            try {
                imprime = new imprimirTitulo();
                imprime.setVisible(true);

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        painelExterno.add(btnImprimir);
        btnImprimir.setBounds(10, 200, 1220, 180);

        getContentPane().add(painelExterno);
        painelExterno.setBounds(0, 0, 1366, 770);

        pack();
    }
}
