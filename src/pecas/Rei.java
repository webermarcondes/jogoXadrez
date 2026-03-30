package pecas;

import enums.CorPeca;
import enums.TipoMovimentoDaPeca;
import jogadores.Jogador;


public class Rei extends Peca{

    public Rei(CorPeca cor, String nome, Jogador jogador) {

        super(cor, nome, jogador, 1,
                new TipoMovimentoDaPeca[]{
                        TipoMovimentoDaPeca.MOVIMENTO_DO_REI});
    }
}
