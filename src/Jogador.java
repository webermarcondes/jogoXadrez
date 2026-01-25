public class Jogador {

    private TipoJogador tipoJogador;
    private String nome;
    private Integer jogadorId;


    public Jogador(TipoJogador tipoJogador, String nome, Integer jogadorId) {
        this.tipoJogador = tipoJogador;
        this.nome = nome;
        this.jogadorId = jogadorId;
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

    public Integer getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(Integer jogadorId) {
        this.jogadorId = jogadorId;
    }
}
