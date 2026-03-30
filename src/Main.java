import enums.CorPeca;
import enums.TipoDeCasaDoTabuleiro;
import enums.TipoJogador;
import jogadores.Jogador;
import pecas.Rainha;
import pecas.Torre;

import java.util.List;
import java.util.Map;

public class Main {

    static void main(String[] args) {



        Tabuleiro tabuleiro = new Tabuleiro();
        Jogador jogador1 = new Jogador(TipoJogador.HUMANO, "Weber", 1);
        Jogador jogador2 = new Jogador(TipoJogador.HUMANO, "Wallaci", 2);


        Rainha pecajg1 = new Rainha(CorPeca.PRETO, "Rainha_jg1", jogador1);
        Torre pecaInimiga = new Torre(CorPeca.BRANCO, "Torre_1_jg2", jogador2);

        int linha = 4;
        int coluna = 7;

        tabuleiro.adicionarPeca(pecajg1, linha, coluna);

        tabuleiro.adicionarPeca(pecaInimiga, 5, 6);



        tabuleiro.mostrarTabuleiro();


        Map<String, Map<TipoDeCasaDoTabuleiro, List<int[]>>> movimentosDaPeca = tabuleiro.calcularMovimentosPorPeca(new int[]{linha, coluna});
        List<int[]> movimentosParaUmTipoDeCasaDoTabuleiro;

        System.out.println("Posição da peça selecionada: [ linha " + linha + ", coluna " + coluna + " ]");
        System.out.println("Movimentos da Peça Selecionada: ");
        for (String tipoMovimento: movimentosDaPeca.keySet()) {
            System.out.println("\nMovimento(s) " + tipoMovimento + " : ");

            for (TipoDeCasaDoTabuleiro tipodeCasaDoTabuleiro : movimentosDaPeca.get(tipoMovimento).keySet()) {
                System.out.print("\n" + tipodeCasaDoTabuleiro + ": ");

                movimentosParaUmTipoDeCasaDoTabuleiro = movimentosDaPeca.get(tipoMovimento).get(tipodeCasaDoTabuleiro);

                if(!movimentosParaUmTipoDeCasaDoTabuleiro.isEmpty()) {

                    System.out.println("\n");
                    for (int[] registroMovimento: movimentosParaUmTipoDeCasaDoTabuleiro) {
                        System.out.println("[ linha " + registroMovimento[0] + ", coluna " + registroMovimento[1] + "] ");}
                }

                else {
                    System.out.println("Sem registro");
                }

            }

        }


    }

}
