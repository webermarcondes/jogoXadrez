import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    //Posição das peças no tabuleiro:

    // TORRE | CAVALO | BISPO | REI | RAINHA | BISPO | CAVALO | TORRE
    // PEÕES


    // PEÕES
    // TORRE | CAVALO | BISPO | REI | RAINHA | BISPO | CAVALO | TORRE


    //Ideias pro código:

    //Definir identificação dos jogadores por número (1º e 2º).

    //Criar uma classe main para iniciar o jogo e retirar do tabuleiro essa responsabilidade
    //de inicio do sistema

    static Peca[][] casas = new Peca[8][8];

    static void main(String[] args) {


        Jogador jogador1 = new Jogador(TipoJogador.HUMANO, "JG1");
        Jogador jogador2 = new Jogador(TipoJogador.HUMANO, "JG2");


        TipoPeca[] tiposPeca = {TipoPeca.PEAO};

        TipoPeca[] tiposPeca1 = {TipoPeca.TORRE, TipoPeca.CAVALO,
                                TipoPeca.BISPO, TipoPeca.REI, TipoPeca.RAINHA,
                                TipoPeca.BISPO, TipoPeca.CAVALO, TipoPeca.TORRE};


        //Adição das Peças do jogador 1 ao tabuleiro:
        adicionarPecas(0, jogador1, CorPeca.PRETO, tiposPeca1);
        adicionarPecas(1, jogador1, CorPeca.PRETO, tiposPeca);


        //Adição das Peças do Jogador 2 ao tabuleiro
        adicionarPecas(7, jogador2, CorPeca.BRANCO, tiposPeca1);
        adicionarPecas(6, jogador2, CorPeca.BRANCO, tiposPeca);

        mostrarTabuleiro();
    }

    static void mostrarTabuleiro(){

        for(int linha = 0; linha < 8; linha++) {
            System.out.println("-".repeat(113));
            System.out.print("|");

            for(int coluna = 0; coluna < 8; coluna++) {
                System.out.print((casas[linha][coluna] == null ? " ".repeat(13) : centralizarNomePeca(casas[linha][coluna].getNome(), 13)));
                System.out.print("|");
            }

            System.out.println();
        }

        System.out.println("-".repeat(113));
    }

    static void adicionarPecas(int linha, Jogador jogador, CorPeca corPeca, TipoPeca[] tiposPeca) {

        List<Peca> pecas = gerarPecas(tiposPeca, jogador, corPeca);

        for (int coluna = 0; coluna < 8; coluna++) {
            casas[linha][coluna] = pecas.get(coluna);
        }

    }


    static List<Peca> gerarPecas(TipoPeca[] tiposPeca, Jogador jogador, CorPeca corPeca) {

        List<Peca> pecas = new ArrayList<>();

        if (tiposPeca.length == 1) {
            for (int i = 0; i < 8; i++) {
                pecas.add(new Peca(corPeca, tiposPeca[0], tiposPeca[0].name() + jogador.getNome(), jogador));
            }
        }

        else {
            for (TipoPeca tipoPeca: tiposPeca) {
                pecas.add(new Peca(corPeca, tipoPeca, tipoPeca.name() + jogador.getNome(), jogador));
            }
        }

        return pecas;
    }


    static String centralizarNomePeca(String texto, int largura) {
        if (texto.length() >= largura) return texto.substring(0, largura);

        int espacosEsquerda = (largura - texto.length()) / 2;
        int espacosDireita = largura - texto.length() - espacosEsquerda;

        return " ".repeat(espacosEsquerda) + texto + " ".repeat(espacosDireita);
    }
}
