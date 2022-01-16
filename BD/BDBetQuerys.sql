USE BetDatabase;
-- DROP SCHEMA BetDataBase;
-- SELECT * FROM utilizador;
-- SELECT * FROM aposta;
-- SELECT * FROM equipa;
-- SELECT * FROM equipa_jogo;
-- SELECT * FROM equipasfavoritas;
-- SELECT * FROM fatura;
-- SELECT * FROM jogo;
-- SELECT * FROM moeda;
-- SELECT * FROM notificacao;
-- SELECT * FROM utilizador_aposta;

INSERT INTO utilizador (username,password,gestor,genero,anoNascimento,localizacao,saldo,contaMultibanco) VALUES
	("Carlos","1234",1,'M',2000,"Braga",0,"200123XYZ"),
    ("Joel","1234",0,'M',2000,"Braga",0,"300123XYZ"),
    ("Dinis","1234",0,'M',2000,"Braga",0,"400123XYZ"),
    ("Ruben","1234",0,'M',2000,"Braga",0,"500123XYZ");

INSERT INTO equipa (idNome,utilizador_username,localidade,liga,desporto,descricao) VALUES
	("FC Porto","Carlos","Porto","1ª liga Portuguesa","Futebol","Futebol Clube do Porto, mais conhecido como FC Porto ou simplesmente Porto, é um clube multidesportivo português sedeado na cidade do Porto. É mais conhecido pela sua equipa de futebol profissional, que joga atualmente na Primeira Liga, a competição mais importante do futebol português."),
    ("SL Benfica","Carlos","Benfica","1ª liga Portuguesa","Futebol","O Sport Lisboa e Benfica é um clube multidesportivo português, fundado em 1904 e sediado na freguesia de São Domingos de Benfica, em Lisboa. A principal modalidade do clube é o futebol");
    
INSERT INTO jogo (idChave,descricao,estado,data,local,utilizador_username,aceitaApostas) VALUES
	(1,"Porto vs Benfica 1 Jornada","Por realizar",'2022-01-10',"Estádio Porto","Carlos",1),
	(2,"Benfica vs Porto 2 Jornada","Por realizar",'2022-01-20',"Estádio Lisboa","Carlos",1);

INSERT INTO equipa_jogo (jogo_idChave,equipa_idNome) VALUES
	(1,"FC Porto"),(2,"FC Porto"),(1,"SL Benfica"),(2,"SL Benfica");

INSERT INTO aposta (minAposta,maxAposta,rate,jogo_idChave,tipo,posicao,equipa_idNome,resultado) VALUES
	(0.0,10.0,1.12,1,"Vitoria",NULL,"SL Benfica",NULL),(0.0,10.0,1.12,1,"Vitoria",NULL,"FC Porto",NULL),(0.0,10.0,1.12,1,"Empate",NULL,NULL,NULL),
    (0.0,10.0,1.12,2,"Vitoria",NULL,"SL Benfica",NULL),(0.0,10.0,1.12,2,"Vitoria",NULL,"FC Porto",NULL),(0.0,10.0,1.12,2,"Empate",NULL,NULL,NULL);
    
INSERT INTO moeda (nome,rateToBet,rateFromBet) VALUES
	("Euro",1.1,0.9),("Cardano",1.3,1.0),("Libras",1.5,0.7);

INSERT INTO equipasfavoritas (utilizador_username,equipa_idNome) VALUES
	("Carlos","SL Benfica"),("Dinis","FC Porto");

INSERT INTO notificacao (utilizador_username,mensagem) VALUES
	("Carlos","Novo jogo disponivel do SL Benfica"),
    ("Dinis","Novo jogo disponivel do FC Porto");
    
INSERT INTO utilizador_aposta (data,valor,rate,utilizador_username,aposta_id) VALUES
	('2022-01-05',3.5,1.12,"Carlos",1),('2022-01-05',3.5,1.12,"Dinis",2);

INSERT INTO fatura (idfatura,valor,tipoDepOuRec,data,estado,dataValidade,utilizador_username,moeda_nome) VALUES
	("XYZ200","10",'D','2022-01-05',"Por Confirmar",'2022-02-05',"Carlos","Euro");

