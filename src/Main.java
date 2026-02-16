import java.util.List;
import java.util.Map;

public class Main {

    static void main(String[] args) {

       Tabuleiro tabuleiro = new Tabuleiro();
       Jogador jogador1 = new Jogador(TipoJogador.HUMANO, "Weber", 1);
       Jogador jogador2 = new Jogador(TipoJogador.HUMANO, "Wallaci", 2);


       Peca rainha1jg1 = new Peca(CorPeca.PRETO, TipoPeca.RAINHA, "RAINHA_JG1", jogador1);
       tabuleiro.adicionarPeca(rainha1jg1, 4, 4);


       Peca torre2jg2 = new Peca(CorPeca.PRETO, TipoPeca.TORRE, "TORRE_2_JG2", jogador1);
       Peca torre3jg2 = new Peca(CorPeca.PRETO, TipoPeca.TORRE, "TORRE_3_JG2", jogador1);
       Peca torre4jg2 = new Peca(CorPeca.PRETO, TipoPeca.TORRE, "TORRE_4_JG2", jogador1);
       Peca torre5jg2 = new Peca(CorPeca.PRETO, TipoPeca.TORRE, "TORRE_5_JG2", jogador1);


       tabuleiro.adicionarPeca(torre2jg2, 4, 1);
       tabuleiro.adicionarPeca(torre3jg2, 2, 4);
       tabuleiro.adicionarPeca(torre4jg2, 6,6);
       tabuleiro.adicionarPeca(torre5jg2, 5,3);


       tabuleiro.mostrarTabuleiro();

       /*


        tabuleiro.adicionarPeca(torre1jg1, 4, 4);
        tabuleiro.adicionarPeca(torre2jg2, 4,2);
        tabuleiro.adicionarPeca(torre3jg1, 6,0);

       tabuleiro.adicionarPecas(jogador1, CorPeca.PRETO);
       tabuleiro.adicionarPecas(jogador2, CorPeca.BRANCO);

       tabuleiro.mostrarTabuleiro();

        System.out.println("\n\n");

        tabuleiro.mostrarPecasPorJogador(jogador1);

        */
        Map<String, List<int[]>> movimentosDaPeca = tabuleiro.calcularMovimentosPorPeca(4,4);


        System.out.println("Posição da peça selecionada: [ linha " + 5 + ", coluna " + 3 + " ]");
        System.out.println("Movimentos da Peça Selecionada: ");
        for (String tipoMovimento: movimentosDaPeca.keySet()) {
            System.out.println("Movimento(s) " + tipoMovimento + " : \n");

            for (int[] registroMovimento : movimentosDaPeca.get(tipoMovimento)) {
                System.out.println("[ linha " + registroMovimento[0] + ", coluna " + registroMovimento[1] + "] ");
            }

            System.out.println();

        }
        //PEAO_1_JG2
        //NOMEPECA + _ + IDPECA + _JG + IDJOGADOR
    }

}
