CREATE TABLE request(
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
  request_id VARCHAR(32) NOT NULL COMMENT '请求id,自身生成',
  payer_request_id VARCHAR(32) COMMENT '支付方响应的id(仅作记录)',
  caller_id BIGINT NOT NULL COMMENT '调用者id',
  relate_id VARCHAR(32) COMMENT '关联id,调用者传入',
  payer_code CHAR(4) NOT NULL COMMENT '支付方code',
  pay_type TINYINT NOT NULL COMMENT '支付类型 0:wap支付; 1:pc快捷; 2:pc网银; 3:代付',
  request_body VARCHAR(1024)  COMMENT '请求信息记录',
  status TINYINT NOT NULL COMMENT '状态 0:等待中; 1:待验证; 2:成功; 3:失败;',
  sync_response VARCHAR(1024) COMMENT '同步响应',
  async_response VARCHAR(1024) COMMENT '异步响应',
  response_message VARCHAR(64) COMMENT '响应消息(异常消息)',

  request_time DATETIME COMMENT '真正请求时间',
  async_response_time  DATETIME COMMENT '异步响应时间',

  delete_flag TINYINT DEFAULT 0 COMMENT '删除标记 0:未删除; 1:删除',
  update_time TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_time TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
  INDEX (request_id),
  INDEX (caller_id),
  INDEX (relate_id),
  INDEX (caller_id,relate_id)
)AUTO_INCREMENT = 1000, COMMENT = '调用表';


CREATE TABLE caller(
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
  name VARCHAR(32) NOT NULL COMMENT '姓名',
  token VARCHAR(128) NOT NULL COMMENT '令牌',
  pre VARCHAR(16) NOT NULL COMMENT '唯一前缀,用于生成每个caller唯一的userId',

  delete_flag TINYINT DEFAULT 0 COMMENT '删除标记 0:未删除; 1:删除',
  update_time TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_time TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
  INDEX (token)
)AUTO_INCREMENT = 1000, COMMENT = '调用者';




CREATE TABLE payer(
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
  code CHAR(4) NOT NULL COMMENT 'code',
  name VARCHAR(32) NOT NULL COMMENT '名称',
  alias VARCHAR(16) NOT NULL COMMENT '别名',
  sort TINYINT NOT NULL COMMENT '排序，从0开始',
  status TINYINT COMMENT '状态 0:禁用; 1:启用',


  delete_flag TINYINT DEFAULT 0 COMMENT '删除标记 0:未删除; 1:删除',
  update_time TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_time TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
  INDEX (code),
  INDEX (alias)
)AUTO_INCREMENT = 1000, COMMENT = '支付方';

INSERT INTO payer(code, name, alias,sort, status)
VALUES
  ('0001','富有','fuyou',0,1),
  ('0002','连连','ll',1,1);

CREATE TABLE bank(
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
  code CHAR(4) NOT NULL COMMENT 'code',
  name VARCHAR(32) NOT NULL COMMENT '银行名',

  delete_flag TINYINT DEFAULT 0 COMMENT '删除标记 0:未删除; 1:删除',
  update_time TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_time TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
  INDEX (code)
)AUTO_INCREMENT = 1000, COMMENT = '银行';

INSERT INTO bank(code, name)
VALUES
  ('0001','广发银行'),
  ('0002','交通银行'),
  ('0003','平安银行'),
  ('0004','上海浦发银行'),
  ('0005','兴业银行'),
  ('0006','招商银行'),
  ('0007','中国工商银行'),
  ('0008','中国光大银行'),
  ('0009','中国建设银行'),
  ('0010','中国民生银行'),
  ('0011','中国农业银行'),
  ('0012','中国银行'),
  ('0013','中国邮政'),
  ('0014','中信银行'),
  ('0015','华夏银行')
;


CREATE TABLE payer_bank(
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
  payer_type TINYINT DEFAULT 0 COMMENT '支付方类型(用于同一支付方不同接口需要不同code)',
  payer_code CHAR(4) NOT NULL COMMENT '支付方code',
  bank_code CHAR(4) NOT NULL COMMENT '银行表的code',
  payer_bank_code VARCHAR(16) NOT NULL COMMENT '该支付方中银行code',
  name VARCHAR(32) COMMENT '银行名(冗余字段)',

  delete_flag TINYINT DEFAULT 0 COMMENT '删除标记 0:未删除; 1:删除',
  update_time TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_time TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
  INDEX (payer_code,payer_type)
)AUTO_INCREMENT = 1000, COMMENT = '支付方银行关系表';

