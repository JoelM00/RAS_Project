import Objetos.Utilizador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DAOUtilizador {
    private static DAOUtilizador singleton = null;

    private DAOUtilizador() {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();

            String sql1 = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`utilizador` ("+
                "`username` VARCHAR(45) NOT NULL,"+
                "`password` VARCHAR(45) NOT NULL,"+
                "`gestor` TINYINT NOT NULL,"+
                "`genero` CHAR(1) NULL,"+
                "`anoNascimento` INT NULL,"+
                "`localizacao` VARCHAR(45) NULL,"+
                "`saldo` INT NOT NULL,"+
                "`contaMultibanco` VARCHAR(45) NOT NULL,"+
                "PRIMARY KEY (`username`));";

            String sql2 = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`equipasFavoritas` ("+
                "`id` INT NOT NULL AUTO_INCREMENT,"+
                "`utilizador_username` VARCHAR(45) NOT NULL,"+
                "`equipa_idNome` VARCHAR(45) NOT NULL,"+
                "PRIMARY KEY (`id`),"+
                "INDEX `fk_equipasFavoritas_utilizador_idx` (`utilizador_username` ASC) VISIBLE,"+
                "INDEX `fk_equipasFavoritas_equipa1_idx` (`equipa_idNome` ASC) VISIBLE,"+
                "CONSTRAINT `fk_equipasFavoritas_utilizador`"+
                "FOREIGN KEY (`utilizador_username`)"+
                "REFERENCES `BetDataBase`.`utilizador` (`username`)"+
                "ON DELETE NO ACTION "+
                "ON UPDATE NO ACTION,"+
                "CONSTRAINT `fk_equipasFavoritas_equipa1`"+
                "FOREIGN KEY (`equipa_idNome`)"+
                "REFERENCES `BetDataBase`.`equipa` (`idNome`)"+
                "ON DELETE NO ACTION "+
                "ON UPDATE NO ACTION)"+
                "ENGINE = InnoDB;";

            stm.executeUpdate(sql1);
            stm.executeUpdate(sql2);
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public static DAOUtilizador getInstance() {
        if (DAOUtilizador.singleton == null) {
            DAOUtilizador.singleton = new DAOUtilizador();
        }
        return DAOUtilizador.singleton;
    }

    public Utilizador getUtilizador(String nome) {
        Utilizador u = null;
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("CALL getUtilizador('"+nome+"');") ;

            if (rs.next()) {  // A chave existe na tabela
                u = new Utilizador(rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("gestor"),
                        rs.getString("genero"),
                        rs.getInt("anoNascimento"),
                        rs.getString("localizacao"),
                        rs.getInt("saldo"),
                        rs.getString("contaMultibanco"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
        return u;
    }

    public void putUtilizador(String username,String password,Boolean gestor,String genero,int anoNascimento,String localizacao,int saldo,String contaMultibanco) {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate("CALL putUtilizador('"+username+"','"+password+"',"+gestor+",'"+genero+"',"+anoNascimento+",'"+localizacao+"',"+saldo+",'"+contaMultibanco+"');");

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }

    public int verSaldo(String username) {
        int saldo = -1;
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("CALL verSaldo('"+username+"');");
            saldo = rs.getInt("saldo");

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
        return saldo;
    }

    public void notificaJogo(int idJogo,String mensagem) {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate("CALL notificaJogo("+idJogo+",'"+mensagem+"');");
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }

    public boolean verificaFavorito(String idEquipa,String username) {
        boolean favorito = false;
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("CALL verificaFavorito('"+idEquipa+"','"+username+"');");
            favorito = rs.getBoolean("favorito");
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
        return favorito;
    }

    public void addFavorito(String idEquipa,String username) {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate("CALL addFavorito('"+idEquipa+"','"+username+"');");
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }
}
