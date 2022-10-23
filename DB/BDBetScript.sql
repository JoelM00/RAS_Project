-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema BetDataBase
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema BetDataBase
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BetDataBase` DEFAULT CHARACTER SET utf8 ;
USE `BetDataBase` ;

-- -----------------------------------------------------
-- Table `BetDataBase`.`utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`utilizador` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `gestor` TINYINT NOT NULL,
  `genero` CHAR(1) NULL,
  `anoNascimento` INT NULL,
  `localizacao` VARCHAR(45) NULL,
  `saldo` INT NOT NULL,
  `contaMultibanco` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`notificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`notificacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `utilizador_username` VARCHAR(45) NOT NULL,
  `mensagem` TEXT(50) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_notificacao_utilizador1_idx` (`utilizador_username` ASC) VISIBLE,
  CONSTRAINT `fk_notificacao_utilizador1`
    FOREIGN KEY (`utilizador_username`)
    REFERENCES `BetDataBase`.`utilizador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`equipa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`equipa` (
  `idNome` VARCHAR(45) NOT NULL,
  `localidade` VARCHAR(45) NOT NULL,
  `liga` VARCHAR(45) NOT NULL,
  `desporto` VARCHAR(45) NOT NULL,
  `descricao` TEXT(100) NOT NULL,
  `utilizador_username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idNome`),
  INDEX `fk_equipa_utilizador1_idx` (`utilizador_username` ASC) VISIBLE,
  CONSTRAINT `fk_equipa_utilizador1`
    FOREIGN KEY (`utilizador_username`)
    REFERENCES `BetDataBase`.`utilizador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`equipasFavoritas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`equipasFavoritas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `utilizador_username` VARCHAR(45) NOT NULL,
  `equipa_idNome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_equipasFavoritas_utilizador_idx` (`utilizador_username` ASC) VISIBLE,
  INDEX `fk_equipasFavoritas_equipa1_idx` (`equipa_idNome` ASC) VISIBLE,
  CONSTRAINT `fk_equipasFavoritas_utilizador`
    FOREIGN KEY (`utilizador_username`)
    REFERENCES `BetDataBase`.`utilizador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_equipasFavoritas_equipa1`
    FOREIGN KEY (`equipa_idNome`)
    REFERENCES `BetDataBase`.`equipa` (`idNome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`moeda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`moeda` (
  `nome` VARCHAR(45) NOT NULL,
  `rateToBet` FLOAT NOT NULL,
  `rateFromBet` FLOAT NOT NULL,
  PRIMARY KEY (`nome`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`fatura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`fatura` (
  `idfatura` VARCHAR(45) NOT NULL,
  `valor` FLOAT NOT NULL,
  `tipoDepOuRec` CHAR(1) NOT NULL,
  `data` DATE NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `dataValidade` DATE NOT NULL,
  `utilizador_username` VARCHAR(45) NOT NULL,
  `moeda_nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idfatura`),
  INDEX `fk_fatura_utilizador1_idx` (`utilizador_username` ASC) VISIBLE,
  INDEX `fk_fatura_moeda1_idx` (`moeda_nome` ASC) VISIBLE,
  CONSTRAINT `fk_fatura_utilizador1`
    FOREIGN KEY (`utilizador_username`)
    REFERENCES `BetDataBase`.`utilizador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_fatura_moeda1`
    FOREIGN KEY (`moeda_nome`)
    REFERENCES `BetDataBase`.`moeda` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`jogo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`jogo` (
  `idChave` INT NOT NULL,
  `descricao` VARCHAR(100) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `data` DATE NOT NULL,
  `local` VARCHAR(45) NOT NULL,
  `utilizador_username` VARCHAR(45) NOT NULL,
  `aceitaApostas` TINYINT NOT NULL,
  PRIMARY KEY (`idChave`),
  INDEX `fk_jogo_utilizador1_idx` (`utilizador_username` ASC) VISIBLE,
  CONSTRAINT `fk_jogo_utilizador1`
    FOREIGN KEY (`utilizador_username`)
    REFERENCES `BetDataBase`.`utilizador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`equipa_jogo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`equipa_jogo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `jogo_idChave` INT NOT NULL,
  `equipa_idNome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_equipa_jogo_jogo1_idx` (`jogo_idChave` ASC) VISIBLE,
  INDEX `fk_equipa_jogo_equipa1_idx` (`equipa_idNome` ASC) VISIBLE,
  CONSTRAINT `fk_equipa_jogo_jogo1`
    FOREIGN KEY (`jogo_idChave`)
    REFERENCES `BetDataBase`.`jogo` (`idChave`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_equipa_jogo_equipa1`
    FOREIGN KEY (`equipa_idNome`)
    REFERENCES `BetDataBase`.`equipa` (`idNome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`aposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`aposta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `minAposta` FLOAT NULL,
  `maxAposta` FLOAT NULL,
  `rate` FLOAT NOT NULL,
  `jogo_idChave` INT NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `posicao` INT NULL,
  `equipa_idNome` VARCHAR(45) NULL,
  `resultado` TINYINT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_aposta_jogo1_idx` (`jogo_idChave` ASC) VISIBLE,
  INDEX `fk_aposta_equipa1_idx` (`equipa_idNome` ASC) VISIBLE,
  CONSTRAINT `fk_aposta_jogo1`
    FOREIGN KEY (`jogo_idChave`)
    REFERENCES `BetDataBase`.`jogo` (`idChave`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aposta_equipa1`
    FOREIGN KEY (`equipa_idNome`)
    REFERENCES `BetDataBase`.`equipa` (`idNome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BetDataBase`.`utilizador_Aposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BetDataBase`.`utilizador_Aposta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data` DATE NOT NULL,
  `valor` FLOAT NOT NULL,
  `rate` FLOAT NOT NULL,
  `utilizador_username` VARCHAR(45) NOT NULL,
  `aposta_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_utilizador_Aposta_utilizador1_idx` (`utilizador_username` ASC) VISIBLE,
  INDEX `fk_utilizador_Aposta_aposta1_idx` (`aposta_id` ASC) VISIBLE,
  CONSTRAINT `fk_utilizador_Aposta_utilizador1`
    FOREIGN KEY (`utilizador_username`)
    REFERENCES `BetDataBase`.`utilizador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_utilizador_Aposta_aposta1`
    FOREIGN KEY (`aposta_id`)
    REFERENCES `BetDataBase`.`aposta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;