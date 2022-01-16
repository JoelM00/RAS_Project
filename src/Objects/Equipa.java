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
