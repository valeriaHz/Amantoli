-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-11-2024 a las 05:14:51
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `store`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_usuarios`
--

CREATE TABLE `t_usuarios` (
  `id_usuario` int(11) DEFAULT NULL,
  `nombre` varchar(250) NOT NULL,
  `apellidoPat` varchar(250) NOT NULL,
  `apellidoMat` varchar(250) NOT NULL,
  `edad` varchar(250) NOT NULL,
  `genero` varchar(250) NOT NULL,
  `correo` varchar(250) NOT NULL,
  `contraseña` varchar(250) NOT NULL,
  `fechaNac` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `t_usuarios`
--

INSERT INTO `t_usuarios` (`id_usuario`, `nombre`, `apellidoPat`, `apellidoMat`, `edad`, `genero`, `correo`, `contraseña`, `fechaNac`) VALUES
(1, 'Sarahi', 'Aguilar', 'Hernandez', '24', 'Femenino', 'sara@email.com', '123456789', '2000/09/13'),
(3, 'Mauricio', 'Cristel', 'Montero', '29', 'Masculino', 'mauri@email.com', '97846532', '1995/05/17'),
(4, 'Morrigan', 'Finel', 'Vera', '25', 'Otro', 'mor@email.com', '65897423', '1968/06/18');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
