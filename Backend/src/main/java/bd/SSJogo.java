package bd;

import bd.Data.DAOApostaUtilizador;
import bd.Data.DAOEquipa;
import bd.Data.DAOJogo;
import bd.Objects.Aposta;
import bd.Objects.ApostaUtilizador;
import bd.Objects.Equipa;
import bd.Objects.Jogo;

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
        return jogo.filtraJogos(dataDe, dataAte, equipasArray);
    }

    public List<Jogo> todosJogos() {
        return jogo.todosJogos();
    }

    public void insereApostaJogoVitoria(String idEquipa, int idJogo, float minApostar, float maxApostar, float rate) {
        jogo.insereApostaJogo(idEquipa,idJogo,null,"VITORIA",minApostar,maxApostar,rate,-1);
    }

    public void insereApostaJogoEmpate(String idEquipa, int idJogo, float minApostar, float maxApostar, float rate) {
        jogo.insereApostaJogo(idEquipa,idJogo,null,"EMPATE",minApostar,maxApostar,rate,-1);
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

    public void fazerAposta(String data, float valor, String utilizador, int apostaId) {
        int saldo = apostUtil.verSaldo(utilizador);
        Aposta ap = jogo.getAposta(apostaId);

        if (valor >= ap.getMinAposta() && valor <= ap.getMaxAposta() && saldo >= valor) {
            apostUtil.fazAposta(data, valor, ap.getRate(), utilizador, apostaId);
        }
    }

    public void alteraJogo(String gestor, String estado, String data, String local, int idJogo, boolean aceitaApostas) {
        if (jogo.verificaGestor(gestor, idJogo)) {
            jogo.alteraJogo(estado, data, local, idJogo, aceitaApostas);
        }
    }

    public void acabaJogo(int idJogo) {
        Jogo j = jogo.getJogo(idJogo);
        alteraJogo(j.getGestor(), "JOGO TERMINADO", j.getData(), j.getLocal(), idJogo, false);
        jogo.notificaJogo(idJogo, j.getDescricao()+"JOGO TERMINADO");
    }

    public void fecharAposta(int idAposta,boolean resultado) {
        Aposta ap = jogo.getAposta(idAposta);

        if (ap.getResultado() == null) {
            jogo.alteraAposta(resultado, ap.getMinAposta(), ap.getMaxAposta(), ap.getRate(), idAposta);
            if (resultado) {
                apostUtil.pagaAposta(idAposta);
            }
        }
    }

    public void alteraRateAposta(float rate, int idAposta) {
        Aposta ap = jogo.getAposta(idAposta);
        jogo.alteraAposta(ap.getResultado(), ap.getMinAposta(), ap.getMaxAposta(), rate, idAposta);
    }

    public void cancelaJogo(int idJogo) {
        Jogo j = jogo.getJogo(idJogo);
        jogo.alteraJogo("TERMINADO", j.getData(), j.getLocal(), idJogo, false);
        apostUtil.devolveAposta(idJogo);
    }

    public Equipa verEquipa(String idEquipa) {
        return jogo.getEquipa(idEquipa);
    }

    public List<Jogo> historicoEquipa(String idEquipa, String data) {
        return equipa.historicoEquipa(idEquipa, data);
    }

    public boolean verificaGestorEquipa(String idGestor,Equipa e) {
        return e.getGestor().equals(idGestor);
    }

    public void alteraEquipa(String idGestor, String idEquipa, String liga, String local, String desporto, String descricao) {
        if (verificaGestorEquipa(idGestor,jogo.getEquipa(idEquipa))) {
            equipa.modificaEquipa(idEquipa, liga, local, desporto, descricao);
        }
    }

    public void alteraEquipaLocal(String idGestor, String idEquipa,String local) {
        Equipa e = jogo.getEquipa(idEquipa);
        if (verificaGestorEquipa(idGestor, e)) {
            equipa.modificaEquipa(idEquipa, e.getLiga(), local, e.getDesporto(), e.getDescricao());
        }
    }

    public void alteraEquipaDescricao(String idGestor, String idEquipa,String descricao) {
        Equipa e = jogo.getEquipa(idEquipa);
        if (verificaGestorEquipa(idGestor,e)) {
            equipa.modificaEquipa(idEquipa, e.getLiga(), e.getLocalidade(), e.getDesporto(), descricao);
        }
    }

    public void insereJogo(String idGestor, int idJogo, String local, String date, String estado, String descricao, boolean aceitaApostas) {
        if (jogo.verificaGestor(idGestor, idJogo)) {
            jogo.insereJogo(idJogo, local, date, estado, descricao, idGestor, aceitaApostas);
        }
    }

    public void insereApostaJogo(String idEquipa, int idJogo, String tipo, float minApostar, float maxApostar, float rate, int posicao) {
        jogo.insereApostaJogo(idEquipa,idJogo,null,tipo,minApostar,maxApostar,rate,posicao);
    }

    public void insereEquipaJogo(String idEquipa, int idJogo) {
        jogo.equipaJogo(idEquipa,idJogo);
    }

    public void notificaEquipa(String idEquipa, String mensagem) {
        equipa.notificaEquipa(idEquipa,mensagem);
    }

    public void insereEquipa(String gestor, String idEquipa, String liga, String local, String desporto, String descricao) {
        equipa.criaEquipa(gestor,idEquipa,liga,local,desporto,descricao);
    }

    public List<String> verLigas() {
        return equipa.verLigas();
    }

    public List<String>	verEquipasDeLiga(String liga) {
        return equipa.verEquipasDeLiga(liga);
    }

}