/*
FORMULÁRIO DE PROCEDURES:
Com estes procedures já é possivel começar a trabalhar nas funcionalidades do sistema.
-- -----------------------------------------------------------------------------------------------------
1  PROCEDURE para buscar utilizador 
	+ (Simplesmente buscar a info relacionada a um utilizador)
	getUtilizador(IN idNome VARCHAR(45)
	CALL getUtilizador("ola1");
-- -----------------------------------------------------------------------------------------------------
2  PROCEDURE para registar utilizador 
	+ (Cuidado - é necessário ver se o username já existe e pass é sufcientemente forte antes de chamar função)
	putUtilizador(IN username VARCHAR(45),IN password VARCHAR(45),IN gestor TINYINT,IN genero CHAR(1),
					IN anoNascimento INT,IN localizacao VARCHAR(45),IN saldo INT,IN contaMultibanco VARCHAR(45))
	CALL putUtilizador("Joana","1234",0,'F',2000,'Porto',0,"CYZ2000");
-- -----------------------------------------------------------------------------------------------------
3  PROCEDURE para buscar jogos por data e equipas
	+ Um simples filtro sobre os jogos, só apresenta aqueles que aceitam apostas
    filtraJogos(IN dataDe DATE,IN dataAte DATE,IN equipasArray VARCHAR(255))
	CALL filtraJogos('2021-01-05','2023-01-05','FC Porto,SL Benfica');
-- -----------------------------------------------------------------------------------------------------
4  PROCEDURE para buscar jogos com mais apostas realizadas
	+ Um simples filtro sobre os jogos, só apresenta aqueles que aceitam apostas
    maisApostados()
    CALL maisApostados();
-- -----------------------------------------------------------------------------------------------------
5  PROCEDURE para buscar único jogo a partir de id
	+ Apresenta informação sobre um jogo
	getJogo(IN idJogo VARCHAR(45))
    CALL getJogo(1);
-- -----------------------------------------------------------------------------------------------------
6  PROCEDURE para buscar apostas dando id do jogo
	+ Apresenta as apostas de um jogo
	getApostas(IN idJogo VARCHAR(45))
    CALL getApostas(1);
-- -----------------------------------------------------------------------------------------------------
7  PROCEDURE para ver saldo de utilizador
	+ Apresenta o saldo do utilizador - Necessário para fazer faturas do tipo Receber e apostas
	verSaldo(IN idUtilizador VARCHAR(45))
    CALL verSaldo("Carlos");
-- -----------------------------------------------------------------------------------------------------
8  PROCEDURE para um utilizador fazer uma aposta
	+ Retira valor ao saldo do utilizador, regista aposta
	fazAposta(IN data DATE,IN valor FLOAT,IN rate FLOAT,IN utilizador VARCHAR(45),IN aposta_id INT)
    CALL fazAposta('2022-01-05',20,1.12,"Joel",3);
-- -----------------------------------------------------------------------------------------------------
9  PROCEDURE para criar uma fatura
	+ Regista a info de uma fatura, é necessário saldo sufciente se for do tipo 'R' receber (usar procedure 7)
    + É necessário chave que API do banco é suposto dar, É necessário o tipo de moeda
    + Só é retirado/adicionado saldo quando o pagamento é confirmado e depende do rate da moeda na altura da confirmação
	registaFatura(IN idFatura VARCHAR(45),IN valor FLOAT,IN tipo CHAR(1),IN data DATE,IN estado VARCHAR(45),
					IN dataV DATE,IN utilizador VARCHAR(45),IN moeda VARCHAR(45))
	CALL registaFatura("XKASD200",50.0,'D','2022-01-05',"Por Confirmar",'2022-02-05',"Joel","Cardano");
-- -----------------------------------------------------------------------------------------------------
10  PROCEDURE para confirmar pagamento atualizando saldo de utilizador
	+ API do banco chama
    + Atualiza saldo e depende do rate da moeda na altura da confirmação
	confirmaFatura(IN idFatura VARCHAR(45))
    CALL confirmaFatura("XKASD200");
-- -----------------------------------------------------------------------------------------------------
11 PROCEDURE para alterar jogo
	+ Simplesmente altera a info de um jogo na base de dados
	alteraJogo(IN estado VARCHAR(45),IN data DATE,IN local VARCHAR(45),IN idJogo INT,IN aceitaApostas BOOLEAN)
    CALL alteraJogo("Acabado",'2022-01-10',"Estádio Porto",1);
-- -----------------------------------------------------------------------------------------------------
12 PROCEDURE para alertar jogadores de mudança num determinado jogo
	+ regista na BD uma notificação a todos os utilizadores que apostaram em jogo x
	notificaJogo(IN idJogo INT,IN mensagem TEXT(50))
    CALL notificaJogo(1,"Jogo Acabou - Benfica vs Porto");
-- -----------------------------------------------------------------------------------------------------
13 PROCEDURE para alterar uma aposta
	+ Simplesmente altera info de aposta na BD
    + Útil para alterar rate de aposta
	alteraAposta(IN resultado BOOLEAN,IN minAposta FLOAT,IN maxAposta FLOAT,IN rate FLOAT,IN id INT)
    CALL alteraAposta(1,0,10,1.5,1);
-- -----------------------------------------------------------------------------------------------------
14 PROCEDURE para pagar aposta ganha
	+ Atualiza (aumenta) sando a todos os utilizadores que fizeram x aposta
	+ O valor depende do rate de quando fizeram a aposta
	pagaAposta(IN idAposta INT)
    CALL pagaAposta(1);
-- -----------------------------------------------------------------------------------------------------
15 PROCEDURE para verificar se gestor x gera jogo y
	+ Verifica se o utilizador x é gestor de um jogo y
	+ Útil quando se tenta alterar jogo ou aposta
	verificaGestor(IN idGestor VARCHAR(45),IN idJogo INT)
    CALL verificaGestor("Carlos",1);
-- -----------------------------------------------------------------------------------------------------
16 PROCEDURE para inserir jogo na base de dados
	+ Insere jogo na BD
    + Neste momento o jogo não esta associado a nenhuma apostas ou equipas
	insereJogo(IN idJogo INT,IN local VARCHAR(45),IN data DATE,IN estado VARCHAR(45),IN descricao VARCHAR(45),
			IN gestor VARCHAR(45),IN aceitaApostas BOOLEAN)
	CALL insereJogo(3,"Estádio Porto",'2023-05-01',"Por realizar","Benfica vs Porto 2023","Carlos",1);
-- -----------------------------------------------------------------------------------------------------
17 PROCEDURE para inserir equipa num jogo
	+ Insere na BD que equipa x participa no jogo y
	equipaJogo(IN idEquipa VARCHAR(45),IN idJogo INT)
    CALL equipaJogo("SL Benfica",3);
    CALL equipaJogo("FC Porto",3);
-- -----------------------------------------------------------------------------------------------------
18 PROCEDURE para notificar utilizadores de equip favorita
	+Insere na BD uma notificação a tds os utilizadores que tem a equipa x em favoritos
	notificaEquipa(IN idEquipa VARCHAR(45),IN mensagem TEXT(50))
	CALL notificaEquipa("FC Porto","Aposte Já Novo Jogo Disponivel do Porto Ano 2023");
	CALL notificaEquipa("SL Benfica","Aposte Já Novo Jogo Disponivel do Benfica Ano 2023");
-- -----------------------------------------------------------------------------------------------------
19 PROCEDURE para inserir apostas de um jogo na base de dados
	+Insere na BD uma nova aposta, é necessário saber o id do jogo.
    +Usado junto com insereEquipaJogo e Insere Jogo tudo junto
	insereApostaJogo(IN idEquipa VARCHAR(45),IN idJogo INT,IN resultado BOOLEAN,IN tipo VARCHAR(45),
				IN minApostar FLOAT,IN maxApostar FLOAT,IN rate FLOAT,IN posicao INT)
	CALL insereApostaJogo("FC Porto",3,0,"Vitória",0,10,1.4,NULL);
	CALL insereApostaJogo("SL Benfica",3,0,"Vitória",0,10,1.4,NULL);
	CALL insereApostaJogo(NULL,3,0,"Empate",0,10,1.4,NULL);
-- -----------------------------------------------------------------------------------------------------
20 PROCEDURE para ver historico de uma equipa
	+Mostra os jogos que uma equipa realizou ANTES de uma certa da (por exemplo data de hoje para trás)
	historicoEquipa(IN idEquipa VARCHAR(45),IN DataNow DATE)
	CALL historicoEquipa("SL Benfica",'2022-01-15');
-- -----------------------------------------------------------------------------------------------------
21 PROCEDURE para criar equipa
	+Simplesmente adiciona uma equipa na BD
	criaEquipa(IN gestor VARCHAR(45),IN idEquipa VARCHAR(45),IN liga VARCHAR(45),IN local VARCHAR(45),
				IN desporto VARCHAR(45),IN descricao TEXT(100))
	CALL criaEquipa("Carlos","SL Sporting","1ª liga portuguesa","estádio de lisboa","Futebol","O Sporting Clube de Portugal é um clube português, eclético e multidesportivo, fundado a 1 de julho de 1906, com sede em Lisboa no Complexo Alvalade XXI. É um dos Tres Grandes clubes em Portugal, juntamente com SL Benfica e FC Porto.");
-- -----------------------------------------------------------------------------------------------------
22 PROCEDURE para ver se equipa ta como favorito
	+Verifica se utilizador x tem equipa y como favorito
	 verificaFavorito(IN idEquipa VARCHAR(45),IN username VARCHAR(45))
     CALL verificaFavorito("SL Sporting","Carlos");
-- -----------------------------------------------------------------------------------------------------
23 PROCEDURE para adicionar equipa como favorito
	+Insere na BD a relação equipa utilizador em favoritos 
	addFavorito(IN idEquipa VARCHAR(45),IN username VARCHAR(45))
    CALL addFavorito("SL Sporting","Carlos");
-- -----------------------------------------------------------------------------------------------------
24 PROCEDURE ver Notificações
	+Mostra todas as notificações de um certo utilizador
	verNotificacoes(IN username VARCHAR(45))
    CALL verNotificacoes("Carlos");
-- -----------------------------------------------------------------------------------------------------
25 PROCEDURE para apagar notificações
	+ Apaga as notificações de um certo utilizador
	apagaNotificacoes(IN username VARCHAR(45))
    CALL apagaNotificacoes("Carlos");
-- -----------------------------------------------------------------------------------------------------
26 PROCEDURE para ver apostas de um utilizador
	+ Mostra as apostas de um certo utilizador
	verApostasUtilizador(IN username VARCHAR(45))
    CALL verApostasUtilizador("Carlos");
-- -----------------------------------------------------------------------------------------------------
27 PROCEDURE para ver ligas
	+ Mostra todas as ligas
	verLigas()
    CALL verLigas();
-- -----------------------------------------------------------------------------------------------------
28 PROCEDURE para ver equipas de uma liga
	+ Mostra todas as equipas de uma liga
	verEquipasDeLiga(IN liga VARCHAR(45))
    CALL verEquipasDeLiga("1ª liga portuguesa");
-- -----------------------------------------------------------------------------------------------------
29 PROCEDURE para devolver dinheiro de aposta
	devolveAposta(IN idAposta INT)
    CALL devolveAposta(1)
-- -----------------------------------------------------------------------------------------------------
30 PROCEDURE para modificar equipas
	modificaEquipa(IN idEquipa VARCHAR(45),IN liga VARCHAR(45),IN local VARCHAR(45),
							IN desporto VARCHAR(45),IN descricao TEXT(100))
	CALL modificaEquipa("SL Sporting","1ª liga portuguesa","estádio de lisboa","Futebol","É um dos Tres Grandes clubes em Portugal, juntamente com SL Benfica e FC Porto.");
-- ------------------------------------------------------------------------------------------------------
31 PROCEDURE para ver faturas
	verFaturas(IN idUti VARCHAR(45));
    CALL verFaturas("Carlos");
*/

