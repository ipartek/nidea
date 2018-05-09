-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.7.20-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para nidea
DROP DATABASE IF EXISTS `nidea`;
CREATE DATABASE IF NOT EXISTS `nidea` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `nidea`;

-- Volcando estructura para tabla nidea.iva
DROP TABLE IF EXISTS `iva`;
CREATE TABLE IF NOT EXISTS `iva` (
  `precio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla nidea.iva: ~0 rows (aproximadamente)
DELETE FROM `iva`;
/*!40000 ALTER TABLE `iva` DISABLE KEYS */;
INSERT INTO `iva` (`precio`) VALUES
	(18);
/*!40000 ALTER TABLE `iva` ENABLE KEYS */;

-- Volcando estructura para tabla nidea.material
DROP TABLE IF EXISTS `material`;
CREATE TABLE IF NOT EXISTS `material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `precio` float NOT NULL DEFAULT '0',
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `fk_material_usuario1_idx` (`id_usuario`),
  CONSTRAINT `fk_material_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla nidea.material: ~3 rows (aproximadamente)
DELETE FROM `material`;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` (`id`, `nombre`, `precio`, `id_usuario`) VALUES
	(9, 'Madera', 12, 2),
	(10, 'Goma', 5, 2),
	(14, 'Formicas', 12, 2);
/*!40000 ALTER TABLE `material` ENABLE KEYS */;

-- Volcando estructura para tabla nidea.rol
DROP TABLE IF EXISTS `rol`;
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla nidea.rol: ~2 rows (aproximadamente)
DELETE FROM `rol`;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`id`, `nombre`) VALUES
	(1, 'admin'),
	(2, 'user');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;

-- Volcando estructura para tabla nidea.usuario
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) NOT NULL,
  `password` varchar(45) NOT NULL,
  `id_rol` int(11) NOT NULL,
  `email` varchar(150) NOT NULL,
  `fecha_alta` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_modificacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_baja` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`id_rol`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_usuario_rol_idx` (`id_rol`),
  CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla nidea.usuario: ~9 rows (aproximadamente)
DELETE FROM `usuario`;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nombre`, `password`, `id_rol`, `email`, `fecha_alta`, `fecha_modificacion`, `fecha_baja`) VALUES
	(2, 'pepe', '123456', 2, 'pepe@gmail.com', '2018-05-02 12:05:22', '2018-05-02 12:06:31', NULL),
	(3, 'usuariox', '123456', 2, 'usuario@gmail.com', '2018-05-02 12:05:22', '2018-05-02 12:06:31', NULL),
	(4, 'usuario0', '123456', 2, 'usuario0@hotmaill.com', '2018-05-02 12:05:22', '2018-05-02 12:09:08', NULL),
	(5, 'usuario1', '123456', 2, 'usuario1@gmail.com', '2018-05-02 12:05:22', '2018-05-02 12:06:31', NULL),
	(6, 'paco', '123456', 2, 'paco@gmail.com', '2018-05-02 12:05:22', '2018-05-02 12:06:31', NULL),
	(21, 'mikel', 'manolo', 2, 'mikel@gmail.com', '2018-05-02 12:05:22', '2018-05-02 12:06:31', NULL),
	(24, 'manolo', 'manolo', 2, 'manolo@gmail.com', '2018-05-02 12:05:22', '2018-05-02 12:06:31', NULL),
	(30, 'imk', 'manolo', 2, 'imk@gmail.com', '2018-05-02 12:05:22', '2018-05-02 12:06:31', NULL),
	(31, 'michael', 'manolo', 2, 'michael@gmail.com', '2018-05-02 12:05:22', '2018-05-02 12:15:43', '2018-05-02 12:15:41'),
	(63, 'daf', '', 2, '', '2018-05-08 16:37:57', '2018-05-08 16:37:57', NULL),
	(64, 'admin', 'admin', 1, 'admin@gmail.com', '2018-05-09 14:33:37', '2018-05-09 14:33:38', NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Volcando estructura para vista nidea.estadisticas_usuarios
DROP VIEW IF EXISTS `estadisticas_usuarios`;
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `estadisticas_usuarios` (
	`usuarios` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista nidea.usuarios_activos
DROP VIEW IF EXISTS `usuarios_activos`;
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `usuarios_activos` (
	`Usuarios Activos` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista nidea.usuarios_eliminados
DROP VIEW IF EXISTS `usuarios_eliminados`;
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `usuarios_eliminados` (
	`Usuarios Eliminados` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para procedimiento nidea.Pa_create_user
DROP PROCEDURE IF EXISTS `Pa_create_user`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `Pa_create_user`(
	OUT `nombre` VARCHAR(150),
	OUT `email` VARCHAR(150),
	IN `id` INT






,
	OUT `pass` VARCHAR(50)
,
	OUT `id_rol` INT
)
    COMMENT 'Crear usuario'
BEGIN


Insert into usuario (id,nombre,password,id_rol,email) Values (-1,"pepe","pepe",2,"pepe@gmail.com");



END//
DELIMITER ;

-- Volcando estructura para procedimiento nidea.Pa_delete_user
DROP PROCEDURE IF EXISTS `Pa_delete_user`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `Pa_delete_user`(
	IN `id` INT,
	OUT `nombre` INT,
	OUT `email` INT

)
    COMMENT 'Borrar usuarios por su id'
BEGIN

Delete from usuario where id="";



END//
DELIMITER ;

-- Volcando estructura para procedimiento nidea.pa_get_user_by_id
DROP PROCEDURE IF EXISTS `pa_get_user_by_id`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_get_user_by_id`(
	IN `p_id` INT,
	OUT `o_nombre` VARCHAR(150),
	OUT `o_email` VARCHAR(150)

)
    COMMENT 'Procedimiento almacenado para obtener usuario por su id'
