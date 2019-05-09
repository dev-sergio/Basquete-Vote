package banco;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import encapsulamentos.Questionario;
import encapsulamentos.Respostas;
import encapsulamentos.NovasPerguntas;
import java.util.Collections;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
public class DAO {

    Connection connection;

    private ArrayList<Questionario> arrayQuestionario = new ArrayList();
    private ArrayList arrayAlternativas = new ArrayList();
    private ArrayList arrayGrupos = new ArrayList();
    //String perguntaApurada;
    String votado;
    String votos;
    //String[] result = new String[3];

    public DAO() {
        try {
            connection = new ConnectionFactory().getConnection();
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int iniciar() throws SQLException {
        String SQL1 = "SELECT iniciar FROM aplicacao";
        Statement stt1 = connection.createStatement();
        ResultSet result1 = stt1.executeQuery(SQL1);
        if (result1.first()) {
            if (result1.getInt("iniciar") == 1) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public void iniciar(int Valor) throws SQLException {
        String SQL = "UPDATE aplicacao SET iniciar = '" + Valor + "'";
        Statement stt1 = connection.createStatement();
        stt1.execute(SQL);
    }

    public ArrayList questionario(int idGrupo) throws SQLException {
        arrayQuestionario.clear();

        boolean sair = false;

        int contper = 0;

        while (!sair) {
            Questionario nova = new Questionario();

            String SQLper = "SELECT id_pergunta FROM perguntas WHERE FK_id_grupo = " + idGrupo + " or FK_id_grupo = " + retornaIdTodos() + " LIMIT " + contper + ", 1";

            Statement stt1per = connection.createStatement();
            ResultSet resultQueryper = stt1per.executeQuery(SQLper);
            if (resultQueryper.first()) {
                String idPergunta = resultQueryper.getString("id_pergunta");
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                String SQL = "SELECT * FROM questionario WHERE id_pergunta = " + idPergunta;
                Statement stt1 = connection.createStatement();
                ResultSet resultQuery = stt1.executeQuery(SQL);
                if (resultQuery.first()) {
                    String id_pergunta = resultQuery.getString("id_pergunta");

                    do {
                        String SQL2 = "SELECT opcao FROM opcoes WHERE id_opcao = " + resultQuery.getString("id_opcao");
                        Statement stt2 = connection.createStatement();
                        ResultSet result2 = stt2.executeQuery(SQL2);
                        if (result2.first()) {
                            nova.setAlternativas(result2.getString("opcao"));
                        }
                    } while (resultQuery.next());

                    String SQL3 = "SELECT pergunta, qntEscolhas FROM perguntas WHERE id_pergunta = " + id_pergunta;
                    Statement stt3 = connection.createStatement();
                    ResultSet result3 = stt3.executeQuery(SQL3);
                    if (result3.first()) {
                        nova.setPergunta(result3.getString("pergunta"));
                        nova.setQntAlternativas(result3.getInt("qntEscolhas"));
                    }

                    arrayQuestionario.add(nova);
                } else {
                    sair = true;
                }

                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                resultQueryper.next();
                contper++;
            } else {
                sair = true;
            }

        }
        return arrayQuestionario;
    }

    public ArrayList questionario() throws SQLException {
        arrayQuestionario.clear();

        boolean sair = false;

        int contper = 0;

        while (!sair) {
            Questionario nova = new Questionario();

            String SQLper = "SELECT id_pergunta FROM perguntas LIMIT " + contper + ", 1";

            Statement stt1per = connection.createStatement();
            ResultSet resultQueryper = stt1per.executeQuery(SQLper);
            if (resultQueryper.first()) {
                String idPergunta = resultQueryper.getString("id_pergunta");
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                String SQL = "SELECT * FROM questionario WHERE id_pergunta = " + idPergunta;
                Statement stt1 = connection.createStatement();
                ResultSet resultQuery = stt1.executeQuery(SQL);
                if (resultQuery.first()) {
                    String id_pergunta = resultQuery.getString("id_pergunta");

                    do {
                        String SQL2 = "SELECT opcao FROM opcoes WHERE id_opcao = " + resultQuery.getString("id_opcao");
                        Statement stt2 = connection.createStatement();
                        ResultSet result2 = stt2.executeQuery(SQL2);
                        if (result2.first()) {
                            nova.setAlternativas(result2.getString("opcao"));
                        }
                    } while (resultQuery.next());

                    String SQL3 = "SELECT pergunta, qntEscolhas FROM perguntas WHERE id_pergunta = " + id_pergunta;
                    Statement stt3 = connection.createStatement();
                    ResultSet result3 = stt3.executeQuery(SQL3);
                    if (result3.first()) {
                        nova.setPergunta(result3.getString("pergunta"));
                        nova.setQntAlternativas(result3.getInt("qntEscolhas"));
                    }

                    arrayQuestionario.add(nova);
                } else {
                    sair = true;
                }

                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                resultQueryper.next();
                contper++;
            } else {
                sair = true;
            }

        }
        return arrayQuestionario;
    }

    public ArrayList apuracao(int cont) throws SQLException {
        boolean sair = false;
        ArrayList apuracaoFinal = new ArrayList();
        while (!sair) {
            Respostas resp = new Respostas();
            String SQL1 = "SELECT pergunta, resposta, count(*) AS votos FROM resultado WHERE pergunta = " + cont + " GROUP BY pergunta, resposta ORDER BY count(*) desc";
            Statement stt1 = connection.createStatement();
            ResultSet result1 = stt1.executeQuery(SQL1);
            if (result1.first()) {
                do {
                    votado = result1.getString("resposta");
                    votos = result1.getString("votos");
                    resp.setVotados(votado);
                    resp.setRespostas(votos);
                    apuracaoFinal.add(resp);
                } while (result1.next());
                sair = true;
            }
        }
        return apuracaoFinal;
    }

    public String quantidadePerguntas() throws SQLException {

        String sql = "SELECT COUNT(*) FROM perguntas";
        Statement stt = connection.createStatement();
        ResultSet resultQuery = stt.executeQuery(sql);
        String quantidade = "0";
        if (resultQuery.first()) {
            quantidade = resultQuery.getString("COUNT(*)");
        }
        return quantidade;
    }

    public ArrayList alternativas() throws SQLException {
        arrayAlternativas.clear();
        boolean sair = false;

        while (!sair) {
            String SQL = "SELECT * FROM opcoes";
            Statement stt1 = connection.createStatement();
            ResultSet resultQuery = stt1.executeQuery(SQL);
            if (resultQuery.first()) {
                do {
                    NovasPerguntas novas = new NovasPerguntas();
                    int id_opcao = resultQuery.getInt("id_opcao");
                    String opcao = resultQuery.getString("opcao");
                    novas.setId_alternativa(id_opcao);
                    novas.setAlternativa(opcao);
                    arrayAlternativas.add(novas);
                } while (resultQuery.next());
                sair = true;
            }else{
                sair = true;
            }
        }
        Collections.sort(arrayAlternativas);
        return arrayAlternativas;

    }

    public ResultSet editar() throws SQLException {
        String SQL = "select perguntas.pergunta as 'Perguntas', COUNT(*) as 'Alternativas' from perguntas INNER JOIN questionario ON perguntas.id_pergunta = questionario.id_pergunta GROUP BY questionario.id_pergunta;";
        Statement stt = connection.createStatement();
        ResultSet rsQ = stt.executeQuery(SQL);
        return rsQ;
    }

    public ResultSet editarOpcao() throws SQLException {
        String SQL = "SELECT opcao FROM opcoes;";
        Statement stt = connection.createStatement();
        ResultSet rsQ = stt.executeQuery(SQL);
        return rsQ;
    }

    public ArrayList editando(int questao) throws SQLException {
        arrayAlternativas.clear();
        boolean sair = false;

        while (!sair) {
            String SQL = "select opcoes.opcao, opcoes.id_opcao from opcoes INNER JOIN questionario on opcoes.id_opcao = questionario.id_opcao WHERE questionario.id_pergunta = " + questao + ";";
            Statement stt1 = connection.createStatement();
            ResultSet resultQuery = stt1.executeQuery(SQL);
            if (resultQuery.first()) {
                do {
                    NovasPerguntas novas = new NovasPerguntas();
                    int id_opcao = resultQuery.getInt("id_opcao");
                    String opcao = resultQuery.getString("opcao");
                    novas.setId_alternativa(id_opcao);
                    novas.setAlternativa(opcao);
                    arrayAlternativas.add(novas);
                } while (resultQuery.next());
                sair = true;
            }
        }
        Collections.sort(arrayAlternativas);
        return arrayAlternativas;
    }

    public void excluirQuestao(int idPergunta) throws SQLException {
        String SQL = "DELETE FROM `questionario` WHERE id_pergunta = " + idPergunta;
        String SQL2 = "DELETE FROM `perguntas` WHERE id_pergunta = " + idPergunta;
        Statement stt1 = connection.createStatement();
        Statement stt2 = connection.createStatement();
        stt1.execute(SQL);
        stt2.execute(SQL2);
    }

    public boolean isMultipla(String pergunta) throws SQLException {
        String SQL = "SELECT multipla FROM perguntas WHERE pergunta = '" + pergunta + "';";
        Statement stt = connection.createStatement();
        ResultSet RS = stt.executeQuery(SQL);
        int multipla = 0;
        if (RS.first()) {
            multipla = RS.getInt("multipla");
        }
        return multipla != 0;
    }

    public int qntMultipla(String pergunta) throws SQLException {
        String SQL = "SELECT qntEscolhas FROM perguntas where pergunta = '" + pergunta + "';";
        Statement stt = connection.createStatement();
        ResultSet rs = stt.executeQuery(SQL);
        int qnt = 0;
        if (rs.first()) {
            qnt = rs.getInt("qntEscolhas");
        }
        return qnt;
    }

    public void addGrupo(String opcao) throws SQLException {
        String SQL = "INSERT INTO `grupo`(`grupo`) VALUES ('" + opcao.toUpperCase() + "')";
        try (Statement stt = connection.createStatement()) {
            stt.execute(SQL);
        }
    }

    public ArrayList retornaGrupos() throws SQLException {
        String SQL = "SELECT grupo FROM grupo";
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                do {
                    arrayGrupos.add(rs.getString("grupo"));
                } while (rs.next());
            } else {
                addGrupo("TODOS");
                arrayGrupos.add("TODOS");
            }
        }
        return arrayGrupos;
    }

    public int retornaIdTodos() throws SQLException {
        int id = 0;
        String SQL = "SELECT id_grupo FROM grupo where grupo = 'TODOS'";
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                id = rs.getInt("id_grupo");
            }
        }
        return id;

    }

