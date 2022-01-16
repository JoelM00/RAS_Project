package Data;

import Objects.Aposta;
import Objects.Jogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOEquipa {
    private static DAOEquipa singleton = null;

    private DAOEquipa() {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`equipa` ("+
                "`idNome` VARCHAR(45) NOT NULL,"+
                "`localidade` VARCHAR(45) NOT NULL,"+
                "`liga` VARCHAR(45) NOT NULL,"+
                "`desporto` VARCHAR(45) NOT NULL,"+
                "`descricao` TEXT(100) NOT NULL,"+
                "`utilizador_username` VARCHAR(45) NOT NULL,"+
                "PRIMARY KEY (`idNome`),"+
                "INDEX `fk_equipa_utilizador1_idx` (`utilizador_username` ASC) VISIBLE,"+
                "CONSTRAINT `fk_equipa_utilizador1`"+
                "FOREIGN KEY (`utilizador_username`)"+
                "REFERENCES `BetDataBase`.`utilizador` (`username`)"+
                "ON DELETE NO ACTION "+
                "ON UPDATE NO ACTION);";

            stm.executeUpdate(sql);

        }  catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }

    public static DAOEquipa getInstance() {
        if (DAOEquipa.singleton == null) {
            DAOEquipa.singleton = new DAOEquipa();
        }
        return DAOEquipa.singleton;
    }

    public List<Jogo> historicoEquipa(String idEquipa, String data) {
        String query = "{CALL historicoEquipa(?,?)}";
        List<Jogo> jogos = new ArrayList<>();

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, idEquipa);
            stm.setString(2, data);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                jogos.add(new Jogo(rs.getInt("idChave"),
                        rs.getString("descricao"),
                        rs.getString("estado"),
                        rs.getString("data"),
                        rs.getString("local"),
                        rs.getBoolean("aceitaApostas")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return jogos;
    }

    public List<Jogo> criaEquipa(String gestor,String idEquipa,String liga,String local,String desporto,String descricao) {
        String query = "{CALL criaEquipa(?,?,?,?,?,?)}";
        List<Jogo> jogos = new ArrayList<>();

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, gestor);
            stm.setString(2, idEquipa);
            stm.setString(3, liga);
            stm.setString(4, local);
            stm.setString(5, desporto);
            stm.setString(6, descricao);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return jogos;
    }

    public List<String> verLigas() {
        String query = "{CALL verLigas()}";
        List<String> ligas = new ArrayList<>();

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                ligas.add(rs.getString("liga"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return ligas;
    }

    public List<String>	verEquipasDeLiga(String liga) {
        String query = "{CALL verEquipasDeLiga(?);}";
        List<String> equipas = new ArrayList<>();

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, liga);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                equipas.add(rs.getString("idNome"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return equipas;
    }

    public void	modificaEquipa(String idEquipa,String liga,String local,String desporto,String descricao) {
        String query = "{CALL modificaEquipa(?,?,?,?,?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, idEquipa);
            stm.setString(2, liga);
            stm.setString(3, local);
            stm.setString(4, desporto);
            stm.setString(5, descricao);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }


}
