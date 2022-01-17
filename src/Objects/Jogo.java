package Objects;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public boolean isAceitaApostas() {
        return aceitaApostas;
    }

    public void setAceitaApostas(boolean aceitaApostas) {
        this.aceitaApostas = aceitaApostas;
    }

    public int getNumApostas() {
        return numApostas;
    }

    public void setNumApostas(int numApostas) {
        this.numApostas = numApostas;
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
