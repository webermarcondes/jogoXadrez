package pecas;

import enums.CorPeca;
import enums.TipoMovimentoDaPeca;
import jogadores.Jogador;

public class Peao extends Peca{
    public Peao(CorPeca cor, String nome, Jogador jogador, TipoMovimentoDaPeca tipoMovimentoDaPeca) {
        super(cor, nome, jogador, 2, new TipoMovimentoDaPeca[]{tipoMovimentoDaPeca});
    }
}