-- -----------------------------------------------------------------------------------------------------------------
-- 1  PROCEDURE para buscar utilizador------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE getUtilizador;
DELIMITER $$
CREATE PROCEDURE getUtilizador(IN idNome VARCHAR(45))
BEGIN
	SELECT username,password,gestor,genero,anoNascimento,localizacao,saldo,contaMultibanco FROM utilizador 
	WHERE username=idNome;
END$$
-- CALL getUtilizador("Carlos");

-- -----------------------------------------------------------------------------------------------------------------
-- 2  PROCEDURE para registar utilizador----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE putUtilizador;
DELIMITER $$
CREATE PROCEDURE putUtilizador(IN username VARCHAR(45),IN password VARCHAR(45),IN gestor TINYINT,IN genero CHAR(1),
							IN anoNascimento INT,IN localizacao VARCHAR(45),IN saldo INT,IN contaMultibanco VARCHAR(45))
BEGIN
	INSERT INTO utilizador (username,password,gestor,genero,anoNascimento,localizacao,saldo,contaMultibanco) VALUES
	(username,password,gestor,genero,anoNascimento,localizacao,saldo,contaMultibanco);
END$$
-- CALL putUtilizador("Joana","1234",0,'F',2000,'Porto',0,"CYZ2000");

-- -----------------------------------------------------------------------------------------------------------------
-- 3  PROCEDURE para buscar jogos por data e equipas ---------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE filtraJogos;
DELIMITER $$
CREATE PROCEDURE filtraJogos(IN dataDe DATE,IN dataAte DATE,IN equipasArray VARCHAR(255))
BEGIN
	SELECT idChave,descricao,estado,data,local,aceitaApostas FROM jogo INNER JOIN equipa_jogo ON jogo_idChave=jogo.idChave 
    WHERE (data>=datade and data<=dataAte and FIND_IN_SET(equipa_idNome,equipasArray) and jogo.aceitaApostas=True)
    GROUP BY jogo.idChave;
