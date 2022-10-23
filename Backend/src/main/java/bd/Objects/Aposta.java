package bd.Objects;

public class Aposta {
    private int id;
    private float minAposta;
    private float maxAposta;
    private float rate;
    private int idJogo;
    private String tipo;
    private int posicao;
    private String equipaNome;
    private Boolean resultado;

    public Aposta(int id,float minAposta, float maxAposta, float rate, String tipo, int posicao, String equipa_idNome){
        this.id = id;
        this.minAposta = minAposta;
        this.maxAposta = maxAposta;
        this.rate = rate;
        this.tipo = tipo;
        this.posicao = posicao;
        this.equipaNome = equipa_idNome;
    }

    public Aposta(int id, float minAposta, float maxAposta, float rate, int idJogo, String tipo, int posicao, String equipaNome,Boolean resultado) {
        this.id = id;
        this.minAposta = minAposta;
        this.maxAposta = maxAposta;
        this.rate = rate;
        this.idJogo = idJogo;
        this.tipo = tipo;
        this.posicao = posicao;
        this.equipaNome = equipaNome;
        this.resultado = resultado;
    }

    public Boolean getResultado() {
        return resultado;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    public float getMinAposta() {
        return minAposta;
    }

    public void setMinAposta(float minAposta) {
        this.minAposta = minAposta;
    }

    public float getMaxAposta() {
        return maxAposta;
    }

    public void setMaxAposta(float maxAposta) {
        this.maxAposta = maxAposta;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(int idJogo) {
        this.idJogo = idJogo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getEquipaNome() {
        return equipaNome;
    }

    public void setEquipaNome(String equipaNome) {
        this.equipaNome = equipaNome;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Aposta{" +
                "minAposta=" + minAposta +
                ", maxAposta=" + maxAposta +
                ", rate=" + rate +
                ", idJogo=" + idJogo +
                ", tipo='" + tipo + '\'' +
                ", posicao=" + posicao +
                ", equipaNome='" + equipaNome + '\'' +
                ", resultado=" + resultado +
                ", id=" + id +
                '}';
    }
}
