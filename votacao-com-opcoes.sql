-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 08-Fev-2017 às 01:17
-- Versão do servidor: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `votacao`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `aplicacao`
--

CREATE TABLE IF NOT EXISTS `aplicacao` (
  `iniciar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `aplicacao`
--

INSERT INTO `aplicacao` (`iniciar`) VALUES
(0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `grupo`
--

CREATE TABLE IF NOT EXISTS `grupo` (
  `id_grupo` int(11) NOT NULL,
  `grupo` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `opcoes`
--

CREATE TABLE IF NOT EXISTS `opcoes` (
  `id_opcao` int(11) NOT NULL,
  `opcao` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `opcoes`
--

INSERT INTO `opcoes` (`id_opcao`, `opcao`) VALUES
(15, 'ALESSANDRO'),
(8, 'BRUNO'),
(1, 'CRISTIANO'),
(2, 'DAVID'),
(14, 'DEIWYS'),
(7, 'FELIPE'),
(11, 'GUILHERME'),
(17, 'JEFFERSON'),
(4, 'JONAS'),
(16, 'LOBAO'),
(18, 'LUCAS'),
(19, 'MAILSON'),
(6, 'MOLINA'),
(20, 'MURILO'),
(5, 'NAGIPIO'),
(13, 'NEGAO'),
(9, 'NICHOLAS'),
(10, 'PEDRO'),
(21, 'RAFA'),
(22, 'RONALDO'),
(12, 'RUAN'),
(23, 'RUBENS'),
(26, 'THIAGO'),
(24, 'VINI'),
(25, 'WALLACE'),
(3, 'WILLIAN');

-- --------------------------------------------------------

--
-- Estrutura da tabela `perguntas`
--

CREATE TABLE IF NOT EXISTS `perguntas` (
  `id_pergunta` int(11) NOT NULL,
  `pergunta` varchar(97) NOT NULL,
  `multipla` int(1) NOT NULL,
  `qntEscolhas` int(2) NOT NULL,
  `FK_id_grupo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `questionario`
--

CREATE TABLE IF NOT EXISTS `questionario` (
  `id_questionario` int(11) NOT NULL,
  `id_pergunta` int(11) NOT NULL,
  `id_opcao` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `resultado`
--

CREATE TABLE IF NOT EXISTS `resultado` (
  `id_resultado` int(11) NOT NULL,
  `pergunta` int(11) NOT NULL,
  `resposta` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `titulo`
--

CREATE TABLE IF NOT EXISTS `titulo` (
  `id_titulo` int(11) NOT NULL,
  `titulo` varchar(20) NOT NULL,
  `grupo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `aplicacao`
--
ALTER TABLE `aplicacao`
  ADD PRIMARY KEY (`iniciar`);

--
-- Indexes for table `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`id_grupo`), ADD UNIQUE KEY `unq_grupo` (`grupo`);

--
-- Indexes for table `opcoes`
--
ALTER TABLE `opcoes`
  ADD PRIMARY KEY (`id_opcao`), ADD UNIQUE KEY `unq_nome` (`opcao`);

--
-- Indexes for table `perguntas`
--
ALTER TABLE `perguntas`
  ADD PRIMARY KEY (`id_pergunta`), ADD KEY `FK_id_grupo` (`FK_id_grupo`);

--
-- Indexes for table `questionario`
--
ALTER TABLE `questionario`
  ADD PRIMARY KEY (`id_questionario`), ADD KEY `id_pergunta` (`id_pergunta`,`id_opcao`), ADD KEY `id_opcao` (`id_opcao`);

--
-- Indexes for table `resultado`
--
ALTER TABLE `resultado`
  ADD PRIMARY KEY (`id_resultado`), ADD KEY `pergunta` (`pergunta`), ADD KEY `resposta` (`resposta`);

--
-- Indexes for table `titulo`
--
ALTER TABLE `titulo`
  ADD PRIMARY KEY (`id_titulo`), ADD UNIQUE KEY `unqTitulo` (`titulo`), ADD KEY `FK_idGrupo` (`grupo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `grupo`
--
ALTER TABLE `grupo`
  MODIFY `id_grupo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `opcoes`
--
ALTER TABLE `opcoes`
  MODIFY `id_opcao` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `perguntas`
--
ALTER TABLE `perguntas`
  MODIFY `id_pergunta` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `questionario`
--
ALTER TABLE `questionario`
  MODIFY `id_questionario` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `resultado`
--
ALTER TABLE `resultado`
  MODIFY `id_resultado` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `titulo`
--
ALTER TABLE `titulo`
  MODIFY `id_titulo` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `perguntas`
--
ALTER TABLE `perguntas`
ADD CONSTRAINT `perguntas_ibfk_1` FOREIGN KEY (`FK_id_grupo`) REFERENCES `grupo` (`id_grupo`);

--
-- Limitadores para a tabela `questionario`
--
ALTER TABLE `questionario`
ADD CONSTRAINT `questionario_ibfk_1` FOREIGN KEY (`id_opcao`) REFERENCES `opcoes` (`id_opcao`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `questionario_ibfk_2` FOREIGN KEY (`id_pergunta`) REFERENCES `perguntas` (`id_pergunta`);

--
-- Limitadores para a tabela `resultado`
--
ALTER TABLE `resultado`
ADD CONSTRAINT `resultado_ibfk_2` FOREIGN KEY (`resposta`) REFERENCES `opcoes` (`opcao`),
ADD CONSTRAINT `resultado_ibfk_3` FOREIGN KEY (`pergunta`) REFERENCES `perguntas` (`id_pergunta`);

--
-- Limitadores para a tabela `titulo`
--
ALTER TABLE `titulo`
ADD CONSTRAINT `titulo_ibfk_1` FOREIGN KEY (`grupo`) REFERENCES `grupo` (`id_grupo`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
