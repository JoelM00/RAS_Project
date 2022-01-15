package Objetos;

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
}
