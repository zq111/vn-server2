/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.5.62 : Database - farmwork1
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`farmwork1` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `farmwork1`;

/*Table structure for table `t_system_menu` */

DROP TABLE IF EXISTS `t_system_menu`;

CREATE TABLE `t_system_menu` (
  `uuid` varchar(40) NOT NULL COMMENT '这是注释',
  `is_leaf` varchar(40) DEFAULT NULL,
  `level` int(11) DEFAULT NULL COMMENT '这是注释',
  `menu_icon` varchar(40) DEFAULT NULL COMMENT '这是注释',
  `menu_name` varchar(40) DEFAULT NULL COMMENT '这是注释',
  `menu_remark` varchar(40) DEFAULT NULL,
  `menu_url` varchar(40) DEFAULT NULL COMMENT '这是注释',
  `parent_uuid` varchar(40) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL COMMENT '这是注释',
  `create_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  `is_delete` varchar(40) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_system_menu_user` */

DROP TABLE IF EXISTS `t_system_menu_user`;

CREATE TABLE `t_system_menu_user` (
  `user_uuid` varchar(40) NOT NULL,
  `menu_uuid` varchar(40) NOT NULL,
  `uuid` varchar(40) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  `is_delete` varchar(40) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_uuid`,`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_system_record` */

DROP TABLE IF EXISTS `t_system_record`;

CREATE TABLE `t_system_record` (
  `uuid` varchar(40) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `channel` varchar(40) DEFAULT NULL,
  `cost_time` varchar(40) DEFAULT NULL,
  `memberuuid` varchar(40) DEFAULT NULL,
  `method` varchar(40) DEFAULT NULL,
  `module` varchar(40) DEFAULT NULL,
  `req_data` varchar(400) DEFAULT NULL,
  `resp_data` varchar(15000) DEFAULT NULL,
  `source_ip` varchar(40) DEFAULT NULL,
  `status` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `INDEX_RECORD_CHANNLE` (`channel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_system_user` */

DROP TABLE IF EXISTS `t_system_user`;

CREATE TABLE `t_system_user` (
  `uuid` varchar(40) NOT NULL,
  `in_use` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `username` varchar(40) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  `is_delete` varchar(40) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
