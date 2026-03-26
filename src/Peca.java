public abstract class Peca {

    private CorPeca cor;
    private String nome;
    private Jogador jogador;
    private TipoMovimentoDaPeca[] tiposMovimentoDaPeca;
    private Integer quantidadeMaximaDeMovimentoPorTipoMovimento;


    public Peca(CorPeca cor,
                String nome,
                Jogador jogador,
                Integer quantidadeMaximaDeMovimentoPorTipoMovimento,
                TipoMovimentoDaPeca[] tiposMovimentoDaPeca
                ) {

        this.cor = cor;
        this.nome = nome;
        this.jogador = jogador;
        this.tiposMovimentoDaPeca = tiposMovimentoDaPeca;
        this.quantidadeMaximaDeMovimentoPorTipoMovimento = quantidadeMaximaDeMovimentoPorTipoMovimento;
    }


    public CorPeca getCor() {
        return cor;
    }

    public void setCor(CorPeca cor) {
        this.cor = cor;
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

    public TipoMovimentoDaPeca[] getTiposMovimentoDaPeca() {
        return tiposMovimentoDaPeca;
    }

    public void setTiposMovimentoDaPeca(TipoMovimentoDaPeca[] tiposMovimentoDaPeca) {
        this.tiposMovimentoDaPeca = tiposMovimentoDaPeca;
    }

    public Integer getQuantidadeMaximaDeMovimentoPorTipoMovimento() {
        return quantidadeMaximaDeMovimentoPorTipoMovimento;
    }

    public void setQuantidadeMaximaDeMovimentoPorTipoMovimento(Integer quantidadeMaximaDeMovimentoPorTipoMovimento) {
        this.quantidadeMaximaDeMovimentoPorTipoMovimento = quantidadeMaximaDeMovimentoPorTipoMovimento;
    }
}
