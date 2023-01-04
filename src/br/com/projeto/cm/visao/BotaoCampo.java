package br.com.projeto.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.projeto.cm.modelo.Campo;
import br.com.projeto.cm.modelo.CampoEvento;
import br.com.projeto.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener{
	
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCADO = new Color(8, 179, 247);
	private final Color BG_EXPLODIR= new Color(189, 66, 68);
	private final Color TEXT_VERDE= new Color(0, 100, 0);
	
	private Campo campo;

	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		campo.registrarObservador(this);
	}
	
	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch(evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
			
		case MARCAR:
			aplicarEstiloMarcar();
			break;
			
		case DESMARCAR:
			aplicarEstiloDesmarcar();
			break;
			
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
			
		default:
			aplicarEstiloPadrao();
		}
	}


	private void aplicarEstiloAbrir() {
		
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			setText("💣");
			return;
		}
		
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		switch(campo.minasNaVizinhaca()) {
		case 1:
			setForeground(TEXT_VERDE);
			break;
			
		case 2:
			setForeground(Color.BLUE);
			break;
			
		case 3:
			setForeground(Color.YELLOW);
			break;
			
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
			break;
		}
		
		String valor = !campo.vizinhacaSegura() ? campo.minasNaVizinhaca() + "" : "";
		
		setText(valor);
	}
	
	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCADO);
		setForeground(Color.BLACK);
		setText("🏴");
	}
	
	private void aplicarEstiloDesmarcar() {
		aplicarEstiloPadrao();
	}
	
	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.BLACK);
		setText("💣");
	}

	private void aplicarEstiloPadrao() {
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_PADRAO);
		setText("");

	}
	
	// Interce eventos Mouse
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		} else {
			campo.alternarMarcacao();
		}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