END$$
-- CALL filtraJogos('2021-01-05','2023-01-05','FC Porto,SL Benfica');

-- -----------------------------------------------------------------------------------------------------------------
-- 4  PROCEDURE para buscar jogos com mais apostas realizadas ------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE maisApostados;
DELIMITER $$
CREATE PROCEDURE maisApostados()
BEGIN
	SELECT idChave,descricao,estado,jogo.data,local,Count(*) AS apostas FROM jogo 
		INNER JOIN aposta ON aposta.jogo_idChave=jogo.idChave
        INNER JOIN utilizador_aposta ON utilizador_aposta.aposta_id=aposta.id
	WHERE jogo.aceitaApostas=True
    GROUP BY jogo.idChave
    ORDER BY apostas DESC
    LIMIT 50;
END$$
-- CALL maisApostados();

-- -----------------------------------------------------------------------------------------------------------------
-- 5  PROCEDURE para buscar único jogo a partir de id --------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE getJogo;
DELIMITER $$
CREATE PROCEDURE getJogo(IN idJogo VARCHAR(45))
BEGIN
	SELECT idChave,descricao,estado,data,local,aceitaApostas FROM jogo 
	WHERE jogo.idChave=idJogo;
END$$
-- CALL getJogo(1);

-- -----------------------------------------------------------------------------------------------------------------
-- 6  PROCEDURE para buscar apostas dando id do jogo ---------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE getApostas;
DELIMITER $$
CREATE PROCEDURE getApostas(IN idJogo VARCHAR(45))
BEGIN
	SELECT id,minAposta,maxAposta,rate,tipo,posicao,equipa_idNome FROM aposta 
	WHERE jogo_idChave=idJogo;
