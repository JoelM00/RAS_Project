package Objects;

public class ApostaUtilizador {
    private int id;
    private String data;
    private float valor;
    private float rate;
    private String tipo;
    private String equipaidNome;
    private String descricao;
    private boolean resultado;

    public ApostaUtilizador(int id, String data, float valor, float rate, String tipo, String equipaidNome, String descricao, boolean resultado) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.rate = rate;
        this.tipo = tipo;
        this.equipaidNome = equipaidNome;
        this.descricao = descricao;
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApostaUtilizador{");
        sb.append("id=").append(id);
        sb.append(", data='").append(data).append('\'');
        sb.append(", valor=").append(valor);
        sb.append(", rate=").append(rate);
        sb.append(", tipo='").append(tipo).append('\'');
        sb.append(", equipaidNome='").append(equipaidNome).append('\'');
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", resultado=").append(resultado);
        sb.append('}');
        return sb.toString();
    }
}
