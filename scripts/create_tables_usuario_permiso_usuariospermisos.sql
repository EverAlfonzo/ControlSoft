CREATE  TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `alias` VARCHAR(45) NOT NULL ,
  `nombre` VARCHAR(45) NULL ,
  `apellido` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NULL ,
  `password` CHAR(41) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `alias_UNIQUE` (`alias` ASC) );

CREATE  TABLE IF NOT EXISTS `permiso` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );


CREATE  TABLE IF NOT EXISTS `user_permisos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `usuario_id` INT NOT NULL ,
  `permiso_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_usuario_permisos_usuario_idx` (`usuario_id` ASC) ,
  INDEX `fk_usuario_permisos_permiso1_idx` (`permiso_id` ASC) ,
  UNIQUE INDEX `unique_together` (`usuario_id` ASC, `permiso_id` ASC) ,
  CONSTRAINT `fk_usuario_permisos_usuario`
    FOREIGN KEY (`usuario_id` )
    REFERENCES `user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_permisos_permiso1`
    FOREIGN KEY (`permiso_id` )
    REFERENCES `permiso` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	