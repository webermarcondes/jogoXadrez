import enums.CorPeca;
import enums.TipoDeCasaDoTabuleiro;
import enums.TipoMovimentoDaPeca;
import jogadores.Jogador;
import pecas.Peca;

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

    public Map<String, Map<TipoDeCasaDoTabuleiro,List<int[]>>> calcularMovimentosPorPeca(int[] posicaoDaPeca) {


        Map<String, Map<TipoDeCasaDoTabuleiro,List<int[]>>> movimentosDaPeca = new LinkedHashMap<>();

        for(TipoMovimentoDaPeca tipoMovimento: casas[posicaoDaPeca[0]][posicaoDaPeca[1]].getTiposMovimentoDaPeca()) {

            if(tipoMovimento == TipoMovimentoDaPeca.VERTICAL_CIMA || tipoMovimento == TipoMovimentoDaPeca.VERTICAL_BAIXO) {

                movimentosDaPeca.put(tipoMovimento.name(), calcularMovimentosDoPeao(posicaoDaPeca,
                                                                                    casas[posicaoDaPeca[0]][posicaoDaPeca[1]].getJogador().getJogadorId(),
                                                                                    tipoMovimento));
            }

            else if (tipoMovimento == TipoMovimentoDaPeca.MOVIMENTO_EM_L) {

                movimentosDaPeca.put(tipoMovimento.name(), calcularMovimentosDoCavalo(posicaoDaPeca,
                                                                                      casas[posicaoDaPeca[0]][posicaoDaPeca[1]].getJogador().getJogadorId()));

            }


            else if(tipoMovimento == TipoMovimentoDaPeca.MOVIMENTO_DO_REI) {

                movimentosDaPeca.put(tipoMovimento.name(), calcularMovimentosDoRei(posicaoDaPeca,
                                                                                   casas[posicaoDaPeca[0]][posicaoDaPeca[1]].getJogador().getJogadorId()));
            }


            else {
                //calculo dos movimentos de ataque e movimentos livres para
                //torre, bispo e rainha que andam na mesma direção em que atacam, diferente de peças
                //como o peão que anda reto e ataca em diagonal.


                movimentosDaPeca.put(tipoMovimento.name(), calcularMovimentosDisponiveisParaAMesmaDirecao(posicaoDaPeca,
                                                                                                          casas[posicaoDaPeca[0]][posicaoDaPeca[1]].getJogador().getJogadorId(),
                                                                                                          tipoMovimento));

                }
            }

        return movimentosDaPeca;
    }


    private Map<TipoDeCasaDoTabuleiro, List<int[]>> calcularMovimentosDoCavalo(int[] posicaoAtualPeca, int jogadorId) {

        Map<TipoDeCasaDoTabuleiro, List<int[]>> movimentosValidosDoCavalo = new LinkedHashMap<>();
        movimentosValidosDoCavalo.put(TipoDeCasaDoTabuleiro.CASA_VAZIA, new ArrayList<int[]>());
        movimentosValidosDoCavalo.put(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO, new ArrayList<int[]>());

        List<int[]> possiveisMovimentosDoCavalo = new ArrayList<>();

        //Possiveis movimentos da área 01 (Cima)
        possiveisMovimentosDoCavalo.add(new int[]{posicaoAtualPeca[0]-2, posicaoAtualPeca[1]-1});
        possiveisMovimentosDoCavalo.add(new int[]{posicaoAtualPeca[0]-2, posicaoAtualPeca[1]+1});

        //Possiveis movimentos da área 02 (Direita)
        possiveisMovimentosDoCavalo.add(new int[]{posicaoAtualPeca[0]-1, posicaoAtualPeca[1]+2});
        possiveisMovimentosDoCavalo.add(new int[]{posicaoAtualPeca[0]+1, posicaoAtualPeca[1]+2});

        //Possiveis movimentos da área 03 (Baixo)
        possiveisMovimentosDoCavalo.add(new int[]{posicaoAtualPeca[0]+2, posicaoAtualPeca[1]-1});
        possiveisMovimentosDoCavalo.add(new int[]{posicaoAtualPeca[0]+2, posicaoAtualPeca[1]+1});

        //Possiveis movimentos da área 04 (Esquerda)
        possiveisMovimentosDoCavalo.add(new int[]{posicaoAtualPeca[0]-1, posicaoAtualPeca[1]-2});
        possiveisMovimentosDoCavalo.add(new int[]{posicaoAtualPeca[0]+1, posicaoAtualPeca[1]-2});


        for(int[] movimento: possiveisMovimentosDoCavalo) {
            if((movimento[0] > -1 && movimento[0] < 8) && (movimento[1] > -1 && movimento[1] < 8)) {

                    if(casas[movimento[0]][movimento[1]] == null) {
                        movimentosValidosDoCavalo.get(TipoDeCasaDoTabuleiro.CASA_VAZIA).add(movimento);
                    }

                    else if(casas[movimento[0]][movimento[1]].getJogador().getJogadorId() != jogadorId) {
                        movimentosValidosDoCavalo.get(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO).add(movimento);
                    }

            }
        }

        return movimentosValidosDoCavalo;

    }


    private Map<TipoDeCasaDoTabuleiro, List<int[]>> calcularMovimentosDoPeao(int[] posicaoAtualPeca, int jogadorId, TipoMovimentoDaPeca tipoMovimentoDoPeao) {

        Map<TipoDeCasaDoTabuleiro, List<int[]>> movimentosDisponiveis = new LinkedHashMap<>();
        movimentosDisponiveis.put(TipoDeCasaDoTabuleiro.CASA_VAZIA, new ArrayList<int[]>());
        movimentosDisponiveis.put(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO, new ArrayList<int[]>());

        List<int[]> possiveisMovimentosParaAtaque = new ArrayList<>();

        int quantidadeDeMovimentosAnalisados = 0;

        Supplier<Boolean> analisarProximaPosicao;
        int[] proximaPosicaoPeca = new int[2];

        if(tipoMovimentoDoPeao == TipoMovimentoDaPeca.VERTICAL_CIMA) {

            proximaPosicaoPeca[0] = posicaoAtualPeca[0] - 1;
            proximaPosicaoPeca[1] = posicaoAtualPeca[1];

            possiveisMovimentosParaAtaque.add(new int[]{posicaoAtualPeca[0]-1, posicaoAtualPeca[1]-1});
            possiveisMovimentosParaAtaque.add(new int[]{posicaoAtualPeca[0]-1, posicaoAtualPeca[1]+1});

            analisarProximaPosicao = () -> proximaPosicaoPeca[0] > -1;

        }

        else { // Vertical para baixo
            proximaPosicaoPeca[0] = posicaoAtualPeca[0] + 1;
            proximaPosicaoPeca[1] = posicaoAtualPeca[1];

            possiveisMovimentosParaAtaque.add(new int[]{posicaoAtualPeca[0]+1, posicaoAtualPeca[1]-1});
            possiveisMovimentosParaAtaque.add(new int[]{posicaoAtualPeca[0]+1, posicaoAtualPeca[1]+1});


            analisarProximaPosicao = () -> proximaPosicaoPeca[0] < 8;
        }


        //Lógica de movimento do peão
        while(analisarProximaPosicao.get() && quantidadeDeMovimentosAnalisados < casas[posicaoAtualPeca[0]][posicaoAtualPeca[1]].getQuantidadeMaximaDeMovimentoPorTipoMovimento()) {

            if (casas[proximaPosicaoPeca[0]][proximaPosicaoPeca[1]] == null) { //Casa com peça vazia

                movimentosDisponiveis.get(TipoDeCasaDoTabuleiro.CASA_VAZIA).add(new int[]{proximaPosicaoPeca[0], proximaPosicaoPeca[1]});

            }

            else {
                break;
            } //Casa com Peça Aliada ou Inimiga que bloqueiam o movimento do Peão



            if(tipoMovimentoDoPeao == TipoMovimentoDaPeca.VERTICAL_CIMA) {
                proximaPosicaoPeca[0]--;
            }

            else { //Vertical para baixo
                proximaPosicaoPeca[0]++;
            }

            quantidadeDeMovimentosAnalisados++;

        }

        //Lógica de análise em diagonal para ataque do peão
        Peca peca;
        for(int[] movimento: possiveisMovimentosParaAtaque) {

            if((movimento[0] > -1 && movimento[0] < 8) && (movimento[1] > -1 && movimento[1] < 8)) {
                peca = casas[movimento[0]][movimento[1]];
                if(peca != null && peca.getJogador().getJogadorId() != jogadorId) {

                    movimentosDisponiveis.get(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO).add(movimento);


                }
            }
        }


        return movimentosDisponiveis;
    }

    private Map<TipoDeCasaDoTabuleiro, List<int[]>> calcularMovimentosDoRei(int[] posicaoAtualPeca, int jogadorId) {

        Map<TipoDeCasaDoTabuleiro, List<int[]>> movimentosValidosDoRei = new LinkedHashMap<>();
        movimentosValidosDoRei.put(TipoDeCasaDoTabuleiro.CASA_VAZIA, new ArrayList<int[]>());
        movimentosValidosDoRei.put(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO, new ArrayList<int[]>());

        List<int[]> possiveisMovimentosDoRei = new ArrayList<>();

        //Movimentos Horizontais
        possiveisMovimentosDoRei.add(new int[]{posicaoAtualPeca[0], posicaoAtualPeca[1]-1});
        possiveisMovimentosDoRei.add(new int[]{posicaoAtualPeca[0], posicaoAtualPeca[1]+1});

        //Movimentos Verticais
        possiveisMovimentosDoRei.add(new int[]{posicaoAtualPeca[0]-1, posicaoAtualPeca[1]});
        possiveisMovimentosDoRei.add(new int[]{posicaoAtualPeca[0]+1, posicaoAtualPeca[1]});

        //Movimentos Diagonal Esquerda para Direita
        possiveisMovimentosDoRei.add(new int[]{posicaoAtualPeca[0]-1, posicaoAtualPeca[1]-1});
        possiveisMovimentosDoRei.add(new int[]{posicaoAtualPeca[0]+1, posicaoAtualPeca[1]+1});

        //Movimentos Diagonal Direita para Esquerda

        possiveisMovimentosDoRei.add(new int[]{posicaoAtualPeca[0]-1, posicaoAtualPeca[1]+1});
        possiveisMovimentosDoRei.add(new int[]{posicaoAtualPeca[0]+1, posicaoAtualPeca[1]-1});


        for(int[] movimento: possiveisMovimentosDoRei) {
            if((movimento[0] > -1 && movimento[0] < 8) && (movimento[1] > -1 && movimento[1] < 8)) {

                if(casas[movimento[0]][movimento[1]] == null) {
                    movimentosValidosDoRei.get(TipoDeCasaDoTabuleiro.CASA_VAZIA).add(movimento);
                }

                else if(casas[movimento[0]][movimento[1]].getJogador().getJogadorId() != jogadorId) {
                    movimentosValidosDoRei.get(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO).add(movimento);
                }

            }
        }

        return movimentosValidosDoRei;

    }

    //responsável por calcular movimentos livres e de ataque
    //para vertical, horizontal e diagonal

    private Map<TipoDeCasaDoTabuleiro, List<int[]>> calcularMovimentosDisponiveisParaAMesmaDirecao(int[] posicaoAtualPeca, int jogadorId, TipoMovimentoDaPeca tipoMovimentoPeca) {

        Map<TipoDeCasaDoTabuleiro, List<int[]>> movimentosDisponiveis = new LinkedHashMap<>();
        movimentosDisponiveis.put(TipoDeCasaDoTabuleiro.CASA_VAZIA, new ArrayList<int[]>());
        movimentosDisponiveis.put(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO, new ArrayList<int[]>());

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

            if (casas[posicaoAnteriorPeca[0]][posicaoAnteriorPeca[1]] == null) {
                movimentosDisponiveis.get(TipoDeCasaDoTabuleiro.CASA_VAZIA).addFirst(new int[]{posicaoAnteriorPeca[0], posicaoAnteriorPeca[1]});

            }

            else if (casas[posicaoAnteriorPeca[0]][posicaoAnteriorPeca[1]].getJogador().getJogadorId() != jogadorId) {
                movimentosDisponiveis.get(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO).addFirst(new int[]{posicaoAnteriorPeca[0], posicaoAnteriorPeca[1]});
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

            if (casas[proximaPosicaoPeca[0]][proximaPosicaoPeca[1]] == null) {
                movimentosDisponiveis.get(TipoDeCasaDoTabuleiro.CASA_VAZIA).add(new int[]{proximaPosicaoPeca[0], proximaPosicaoPeca[1]});


            }

            else if (casas[proximaPosicaoPeca[0]][proximaPosicaoPeca[1]].getJogador().getJogadorId() != jogadorId) {
                movimentosDisponiveis.get(TipoDeCasaDoTabuleiro.CASA_OCUPADA_POR_INIMIGO).add(new int[]{proximaPosicaoPeca[0], proximaPosicaoPeca[1]});
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
        /*
        //.split("_") separa o nome de peças como Peão, pecas.Bispo, pecas.Cavalo e pecas.Torre que tem um Id vinculado
        //ao nome.

        for(String nomePeca : pecasPorLinha) {

            pecas.add(new pecas.Peca(corPeca, TipoPeca.valueOf(nomePeca.split("_")[0]), nomePeca + "_JG" + jogador.getJogadorId(), jogador));
        }
        */
        return pecas;
    }


    private String centralizarNomePeca(String texto, int largura) {
        if (texto.length() >= largura) return texto.substring(0, largura);

        int espacosEsquerda = (largura - texto.length()) / 2;
        int espacosDireita = largura - texto.length() - espacosEsquerda;

        return " ".repeat(espacosEsquerda) + texto + " ".repeat(espacosDireita);
    }


}
