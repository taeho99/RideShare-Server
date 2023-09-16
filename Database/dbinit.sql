-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema RideShareDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `RideShareDB` ;

-- -----------------------------------------------------
-- Schema RideShareDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `RideShareDB` DEFAULT CHARACTER SET utf8 ;
USE `RideShareDB` ;

-- -----------------------------------------------------
-- Table `RideShareDB`.`MEMBER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RideShareDB`.`MEMBER` ;

CREATE TABLE IF NOT EXISTS `RideShareDB`.`MEMBER` (
  `m_id` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(15) NOT NULL,
  `pw` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(10) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `auth_code` INT NOT NULL,
  `auth_status` TINYINT NOT NULL,
  `authority` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`m_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RideShareDB`.`PARTY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RideShareDB`.`PARTY` ;

CREATE TABLE IF NOT EXISTS `RideShareDB`.`PARTY` (
  `p_id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(2) NOT NULL,
  `start_date` VARCHAR(12) NOT NULL,
  `start_time` VARCHAR(10) NOT NULL,
  `start_point` VARCHAR(100) NOT NULL,
  `start_lat` VARCHAR(20) NOT NULL,
  `start_lng` VARCHAR(20) NOT NULL,
  `end_point` VARCHAR(100) NOT NULL,
  `end_lat` VARCHAR(20) NOT NULL,
  `end_lng` VARCHAR(20) NOT NULL,
  `current_headcnt` INT NOT NULL DEFAULT 1,
  `total_headcnt` INT NOT NULL,
  `is_confirm` TINYINT(1) NOT NULL DEFAULT 0,
  `is_finish` TINYINT(1) NOT NULL DEFAULT 0,
  `car_number` VARCHAR(8) NULL,
  `content` TEXT NULL,
  PRIMARY KEY (`p_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RideShareDB`.`REVIEW`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RideShareDB`.`REVIEW` ;

CREATE TABLE IF NOT EXISTS `RideShareDB`.`REVIEW` (
  `r_id` INT NOT NULL AUTO_INCREMENT,
  `score` INT NOT NULL,
  `m_id` INT NOT NULL,
  PRIMARY KEY (`r_id`),
  INDEX `fk_REVIEW_MEMBER1_idx` (`m_id` ASC) VISIBLE,
  CONSTRAINT `fk_REVIEW_MEMBER1`
    FOREIGN KEY (`m_id`)
    REFERENCES `RideShareDB`.`MEMBER` (`m_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RideShareDB`.`MEMBER_has_PARTY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RideShareDB`.`MEMBER_has_PARTY` ;

CREATE TABLE IF NOT EXISTS `RideShareDB`.`MEMBER_has_PARTY` (
  `j_id` INT NOT NULL AUTO_INCREMENT,
  `m_id` INT NOT NULL,
  `p_id` INT NOT NULL,
  `is_writer` TINYINT(1) NOT NULL DEFAULT 0,
  INDEX `fk_MEMBER_has_PARTY_PARTY1_idx` (`p_id` ASC) VISIBLE,
  INDEX `fk_MEMBER_has_PARTY_MEMBER_idx` (`m_id` ASC) VISIBLE,
  PRIMARY KEY (`j_id`),
  CONSTRAINT `fk_MEMBER_has_PARTY_MEMBER`
    FOREIGN KEY (`m_id`)
    REFERENCES `RideShareDB`.`MEMBER` (`m_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MEMBER_has_PARTY_PARTY1`
    FOREIGN KEY (`p_id`)
    REFERENCES `RideShareDB`.`PARTY` (`p_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RideShareDB`.`REFRESH_TOKEN`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RideShareDB`.`REFRESH_TOKEN` ;

CREATE TABLE IF NOT EXISTS `RideShareDB`.`REFRESH_TOKEN` (
  `rt_key` VARCHAR(10) NOT NULL,
  `rt_value` VARCHAR(255) NULL,
  PRIMARY KEY (`rt_key`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `RideShareDB`.`CHAT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RideShareDB`.`CHAT` ;

CREATE TABLE IF NOT EXISTS `RideShareDB`.`CHAT` (
   chat_id` BIGINT NOT NULL AUTO_INCREMENT,
   sender` VARCHAR(15) NOT NULL,
   `message` TEXT NOT NULL,
   `time` TIMESTAMP NOT NULL,
   `room_id` INT NOT NULL,
   INDEX `fk_CHAT_PARTY1_idx` (`room_id` ASC) VISIBLE,
   PRIMARY KEY (`chat_id`),
   CONSTRAINT `fk_CHAT_PARTY1`
     FOREIGN KEY (`room_id`)
     REFERENCES `RideShareDB`.`PARTY` (`p_id`)
     ON DELETE CASCADE
     ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

