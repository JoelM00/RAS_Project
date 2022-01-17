package Objects;

public class Equipa {
    private String idnome;
    private String localidade;
    private String liga;
    private String desporto;
    private String descricao;
    private String gestor;

    public Equipa(String idnome, String localidade, String liga, String desporto, String descricao, String gestor) {
        this.idnome = idnome;
        this.localidade = localidade;
        this.liga = liga;
        this.desporto = desporto;
        this.descricao = descricao;
        this.gestor = gestor;
    }

    public String getIdnome() {
        return idnome;
    }

    public void setIdnome(String idnome) {
        this.idnome = idnome;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getDesporto() {
        return desporto;
    }

    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    @Override
    public String toString() {
        return "Equipa{" +
                "idnome='" + idnome + '\'' +
                ", localidade='" + localidade + '\'' +
                ", liga='" + liga + '\'' +
                ", desporto='" + desporto + '\'' +
                ", descricao='" + descricao + '\'' +
                ", gestor='" + gestor + '\'' +
                '}';
    }
}
