package bd.Objects;

import java.io.Serializable;

public class ApostaUtilizador implements Serializable {
    private int id;
    private String data;
    private float valor;
    private float rate;
    private String tipo;
    private String equipaidNome;
    private String descricao;
    private boolean resultado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEquipaidNome() {
        return equipaidNome;
    }

    public void setEquipaidNome(String equipaidNome) {
        this.equipaidNome = equipaidNome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

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