INSERT INTO payer_bank(payer_code, bank_code, payer_bank_code)
VALUES
  #   富有
  ('0001','0002','0301'),# 交通
  ('0001','0007','0102'),# 工商
  ('0001','0011','0103'),# 农业
  ('0001','0009','0105'),# 建设
  ('0001','0012','0104'),# 中国银行
  ('0001','0005','0309'),# 兴业
  ('0001','0008','0303'),# 光大
  ('0001','0014','0302'),# 中信
  ('0001','0006','0308'),# 招商
  ('0001','0013','0403'),# 邮政
  ('0001','0003','0307'),# 平安
  ('0001','0010','0305'),# 民生
  ('0001','0001','0306'),# 广发
  ('0001','0004','0310'),# 浦发
  ('0001','0015','0304'),# 华夏
  #   连连
  ('0002','0002','03010000'),# 交通
  ('0002','0007','01020000'),# 工商
  ('0002','0011','01030000'),# 农业
  ('0002','0009','01050000'),# 建设
  ('0002','0012','01040000'),# 中国银行
  ('0002','0005','03090000'),# 兴业
  ('0002','0008','03030000'),# 光大
  ('0002','0014','03020000'),# 中信
  ('0002','0006','03080000'),# 招商
  ('0002','0013','01000000'),# 邮政
  ('0002','0003','03070000'),# 平安
  ('0002','0010','03050000'),# 民生
  ('0002','0001','03060000'),# 广发
  ('0002','0004','03100000'),# 浦发
  ('0002','0015','03040000') # 华夏
;



# 富有 网银支付接口，需要不同的code,所以将payer_type设为1（默认为0）
INSERT INTO payer_bank(payer_code, bank_code, payer_bank_code,payer_type)
VALUES
  ('0001','0002','0803010000',1),
  ('0001','0007','0801020000',1),
  ('0001','0011','0801030000',1),
  ('0001','0009','0801050000',1),
  ('0001','0012','0801040000',1),
  ('0001','0005','0803090000',1),
  ('0001','0008','0803030000',1),
  ('0001','0014','0803020000',1),
  ('0001','0006','0803080000',1),
  ('0001','0013','0801000000',1),
  ('0001','0010','0803050000',1),
  ('0001','0001','0803060000',1),
  ('0001','0004','0803100000',1),
  ('0001','0015','0803040000',1);

# 注入name字段
update payer_bank pb,bank b   set pb.name = b.name where pb.bank_code = b.code;


CREATE TABLE bank_quota(
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
  payer_code CHAR(4) NOT NULL COMMENT '支付方code',
  bank_code CHAR(4) NOT NULL COMMENT '银行表的code',
  pay_type VARCHAR(16) NOT NULL COMMENT '支付类型(可包含多个，用","分隔) 0:wap支付; 1:pc快捷; 2:pc网银（没有限额）; 3:代付',
  name VARCHAR(32) COMMENT '银行名(冗余字段)',

  single_quota INT  COMMENT '单笔限额,0表示未知,-1表示无限',
  day_quota INT COMMENT '日限额,0表示未知,-1表示无限',
  month_quota INT  COMMENT '月限额,0表示未知,-1表示无限',

  delete_flag TINYINT DEFAULT 0 COMMENT '删除标记 0:未删除; 1:删除',
  update_time TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_time TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
  INDEX (payer_code,bank_code)
)AUTO_INCREMENT = 1000, COMMENT = '银行限额表';

