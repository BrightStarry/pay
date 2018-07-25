CREATE TABLE test(
  id BIGINT AUTO_INCREMENT PRIMARY KEY ,
  username VARCHAR(32) COMMENT '用户',
  pwd VARCHAR(32) COMMENT '密码',

  delete_flag TINYINT DEFAULT 0 COMMENT '删除标记 0:未删除; 1:删除',
  update_time TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_time TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
  INDEX (username)

)AUTO_INCREMENT = 1000, COMMENT = '测试';