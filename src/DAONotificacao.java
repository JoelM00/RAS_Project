import Objetos.Notificacao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAONotificacao {
    private static DAONotificacao singleton = null;

    private DAONotificacao() {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`notificacao` (" +
                "`id` INT NOT NULL AUTO_INCREMENT,"+
                "`utilizador_username` VARCHAR(45) NOT NULL,"+
                "`mensagem` TEXT(50) NOT NULL,"+
                "PRIMARY KEY (`id`),"+
                "INDEX `fk_notificacao_utilizador1_idx` (`utilizador_username` ASC) VISIBLE,"+
                "CONSTRAINT `fk_notificacao_utilizador1`"+
                "FOREIGN KEY (`utilizador_username`)"+
                "REFERENCES `BetDataBase`.`utilizador` (`username`)"+
                "ON DELETE NO ACTION "+
                "ON UPDATE NO ACTION);";

            stm.executeUpdate(sql);
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public static DAONotificacao getInstance() {
        if (DAONotificacao.singleton == null) {
            DAONotificacao.singleton = new DAONotificacao();
        }
        return DAONotificacao.singleton;
    }

    public void notificaEquipa(String idEquipa,String mensagem) {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeQuery("CALL notificaEquipa('"+idEquipa+"','"+mensagem+"');");
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }

    public List<Notificacao> verNotificacoes(String username) {
        List<Notificacao> notf = new ArrayList<>();
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("CALL verNotificacoes('"+username+"');");

            while (rs.next()) {
                notf.add(new Notificacao(
                        rs.getInt("id"),
                        rs.getString("utilizador_username"),
                        rs.getString("mensagem")
                ));
            }
        } catch (Exception e) {
        e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
        return notf;
    }

    public void apagaNotificacoes(String username) {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeQuery( "CALL apagaNotificacoes('"+username+"');");
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }
}
