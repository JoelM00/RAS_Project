package Objects;

public class Fatura {
    private String id;
    private float valor;
    private String tipoTransacao;
    private String date;
    private String estado;
    private String dataValidade;
    private String gestor;
    private String moeda;

    public Fatura(String id, float valor, String tipoTransacao, String date, String estado, String dataValidade, String gestor, String moeda) {
        this.id = id;
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.date = date;
        this.estado = estado;
        this.dataValidade = dataValidade;
        this.gestor = gestor;
        this.moeda = moeda;
    }
}
