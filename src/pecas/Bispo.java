package pecas;

import enums.CorPeca;
import enums.TipoMovimentoDaPeca;
import jogadores.Jogador;

public class Bispo extends Peca {

    public Bispo(CorPeca cor, String nome, Jogador jogador) {

        super(cor, nome, jogador, 7,
                new TipoMovimentoDaPeca[]{TipoMovimentoDaPeca.DIAGONAL_ESQUERDA_PARA_DIREITA,
                        TipoMovimentoDaPeca.DIAGONAL_DIREITA_PARA_ESQUERDA});
    }
}
