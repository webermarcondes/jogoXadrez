import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    static void main(String[] args) {

        /*
        Map<String, List<String>> teste = new LinkedHashMap<>();

        teste.put("Livres", new ArrayList<String>());
        teste.put("Inimigas", new ArrayList<String>());


        teste.get("Livres").add("Linha 7, coluna 8");
        teste.get("Inimigas").add("Linha 1, coluna 2");


        System.out.println(teste);
        */




        Tabuleiro tabuleiro = new Tabuleiro();
        Jogador jogador1 = new Jogador(TipoJogador.HUMANO, "Weber", 1);
        Jogador jogador2 = new Jogador(TipoJogador.HUMANO, "Wallaci", 2);


        Rainha pecajg1 = new Rainha(CorPeca.PRETO, "Rainha_jg1", jogador1);
        Torre pecaInimiga = new Torre(CorPeca.BRANCO, "Torre_1_jg2", jogador2);

        tabuleiro.adicionarPeca(pecajg1, 4, 7);

        tabuleiro.adicionarPeca(pecaInimiga, 5, 6);
        //tabuleiro.adicionarPeca(torre1jg2, 1, 2);


        tabuleiro.mostrarTabuleiro();

        //peao1jg1.setQuantidadeMaximaDeMovimentoPorTipoMovimento(1);


        Map<String, Map<String, List<int[]>>> movimentosDaPeca = tabuleiro.calcularMovimentosPorPeca(4,7);

        System.out.println("Posição da peça selecionada: [ linha " + 5 + ", coluna " + 3 + " ]");
        System.out.println("Movimentos da Peça Selecionada: ");
        for (String tipoMovimento: movimentosDaPeca.keySet()) {
            System.out.println("Movimento(s) " + tipoMovimento + " : ");

            for (String tipoDeRegistroDeMovimento: movimentosDaPeca.get(tipoMovimento).keySet()) {
                System.out.println("\n" + tipoDeRegistroDeMovimento + ": \n");
                for (int[] registroMovimento: movimentosDaPeca.get(tipoMovimento).get(tipoDeRegistroDeMovimento)) {
                    System.out.println("[ linha " + registroMovimento[0] + ", coluna " + registroMovimento[1] + "] ");

                }

            }
            /*
            for (int[] registroMovimento : movimentosDaPeca.get(tipoMovimento)) {
                System.out.println("[ linha " + registroMovimento[0] + ", coluna " + registroMovimento[1] + "] ");
            }

            System.out.println();*/

        }


    }

}
