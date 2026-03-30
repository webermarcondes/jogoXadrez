package pecas;

import enums.CorPeca;
import enums.TipoMovimentoDaPeca;
import jogadores.Jogador;

public class Cavalo extends Peca {
    public Cavalo(CorPeca cor, String nome, Jogador jogador) {
        super(cor, nome, jogador, 3, new TipoMovimentoDaPeca[]{TipoMovimentoDaPeca.MOVIMENTO_EM_L});
    }
}
