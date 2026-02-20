import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {

    static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro();
        Jogador jogador1 = new Jogador(TipoJogador.HUMANO, "Weber", 1);
        Jogador jogador2 = new Jogador(TipoJogador.HUMANO, "Wallaci", 2);


        Peca bispo1jg1 = new Peca(CorPeca.PRETO, TipoPeca.BISPO, "BISPO_1_JG1", jogador1);
        tabuleiro.adicionarPeca(bispo1jg1, 4, 4);

        /*
        Peca torre1jg2 = new Peca(CorPeca.BRANCO, TipoPeca.TORRE, "TORRE_1_JG2", jogador1);
        tabuleiro.adicionarPeca(torre1jg2, 4,6);
        */



        tabuleiro.mostrarTabuleiro();

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
