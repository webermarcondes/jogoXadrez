import enums.CorPeca;
import enums.TipoDeCasaDoTabuleiro;
import enums.TipoJogador;
import jogadores.Jogador;
import pecas.Rainha;
import pecas.Rei;
import pecas.Torre;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    static void main(String[] args) {


        Tabuleiro tabuleiro = new Tabuleiro();
        Map<String, List<int[]>> movimentosDaPecaSelecionada;

        Map<String, List<int[]>> movimentosPorTipodeCasaDoTabuleiro = new LinkedHashMap<>();
        movimentosPorTipodeCasaDoTabuleiro.put("Casas Livres", new ArrayList<int[]>());
        movimentosPorTipodeCasaDoTabuleiro.put("Casas com Inimigo", new ArrayList<int[]>());


        Jogador jogador1 = new Jogador(TipoJogador.HUMANO, "Weber", 1);
        Jogador jogador2 = new Jogador(TipoJogador.HUMANO, "Wallaci", 2);


        Rei pecajg1 = new Rei(CorPeca.PRETO, "Rei_jg1", jogador1);
        Torre pecaInimiga = new Torre(CorPeca.BRANCO, "Torre_1_jg2", jogador2);

        int linha = 4;
        int coluna = 7;

        tabuleiro.adicionarPeca(pecajg1, linha, coluna);
        tabuleiro.adicionarPeca(pecaInimiga, 5, 6);

        tabuleiro.mostrarTabuleiro();


        movimentosDaPecaSelecionada = tabuleiro.calcularMovimentosPorPeca(new int[]{linha, coluna});

        int jogadorId = 1;


        System.out.println("Posição da peça selecionada: [ linha " + linha + ", coluna " + coluna + " ]");
        System.out.println("Movimentos da Peça Selecionada: ");

        for (String tipoMovimento: movimentosDaPecaSelecionada.keySet()) {
            System.out.println("\nMovimento(s) " + tipoMovimento + " : ");

            for (int[] registroMovimento: movimentosDaPecaSelecionada.get(tipoMovimento)) {

                if(tabuleiro.getPecaDoTabuleiro(registroMovimento) == null) {
                    movimentosPorTipodeCasaDoTabuleiro.get("Casas Livres").add(registroMovimento);
                }

                else if(tabuleiro.getPecaDoTabuleiro(registroMovimento).getJogador().getJogadorId() != jogadorId) {
                    movimentosPorTipodeCasaDoTabuleiro.get("Casas com Inimigo").add(registroMovimento);
                }

            }


            for (String tipoDeCasaDoTabuleiro : movimentosPorTipodeCasaDoTabuleiro.keySet()) {
                System.out.println("\n" + tipoDeCasaDoTabuleiro + "\n");

                for (int[] registroM: movimentosPorTipodeCasaDoTabuleiro.get(tipoDeCasaDoTabuleiro)) {
                    System.out.println("[ linha " + registroM[0] + ", coluna " + registroM[1] + "] ");}

                }
        }
    }
}







