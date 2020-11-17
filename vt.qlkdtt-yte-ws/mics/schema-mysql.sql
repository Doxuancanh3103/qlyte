CREATE TABLE organizations(
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
   
  logo VARCHAR(500),
  description VARCHAR(2000),
  status VARCHAR(50) NOT NULL,

  parent_id BIGINT,

  created_by VARCHAR(255),
  created_time TIMESTAMP NULL ,
  last_modified_by VARCHAR(255),
  last_modified_time TIMESTAMP NULL ,

  CONSTRAINT pk_organizations PRIMARY KEY (id),
  CONSTRAINT fk_organizations_1 FOREIGN KEY (parent_id) REFERENCES organizations(id)

);