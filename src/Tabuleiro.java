import java.util.*;
import java.util.function.Supplier;

public class Tabuleiro {

    private Peca[][] casas;
    private Map<String, Integer[]> posicaoDasPecas;

    public Tabuleiro() {
        casas = new Peca[8][8];
        posicaoDasPecas = new LinkedHashMap<>();
    }

    public void mostrarTabuleiro(){


        //Montagem da barra superior com o número de cada coluna
        System.out.print("      ");
        for (int coluna = 0; coluna < 8; coluna++) {
            System.out.print("|" + " ".repeat(7) + coluna + " ".repeat(7));
        }
        System.out.println("|");


        //Montagem do tabuleiro, com cada casa tendo espaços iguais e as linhas numeradas
        for(int linha = 0; linha < 8; linha++) {

            System.out.println("      " + "-".repeat(129));
            System.out.print(linha + "  -  |");

            for(int coluna = 0; coluna < 8; coluna++) {
                System.out.print((casas[linha][coluna] == null ? " ".repeat(15) : centralizarNomePeca(casas[linha][coluna].getNome(), 15)));
                System.out.print("|");
            }

            System.out.println();
        }

        System.out.println("      " + "-".repeat(129));
    }

    public void adicionarPeca(Peca peca, Integer linha, Integer coluna) {
        casas[linha][coluna] = peca;

        Integer[] posicaoPeca = {linha, coluna};

        posicaoDasPecas.put(peca.getNome(), posicaoPeca);

    }

    public void adicionarPecas(Jogador jogador, CorPeca corPeca) {

        String[][] pecasPorLinha = {{"PEAO_1", "PEAO_2", "PEAO_3", "PEAO_4",
                                    "PEAO_5", "PEAO_6", "PEAO_7", "PEAO_8"},

                                    {"TORRE_1", "CAVALO_1", "BISPO_1", "REI",
                                     "RAINHA", "BISPO_2", "CAVALO_2", "TORRE_2"}};
        List<Peca> pecas;

        int linha;
        Integer[] registroPosicaoPeca;

        if (jogador.getJogadorId() == 1) {

            linha = 0;
            for (int i = 1; i >= 0; i--) {
                pecas = gerarPecas(pecasPorLinha[i], jogador, corPeca);


                for(int coluna = 0; coluna < 8; coluna++) {
                    registroPosicaoPeca = new Integer[2];
                    casas[linha][coluna] = pecas.get(coluna);

                    registroPosicaoPeca[0] = linha;
                    registroPosicaoPeca[1] = coluna;
                    posicaoDasPecas.put(pecas.get(coluna).getNome(), registroPosicaoPeca);
                }
                linha++;

                if(linha == 2){
                    break;}


            }

        }

        else {

            linha = 6;

            for (int i = 0; i < 2; i++) {
                pecas = gerarPecas(pecasPorLinha[i], jogador, corPeca);


                for(int coluna = 0; coluna < 8; coluna++) {
                    registroPosicaoPeca = new Integer[2];
                    casas[linha][coluna] = pecas.get(coluna);

                    registroPosicaoPeca[0] = linha;
                    registroPosicaoPeca[1] = coluna;
                    posicaoDasPecas.put(pecas.get(coluna).getNome(), registroPosicaoPeca);

                }
                linha++;

                if(linha == 8){
                    break;}
            }

        }

    }

    public void moverPeca(Integer linhaInicial, Integer colunaInicial,
                          Integer linhaFinal, Integer colunaFinal) {

        Peca peca = casas[linhaInicial][colunaInicial];
        casas[linhaInicial][colunaInicial] = null;

        adicionarPeca(peca, linhaFinal, colunaFinal);

    }

    public void mostrarPecasPorJogador(Jogador jogador) {

        String codJogador = "JG" + jogador.getJogadorId();

        for(String nomePeca: posicaoDasPecas.keySet()) {
            if (nomePeca.contains(codJogador)) {
                System.out.println(nomePeca + ": [ linha " + posicaoDasPecas.get(nomePeca)[0] + ", coluna " + posicaoDasPecas.get(nomePeca)[1] + " ]");
            }
        }
    }