END$$
-- CALL getApostas(1);

-- DROP PROCEDURE getAposta;
DELIMITER $$
CREATE PROCEDURE getAposta(IN idAposta VARCHAR(45))
BEGIN
	SELECT * FROM aposta 
	WHERE id=idAposta;
END$$
-- CALL getAposta(1);

-- -----------------------------------------------------------------------------------------------------------------
-- 7  PROCEDURE para ver saldo de utilizador ...... ----------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE verSaldo;
DELIMITER $$
CREATE PROCEDURE verSaldo(IN idUtilizador VARCHAR(45))
BEGIN
    SELECT utilizador.saldo from utilizador WHERE utilizador.username=idUtilizador;
END$$
-- CALL verSaldo("Carlos");

-- -----------------------------------------------------------------------------------------------------------------
-- 8  PROCEDURE para um utilizador fazer uma aposta ----------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE fazAposta;
DELIMITER $$
CREATE PROCEDURE fazAposta(IN data DATE,IN valor FLOAT,IN rate FLOAT,IN utilizador VARCHAR(45),IN aposta_id INT)
BEGIN
	INSERT INTO utilizador_aposta (data,valor,rate,utilizador_username,aposta_id) VALUES
			(data,valor,rate,utilizador,aposta_id);
	UPDATE utilizador SET saldo=saldo-valor WHERE utilizador.username=utilizador;
END$$
-- CALL fazAposta('2022-01-05',20,1.12,"Joel",3);

-- -----------------------------------------------------------------------------------------------------------------
-- 9  PROCEDURE para criar uma fatura ------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE registaFatura;
DELIMITER $$
CREATE PROCEDURE registaFatura(IN idFatura VARCHAR(45),IN valor FLOAT,IN tipo CHAR(1),IN data DATE,IN estado VARCHAR(45)
							,IN dataV DATE,IN utilizador VARCHAR(45),IN moeda VARCHAR(45))
BEGIN
	INSERT INTO fatura (idfatura,valor,tipoDepOuRec,data,estado,dataValidade,utilizador_username,moeda_nome) VALUES
	(idFatura,valor,tipo,data,estado,dataV,utilizador,moeda);
END$$
-- CALL registaFatura("XKASD200",50.0,'D','2022-01-05',"Por Confirmar",'2022-02-05',"Joel","Cardano");

-- -----------------------------------------------------------------------------------------------------------------
-- 10  PROCEDURE para confirmar pagamento atualizando saldo de utilizador -------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE confirmaFatura;
DELIMITER $$
CREATE PROCEDURE confirmaFatura(IN idFatura VARCHAR(45))
BEGIN
	DECLARE tipo CHAR(1);
    DECLARE moeda VARCHAR(45);
    DECLARE rate FLOAT;
    DECLARE valor FLOAT;
    DECLARE utilizador VARCHAR(45);
    
    SELECT tipoDepOuRec,moeda_nome,utilizador_username,fatura.valor INTO tipo,moeda,utilizador,valor
	FROM fatura where fatura.idfatura=idFatura;
    IF (tipo='R') THEN
		SELECT rateFromBet into rate FROM moeda where moeda.nome=moeda;
		UPDATE utilizador SET saldo= saldo - (valor * rate) WHERE username=utilizador;
	END IF;
    IF (tipo='D') THEN
		SELECT rateToBet into rate FROM moeda where moeda.nome=moeda;
		UPDATE utilizador SET saldo= saldo + (valor * rate) WHERE username=utilizador;
    END IF;
    UPDATE fatura SET estado="Confirmado" WHERE fatura.idfatura=idFatura;
END$$
-- CALL confirmaFatura("XKASD200");

-- -----------------------------------------------------------------------------------------------------------------
-- 11 PROCEDURE para alterar jogo ----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE alteraJogo;
DELIMITER $$
CREATE PROCEDURE alteraJogo(IN estado VARCHAR(45),IN data DATE,IN local VARCHAR(45),IN idJogo INT,IN aceitaApostas BOOLEAN)
BEGIN
	UPDATE jogo SET jogo.estado=estado,jogo.data=data,jogo.local=local,jogo.aceitaApostas=aceitaApostas WHERE jogo.idChave=idJogo;
