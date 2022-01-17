import Data.DAOUtilizador;
import Objects.ApostaUtilizador;
import Objects.Jogo;

import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            SSUtilizador ssu = new SSUtilizador();
            SSJogo ssj = new SSJogo();

            List<ApostaUtilizador> apostas = ssj.verHistoricoApostas("Dinis");
            List<Jogo> jogos = ssj.maisApostados();

            System.out.println(apostas);

            System.out.println(jogos);

            System.out.println(ssu.login("Dinis", "1234"));

            System.out.println(ssu.verSaldo("Dinis"));

            System.out.println(ssu.verNotificacoes("Carlos"));

            System.out.println(ssu.verFaturas("Carlos"));

            System.out.println(ssu.verificaFavorito("SL Benfica", "Carlos"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
