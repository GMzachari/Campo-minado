package br.com.projeto.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

	// Método construtor onde serão passados as quantidades de linhas/colunas/minas, e a partir disso inicializar o jogo; 
	public Tabuleiro(int linhas, int coluna, int minas) {
		this.linhas = linhas;
		this.colunas = coluna;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void paraCadaCampo(Consumer<Campo> funcao) {	
		campos.forEach(funcao);
	}
	public int getLinhas() {
		return linhas;
	}
	
	public int getColunas() {
		return colunas;
	}
	
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(boolean resultado) {
		observadores.stream()	
					.forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}
	
	public void abrir(int linha, int coluna) {
			campos.parallelStream()
				.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.abrir());
		
	}
	
	
	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.alternarMarcacao());
	}



	// Gerar todos os elementos dos campos do tabuleiro;
	private void gerarCampos() {
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				Campo campo = new Campo(i, j);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
	}
	
	// Irá associar os vizinhos de cada campo do tabuleiro;
	private void associarVizinhos() {
		for(Campo c1 : campos) {
			for(Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	// Sortear as minas em lugares aleatórios do tabuleiro;
	private void sortearMinas() {
		long minasPlantadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		
		do {
			int randow =(int) (Math.random() * campos.size());
			campos.get(randow).minar();
			minasPlantadas = campos.stream().filter(minado).count();
		} while(minasPlantadas < minas);
	}
	
	// Irá percorrer todos os campos do tabuleiro para identificar se o campo ja foi jogado;
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	// Reinicialização do jogo, voltando na posição inicial e sorteando as minas novamente;
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
	
		if(evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		} else if(objetivoAlcancado()) {
			notificarObservadores(true);
		}
		
	}
	
	private void mostrarMinas() {
		campos.stream()
		.filter(c -> c.isMinado())
		.filter(c -> !c.isMarcado())
		.forEach(c -> c.setAberto(true));
		
	}
	
}



