public class Torre extends Peca{

    public Torre(CorPeca cor, String nome, Jogador jogador) {

        super(cor, nome, jogador, 7,
                new TipoMovimentoDaPeca[]{TipoMovimentoDaPeca.HORIZONTAL,
                                          TipoMovimentoDaPeca.VERTICAL});
    }
}