END$$
-- CALL alteraJogo("Acabado",'2022-01-10',"Estádio Porto",1);

-- -----------------------------------------------------------------------------------------------------------------
-- 12 PROCEDURE para alertar jogadores de mudança num determinado jogo ---------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE notificaJogo;
DELIMITER $$
CREATE PROCEDURE notificaJogo(IN idJogo INT,IN mensagem TEXT(50))
BEGIN
	DECLARE username VARCHAR(45);
    DECLARE done INT DEFAULT FALSE;
	DECLARE cur1 CURSOR FOR SELECT utilizador_username 
		FROM utilizador_aposta INNER JOIN aposta ON utilizador_aposta.aposta_id=aposta.id
        WHERE jogo_idChave=idJogo
        GROUP BY utilizador_username;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cur1;
    loop1: LOOP
		FETCH cur1 into username;
		IF done THEN leave loop1;
        END IF;
        INSERT INTO notificacao (utilizador_username,mensagem) VALUES (username,mensagem);
	END LOOP;
END$$
-- CALL notificaJogo(1,"Jogo Acabou - Benfica vs Porto"); 

-- -----------------------------------------------------------------------------------------------------------------
-- 13 PROCEDURE para alterar uma aposta -------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE alteraAposta;
DELIMITER $$
CREATE PROCEDURE alteraAposta(IN resultado BOOLEAN,IN minAposta FLOAT,IN maxAposta FLOAT,IN rate FLOAT,IN id INT)
BEGIN
	UPDATE aposta SET aposta.resultado=resultado,aposta.minAposta=minAposta,
    aposta.maxAposta=maxAposta,aposta.rate=rate WHERE aposta.id=id;
END$$
-- CALL alteraAposta(1,0,10,1.5,1);

-- -----------------------------------------------------------------------------------------------------------------
-- 14 PROCEDURE para pagar aposta ganha ----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE pagaAposta;
DELIMITER $$
CREATE PROCEDURE pagaAposta(IN idAposta INT)
BEGIN
	DECLARE username VARCHAR(45);
    DECLARE valor1 FLOAT;
    DECLARE rate1 FLOAT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cur1 CURSOR FOR SELECT utilizador_username,valor,rate 
			FROM utilizador_aposta 
			WHERE aposta_id=idAposta;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	OPEN cur1;
	loop1: LOOP
		FETCH cur1 into username,valor1,rate1;
		IF done THEN leave loop1;
		END IF;
		UPDATE utilizador SET saldo=saldo+(valor1*rate1) WHERE utilizador.username=username;
	END LOOP;
END$$
-- CALL pagaAposta(1);

-- -----------------------------------------------------------------------------------------------------------------
-- 15 PROCEDURE para verificar se gestor x gera jogo y -------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE verificaGestor;
DELIMITER $$
CREATE PROCEDURE verificaGestor(IN idGestor VARCHAR(45),IN idJogo INT)
BEGIN
	SELECT Count(*) as tf FROM jogo WHERE jogo.idChave=idJogo and jogo.utilizador_username=idGestor;
END$$
-- CALL verificaGestor("Carlos",1);

-- -----------------------------------------------------------------------------------------------------------------
-- 16 PROCEDURE para inserir jogo na base de dados -----------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE insereJogo;
DELIMITER $$
CREATE PROCEDURE insereJogo(IN idJogo INT,IN local VARCHAR(45),IN data DATE,IN estado VARCHAR(45),IN descricao VARCHAR(45),
							IN gestor VARCHAR(45),IN aceitaApostas BOOLEAN)
BEGIN
	INSERT INTO jogo (idChave,descricao,estado,data,local,utilizador_username,aceitaApostas) VALUES
	(IdJogo,descricao,estado,data,local,gestor,aceitaApostas);
END$$
-- CALL insereJogo(3,"Estádio Porto",'2023-05-01',"Por realizar","Benfica vs Porto 2023","Carlos",1);

-- -----------------------------------------------------------------------------------------------------------------
-- 17 PROCEDURE para inserir equipa num jogo -----------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE equipaJogo;
DELIMITER $$
CREATE PROCEDURE equipaJogo(IN idEquipa VARCHAR(45),IN idJogo INT)
BEGIN
	INSERT INTO equipa_jogo (jogo_idChave,equipa_idNome) VALUES (idJogo,idEquipa);
END$$
-- CALL equipaJogo("SL Benfica",3);
-- CALL equipaJogo("FC Porto",3);

