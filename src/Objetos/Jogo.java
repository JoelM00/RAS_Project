package Objetos;

import java.util.List;

public class Jogo {
    private int id;
    private String descricao;
    private String estado;
    private String data;
    private String local;
    private String gestor;
    private boolean aceitaApostas;
    private int numApostas;

    public Jogo(int id, String desc, String estado, String data, String local, boolean apostas){
        this.id = id;
        this.descricao = desc;
        this.estado = estado;
        this.data = data;
        this.local = local;
        this.aceitaApostas = apostas;
    }

    @Override
    public String toString() {
        return "Jogo{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", estado='" + estado + '\'' +
                ", data='" + data + '\'' +
                ", local='" + local + '\'' +
                ", gestor='" + gestor + '\'' +
                ", aceitaApostas=" + aceitaApostas +
                ", numApostas=" + numApostas +
                '}';
    }
}
