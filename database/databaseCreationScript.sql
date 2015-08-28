SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `rosterdb` DEFAULT CHARACTER SET latin1 ;
USE `rosterdb` ;

-- -----------------------------------------------------
-- Table `rosterdb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rosterdb`.`users` ;

CREATE  TABLE IF NOT EXISTS `rosterdb`.`users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `login` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `userType` CHAR(1) NOT NULL ,
  `firstName` VARCHAR(45) NULL DEFAULT NULL ,
  `lastName` VARCHAR(45) NULL DEFAULT NULL ,
  `email` VARCHAR(45) NULL DEFAULT NULL ,
  `phone` VARCHAR(45) NULL DEFAULT NULL ,
  'rosterShowStartDate' DATETIME NULL DEFAULT NULL ,
  'rosterShowEndtDate' DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `rosterdb`.`user_patterns`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rosterdb`.`user_patterns` ;

CREATE  TABLE IF NOT EXISTS `rosterdb`.`user_patterns` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `userId` BIGINT(20) NOT NULL ,
  `patternId` BIGINT(20) NOT NULL ,
  `startDay` DATETIME NOT NULL ,
  `endDay` DATETIME NULL DEFAULT NULL ,
  `patternStartDay` INT(11) NULL DEFAULT '1' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `rosterdb`.`shifts_on_exact_day`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `rosterdb`.`shifts_on_exact_day` ;

CREATE TABLE IF NOT EXISTS `rosterdb`.`shifts_on_exact_day` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `userId` BIGINT(20) NOT NULL,
  `date` DATE NOT NULL,
  `shiftId` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB ;

-- -----------------------------------------------------
-- Table `rosterdb`.`unique_shifts`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `rosterdb`.`shifts` ;

CREATE TABLE IF NOT EXISTS `rosterdb`.`shifts` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45),
  `start` VARCHAR(45),
  `end` VARCHAR(45),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB ;


 -- -----------------------------------------------------
-- Table `rosterdb`.`patterns`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `rosterdb`.`patterns` ;

CREATE  TABLE IF NOT EXISTS `rosterdb`.`patterns` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rosterdb`.`patterns_shifts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rosterdb`.`patterns_shifts` ;

CREATE  TABLE IF NOT EXISTS `rosterdb`.`patterns_shifts` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `patternId` BIGINT(20) NULL DEFAULT NULL ,
  `shiftId` BIGINT(20) NULL DEFAULT NULL ,
  `seqNo` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
   KEY `shift_fk` (`shiftId`),
  CONSTRAINT `shift_fk` FOREIGN KEY (shiftId) REFERENCES `shifts` (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


USE `rosterdb` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
