package br.com.projeto.cm.visao;

import javax.swing.JFrame;

import br.com.projeto.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class MainScreen extends JFrame{

	public MainScreen() {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 2);
		add(new PainelTabuleiro(tabuleiro));
		
		setTitle("Campo Minado"); // Definit titulo 
		setSize(690, 438); // Definir tamanho
		setLocationRelativeTo(null); // Centralizar no meio da tela
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Finalizar operação quando fechada
		setVisible(true); // Mostrar tela
	}
	public static void main(String[] args) {
		new MainScreen();
		
	}
}
