package Data;

import Objects.Aposta;
import Objects.Equipa;
import Objects.Jogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOJogo {
    private static DAOJogo singleton = null;

    /**
     * Construtor default para Data.DAOJogo.
     */
    private DAOJogo() {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`jogo` (" +
                    "`idChave` INT NOT NULL," +
                    "`descricao` VARCHAR(100) NOT NULL," +
                    "`estado` VARCHAR(45) NOT NULL," +
                    "`data` DATE NOT NULL," +
                    "`local` VARCHAR(45) NOT NULL," +
                    "`utilizador_username` VARCHAR(45) NOT NULL," +
                    "`aceitaApostas` TINYINT NOT NULL," +
                    "PRIMARY KEY (`idChave`)," +
                    "INDEX `fk_jogo_utilizador1_idx` (`utilizador_username` ASC) VISIBLE," +
                    "CONSTRAINT `fk_jogo_utilizador1`" +
                    "FOREIGN KEY (`utilizador_username`)" +
                    "REFERENCES `BetDataBase`.`utilizador` (`username`)" +
                    "ON DELETE NO ACTION " +
                    "ON UPDATE NO ACTION);";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`aposta` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `minAposta` FLOAT NULL," +
                    "  `maxAposta` FLOAT NULL," +
                    "  `rate` FLOAT NOT NULL," +
                    "  `jogo_idChave` INT NOT NULL," +
                    "  `tipo` VARCHAR(45) NOT NULL," +
                    "  `posicao` INT NULL," +
                    "  `equipa_idNome` VARCHAR(45) NULL," +
                    "  `resultado` TINYINT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  INDEX `fk_aposta_jogo1_idx` (`jogo_idChave` ASC) VISIBLE," +
                    "  INDEX `fk_aposta_equipa1_idx` (`equipa_idNome` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_aposta_jogo1`" +
                    "    FOREIGN KEY (`jogo_idChave`)" +
                    "    REFERENCES `BetDataBase`.`jogo` (`idChave`)" +
                    "    ON DELETE NO ACTION " +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_aposta_equipa1`" +
                    "    FOREIGN KEY (`equipa_idNome`)" +
                    "    REFERENCES `BetDataBase`.`equipa` (`idNome`)" +
                    "    ON DELETE NO ACTION " +
                    "    ON UPDATE NO ACTION)" +
                    "ENGINE = InnoDB;";

            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `BetDataBase`.`equipa_jogo` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`jogo_idChave` INT NOT NULL," +
                    "`equipa_idNome` VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "INDEX `fk_equipa_jogo_jogo1_idx` (`jogo_idChave` ASC) VISIBLE," +
                    "INDEX `fk_equipa_jogo_equipa1_idx` (`equipa_idNome` ASC) VISIBLE," +
                    "CONSTRAINT `fk_equipa_jogo_jogo1`" +
                    "FOREIGN KEY (`jogo_idChave`)" +
                    "REFERENCES `BetDataBase`.`jogo` (`idChave`)" +
                    "ON DELETE NO ACTION " +
                    "ON UPDATE NO ACTION," +
                    "CONSTRAINT `fk_equipa_jogo_equipa1`" +
                    "FOREIGN KEY (`equipa_idNome`)" +
                    "REFERENCES `BetDataBase`.`equipa` (`idNome`)" +
                    "ON DELETE NO ACTION " +
                    "ON UPDATE NO ACTION)" +
                    "ENGINE = InnoDB;";

            stm.executeUpdate(sql);
        }  catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }

    /**
     * Método que implementa o padrão Singleton.
     *
     * @return instância única desta classe.
     */
    public static DAOJogo getInstance() {
        if (DAOJogo.singleton == null) {
            DAOJogo.singleton = new DAOJogo();
        }
        return DAOJogo.singleton;
    }

    public List<Jogo> filtraJogos(String dataDe, String dataAte, String equipasArray) {
        String query = "{CALL filtraJogos(?, ?, ?)}";
        List<Jogo> jogos = new ArrayList<>();

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, dataDe);
            stm.setString(2, dataAte);
            stm.setString(3, equipasArray);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Jogo jogo = new Jogo(rs.getInt("idChave"),
                        rs.getString("descricao"),
                        rs.getString("estado"),
                        rs.getString("data"),
                        rs.getString("local"),
                        rs.getBoolean("aceitaApostas"));

                jogos.add(jogo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return jogos;
    }

    public List<Jogo> maisApostados() {
        List<Jogo> jogos = new ArrayList<>();
        String query = "{CALL maisApostados()}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                jogos.add(new Jogo(rs.getInt("idChave"),
                        rs.getString("descricao"),
                        rs.getString("estado"),
                        rs.getString("data"),
                        rs.getString("local"),
                        rs.getBoolean("apostas")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return jogos;
    }

    public Jogo getJogo(int idJogo) {
        Jogo jogo;
        String query = "{CALL getJogo(?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setInt(1,idJogo);

            ResultSet rs = stm.executeQuery();
            jogo = new Jogo(rs.getInt("idChave"),
                    rs.getString("descricao"),
                    rs.getString("estado"),
                    rs.getString("data"),
                    rs.getString("local"),
                    rs.getBoolean("aceitaApostas"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return jogo;
    }

    public List<Aposta> getApostas(int idJogo) {
        List<Aposta> apostas = new ArrayList<>();
        String query = "{CALL getApostas(?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setInt(1, idJogo);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Aposta aposta = new Aposta(rs.getInt("id"),
                        rs.getFloat("minAposta"),
                        rs.getFloat("maxAposta"),
                        rs.getFloat("rate"),
                        rs.getString("tipo"),
                        rs.getInt("posicao"),
                        rs.getString("equipa_idNome"));

                apostas.add(aposta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return apostas;
    }

    public Aposta getAposta(int idAposta) {
        String query = "{CALL getAposta(?)}";
        Aposta aposta = null;
        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setInt(1, idAposta);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                float minAposta = rs.getFloat("minAposta");
                float maxAposta = rs.getFloat("maxAposta");
                float rate = rs.getFloat("rate");
                int jogo_idChave = rs.getInt("jogo_idChave");
                String tipo = rs.getString("tipo");
                int posicao = rs.getInt("posicao");
                String equipa_idNome = rs.getString("equipa_idNome");
                Object resul = rs.getObject("resultado");
                if (resul == null) {
                     return new Aposta(id,minAposta,maxAposta,rate,jogo_idChave,tipo,posicao,equipa_idNome,null);
                } else {
                    Boolean r = (Boolean) resul;
                    return new Aposta(id,minAposta,maxAposta,rate,jogo_idChave,tipo,posicao,equipa_idNome,r);
                }
            }
            return aposta;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public void alteraJogo(String estado, String data, String local, int idJogo, boolean aceitaApostas) {
        String query = "{CALL alteraJogo(?, ?, ?, ?, ?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);

            stm.setString(1, estado);
            stm.setString(2, data);
            stm.setString(3, local);
            stm.setInt(4, idJogo);
            stm.setBoolean(5, aceitaApostas);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public void alteraAposta(Boolean resultado, float minAposta, float maxAposta, float rate, int id) {
        String query = "{CALL alteraAposta(?, ?, ?, ?, ?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            int res;
            if (resultado) res = 1;
            else res = 0;

            stm.setInt(1, res);
            stm.setFloat(2, minAposta);
            stm.setFloat(3, maxAposta);
            stm.setFloat(4, rate);
            stm.setInt(5, id);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public boolean verificaGestor(String idGestor, int idJogo){
        String query = "{CALL verificaGestor(?, ?)}";
        boolean ret;
        int r = -1;

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, idGestor);
            stm.setInt(2, idJogo);

            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                 r = rs.getInt("verificacao");
            }

            if (r == 1) ret = true;
            else ret = false;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }

        return ret;
    }

    public void insereJogo(int idJogo, String local, String date, String estado, String descricao, String gestor, boolean aceitaApostas) {
        String query = "{CALL insereJogo(?, ?, ?, ?, ?, ?, ?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setInt(1, idJogo);
            stm.setString(2, local);
            stm.setString(3, date);
            stm.setString(4, estado);
            stm.setString(5, descricao);
            stm.setString(6, gestor);
            stm.setBoolean(7, aceitaApostas);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public void equipaJogo(String idEquipa, int idJogo){
        String query = "{CALL equipaJogo(?, ?)}";

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, idEquipa);
            stm.setInt(2, idJogo);

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    /*NAO FUNCIONA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    public void insereApostaJogo(String idEquipa, int idJogo, Boolean resultado, String tipo, float minApostar, float maxApostar, float rate, int posicao) {
        String query = "{CALL insereApostaJogo(?, ?, ?, ?, ?, ?, ?, ?)}";

        Connection conn = ConfigDAO.connect();
        try {

            CallableStatement stm = conn.prepareCall(query);

            stm.setString(1, idEquipa);
            stm.setInt(2, idJogo);
            stm.setBoolean(3, resultado);
            stm.setString(4, tipo);
            stm.setFloat(5, minApostar);
            stm.setFloat(6, maxApostar);
            stm.setFloat(7, rate);

            if (posicao != -1) {
                stm.setNull(8, posicao);
            } else {
                stm.setInt(8,posicao);
            }

            stm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
    }

    public void notificaJogo(int idJogo,String mensagem) {
        Connection conn = ConfigDAO.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeQuery("CALL notificaJogo("+idJogo+",'"+mensagem+"');");
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConfigDAO.close(conn);
        }
    }

    public Equipa getEquipa(String idEquipa) {
        String query = "{CALL getEquipa(?)}";
        Equipa equipa = null;

        Connection conn = ConfigDAO.connect();
        try {
            CallableStatement stm = conn.prepareCall(query);
            stm.setString(1, idEquipa);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new Equipa(rs.getString("idNome"),
                        rs.getString("localidade"),
                        rs.getString("liga"),
                        rs.getString("desporto"),
                        rs.getString("descricao"),
                        rs.getString("utilizador_username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } finally {
            ConfigDAO.close(conn);
        }
        return equipa;
    }


}
