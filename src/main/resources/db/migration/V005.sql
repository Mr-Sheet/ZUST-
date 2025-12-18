-- 修改password字段长度为128
ALTER TABLE tab_user MODIFY COLUMN password VARCHAR(128) NOT NULL;

-- 新增salt字段（长度8）
ALTER TABLE tab_user ADD COLUMN salt VARCHAR(8) COMMENT '加密盐值';