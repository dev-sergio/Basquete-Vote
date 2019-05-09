package metodos;

/**
 *
 * @author Sergio Junior <sergio.ltnj@gmail.com>
 */
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Implementação de um {@link TableModel} para renderizar o {@link ResultSet} de
 * uma consulta ao banco
 * 
 * @author Ricardo Artur Staroski
 */
public class ResultSetTableModel extends AbstractTableModel {

    /**
     * Classe interna utilizada para armazenar o nome de uma coluna e a classe
     * Java correspondente ao tipo de dado da mesma
     */
    private static class Column {

        /**
         * A classe Java do tipo de dado da coluna
         */
        public final Class<?> CLASS;

        /**
         * O nome da coluna
         */
        public final String   NAME;

        /**
         * Instancia um objeto {@link Column} com o tipo de dado e nome
         * informados
         * 
         * @param type
         *            A classe Java correspondente ao tipo de dado da coluna
         * @param name
         *            O nome da coluna
         */
        public Column(final String name,
                      final Class<?> type) {
            NAME = name;
            CLASS = type;
        }
    }

    /**
     * Classe interna utilizada para armazenar os valores de um registro da
     * tabela
     */
    private static class Row {

        /**
         * Os valores de cada coluna do registro
         */
        public final Object[] VALUES;

        /**
         * Instancia um objeto {@link Row} para o {@link ResultSet} informado
         * 
         * @param rs
         *            O {@link ResultSet} da linha a ser processada
         * @throws SQLException
         */
        public Row(final ResultSet rs) throws SQLException {
            final int columns = rs.getMetaData().getColumnCount();
            VALUES = new Object[columns];
            for (int i = 1; i <= columns; i++) {
                VALUES[i - 1] = rs.getObject(i);
            }
        }
    }

    // Classes serializável, declarar este atributo para não gerar warnings
    private static final long serialVersionUID = 1L;

    // linsta de colunas da tabela
    private List<Column>      columns;
    // lista de registros da tabela
    private List<Row>         lines;

    /**
     * Instancia um {@link ResultSetTableModel} para o {@link ResultSet}
     * informado
     * 
     * @param rs
     *            O {@link ResultSet} que a {@link JTable} deste modelo irá
     *            renderizar
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSetTableModel(final ResultSet rs) throws SQLException,
                                                  ClassNotFoundException {
        columns = new ArrayList<Column>();
        final ResultSetMetaData md = rs.getMetaData();
        final int count = md.getColumnCount();
        for (int i = 1; i <= count; i++) {
            columns.add(new Column(md.getColumnName(i),
                                   Class.forName(md.getColumnClassName(i))));
        }
        lines = new ArrayList<Row>();
        while (rs.next()) {
            lines.add(new Row(rs));
        }
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return columns.get(columnIndex).CLASS;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(final int column) {
        return columns.get(column).NAME;
    }

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return lines.get(rowIndex).VALUES[columnIndex];
    }

    @Override
    public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        return false;
    }
}