public class Jogador {

    private TipoJogador tipoJogador;
    private String nome;


    public Jogador(TipoJogador tipoJogador, String nome) {
        this.tipoJogador = tipoJogador;
        this.nome = nome;
    }

    public TipoJogador getTipoJogador() {
        return tipoJogador;
    }

    public void setTipoJogador(TipoJogador tipoJogador) {
        this.tipoJogador = tipoJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
