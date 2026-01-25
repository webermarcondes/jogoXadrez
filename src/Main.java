public class Main {

    static void main(String[] args) {

       Tabuleiro tabuleiro = new Tabuleiro();
       Jogador jogador1 = new Jogador(TipoJogador.HUMANO, "Weber", 1);
       Jogador jogador2 = new Jogador(TipoJogador.HUMANO, "Wallaci", 2);

       tabuleiro.adicionarPecas(jogador1, CorPeca.PRETO);
       tabuleiro.adicionarPecas(jogador2, CorPeca.BRANCO);

       tabuleiro.mostrarTabuleiro();

    }

}
