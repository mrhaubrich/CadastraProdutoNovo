package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.IDataBase;
import models.Produto;

public class ProdutoDB implements IDataBase {
    private Connection conn;

    public ProdutoDB(Connection conn2) {
        this.conn = conn2;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Produto buscar(int codigo) {
        PreparedStatement pstmt;
		ResultSet rs;

		try {

			pstmt = this.conn.prepareStatement("select codigo, nome, descricao from produto where codigo = ?");

			pstmt.setInt(1, codigo);
			rs = pstmt.executeQuery();
            Produto produto = null;

			if (rs.next()) {
                int newCodigo = rs.getInt("codigo");
                String newDesc = rs.getString("descricao");
                String newNome = rs.getString("nome");
                produto = new Produto(newCodigo, newNome, newDesc);
			}

			rs.close();
			pstmt.close();
            return produto;
		} catch (SQLException e) {
            return null;
		}
    }

    @Override
    public void closeConnection() throws SQLException {
        if(this.conn != null) {
            this.conn.close();
        }
    }

    @Override
    public void inserir(Produto produto) throws SQLException {
        PreparedStatement pstmt;
        pstmt = conn.prepareStatement("insert into produto(codigo, nome, descricao) values (?, ?, ?)");

        pstmt.setInt(1, produto.getCodigo());
        pstmt.setString(2, produto.getNome());
        pstmt.setString(3, produto.getDescricao());

        pstmt.execute();

        pstmt.close();
    }

    @Override
    public void alterar(Produto produto) throws SQLException {
        PreparedStatement pstmt;
        pstmt = conn.prepareStatement("update produto set nome = ?, descricao = ? where codigo =  ?");

        pstmt.setString(1, produto.getNome());
        pstmt.setString(2, produto.getDescricao());
        pstmt.setInt(3, produto.getCodigo());

        pstmt.execute();

        pstmt.close();
    }

    @Override
    public void remover(int codigo) throws SQLException {
        PreparedStatement pstmt;

        pstmt = conn.prepareStatement("delete from produto where codigo =  ?");
        pstmt.setInt(1, codigo);

        pstmt.execute();

        pstmt.close();

    }
    
}
