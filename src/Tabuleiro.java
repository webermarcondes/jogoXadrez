import java.util.*;

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


            movimentosDaPeca.put("Vertical", calcularMovimentoVertical(linhaPeca,
                                                                        colunaPeca,
                                                                        peca.getJogador().getJogadorId()));




            movimentosDaPeca.put("Horizontal", calcularMovimentoHorizontal(linhaPeca,
                                                                           colunaPeca,
                                                                            peca.getJogador().getJogadorId()));
        }

        else if(peca.getTipoPeca() == TipoPeca.BISPO) {
            movimentosDaPeca.put("Diagonal \\", calcularMovimentosDiagonalEsquerdaParaDireita(linhaPeca,
                                                                        colunaPeca,
                                                                        peca.getJogador().getJogadorId()));

            movimentosDaPeca.put("Diagonal /", calcularMovimentosDiagonalDireitaParaEsquerda(linhaPeca,
                                                                                              colunaPeca,
                                                                                              peca.getJogador().getJogadorId()));
        }

        else if(peca.getTipoPeca() == TipoPeca.RAINHA || peca.getTipoPeca() == TipoPeca.REI) {

            movimentosDaPeca.put("Vertical", calcularMovimentoVertical(linhaPeca,
                    colunaPeca,
                    peca.getJogador().getJogadorId()));




            movimentosDaPeca.put("Horizontal", calcularMovimentoHorizontal(linhaPeca,
                    colunaPeca,
                    peca.getJogador().getJogadorId()));


            movimentosDaPeca.put("Diagonal \\", calcularMovimentosDiagonalEsquerdaParaDireita(linhaPeca,
                    colunaPeca,
                    peca.getJogador().getJogadorId()));

            movimentosDaPeca.put("Diagonal /", calcularMovimentosDiagonalDireitaParaEsquerda(linhaPeca,
                    colunaPeca,
                    peca.getJogador().getJogadorId()));

        }

        return movimentosDaPeca;
    }



    private List<int[]> calcularMovimentosDiagonalEsquerdaParaDireita(int linhaAtualPeca, int colunaAtualPeca, Integer jogadorId) {

        List<int[]> movimentosDiagonalEsquerdaParaDireita = new ArrayList<>();


        int linhaAnteriorPeca = linhaAtualPeca - 1;
        int colunaAnteriorPeca = colunaAtualPeca - 1;

        int proximaLinhaPeca = linhaAtualPeca + 1;
        int proximaColunaPeca = colunaAtualPeca + 1;

        //Analise antes da peça
        while (linhaAnteriorPeca >= 0 & colunaAnteriorPeca >= 0) {

            if (casas[linhaAnteriorPeca][colunaAnteriorPeca] == null) { //Casa com peça vazia
                movimentosDiagonalEsquerdaParaDireita.addFirst(new int[]{linhaAnteriorPeca, colunaAnteriorPeca});
            }

            else if (casas[linhaAnteriorPeca][colunaAnteriorPeca].getJogador().getJogadorId() != jogadorId) {

                System.out.println("Identificado peça inimiga na posição linha " + linhaAnteriorPeca + ", coluna " + colunaAnteriorPeca);
                movimentosDiagonalEsquerdaParaDireita.addFirst(new int[]{linhaAnteriorPeca, colunaAnteriorPeca});
                break;

            } //Casa com Peça Inimiga

            else {
                break;
            } //Casa com Peça Aliada

            --linhaAnteriorPeca;
            --colunaAnteriorPeca;
        }

        //Analise após a peça
        while (proximaLinhaPeca <= 7 & proximaColunaPeca <= 7) {

            if (casas[proximaLinhaPeca][proximaColunaPeca] == null) { //Casa com peça vazia
                movimentosDiagonalEsquerdaParaDireita.add(new int[]{proximaLinhaPeca, proximaColunaPeca});
            }

            else if (casas[proximaLinhaPeca][proximaColunaPeca].getJogador().getJogadorId() != jogadorId) {

                System.out.println("Identificado peça inimiga na posição linha " + proximaLinhaPeca + ", coluna " + proximaColunaPeca);
                movimentosDiagonalEsquerdaParaDireita.add(new int[]{proximaLinhaPeca, proximaColunaPeca});
                break;


            } //Casa com Peça Inimiga

            else {
                break;
            } //Casa com Peça Aliada

            ++proximaLinhaPeca;
            ++proximaColunaPeca;
        }

        return movimentosDiagonalEsquerdaParaDireita;

    }


    private List<int[]> calcularMovimentosDiagonalDireitaParaEsquerda(int linhaAtualPeca, int colunaAtualPeca, int jogadorId) {

        List<int[]> movimentosDiagonalDireitaParaEsquerda = new ArrayList<>();


        //Anterior a peça
        int linhaAnteriorPeca = linhaAtualPeca + 1;
        int colunaAnteriorPeca = colunaAtualPeca - 1;

        //Após a Peça
        int proximaLinhaPeca = linhaAtualPeca - 1;
        int proximaColunaPeca = colunaAtualPeca + 1;

        //Análise antes da peça
        while (linhaAnteriorPeca <= 7 & colunaAnteriorPeca >= 0) {

            if (casas[linhaAnteriorPeca][colunaAnteriorPeca] == null) { //Casa com peça vazia
                movimentosDiagonalDireitaParaEsquerda.add(new int[]{linhaAnteriorPeca, colunaAnteriorPeca});
            }

            else if (casas[linhaAnteriorPeca][colunaAnteriorPeca].getJogador().getJogadorId() != jogadorId) {

                System.out.println("Identificado peça inimiga na posição linha " + linhaAnteriorPeca + ", coluna " + colunaAnteriorPeca);
                movimentosDiagonalDireitaParaEsquerda.add(new int[]{linhaAnteriorPeca, colunaAnteriorPeca});
                break;


            } //Casa com Peça Inimiga

            else {
                break;
            } //Casa com Peça Aliada


            ++linhaAnteriorPeca;
            --colunaAnteriorPeca;

        }

        //Análise após a peça
        while (proximaLinhaPeca >= 0 & proximaColunaPeca <= 7) {

            if (casas[proximaLinhaPeca][proximaColunaPeca] == null) { //Casa com peça vazia
                movimentosDiagonalDireitaParaEsquerda.addFirst(new int[]{proximaLinhaPeca, proximaColunaPeca});
            }

            else if (casas[proximaLinhaPeca][proximaColunaPeca].getJogador().getJogadorId() != jogadorId) {

                System.out.println("Identificado peça inimiga na posição linha " + proximaLinhaPeca + ", coluna " + proximaColunaPeca);
                movimentosDiagonalDireitaParaEsquerda.addFirst(new int[]{proximaLinhaPeca, proximaColunaPeca});
                break;

            } //Casa com Peça Inimiga

            else {
                break;
            } //Casa com Peça Aliada

            --proximaLinhaPeca;
            ++proximaColunaPeca;

        }

        return movimentosDiagonalDireitaParaEsquerda;
    }

    private List<int[]> calcularMovimentoHorizontal(int linhaAtualPeca, int colunaAtualPeca, Integer jogadorId) {

        List<int[]> movimentosHorizontais = new ArrayList<>();


        //Calculo de posiçoes livres anteriores a Coluna da Peça
        if(colunaAtualPeca > 0) {
            for (int colunaAnterior = colunaAtualPeca-1; colunaAnterior >= 0; colunaAnterior--) {

                if (casas[linhaAtualPeca][colunaAnterior] == null) { //Casa com peça vazia
                    movimentosHorizontais.addFirst(new int[]{linhaAtualPeca, colunaAnterior});
                }

                else if (casas[linhaAtualPeca][colunaAnterior].getJogador().getJogadorId() != jogadorId) {

                    System.out.println("Identificado peça inimiga na posição linha " + linhaAtualPeca + ", coluna " + colunaAnterior);
                    movimentosHorizontais.addFirst(new int[]{linhaAtualPeca, colunaAnterior});
                    break;

                } //Casa com Peça Inimiga

                else {
                    break;
                }
            }
        }

        //Calculo das posições Livres após a Coluna da Peça
        if(colunaAtualPeca < 7) {
            for (int proximaColuna = colunaAtualPeca+1; proximaColuna < 8; proximaColuna++) {

                if (casas[linhaAtualPeca][proximaColuna] == null) { //Casa com peça vazia
                    movimentosHorizontais.add(new int[]{linhaAtualPeca, proximaColuna});
                }

                else if (casas[linhaAtualPeca][proximaColuna].getJogador().getJogadorId() != jogadorId) {

                    System.out.println("Identificado peça inimiga na posição linha " + linhaAtualPeca + ", coluna " + proximaColuna);
                    movimentosHorizontais.add(new int[]{linhaAtualPeca, proximaColuna});
                    break;

                } //Casa com Peça Inimiga

                else {
                    break;
                }
            }
        }


        return movimentosHorizontais;


    }

    private List<int[]> calcularMovimentoVertical(int linhaAtualPeca, int colunaAtualPeca, int jogadorId) {

        List<int[]> movimentosVerticais = new ArrayList<>();


        //Calculo de posiçoes livres anteriores a Linha da Peça
        if(linhaAtualPeca > 0) {
            for (int linhaAnterior = linhaAtualPeca-1; linhaAnterior >= 0; linhaAnterior--) {

                if (casas[linhaAnterior][colunaAtualPeca] == null) { //Casa com peça vazia
                    movimentosVerticais.addFirst(new int[]{linhaAnterior, colunaAtualPeca});
                }

                else if (casas[linhaAnterior][colunaAtualPeca].getJogador().getJogadorId() != jogadorId) {

                    System.out.println("Identificado peça inimiga na posição linha " + linhaAnterior + ", coluna " + colunaAtualPeca);
                    movimentosVerticais.addFirst(new int[]{linhaAnterior, colunaAtualPeca});
                    break;

                } //Casa com Peça Inimiga

                else {
                    break;
                }
            }
        }

        //Calculo das posições Livres após a Linha da Peça
        if(linhaAtualPeca < 7) {
            for(int proximaLinha = linhaAtualPeca + 1; proximaLinha < 8; proximaLinha++) {

                if (casas[proximaLinha][colunaAtualPeca] == null) { //Casa com peça vazia
                    movimentosVerticais.add(new int[]{proximaLinha, colunaAtualPeca});
                }

                else if (casas[proximaLinha][colunaAtualPeca].getJogador().getJogadorId() != jogadorId) {

                    System.out.println("Identificado peça inimiga na posição linha " + proximaLinha + ", coluna " + colunaAtualPeca);
                    movimentosVerticais.add(new int[]{proximaLinha, colunaAtualPeca});
                    break;

                } //Casa com Peça Inimiga

                else {
                    break;
                }
            }
        }


        return movimentosVerticais;

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
