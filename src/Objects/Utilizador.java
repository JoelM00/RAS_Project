package Objects;

public class Utilizador {
    private String username;
    private String password;
    private boolean gestor;
    private String genero;
    private int anoNascimento;
    private String localizacao;
    private int saldo;
    private String contaMultibanco;

    public Utilizador(String username, String password, boolean gestor, String genero, int anoNascimento, String localizacao, int saldo, String contaMultibanco) {
        this.username = username;
        this.password = password;
        this.gestor = gestor;
        this.genero = genero;
        this.anoNascimento = anoNascimento;
        this.localizacao = localizacao;
        this.saldo = saldo;
        this.contaMultibanco = contaMultibanco;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGestor() {
        return gestor;
    }

    public void setGestor(boolean gestor) {
        this.gestor = gestor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getContaMultibanco() {
        return contaMultibanco;
    }

    public void setContaMultibanco(String contaMultibanco) {
        this.contaMultibanco = contaMultibanco;
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gestor=" + gestor +
                ", genero='" + genero + '\'' +
                ", anoNascimento=" + anoNascimento +
                ", localizacao='" + localizacao + '\'' +
                ", saldo=" + saldo +
                ", contaMultibanco='" + contaMultibanco + '\'' +
                '}';
    }
}
