import Data.DAOApostaUtilizador;
import Data.DAOFaturas;
import Data.DAONotificacao;
import Data.DAOUtilizador;
import Objects.*;

import java.util.ArrayList;
import java.util.List;

public class SSUtilizador {
    private DAOUtilizador utilizador;
    private DAONotificacao notificacao;
    private DAOFaturas faturas;

    public SSUtilizador() {
        this.utilizador = DAOUtilizador.getInstance();
        this.notificacao = DAONotificacao.getInstance();
        this.faturas = DAOFaturas.getInstance();
    }

    public boolean login(String nome,String password) {
        Utilizador u = utilizador.getUtilizador(nome);

        if (u != null) {
            if (u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }


    public boolean registaUtilizador(String nome,String password,Boolean gestor,String genero,int anoNascimento,String localizacao,int saldo,String contaMultibanco) {
        Utilizador u = utilizador.getUtilizador(nome);

        if (u == null) {
            utilizador.putUtilizador(nome,password,gestor,genero,anoNascimento,localizacao,saldo,contaMultibanco);
            return true;
        }
        return false;
    }

    public String verLicenca() {
        return "Este produto de Software foi devidamente autorizado pela entidade autoritativa do pais.";
    }

    public List<Notificacao> verNotificacoes(String username) {
        return notificacao.verNotificacoes(username);
    }

    public void apagaNotificacoes(String username) {
        notificacao.apagaNotificacoes(username);
    }

    public void criarFatura(String idFatura,float valor,String tipo,String data,String estado,String dataV,String utilizador,String moeda) {
        faturas.registaFatura(idFatura,valor,tipo,data,estado,dataV,utilizador,moeda);
    }

    public int verSaldo(String username) {
        return utilizador.verSaldo(username);
    }

    public List<Fatura> verFaturas(String idUser) {
        return faturas.verFaturas(idUser);
    }

    public void confirmaPagamento(String idFatura) {
        faturas.confirmaFatura(idFatura);
    }
}
