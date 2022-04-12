package interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import models.Produto;

public interface IDataBase {
    public Produto buscar(int codigo);
    public void closeConnection() throws SQLException;
    public void inserir(Produto produto) throws SQLException;
    public void alterar(Produto produto) throws SQLException;
    public void remover(int codigo) throws SQLException;
}