BEGIN

	Select nombre,email
	into o_nombre,o_email
	from usuario
	where id=p_id;
END//
DELIMITER ;

-- Volcando estructura para función nidea.nombre_mes
DROP FUNCTION IF EXISTS `nombre_mes`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `nombre_mes`(
	`fecha` DATETIME
) RETURNS varchar(50) CHARSET utf8
    COMMENT 'Retorna el nombre del mes en castellano a partir de un datetime'
BEGIN


	set lc_time_names='es_ES';
 	RETURN DATE_FORMAT(fecha,'%M');

END//
DELIMITER ;

-- Volcando estructura para función nidea.saludo
DROP FUNCTION IF EXISTS `saludo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `saludo`(
	`Nombre` VARCHAR(50)
) RETURNS varchar(200) CHARSET utf8
BEGIN

RETURN CONCAT('hola ',nombre);

END//
DELIMITER ;

-- Volcando estructura para disparador nidea.tau_iva
DROP TRIGGER IF EXISTS `tau_iva`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tau_iva` AFTER UPDATE ON `iva` FOR EACH ROW BEGIN

Update material set precio=(precio / OLD.precio=NEW.precio);


END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para vista nidea.estadisticas_usuarios
DROP VIEW IF EXISTS `estadisticas_usuarios`;
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `estadisticas_usuarios`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `estadisticas_usuarios` AS select count(0) AS `usuarios` from `usuario` where (`usuario`.`id_rol` = 2);

-- Volcando estructura para vista nidea.usuarios_activos
DROP VIEW IF EXISTS `usuarios_activos`;
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `usuarios_activos`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `usuarios_activos` AS select count(0) AS `Usuarios Activos` from `usuario` where isnull(`usuario`.`fecha_baja`);

-- Volcando estructura para vista nidea.usuarios_eliminados
DROP VIEW IF EXISTS `usuarios_eliminados`;
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `usuarios_eliminados`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `usuarios_eliminados` AS select count(0) AS `Usuarios Eliminados` from `usuario` where (`usuario`.`fecha_baja` is not null);

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
