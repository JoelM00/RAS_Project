package Objetos;

public class Aposta {
    private float minAposta;
    private float maxAposta;
    private float rate;
    private int idJogo;
    private String tipo;
    private String data;
    private int posicao;
    private String equipaNome;
    private boolean resultado;
    private int id;

    public Aposta(int id,float minAposta, float maxAposta, float rate, String tipo, int posicao, String equipa_idNome){
        this.id = id;
        this.minAposta = minAposta;
        this.maxAposta = maxAposta;
        this.rate = rate;
        this.tipo = tipo;
        this.posicao = posicao;
        this.equipaNome = equipa_idNome;
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