    public Map<String, List<int[]>> calcularMovimentosPorPeca(int linhaPeca, int colunaPeca) {


        Map<String, List<int[]>> movimentosDaPeca = new LinkedHashMap<>();

        Peca peca = casas[linhaPeca][colunaPeca];

        if (peca.getTipoPeca() == TipoPeca.TORRE) {

            movimentosDaPeca.put("Vertical", calcularMovimentosDisponiveisPorTipoMovimento(new int[]{linhaPeca, colunaPeca},
                                                                                           peca.getJogador().getJogadorId(),
                                                                                           TipoMovimentoDaPeca.VERTICAL));


            movimentosDaPeca.put("Horizontal", calcularMovimentosDisponiveisPorTipoMovimento(new int[]{linhaPeca, colunaPeca},
                                                                                             peca.getJogador().getJogadorId(),
                                                                                             TipoMovimentoDaPeca.HORIZONTAL));


        }

        else if(peca.getTipoPeca() == TipoPeca.BISPO) {


            movimentosDaPeca.put("Diagonal \\", calcularMovimentosDisponiveisPorTipoMovimento(new int[]{linhaPeca, colunaPeca},
                                                                                              peca.getJogador().getJogadorId(),
                                                                                              TipoMovimentoDaPeca.DIAGONAL_ESQUERDA_PARA_DIREITA));


            movimentosDaPeca.put("Diagonal /", calcularMovimentosDisponiveisPorTipoMovimento(new int[]{linhaPeca, colunaPeca},
                                                                                             peca.getJogador().getJogadorId(),
                                                                                             TipoMovimentoDaPeca.DIAGONAL_DIREITA_PARA_ESQUERDA));
 }

        else if(peca.getTipoPeca() == TipoPeca.RAINHA || peca.getTipoPeca() == TipoPeca.REI) {

            movimentosDaPeca.put("Vertical", calcularMovimentosDisponiveisPorTipoMovimento(new int[]{linhaPeca, colunaPeca},
                    peca.getJogador().getJogadorId(),
                    TipoMovimentoDaPeca.VERTICAL));


            movimentosDaPeca.put("Horizontal", calcularMovimentosDisponiveisPorTipoMovimento(new int[]{linhaPeca, colunaPeca},
                    peca.getJogador().getJogadorId(),
                    TipoMovimentoDaPeca.HORIZONTAL));



            movimentosDaPeca.put("Diagonal \\", calcularMovimentosDisponiveisPorTipoMovimento(new int[]{linhaPeca, colunaPeca},
                    peca.getJogador().getJogadorId(),
                    TipoMovimentoDaPeca.DIAGONAL_ESQUERDA_PARA_DIREITA));


            movimentosDaPeca.put("Diagonal /", calcularMovimentosDisponiveisPorTipoMovimento(new int[]{linhaPeca, colunaPeca},
                    peca.getJogador().getJogadorId(),
                    TipoMovimentoDaPeca.DIAGONAL_DIREITA_PARA_ESQUERDA));


        }

        return movimentosDaPeca;
    }


