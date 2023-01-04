package br.com.projeto.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.projeto.cm.modelo.Tabuleiro;

public class PainelTabuleiro extends JPanel {

	 public PainelTabuleiro(Tabuleiro tabuleiro) {
		setLayout(new GridLayout(
				tabuleiro.getLinhas(), tabuleiro.getColunas())); // Organizar os componentes na tela
		
		int total = tabuleiro.getLinhas() * tabuleiro.getColunas();
		
		tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
		tabuleiro.registrarObservador(e -> {
			
			SwingUtilities.invokeLater(() -> {
				
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "Ganhou!!! 🏆");
				} else {
					JOptionPane.showMessageDialog(this, "Perdeu 😭");
				}
				
				tabuleiro.reiniciar();
			});
			
			
		});
	}
}
