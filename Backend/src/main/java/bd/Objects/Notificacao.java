package bd.Objects;

public class Notificacao {
    private int id;
    private String gestor;
    private String msg;

    public Notificacao(int id, String gestor, String msg) {
        this.id = id;
        this.gestor = gestor;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Notificacao{" +
                "id=" + id +
                ", gestor='" + gestor + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
