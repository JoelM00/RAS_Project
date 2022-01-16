import Data.DAOApostaUtilizador;
import Data.DAOEquipa;
import Data.DAOJogo;
import Objects.Aposta;
import Objects.ApostaUtilizador;
import Objects.Jogo;

import java.util.List;

public class SSJogo {
    private DAOJogo jogo;
    private DAOEquipa equipa;
    private DAOApostaUtilizador apostUtil;

    public SSJogo() {
        this.jogo = DAOJogo.getInstance();
        this.equipa = DAOEquipa.getInstance();
        this.apostUtil = DAOApostaUtilizador.getInstance();
    }

    public List<ApostaUtilizador> verHistoricoApostas(String username) {
        return apostUtil.verApostasUtilizador(username);
    }

    public List<Jogo> filtraJogos(String dataDe, String dataAte, String equipasArray) {
        return jogo.filtraJogos(dataDe,dataAte,equipasArray);
    }

    public List<Jogo> maisApostados() {
        return jogo.maisApostados();
    }

    public Jogo verJogo(int idJogo) {
        return jogo.getJogo(idJogo);
    }

    public List<Aposta> getApostas(int idJogo) {
        return jogo.getApostas(idJogo);
    }

    public void fazerAposta(String data, float valor, float rate, String utilizador,int apostaId) {
        int saldo = apostUtil.verSaldo(utilizador);
        Aposta ap = jogo.getAposta(apostaId);

        if (valor >= ap.getMinAposta() && valor <= ap.getMaxAposta() && saldo >= valor) {
            apostUtil.fazAposta(data,valor,rate,utilizador,apostaId);
        }
    }

    public void alteraJogo(String gestor,String estado,String data,String local,int idJogo,boolean aceitaApostas) {
        if (jogo.verificaGestor(gestor,idJogo)) {
            jogo.alteraJogo(estado,data,local,idJogo,aceitaApostas);
        }
    }

    public void acabaJogo(String gestor,String data,String local,int idJogo,boolean aceitaApostas) {
        alteraJogo(gestor,"JOGO TERMINADO",data,local,idJogo,aceitaApostas);
        jogo.notificaJogo(idJogo,"JOGO TERMINADO");
    }

    public void fecharAposta(Boolean resultado, float minAposta, float maxAposta, float rate, int id) {
        jogo.alteraAposta(resultado,minAposta,maxAposta,rate,id);

        if (resultado!=null) {
            if (resultado == true) {

            }
        }

    }



}