    public int retornaIDGrupo(String grupo) throws SQLException {
        String SQL = "SELECT id_grupo FROM GRUPO WHERE grupo = '" + grupo + "';";
        int retorno = 0;
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                retorno = rs.getInt("id_grupo");
            }
        }
        return retorno;
    }

    public void updateOpcao(String novo, String selecionado) throws SQLException {
        String SQL = "UPDATE opcoes SET opcao = '" + novo.toUpperCase() + "' WHERE opcao = '" + selecionado.toUpperCase() + "';";
        try (Statement stt = connection.createStatement()) {
            stt.execute(SQL);
        }
    }

    public void updateGrupo(String novo, String selecionado) throws SQLException {
        String SQL = "UPDATE grupo SET grupo = '" + novo.toUpperCase() + "' WHERE grupo = '" + selecionado.toUpperCase() + "';";
        try (Statement stt = connection.createStatement()) {
            stt.execute(SQL);
        }
    }

    public ResultSet editarGrupo() throws SQLException {
        String SQL = "SELECT grupo FROM grupo WHERE grupo != 'TODOS';";
        Statement stt = connection.createStatement();
        ResultSet rsQ = stt.executeQuery(SQL);
        return rsQ;
    }

    public ResultSet imprimirTitulo() throws SQLException {
        String SQL = "SELECT grupo FROM grupo WHERE grupo != 'TODOS';";
        Statement stt = connection.createStatement();
        ResultSet rsQ = stt.executeQuery(SQL);
        return rsQ;
    }

    public int ContarPergunta() throws SQLException {
        String SQL = "SELECT count(*) as contador FROM perguntas";
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                return rs.getInt("contador");
            }
        }
        return 0;
    }

    public int somaRespostas(int idGrupo) throws SQLException {
        String SQL = "select SUM(qntEscolhas) as cont FROM perguntas WHERE FK_id_grupo = " + idGrupo + " or FK_id_grupo = " + retornaIdTodos();
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                return rs.getInt("cont");
            }
        }
        return 0;
    }

    //VERIFICA PRA EXCLUIR
    public boolean verificaOpcao(int idOpcao) throws SQLException {
        String SQL = "SELECT * FROM questionario WHERE id_opcao = " + idOpcao;
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                return false;
            }
        }
        return true;
    }

    public boolean verificaGrupos(int idGrupo) throws SQLException {
        String SQL = "SELECT * FROM perguntas WHERE FK_id_grupo = " + idGrupo;
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTituloRepetido(int titulo) throws SQLException {
        boolean repetido = false;
        String SQL = "SELECT * FROM titulo WHERE titulo = (" + titulo + ");";
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                repetido = true;
            }
        }
        return repetido;
    }

    public boolean isEditavel() throws SQLException {
        String SQL = "SELECT * FROM `resultado` WHERE 1";
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                return false;
            }
        }
        return true;
    }

    //EXCLUIR
    public void excluirOpcao(int idOpcao) throws SQLException {
        String SQL = "DELETE FROM opcoes WHERE id_opcao = " + idOpcao;
        try (Statement stt = connection.createStatement()) {
            stt.execute(SQL);
        }
    }

    public void excluirGrupo(int idGrupo) throws SQLException {
        String SQL = "DELETE FROM grupo WHERE id_grupo = " + idGrupo;
        try (Statement stt = connection.createStatement()) {
            stt.execute(SQL);
        }
    }

    public void excluirTitulo(int titulo) throws SQLException {
        String SQL = "DELETE FROM titulo WHERE titulo = " + titulo;
        try (Statement stt = connection.createStatement()) {
            stt.execute(SQL);
        }
    }

    //RETORNAM ALGO
    public int retornaIdPergunta(String pergunta) throws SQLException {
        String SQL = "select id_pergunta from perguntas where pergunta = '" + pergunta + "';";
        Statement stt = connection.createStatement();
        ResultSet result2 = stt.executeQuery(SQL);
        int retorno = -1;
        if (result2.first()) {
            retorno = result2.getInt("id_pergunta");

        }
        return retorno;
    }

    public int retornaIntQuestao(int contper) throws SQLException {
        int retorno = 0;
        String SQLper = "SELECT id_pergunta FROM perguntas LIMIT " + contper + ", 1";
        Statement stt1per = connection.createStatement();
        ResultSet resultQueryper = stt1per.executeQuery(SQLper);
        if (resultQueryper.first()) {
            retorno = resultQueryper.getInt("id_pergunta");
        }

        return retorno;
    }

    public int retornaIdOpcao(String opcao) throws SQLException {
        String SQL = "SELECT id_opcao FROM opcoes WHERE opcao = '" + opcao + "';";
        int retorno = 0;
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                retorno = rs.getInt("id_opcao");
            }
        }
        return retorno;
    }

    public int retornaIdAlternativa(String alternativa) throws SQLException {
        int idAlternativa = 0;
        String SQL = "SELECT id_opcao FROM opcoes WHERE opcao = ('" + alternativa + "');";
        Statement stt = connection.createStatement();
        ResultSet rsQ = stt.executeQuery(SQL);
        if (rsQ.first()) {
            idAlternativa = rsQ.getInt("id_opcao");
        }
        return idAlternativa;
    }

    public int retornaGrupoTitulo(String titulo) throws SQLException {
        int retornar = 0;
        String SQL = "SELECT grupo FROM titulo WHERE titulo = " + titulo;
        try (Statement stt = connection.createStatement()) {
            ResultSet rs = stt.executeQuery(SQL);
            if (rs.first()) {
                retornar = rs.getInt("grupo");
            }
        }

        return retornar;
    }

    //SALVAM
    public void salvarTitulo(int titulo, int grupo) throws SQLException {
        String SQL = "INSERT INTO titulo(titulo, grupo) VALUES (" + titulo + ", " + grupo + ")";
        try (Statement stt = connection.createStatement()) {
            stt.execute(SQL);
        }
    }

    public void salvarRespostas(int idPergunta, String resposta) throws SQLException {
        String SQL = "INSERT INTO resultado(pergunta, resposta) VALUES('" + idPergunta + "', '" + resposta + "')";
        Statement stt1 = connection.createStatement();
        stt1.execute(SQL);
    }

    public void salvarAlternativa(String alternativa) throws SQLException {

        String SQL = "INSERT INTO opcoes(opcao) VALUES('" + alternativa.toUpperCase() + "')";
        Statement stt1 = connection.createStatement();
        stt1.execute(SQL);
    }

    public int salvarQuestao(String questao, int multipla, int qntEscolhas, int id_grupo) throws SQLException {

        String SQL = "INSERT INTO perguntas(pergunta, multipla, qntEscolhas, FK_id_grupo) VALUES('" + questao.toUpperCase() + "', " + multipla + ", " + qntEscolhas + ", " + id_grupo + ")";
        Statement stt1 = connection.createStatement();
        stt1.execute(SQL);

        int idQuestao = 0;
        String sql2 = "SELECT id_pergunta FROM perguntas where pergunta = ('" + questao + "');";
        Statement stt2 = connection.createStatement();
        ResultSet resultQuery = stt2.executeQuery(sql2);
        if (resultQuery.first()) {
            idQuestao = resultQuery.getInt("id_pergunta");
        }
        return idQuestao;

    }

    public void salvarQuestionario(int idQuestao, int idAlternativa) throws SQLException {

        String SQL = "INSERT INTO questionario(id_pergunta, id_opcao) VALUES('" + idQuestao + "', '" + idAlternativa + "')";
        Statement stt1 = connection.createStatement();
        stt1.execute(SQL);
    }

    public void salvarQuestaoEditada(int id, String questao, int multipla, int qntEscolhas, int idGrupo) throws SQLException {

        String SQL = "INSERT INTO perguntas(id_pergunta, pergunta, multipla, qntEscolhas, FK_id_grupo) VALUES('" + id + "', '" + questao.toUpperCase() + "', " + multipla + ", " + qntEscolhas + ", " + idGrupo + ")";
        Statement stt1 = connection.createStatement();
        stt1.execute(SQL);
    }

}
