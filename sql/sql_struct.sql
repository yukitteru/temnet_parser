CREATE DATABASE IF NOT EXISTS `ejabberd`
USE `ejabberd`;

-- Дамп структуры для таблица ejabberd.archive
CREATE TABLE IF NOT EXISTS `archive` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `timestamp` bigint(20) unsigned NOT NULL,
  `peer` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bare_peer` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `xml` mediumtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `txt` mediumtext COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `kind` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nick` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `id` (`id`),
  KEY `i_username_timestamp` (`username`,`timestamp`) USING BTREE,
  KEY `i_username_peer` (`username`,`peer`) USING BTREE,
  KEY `i_username_bare_peer` (`username`,`bare_peer`) USING BTREE,
  KEY `i_timestamp` (`timestamp`) USING BTREE,
  FULLTEXT KEY `i_text` (`txt`)
) ENGINE=InnoDB AUTO_INCREMENT=2141425 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.archive_prefs
CREATE TABLE IF NOT EXISTS `archive_prefs` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `def` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `always` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `never` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.bosh
CREATE TABLE IF NOT EXISTS `bosh` (
  `sid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `pid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_bosh_sid` (`sid`(75))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.caps_features
CREATE TABLE IF NOT EXISTS `caps_features` (
  `node` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `subnode` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `feature` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  KEY `i_caps_features_node_subnode` (`node`(75),`subnode`(75))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.carboncopy
CREATE TABLE IF NOT EXISTS `carboncopy` (
  `username` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `resource` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `namespace` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_carboncopy_ur` (`username`(75),`resource`(75)),
  KEY `i_carboncopy_user` (`username`(75))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.last
CREATE TABLE IF NOT EXISTS `last` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `seconds` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `state` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.mix_channel
CREATE TABLE IF NOT EXISTS `mix_channel` (
  `channel` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `service` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `domain` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `jid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `hidden` tinyint(1) NOT NULL,
  `hmac_key` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `i_mix_channel` (`channel`(191),`service`(191)),
  KEY `i_mix_channel_serv` (`service`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.mix_pam
CREATE TABLE IF NOT EXISTS `mix_pam` (
  `username` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `channel` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `service` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `id` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `i_mix_pam` (`username`(191),`channel`(191),`service`(191)),
  KEY `i_mix_pam_u` (`username`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.mix_participant
CREATE TABLE IF NOT EXISTS `mix_participant` (
  `channel` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `service` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `domain` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `jid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `id` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nick` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `i_mix_participant` (`channel`(191),`service`(191),`username`(191),`domain`(191)),
  KEY `i_mix_participant_chan_serv` (`channel`(191),`service`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.mix_subscription
CREATE TABLE IF NOT EXISTS `mix_subscription` (
  `channel` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `service` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `domain` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `jid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_mix_subscription` (`channel`(153),`service`(153),`username`(153),`domain`(153),`node`(153)),
  KEY `i_mix_subscription_chan_serv_ud` (`channel`(191),`service`(191),`username`(191),`domain`(191)),
  KEY `i_mix_subscription_chan_serv_node` (`channel`(191),`service`(191),`node`(191)),
  KEY `i_mix_subscription_chan_serv` (`channel`(191),`service`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.motd
CREATE TABLE IF NOT EXISTS `motd` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `xml` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.mqtt_pub
CREATE TABLE IF NOT EXISTS `mqtt_pub` (
  `username` varchar(191) NOT NULL,
  `resource` varchar(191) NOT NULL,
  `topic` text NOT NULL,
  `qos` tinyint(4) NOT NULL,
  `payload` blob NOT NULL,
  `payload_format` tinyint(4) NOT NULL,
  `content_type` text NOT NULL,
  `response_topic` text NOT NULL,
  `correlation_data` blob NOT NULL,
  `user_properties` blob NOT NULL,
  `expiry` int(10) unsigned NOT NULL,
  UNIQUE KEY `i_mqtt_topic` (`topic`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.muc_online_room
CREATE TABLE IF NOT EXISTS `muc_online_room` (
  `name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `host` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `pid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_muc_online_room_name_host` (`name`(75),`host`(75)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.muc_online_users
CREATE TABLE IF NOT EXISTS `muc_online_users` (
  `username` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `server` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `resource` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `host` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_muc_online_users` (`username`(75),`server`(75),`resource`(75),`name`(75),`host`(75)) USING BTREE,
  KEY `i_muc_online_users_us` (`username`(75),`server`(75)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.muc_registered
CREATE TABLE IF NOT EXISTS `muc_registered` (
  `jid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `host` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nick` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `i_muc_registered_jid_host` (`jid`(75),`host`(75)) USING BTREE,
  KEY `i_muc_registered_nick` (`nick`(75)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.muc_room
CREATE TABLE IF NOT EXISTS `muc_room` (
  `name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `host` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `opts` mediumtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `i_muc_room_name_host` (`name`(75),`host`(75)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.muc_room_subscribers
CREATE TABLE IF NOT EXISTS `muc_room_subscribers` (
  `room` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `host` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `jid` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nick` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nodes` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `i_muc_room_subscribers_host_room_jid` (`host`,`room`,`jid`),
  KEY `i_muc_room_subscribers_host_jid` (`host`,`jid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.oauth_client
CREATE TABLE IF NOT EXISTS `oauth_client` (
  `client_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `grant_type` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `options` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.oauth_token
CREATE TABLE IF NOT EXISTS `oauth_token` (
  `token` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `jid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `scope` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `expire` bigint(20) NOT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.privacy_default_list
CREATE TABLE IF NOT EXISTS `privacy_default_list` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.privacy_list
CREATE TABLE IF NOT EXISTS `privacy_list` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `i_privacy_list_username_name` (`username`(75),`name`(75)) USING BTREE,
  KEY `i_privacy_list_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.privacy_list_data
CREATE TABLE IF NOT EXISTS `privacy_list_data` (
  `id` bigint(20) DEFAULT NULL,
  `t` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `action` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ord` decimal(10,0) NOT NULL,
  `match_all` tinyint(1) NOT NULL,
  `match_iq` tinyint(1) NOT NULL,
  `match_message` tinyint(1) NOT NULL,
  `match_presence_in` tinyint(1) NOT NULL,
  `match_presence_out` tinyint(1) NOT NULL,
  KEY `i_privacy_list_data_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.private_storage
CREATE TABLE IF NOT EXISTS `private_storage` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `namespace` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `data` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `i_private_storage_username_namespace` (`username`(75),`namespace`(75)) USING BTREE,
  KEY `i_private_storage_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.proxy65
CREATE TABLE IF NOT EXISTS `proxy65` (
  `sid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `pid_t` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `pid_i` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node_t` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node_i` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `jid_i` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_proxy65_sid` (`sid`(191)),
  KEY `i_proxy65_jid` (`jid_i`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.pubsub_item
CREATE TABLE IF NOT EXISTS `pubsub_item` (
  `nodeid` bigint(20) DEFAULT NULL,
  `itemid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `publisher` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `creation` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `modification` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` mediumtext COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_pubsub_item_tuple` (`nodeid`,`itemid`(36)),
  KEY `i_pubsub_item_itemid` (`itemid`(36)),
  CONSTRAINT `pubsub_item_ibfk_1` FOREIGN KEY (`nodeid`) REFERENCES `pubsub_node` (`nodeid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.pubsub_node
CREATE TABLE IF NOT EXISTS `pubsub_node` (
  `host` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `plugin` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `nodeid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nodeid`),
  UNIQUE KEY `i_pubsub_node_tuple` (`host`(20),`node`(120)),
  KEY `i_pubsub_node_parent` (`parent`(120))
) ENGINE=InnoDB AUTO_INCREMENT=1604 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.pubsub_node_option
CREATE TABLE IF NOT EXISTS `pubsub_node_option` (
  `nodeid` bigint(20) DEFAULT NULL,
  `name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `val` text COLLATE utf8mb4_unicode_ci NOT NULL,
  KEY `i_pubsub_node_option_nodeid` (`nodeid`),
  CONSTRAINT `pubsub_node_option_ibfk_1` FOREIGN KEY (`nodeid`) REFERENCES `pubsub_node` (`nodeid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.pubsub_node_owner
CREATE TABLE IF NOT EXISTS `pubsub_node_owner` (
  `nodeid` bigint(20) DEFAULT NULL,
  `owner` text COLLATE utf8mb4_unicode_ci NOT NULL,
  KEY `i_pubsub_node_owner_nodeid` (`nodeid`),
  CONSTRAINT `pubsub_node_owner_ibfk_1` FOREIGN KEY (`nodeid`) REFERENCES `pubsub_node` (`nodeid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.pubsub_state
CREATE TABLE IF NOT EXISTS `pubsub_state` (
  `nodeid` bigint(20) DEFAULT NULL,
  `jid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `affiliation` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subscriptions` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `stateid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`stateid`),
  UNIQUE KEY `i_pubsub_state_tuple` (`nodeid`,`jid`(60)),
  KEY `i_pubsub_state_jid` (`jid`(60)),
  CONSTRAINT `pubsub_state_ibfk_1` FOREIGN KEY (`nodeid`) REFERENCES `pubsub_node` (`nodeid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1640 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.pubsub_subscription_opt
CREATE TABLE IF NOT EXISTS `pubsub_subscription_opt` (
  `subid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `opt_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `opt_value` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_pubsub_subscription_opt` (`subid`(32),`opt_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.push_session
CREATE TABLE IF NOT EXISTS `push_session` (
  `username` text NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  `service` text NOT NULL,
  `node` text NOT NULL,
  `xml` text NOT NULL,
  UNIQUE KEY `i_push_usn` (`username`(191),`service`(191),`node`(191)),
  UNIQUE KEY `i_push_ut` (`username`(191),`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.report
CREATE TABLE IF NOT EXISTS `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `groupname` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` mediumtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `i_username` (`username`),
  KEY `i_username_created_at` (`username`,`created_at`),
  KEY `i_created_at` (`created_at`),
  KEY `i_groupname` (`groupname`),
  KEY `i_groupname_username` (`groupname`,`username`) USING BTREE,
  KEY `i_id_username` (`id`,`username`),
  KEY `i_id_created_at` (`id`,`created_at`),
  FULLTEXT KEY `message` (`message`)
) ENGINE=InnoDB AUTO_INCREMENT=2162656 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.rostergroups
CREATE TABLE IF NOT EXISTS `rostergroups` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `jid` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `grp` text COLLATE utf8mb4_unicode_ci NOT NULL,
  KEY `pk_rosterg_user_jid` (`username`(75),`jid`(75))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.rosterusers
CREATE TABLE IF NOT EXISTS `rosterusers` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `jid` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nick` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `subscription` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ask` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `askmessage` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `server` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `subscribe` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `i_rosteru_user_jid` (`username`(75),`jid`(75)),
  KEY `i_rosteru_username` (`username`),
  KEY `i_rosteru_jid` (`jid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.roster_version
CREATE TABLE IF NOT EXISTS `roster_version` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `version` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.route
CREATE TABLE IF NOT EXISTS `route` (
  `domain` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `server_host` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `pid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `local_hint` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_route` (`domain`(75),`server_host`(75),`node`(75),`pid`(75)),
  KEY `i_route_domain` (`domain`(75))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.sm
CREATE TABLE IF NOT EXISTS `sm` (
  `usec` bigint(20) NOT NULL,
  `pid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `node` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `resource` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `priority` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `info` text COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `i_sid` (`usec`,`pid`(75)),
  KEY `i_node` (`node`(75)),
  KEY `i_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.spool
CREATE TABLE IF NOT EXISTS `spool` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `xml` mediumtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `seq` (`seq`),
  KEY `i_despool` (`username`) USING BTREE,
  KEY `i_spool_created_at` (`created_at`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1423 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.sr_group
CREATE TABLE IF NOT EXISTS `sr_group` (
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `opts` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.sr_user
CREATE TABLE IF NOT EXISTS `sr_user` (
  `jid` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `grp` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_sr_user_jid_group` (`jid`(75),`grp`(75)),
  KEY `i_sr_user_jid` (`jid`),
  KEY `i_sr_user_grp` (`grp`)
) ENGINE=InnoDB AUTO_INCREMENT=2912 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.users
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `serverkey` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `salt` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `iterationcount` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.vcard
CREATE TABLE IF NOT EXISTS `vcard` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vcard` mediumtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица ejabberd.vcard_search
CREATE TABLE IF NOT EXISTS `vcard_search` (
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lusername` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fn` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lfn` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `family` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lfamily` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `given` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lgiven` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `middle` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lmiddle` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nickname` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lnickname` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bday` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lbday` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ctry` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lctry` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `locality` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `llocality` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lemail` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `orgname` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lorgname` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `orgunit` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `lorgunit` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`lusername`),
  KEY `i_vcard_search_lfn` (`lfn`),
  KEY `i_vcard_search_lfamily` (`lfamily`),
  KEY `i_vcard_search_lgiven` (`lgiven`),
  KEY `i_vcard_search_lmiddle` (`lmiddle`),
  KEY `i_vcard_search_lnickname` (`lnickname`),
  KEY `i_vcard_search_lbday` (`lbday`),
  KEY `i_vcard_search_lctry` (`lctry`),
  KEY `i_vcard_search_llocality` (`llocality`),
  KEY `i_vcard_search_lemail` (`lemail`),
  KEY `i_vcard_search_lorgname` (`lorgname`),
  KEY `i_vcard_search_lorgunit` (`lorgunit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Экспортируемые данные не выделены.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
