SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mc_app`
-- ----------------------------
DROP TABLE IF EXISTS `mc_app`;
CREATE TABLE `mc_app` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `MerchantId` int(11) NOT NULL COMMENT '商户id/渠道Id，mc_merchant.merchantId',
  `AppId` varchar(64) NOT NULL COMMENT '商户应用id，本意支持单商户多应用',
  `AppName` varchar(64) NOT NULL COMMENT '应用名称',
  `AppKey` text COMMENT '应用Key',
  `OrderPrefix` varchar(6) NOT NULL COMMENT '订单前缀',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idx_appid` (`AppId`),
  UNIQUE KEY `idx_orderprefix` (`OrderPrefix`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `mc_merchant`
-- ----------------------------
DROP TABLE IF EXISTS `mc_merchant`;
CREATE TABLE `mc_merchant` (
  `MerchantId` int(11) NOT NULL COMMENT '商户Id',
  `MerchantName` varchar(64) NOT NULL COMMENT '商户名称、渠道名称',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`MerchantId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `mc_notify`
-- ----------------------------
DROP TABLE IF EXISTS `mc_notify`;
CREATE TABLE `mc_notify` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `OrderId` bigint(20) NOT NULL COMMENT '订单id,mc_order.id',
  `NotifyNum` int(11) NOT NULL COMMENT '回调次数',
  `PlanNotifyTime` datetime DEFAULT NULL COMMENT '计划/预期回调时间',
  `NextNotifyTime` datetime DEFAULT NULL COMMENT '下一次回调时间',
  `Status` int(11) NOT NULL COMMENT '回调启用状态，1:需要回调 2：所有回调完成',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `NotifyCode` varchar(64) DEFAULT NULL COMMENT '回调响应状态',
  `NotifyMsg` varchar(200) DEFAULT NULL COMMENT '回调响应消息',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `mc_order`
-- ----------------------------
DROP TABLE IF EXISTS `mc_order`;
CREATE TABLE `mc_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `MerchantOrderNo` varchar(32) NOT NULL COMMENT '商户订单编号',
  `OrderNo` varchar(64) NOT NULL COMMENT '我方订单号 基本规则App订单前缀+商户订单号',
  `MerchantId` int(11) NOT NULL COMMENT '商户Id',
  `AppId` varchar(32) NOT NULL COMMENT '应用Id mc_app.appid',
  `PayChannel` varchar(10) NOT NULL COMMENT '支付渠道 alipay:支付宝支付,wxpay:微信支付,jdpay:京东支付',
  `PayMethod` tinyint(2) NOT NULL COMMENT '支付渠道',
  `PayWay` varchar(10) NOT NULL COMMENT '支付方式 app,h5',
  `OrderName` varchar(64) DEFAULT NULL COMMENT '订单名称或描述',
  `OrderMoney` int(10) DEFAULT '0' COMMENT '订单金额 单位分',
  `ClientIp` varchar(20) DEFAULT NULL COMMENT '客户端ip',
  `ExpireSecond` int(11) DEFAULT NULL COMMENT '订单过期时间',
  `PayStatus` int(4) DEFAULT '300' COMMENT '支付结果状态,200:交易成功,300:交易未支付,400:交易失败,500:交易关闭,600:交易结束',
  `PayMsg` varchar(64) DEFAULT NULL COMMENT '支付返回消息',
  `PayTime` datetime DEFAULT NULL COMMENT '支付渠道支付完成时间',
  `TradeNo` varchar(64) DEFAULT NULL COMMENT '支付渠道交易流水号',
  `TradeStatus` varchar(20) DEFAULT NULL COMMENT '支付渠道返回码',
  `TradeMsg` varchar(64) DEFAULT NULL COMMENT '支付渠道返回信息',
  `OrderDesc` varchar(200) DEFAULT NULL COMMENT '订单描述',
  `NotifyUrl` varchar(400) DEFAULT NULL COMMENT '异步回调地址',
  `ReturnUrl` varchar(400) DEFAULT NULL COMMENT '页面返回地址',
  `RequestTime` datetime DEFAULT NULL COMMENT '订单请求发起时间',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `RefundStatus` tinyint(1) DEFAULT '1' COMMENT '退款状态 1:正常 2:已退款',
  `RefundTime` datetime DEFAULT NULL COMMENT '退款时间',
  `TradeOrderUrl` varchar(200) DEFAULT NULL COMMENT '支付渠道OrderUrl',
  `TradePrepay` text COMMENT '支付渠道的Prepay',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_orderno` (`MerchantOrderNo`,`AppId`),
  UNIQUE KEY `OrderNo` (`OrderNo`),
  KEY `idx_tradeno` (`AppId`,`TradeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `mc_refund`
-- ----------------------------
DROP TABLE IF EXISTS `mc_refund`;
CREATE TABLE `mc_refund` (
  `Id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `OrderId` bigint(12) NOT NULL COMMENT '订单id,mc_order.id',
  `RefundMoney` decimal(10,0) DEFAULT NULL COMMENT '退款金额，单元分',
  `AccountName` varchar(64) NOT NULL COMMENT '收款账户',
  `AccountBank` varchar(64) DEFAULT NULL COMMENT '收款银行',
  `AccountNumber` varchar(32) DEFAULT NULL COMMENT '收款账号',
  `RefundWay` tinyint(4) DEFAULT NULL COMMENT '退款渠道 1:支付宝 2：微信  3：银行转账',
  `Operator` varchar(32) NOT NULL COMMENT '操作人',
  `RefundDesc` varchar(255) DEFAULT NULL COMMENT '退款说明',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `mc_refundlog`
-- ----------------------------
DROP TABLE IF EXISTS `mc_refundlog`;
CREATE TABLE `mc_refundlog` (
  `Id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `RefundId` bigint(12) NOT NULL COMMENT '退款Id,mc_refund.id',
  `RefundMoney` decimal(10,0) DEFAULT NULL COMMENT '退款金额，单元分',
  `AccountName` varchar(64) NOT NULL COMMENT '收款账户',
  `AccountBank` varchar(64) DEFAULT NULL COMMENT '收款银行',
  `AccountNumber` varchar(32) DEFAULT NULL COMMENT '收款账号',
  `RefundWay` tinyint(4) DEFAULT NULL COMMENT '退款渠道 1:支付宝 2：微信  3：银行转账',
  `Operator` varchar(32) NOT NULL COMMENT '操作人',
  `RefundDesc` varchar(255) DEFAULT NULL COMMENT '退款说明',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `CRON_EXPRESSION` varchar(200) COLLATE utf8_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `ENTRY_ID` varchar(95) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8_bin NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_simprop_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `STR_PROP_1` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_2` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_3` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8_bin NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