    private List<int[]> calcularMovimentosDisponiveisPorTipoMovimento(int[] posicaoAtualPeca, int jogadorId, TipoMovimentoDaPeca tipoMovimentoPeca) {
        List<int[]> movimentosDisponiveis = new ArrayList<>();

        Supplier<Boolean> analisarPosicaoAnterior;
        Supplier<Boolean> analisarProximaPosicao;

        int[] posicaoAnteriorPeca = new int[2];
        int[] proximaPosicaoPeca = new int[2];

        if(tipoMovimentoPeca == TipoMovimentoDaPeca.HORIZONTAL) {

            //Valores iniciais de linha([0]), coluna([1]) e validação para antes da peça
            posicaoAnteriorPeca[0] = posicaoAtualPeca[0];
            posicaoAnteriorPeca[1] = posicaoAtualPeca[1] - 1;

            analisarPosicaoAnterior = () -> posicaoAnteriorPeca[1] > -1;


            //Valores iniciais de linha([0]), coluna([1]) e validação para após a peça
            proximaPosicaoPeca[0] = posicaoAtualPeca[0];;
            proximaPosicaoPeca[1] = posicaoAtualPeca[1] + 1;

            analisarProximaPosicao = () -> proximaPosicaoPeca[1] < 8;
        }

        else if(tipoMovimentoPeca == TipoMovimentoDaPeca.VERTICAL) {

            //Valores iniciais de linha([0]), coluna([1]) e validação para antes da peça
            posicaoAnteriorPeca[0] = posicaoAtualPeca[0] - 1;
            posicaoAnteriorPeca[1] = posicaoAtualPeca[1];

            analisarPosicaoAnterior = () -> posicaoAnteriorPeca[0] > -1;


            //Valores iniciais de linha([0]), coluna([1]) e validação para após a peça
            proximaPosicaoPeca[0] = posicaoAtualPeca[0] + 1;
            proximaPosicaoPeca[1] = posicaoAtualPeca[1];

            analisarProximaPosicao = () -> proximaPosicaoPeca[0] < 8;

        }

        else if (tipoMovimentoPeca == TipoMovimentoDaPeca.DIAGONAL_ESQUERDA_PARA_DIREITA) {

            //Valores iniciais de linha([0]), coluna([1]) e validação para antes da peça
            posicaoAnteriorPeca[0] = posicaoAtualPeca[0] - 1;
            posicaoAnteriorPeca[1] = posicaoAtualPeca[1] - 1;

            analisarPosicaoAnterior = () -> posicaoAnteriorPeca[0] > -1 & posicaoAnteriorPeca[1] > -1;


            //Valores iniciais de linha([0]), coluna([1]) e validação para após a peça
            proximaPosicaoPeca[0] = posicaoAtualPeca[0] + 1;
            proximaPosicaoPeca[1] = posicaoAtualPeca[1] + 1;

            analisarProximaPosicao = () -> proximaPosicaoPeca[0] < 8 & proximaPosicaoPeca[1] < 8;
        }

        else { //Diagonal Direita para Esquerda /

            //Valores iniciais de linha([0]), coluna([1]) e validação para antes da peça
            posicaoAnteriorPeca[0] = posicaoAtualPeca[0] + 1;
            posicaoAnteriorPeca[1] = posicaoAtualPeca[1] - 1;

            analisarPosicaoAnterior = () -> posicaoAnteriorPeca[0] < 8 & posicaoAnteriorPeca[1] > -1;


            //Valores iniciais de linha([0]), coluna([1]) e validação para após a peça
            proximaPosicaoPeca[0] = posicaoAtualPeca[0] - 1;
            proximaPosicaoPeca[1] = posicaoAtualPeca[1] + 1;

            analisarProximaPosicao = () -> proximaPosicaoPeca[0] > -1 & proximaPosicaoPeca[1] < 8;

        }



        //ANÁLISE ANTES DA PEÇA
        while(analisarPosicaoAnterior.get()) {

            if (casas[posicaoAnteriorPeca[0]][posicaoAnteriorPeca[1]] == null) { //Casa com peça vazia
                movimentosDisponiveis.addFirst(new int[]{posicaoAnteriorPeca[0], posicaoAnteriorPeca[1]});
            }

            else if (casas[posicaoAnteriorPeca[0]][posicaoAnteriorPeca[1]].getJogador().getJogadorId() != jogadorId) {

                System.out.println("Identificado peça inimiga na posição linha " + posicaoAnteriorPeca[0] + ", coluna " + posicaoAnteriorPeca[1]);
                movimentosDisponiveis.addFirst(new int[]{posicaoAnteriorPeca[0], posicaoAnteriorPeca[1]});
                break;

            } //Casa com Peça Inimiga

            else {
                break;
            } //Casa com Peça Aliada



            if(tipoMovimentoPeca == TipoMovimentoDaPeca.HORIZONTAL) {
                posicaoAnteriorPeca[1]--;
            }

            else if(tipoMovimentoPeca == TipoMovimentoDaPeca.VERTICAL) {
                posicaoAnteriorPeca[0]--;
            }

            else if (tipoMovimentoPeca == TipoMovimentoDaPeca.DIAGONAL_ESQUERDA_PARA_DIREITA) {
                posicaoAnteriorPeca[0]--;
                posicaoAnteriorPeca[1]--;
            }

            else { // Diagonal Direita para Esquerda /
                posicaoAnteriorPeca[0]++;
                posicaoAnteriorPeca[1]--;
            }
        }

        
        //ANÁLISE APÓS A PEÇA
        while(analisarProximaPosicao.get()) {

            if (casas[proximaPosicaoPeca[0]][proximaPosicaoPeca[1]] == null) { //Casa com peça vazia
                movimentosDisponiveis.add(new int[]{proximaPosicaoPeca[0], proximaPosicaoPeca[1]});
            }

            else if (casas[proximaPosicaoPeca[0]][proximaPosicaoPeca[1]].getJogador().getJogadorId() != jogadorId) {

                System.out.println("Identificado peça inimiga na posição linha " + proximaPosicaoPeca[0] + ", coluna " + proximaPosicaoPeca[1]);
                movimentosDisponiveis.add(new int[]{proximaPosicaoPeca[0], proximaPosicaoPeca[1]});
                break;

            } //Casa com Peça Inimiga

            else {
                break;
            } //Casa com Peça Aliada



            if(tipoMovimentoPeca == TipoMovimentoDaPeca.HORIZONTAL) {
                proximaPosicaoPeca[1]++;
            }

            else if(tipoMovimentoPeca == TipoMovimentoDaPeca.VERTICAL) {
                proximaPosicaoPeca[0]++;
            }

            else if (tipoMovimentoPeca == TipoMovimentoDaPeca.DIAGONAL_ESQUERDA_PARA_DIREITA) {
                proximaPosicaoPeca[0]++;
                proximaPosicaoPeca[1]++;
            }

            else { // Diagonal Direita para Esquerda /
                proximaPosicaoPeca[0]--;
                proximaPosicaoPeca[1]++;
            }
        }

        return movimentosDisponiveis;
    }

    private List<Peca> gerarPecas(String[] pecasPorLinha, Jogador jogador, CorPeca corPeca) {

        List<Peca> pecas = new ArrayList<>();

        //.split("_") separa o nome de peças como Peão, Bispo, Cavalo e Torre que tem um Id vinculado
        //ao nome.

        for(String nomePeca : pecasPorLinha) {

            pecas.add(new Peca(corPeca, TipoPeca.valueOf(nomePeca.split("_")[0]), nomePeca + "_JG" + jogador.getJogadorId(), jogador));
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