-- -----------------------------------------------------------------------------------------------------------------
-- 18 PROCEDURE para notificar utilizadores de equip favorita ------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE notificaEquipa;
DELIMITER $$
CREATE PROCEDURE notificaEquipa(IN idEquipa VARCHAR(45),IN mensagem TEXT(50))
BEGIN
	DECLARE username VARCHAR(45);
    DECLARE done INT DEFAULT FALSE;
	DECLARE cur1 CURSOR FOR SELECT utilizador_username  
		FROM equipasfavoritas WHERE equipasfavoritas.equipa_idNome=idEquipa
        GROUP BY utilizador_username;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN cur1;
    loop1: LOOP
		FETCH cur1 into username;
		IF done THEN leave loop1;
        END IF;
        INSERT INTO notificacao (utilizador_username,mensagem) VALUES (username,mensagem);
	END LOOP;
END$$
-- CALL notificaEquipa("FC Porto","Aposte Já Novo Jogo Disponivel do Porto Ano 2023");
-- CALL notificaEquipa("SL Benfica","Aposte Já Novo Jogo Disponivel do Benfica Ano 2023");

-- -----------------------------------------------------------------------------------------------------------------
-- 19 PROCEDURE para inserir apostas de um jogo na base de dados ---------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE insereApostaJogo;
DELIMITER $$
CREATE PROCEDURE insereApostaJogo(IN idEquipa VARCHAR(45),IN idJogo INT,IN resultado BOOLEAN,IN tipo VARCHAR(45),
				IN minApostar FLOAT,IN maxApostar FLOAT,IN rate FLOAT,IN posicao INT)
BEGIN
	INSERT INTO aposta (minAposta,maxAposta,rate,jogo_idChave,tipo,posicao,equipa_idNome,resultado) VALUES
	(minApostar,maxApostar,rate,idJogo,tipo,NULL,idEquipa,NULL);
END$$
-- CALL insereApostaJogo("FC Porto",3,0,"Vitória",0,10,1.4,NULL);
-- CALL insereApostaJogo("SL Benfica",3,0,"Vitória",0,10,1.4,NULL);
-- CALL insereApostaJogo(NULL,3,0,"Empate",0,10,1.4,NULL);


-- -----------------------------------------------------------------------------------------------------------------
-- 20 PROCEDURE para ver historico de uma equipa -------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE historicoEquipa;
DELIMITER $$
CREATE PROCEDURE historicoEquipa(IN idEquipa VARCHAR(45),IN DataNow DATE)
BEGIN
	SELECT idChave,descricao,estado,data,local,aceitaApostas FROM jogo
    INNER JOIN equipa_jogo ON jogo_idChave=jogo.idChave 
		WHERE equipa_jogo.equipa_idNome=idEquipa and jogo.data<dataNow
    GROUP BY jogo.idChave;
END$$
-- CALL historicoEquipa("SL Benfica",'2022-01-15');

-- -----------------------------------------------------------------------------------------------------------------
-- 21 PROCEDURE para criar equipa ----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE criaEquipa;
DELIMITER $$
CREATE PROCEDURE criaEquipa(IN gestor VARCHAR(45),IN idEquipa VARCHAR(45),IN liga VARCHAR(45),IN local VARCHAR(45),
							IN desporto VARCHAR(45),IN descricao TEXT(100))
BEGIN
	INSERT INTO equipa (idNome,utilizador_username,localidade,liga,desporto,descricao) VALUES
		(idEquipa,gestor,local,liga,desporto,descricao);
END$$
-- CALL criaEquipa("Carlos","SL Sporting","1ª liga portuguesa","estádio de lisboa","Futebol","O Sporting Clube de Portugal é um clube português, eclético e multidesportivo, fundado a 1 de julho de 1906, com sede em Lisboa no Complexo Alvalade XXI. É um dos Tres Grandes clubes em Portugal, juntamente com SL Benfica e FC Porto.");

-- -----------------------------------------------------------------------------------------------------------------
-- 22 PROCEDURE para ver se equipa ta como favorito ----------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE verificaFavorito;
DELIMITER $$
CREATE PROCEDURE verificaFavorito(IN idEquipa VARCHAR(45),IN username VARCHAR(45))
BEGIN
	SELECT Count(*) as favorito FROM equipasfavoritas WHERE utilizador_username=username and equipa_idNome=idEquipa;
END$$
-- CALL verificaFavorito("SL Sporting","Carlos");

-- -----------------------------------------------------------------------------------------------------------------
-- 23 PROCEDURE para adicionar equipa como favorito ----------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE addFavorito;
DELIMITER $$
CREATE PROCEDURE addFavorito(IN idEquipa VARCHAR(45),IN username VARCHAR(45))
BEGIN
	INSERT INTO equipasfavoritas (utilizador_username,equipa_idNome) VALUES
		(username,idEquipa);
END$$
-- CALL addFavorito("SL Sporting","Carlos");

-- -----------------------------------------------------------------------------------------------------------------
-- 24 PROCEDURE ver Notificações -----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE verNotificacoes;
DELIMITER $$
CREATE PROCEDURE verNotificacoes(IN username VARCHAR(45))
BEGIN
	SELECT * FROM notificacao WHERE utilizador_username=username; 
