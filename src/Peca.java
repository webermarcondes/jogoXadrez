public class Peca {

    private CorPeca cor;
    private TipoPeca tipoPeca;
    private String nome;
    private Jogador jogador;


    public Peca(CorPeca cor, TipoPeca tipoPeca, String nome, Jogador jogador) {
        this.cor = cor;
        this.tipoPeca = tipoPeca;
        this.nome = nome;
        this.jogador = jogador;
    }


    public CorPeca getCor() {
        return cor;
    }

    public void setCor(CorPeca cor) {
        this.cor = cor;
    }

    public TipoPeca getTipoPeca() {
        return tipoPeca;
    }

    public void setTipoPeca(TipoPeca tipoPeca) {
        this.tipoPeca = tipoPeca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
}
