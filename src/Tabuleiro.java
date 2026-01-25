import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    //Ideia: Deixar o método de Geração de Peças automático mais simples (menos código repetido)

    private Peca[][] casas;


    public Tabuleiro() {
        casas = new Peca[8][8];
    }

    public void mostrarTabuleiro(){

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

    public void adicionarPecas(Jogador jogador, CorPeca corPeca) {


        TipoPeca[][] tiposPeca = {{TipoPeca.PEAO}, {TipoPeca.TORRE, TipoPeca.CAVALO,
                                                        TipoPeca.BISPO, TipoPeca.REI,
                                                        TipoPeca.RAINHA, TipoPeca.BISPO,
                                                        TipoPeca.CAVALO, TipoPeca.TORRE}};

        List<Peca> pecas;

        int linha;
        if (jogador.getJogadorId() == 1) {

            linha = 0;
            for (int i = 1; i >= 0; i--) {
                pecas = gerarPecas(tiposPeca[i], jogador, corPeca);


                for(int coluna = 0; coluna < 8; coluna++) {
                    casas[linha][coluna] = pecas.get(coluna);
                }
                linha++;

                if(linha == 2){
                    break;}


            }

        }

        else {

            linha = 6;

            for (int i = 0; i < 2; i++) {
                pecas = gerarPecas(tiposPeca[i], jogador, corPeca);


                for(int coluna = 0; coluna < 8; coluna++) {
                    casas[linha][coluna] = pecas.get(coluna);
                }
                linha++;

                if(linha == 8){
                    break;}
            }

        }

    }


    private List<Peca> gerarPecas(TipoPeca[] tiposPeca, Jogador jogador, CorPeca corPeca) {

        List<Peca> pecas = new ArrayList<>();

        if (tiposPeca.length == 1) {
            for (int i = 0; i < 8; i++) {
                pecas.add(new Peca(corPeca, tiposPeca[0], tiposPeca[0].name() + "JG" + jogador.getJogadorId(), jogador));
            }
        }

        else {
            for (TipoPeca tipoPeca: tiposPeca) {
                pecas.add(new Peca(corPeca, tipoPeca, tipoPeca.name() + "JG" + jogador.getJogadorId(), jogador));
            }
        }

        return pecas;
    }


    private String centralizarNomePeca(String texto, int largura) {
        if (texto.length() >= largura) return texto.substring(0, largura);

        int espacosEsquerda = (largura - texto.length()) / 2;
        int espacosDireita = largura - texto.length() - espacosEsquerda;

        return " ".repeat(espacosEsquerda) + texto + " ".repeat(espacosDireita);
    }
}