END$$
-- CALL verNotificacoes("Carlos");

-- -----------------------------------------------------------------------------------------------------------------
-- 25 PROCEDURE para apagar notificações ---------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE apagaNotificacoes;
DELIMITER $$
CREATE PROCEDURE apagaNotificacoes(IN username VARCHAR(45))
BEGIN
	DELETE FROM notificacao WHERE utilizador_username=username;
END$$
-- CALL apagaNotificacoes("Carlos");

-- -----------------------------------------------------------------------------------------------------------------
-- 26 PROCEDURE para ver apostas de um utilizador ------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE verApostasUtilizador;
DELIMITER $$
CREATE PROCEDURE verApostasUtilizador(IN username VARCHAR(45))
BEGIN
	SELECT jogo.idChave,utilizador_aposta.data,valor,utilizador_aposta.rate,tipo,equipa_idNome,descricao,resultado 
		FROM utilizador_aposta INNER JOIN aposta ON aposta_id=aposta.id
        INNER JOIN jogo ON jogo_idChave=idChave
        WHERE utilizador_aposta.utilizador_username=username;
END$$
-- CALL verApostasUtilizador("Carlos");

-- -----------------------------------------------------------------------------------------------------------------
-- 27 PROCEDURE para ver ligas -------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE verLigas;
DELIMITER $$
CREATE PROCEDURE verLigas()
BEGIN
	SELECT liga FROM equipa
    GROUP BY liga;
END$$
-- CALL verLigas();

-- -----------------------------------------------------------------------------------------------------------------
-- 28 PROCEDURE para ver equipas de uma liga -----------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE verEquipasDeLiga;
DELIMITER $$
CREATE PROCEDURE verEquipasDeLiga(IN liga VARCHAR(45))
BEGIN
	SELECT idNome FROM equipa WHERE equipa.liga=liga;
END$$
-- CALL verEquipasDeLiga("1ª liga portuguesa");

-- -----------------------------------------------------------------------------------------------------------------
-- 29 PROCEDURE para devolver dinheiro aposta  ---------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE devolveAposta;
DELIMITER $$
CREATE PROCEDURE devolveAposta(IN idAposta INT)
BEGIN
	DECLARE username VARCHAR(45);
    DECLARE valor1 FLOAT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cur1 CURSOR FOR SELECT utilizador_username,valor 
			FROM utilizador_aposta 
			WHERE aposta_id=idAposta;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	OPEN cur1;
	loop1: LOOP
		FETCH cur1 into username,valor1;
		IF done THEN leave loop1;
		END IF;
		UPDATE utilizador SET saldo=saldo+(valor1) WHERE utilizador.username=username;
	END LOOP;
END$$
-- CALL devolveAposta(1);

-- -----------------------------------------------------------------------------------------------------------------
-- 30 PROCEDURE para modificar equipa ----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE modificaEquipa;
DELIMITER $$
CREATE PROCEDURE modificaEquipa(IN idEquipa VARCHAR(45),IN liga VARCHAR(45),IN local VARCHAR(45),
							IN desporto VARCHAR(45),IN descricao TEXT(100))
BEGIN
	UPDATE equipa SET equipa.liga=liga, equipa.localidade=local, equipa.desporto=desporto, equipa.descricao=descricao
		WHERE equipa.idNome=idEquipa;
END$$
-- CALL modificaEquipa("SL Sporting","1ª liga portuguesa","estádio de lisboa","Futebol","O Sporting Clube de Portugal é um clube português, eclético e multidesportivo, fundado a 1 de julho de 1906, com sede em Lisboa no Complexo Alvalade XXI. É um dos Tres Grandes clubes em Portugal, juntamente com SL Benfica e FC Porto.");

-- -----------------------------------------------------------------------------------------------------------------
-- 31 PROCEDURE para ver faturas de utilizador ---------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE verFaturas;
DELIMITER $$
CREATE PROCEDURE verFaturas(IN idUti VARCHAR(45))
BEGIN
	SELECT * FROM fatura WHERE fatura.utilizador_username = idUti;
END$$
-- CALL verFaturas("Carlos");

-- -----------------------------------------------------------------------------------------------------------------
-- 32 PROCEDURE para ver equipa ---------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE getEquipa;
DELIMITER $$
CREATE PROCEDURE getEquipa(IN idEquipa VARCHAR(45))
BEGIN
    SELECT * FROM equipa WHERE equipa.idNome = idEquipa;
END$$
-- CALL getEquipa("SL Benfica");

-- -----------------------------------------------------------------------------------------------------------------
-- 33 PROCEDURE para ver equipa ---------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------
-- DROP PROCEDURE getMoedas;
DELIMITER $$
CREATE PROCEDURE getMoedas()
BEGIN
    SELECT * FROM moeda;
END$$
-- CALL getMoedas();
