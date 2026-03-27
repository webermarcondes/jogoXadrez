public class Rainha extends Peca{

    public Rainha(CorPeca cor, String nome, Jogador jogador) {

        super(cor, nome, jogador, 7,
                new TipoMovimentoDaPeca[]{
                        TipoMovimentoDaPeca.HORIZONTAL,
                        TipoMovimentoDaPeca.VERTICAL,
                        TipoMovimentoDaPeca.DIAGONAL_ESQUERDA_PARA_DIREITA,
                        TipoMovimentoDaPeca.DIAGONAL_DIREITA_PARA_ESQUERDA});
    }
}
