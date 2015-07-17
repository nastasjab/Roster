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
  `shiftPatternId` BIGINT(20) NOT NULL ,
  `startDay` DATETIME NOT NULL ,
  `endDay` DATETIME NULL DEFAULT NULL ,
  `patternStartDay` INT(11) NULL DEFAULT '1' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

USE `rosterdb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
