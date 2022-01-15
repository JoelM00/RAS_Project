package Objetos;

import java.util.List;

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
