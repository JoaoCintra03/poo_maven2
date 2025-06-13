-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 10/06/2025 às 19:59
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `db_eventos`
--
CREATE DATABASE IF NOT EXISTS `db_eventos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `db_eventos`;

-- --------------------------------------------------------

--
-- Estrutura para tabela `alunos`
--

CREATE TABLE `alunos` (
  `id` int(11) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `senha` varchar(250) NOT NULL,
  `telefone` varchar(15) NOT NULL,
  `cpf` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `eventos`
--

CREATE TABLE `eventos` (
  `id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `descricao` text DEFAULT NULL,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `curso` varchar(100) NOT NULL,
  `palestrante_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `inscricoes`
--

CREATE TABLE `inscricoes` (
  `id` int(11) NOT NULL,
  `aluno_id` int(11) NOT NULL,
  `evento_id` int(11) NOT NULL,
  `data_inscricao` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `palestrantes`
--

CREATE TABLE `palestrantes` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `mincurriculo` text DEFAULT NULL,
  `foto_url` varchar(250) DEFAULT NULL,
  `tema` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `alunos`
--
ALTER TABLE `alunos`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `eventos`
--
ALTER TABLE `eventos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk1` (`palestrante_id`);

--
-- Índices de tabela `inscricoes`
--
ALTER TABLE `inscricoes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unica_inscricao` (`aluno_id`,`evento_id`) USING BTREE,
  ADD KEY `fk3` (`evento_id`);

--
-- Índices de tabela `palestrantes`
--
ALTER TABLE `palestrantes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `alunos`
--
ALTER TABLE `alunos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `eventos`
--
ALTER TABLE `eventos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `inscricoes`
--
ALTER TABLE `inscricoes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `palestrantes`
--
ALTER TABLE `palestrantes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `eventos`
--
ALTER TABLE `eventos`
  ADD CONSTRAINT `fk1` FOREIGN KEY (`palestrante_id`) REFERENCES `palestrantes` (`id`);

--
-- Restrições para tabelas `inscricoes`
--
ALTER TABLE `inscricoes`
  ADD CONSTRAINT `fk2` FOREIGN KEY (`aluno_id`) REFERENCES `alunos` (`id`),
  ADD CONSTRAINT `fk3` FOREIGN KEY (`evento_id`) REFERENCES `eventos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