INSERT INTO bank_quota(payer_code,bank_code,pay_type,single_quota,day_quota,month_quota)
VALUES
  #   富有 认证支付
  ('0001','0002','0,1',50000,50000,1000000),# 交通
  ('0001','0007','0,1',50000,50000,1000000),# 工商
  ('0001','0011','0,1',0,0,0),# 农业
  ('0001','0009','0,1',50000,100000,1000000),# 建设
  ('0001','0012','0,1',50000,100000,1000000),# 中国银行
  ('0001','0005','0,1',50000,50000,1000000),# 兴业
  ('0001','0008','0,1',50000,50000,1000000),# 光大
  ('0001','0014','0,1',1000,1000,2000),# 中信
  ('0001','0006','0,1',50000,50000,1000000),# 招商
  ('0001','0013','0,1',5000,5000,1000000),# 邮政
  ('0001','0003','0,1',50000,50000,1000000),# 平安
  ('0001','0010','0,1',0,0,0),# 民生
  ('0001','0001','0,1',100000,200000,1000000),# 广发
  ('0001','0004','0,1',100000,200000,1000000),# 浦发
  ('0001','0015','0,1',1000,5000,1000000), # 华夏
  # 富有 代付
  ('0001','0002','3',50000,100000,30*100000),# 交通
  ('0001','0007','3',50000,50000,30*50000),# 工商
  ('0001','0011','3',50000,50000,30*50000),# 农业
  ('0001','0009','3',50000,100000,30*100000),# 建设
  ('0001','0012','3',500000,10000000,10000000),# 中国银行
  ('0001','0005','3',10000,50000,30*50000),# 兴业
  ('0001','0008','3',500000,10000000,10000000),# 光大
  ('0001','0014','3',500000,10000000,10000000),# 中信
  ('0001','0006','3',1000,50000,30*50000),# 招商
  ('0001','0013','3',500000,10000000,10000000),# 邮政
  ('0001','0003','3',500000,10000000,10000000),# 平安
  ('0001','0010','3',500000,10000000,10000000),# 民生
  ('0001','0001','3',500000,10000000,10000000),# 广发
  ('0001','0004','3',49999,50000,30*50000),# 浦发
  ('0001','0015','3',500000,10000000,10000000), # 华夏
  #   连连 新认证支付(wap支付)
  ('0002','0002','0',200000,200000,6000000),# 交通
  ('0002','0007','0',50000,50000,1500000),# 工商
  ('0002','0011','0',5000,5000,150000),# 农业
  ('0002','0009','0',200000,200000,6000000),# 建设
  ('0002','0012','0',50000,200000,6000000),# 中国银行
  ('0002','0005','0',50000,50000,1500000),# 兴业
  ('0002','0008','0',30000,30000,150000),# 光大
  ('0002','0014','0',5000,50000,150000),# 中信
  ('0002','0006','0',20000,20000,600000),# 招商
  ('0002','0013','0',5000,5000,150000),# 邮政
  ('0002','0003','0',50000,50000,1500000),# 平安
  ('0002','0010','0',50000,50000,1500000),# 民生
  ('0002','0001','0',500000,500000,15000000),# 广发
  ('0002','0004','0',0,0,0),# 浦发
  ('0002','0015','0',10000,10000,300000), # 华夏
  #   连连 pc快捷
  ('0002','0002','1',10000,10000,50000),# 交通
  ('0002','0007','1',5000,50000,1500000),# 工商
  ('0002','0011','1',5000,5000,-1),# 农业
  ('0002','0009','1',10000,10000,50000),# 建设
  ('0002','0012','1',10000,10000,-1),# 中国银行
  ('0002','0005','1',5000,5000,-1),# 兴业
  ('0002','0008','1',30000,30000,-1),# 光大
  ('0002','0014','1',5000,50000,150000),# 中信
  ('0002','0006','1',20000,20000,600000),# 招商
  ('0002','0013','1',5000,5000,-1),# 邮政
  ('0002','0003','1',50000,50000,-1),# 平安
  ('0002','0010','1',50000,50000,1500000),# 民生
  ('0002','0001','1',10000,10000,-1),# 广发
  ('0002','0004','1',20000,20000,-1),# 浦发
  ('0002','0015','1',10000,10000,-1), # 华夏
  #   连连 代付
  ('0002','0002','3',0,0,0),# 交通
  ('0002','0007','3',0,0,0),# 工商
  ('0002','0011','3',0,0,0),# 农业
  ('0002','0009','3',0,0,0),# 建设
  ('0002','0012','3',0,0,0),# 中国银行
  ('0002','0005','3',0,0,0),# 兴业
  ('0002','0008','3',0,0,0),# 光大
  ('0002','0014','3',0,0,0),# 中信
  ('0002','0006','3',0,0,0),# 招商
  ('0002','0013','3',0,0,0),# 邮政
  ('0002','0003','3',0,0,0),# 平安
  ('0002','0010','3',0,0,0),# 民生
  ('0002','0001','3',0,0,0),# 广发
  ('0002','0004','3',0,0,0),# 浦发
  ('0002','0015','3',0,0,0) # 华夏
;

# 注入name字段
update bank_quota bq,bank b   set bq.name = b.name where bq.bank_code = b.code;