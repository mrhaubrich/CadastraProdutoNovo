import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classes.ProdutoDB;
import models.Produto;

public class CadastraProduto extends JFrame implements ActionListener {

	private JPanel fundo;
	private JPanel campos;
	private JPanel botoes;

	private JButton bBusca;
	private JButton bLimpa;
	private JButton bInsere;
	private JButton bAltera;
	private JButton bRemove;

	private JLabel lCodigo;
	private JLabel lNome;
	private JLabel lDescricao;

	private JTextField tCodigo;
	private JTextField tNome;
	private JTextField tDescricao;

	ProdutoDB produtoDB;

	public CadastraProduto() {

		super("Cadastro de Produtos");

		init();
	}

	private void init(){
		initCampos();
		initBotoes();
		initListener();
		initLayout();
	}
	
	private void initBotoes() {
		this.bBusca = new JButton("Buscar");
		this.bBusca.addActionListener(this);
		this.bLimpa = new JButton("Limpar");
		this.bLimpa.addActionListener(this);
		this.bInsere = new JButton("Incluir");
		this.bInsere.addActionListener(this);
		this.bAltera = new JButton("Alterar");
		this.bAltera.addActionListener(this);
		this.bRemove = new JButton("Remover");
		this.bRemove.addActionListener(this);

		this.botoes.add(bBusca);
		this.botoes.add(bLimpa);
		this.botoes.add(bInsere);
		this.botoes.add(bAltera);
		this.botoes.add(bRemove);
	}
	private void initCampos() {
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(3, 2));
		this.botoes = new JPanel(new FlowLayout());

		this.lCodigo = new JLabel("Código:");
		this.lNome = new JLabel("Nome:");
		this.lDescricao = new JLabel("Descrição:");

		this.tCodigo = new JTextField(10);
		this.tNome = new JTextField(30);
		this.tDescricao = new JTextField(60);

		this.campos.add(lCodigo);
		this.campos.add(tCodigo);
		this.campos.add(lNome);
		this.campos.add(tNome);
		this.campos.add(lDescricao);
		this.campos.add(tDescricao);
	}

	private void initListener() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if(produtoDB != null)
						produtoDB.closeConnection();
				} catch (SQLException se) {
					System.out.println("Não foi possível conectar ao Banco de Dados");
				}
				System.exit(0);
			}
		});
	}

	private void initLayout() {
		this.fundo.add(this.campos, BorderLayout.CENTER);

		this.fundo.add(this.botoes, BorderLayout.SOUTH);

		this.getContentPane().add(this.fundo);

		this.setSize(500, 150);
	}

	public static void main(String[] args) {

		CadastraProduto c = new CadastraProduto();

		Connection conn = c.getConnection("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/Solid", "postgres",
				"ucs");
		c.produtoDB = new ProdutoDB(conn);
		c.setVisible(true);

	}

	private void acaoLimpar() {
		this.tCodigo.setText("");
		this.tNome.setText("");
		this.tDescricao.setText("");
	}

	private void acaoBuscar() {

		int codigo = -1;

		try {
			codigo = Integer.parseInt(this.tCodigo.getText());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "O dado digitado não é um número!");
			acaoLimpar();
			return;
		}
		Produto p = produtoDB.buscar(codigo);

		this.tCodigo.setText(p.getCodigo() + "");
		this.tNome.setText(p.getNome());
		this.tDescricao.setText(p.getDescricao());


	}

	private void acaoInserir() {
		int codigo = Integer.parseInt(this.tCodigo.getText());
		String nome = this.tNome.getText();
		String descricao = this.tDescricao.getText();
		Produto p = new Produto(codigo, nome, descricao);
		try {
			produtoDB.inserir(p);
			// show success dialog
			JOptionPane.showMessageDialog(this, "Produto inserido com sucesso!");
		} catch (SQLException e) {
			// show error dialog
			JOptionPane.showMessageDialog(this, "Não foi possível inserir o produto!");
		}
	}

	private void acaoAlterar() {
		int codigo = Integer.parseInt(this.tCodigo.getText());
		String nome = this.tNome.getText();
		String descricao = this.tDescricao.getText();
		Produto p = new Produto(codigo, nome, descricao);
		try {
			produtoDB.alterar(p);
			// show success dialog
			JOptionPane.showMessageDialog(this, "Produto alterado com sucesso!");
		} catch (SQLException e) {
			// show error dialog
			JOptionPane.showMessageDialog(this, "Não foi possível alterar o produto!");
		}
	}

	private void acaoRemover() {
		int codigo = Integer.parseInt(this.tCodigo.getText());
		try {
			produtoDB.remover(codigo);
			// show success dialog
			JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
			this.acaoLimpar();
		} catch (SQLException e) {
			// show error dialog
			JOptionPane.showMessageDialog(this, "Não foi possível remover o produto!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bLimpa) {
			this.acaoLimpar();
		} else if (e.getSource() == bBusca) {
			this.acaoBuscar();
		} else if (e.getSource() == bInsere) {
			this.acaoInserir();
		} else if (e.getSource() == bRemove) {
			this.acaoRemover();
		} else if (e.getSource() == bAltera) {
			this.acaoAlterar();
		}

	}

	public Connection getConnection(String driver, String url, String user, String pwd) {

		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Não foi possível encontrar o driver JDBC");
		} catch (SQLException se) {
			System.out.println("Não foi possível conectar ao Banco de Dados");
		}

		return conn;
	}

}
