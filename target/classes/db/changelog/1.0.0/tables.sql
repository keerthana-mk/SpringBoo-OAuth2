CREATE TABLE oauth_client_details (
  client_id varchar(255) NOT NULL,
  resource_ids varchar(255) DEFAULT NULL,
  client_secret varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  authorized_grant_types varchar(255) DEFAULT NULL,
  web_server_redirect_uri varchar(255) DEFAULT NULL,
  authorities varchar(255) DEFAULT NULL,
  access_token_validity int(11) DEFAULT NULL,
  refresh_token_validity int(11) DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  autoapprove varchar(255) DEFAULT NULL,
  PRIMARY KEY (client_id)
);

CREATE TABLE role (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(60) NOT NULL,
  created_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE KEY name_UNIQUE_2 (name)
);


CREATE TABLE permission (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(60) NOT NULL,
  created_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE KEY name_UNIQUE_1 (name)
);

CREATE TABLE permission_role (
  permission_id bigint(20) unsigned NOT NULL,
  role_id bigint(20) unsigned NOT NULL,
  created_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (permission_id,role_id),
  FOREIGN KEY(permission_id) REFERENCES permission ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY(role_id) REFERENCES role ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE user (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  username varchar(24) NOT NULL,
  password varchar(200) NOT NULL,
  email varchar(255) NOT NULL,
  enabled bit(1) NOT NULL,
  account_expired bit(1) NOT NULL,
  credentials_expired bit(1) NOT NULL,
  account_locked bit(1) NOT NULL,
  created_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE KEY (username),
  UNIQUE KEY (email)
);

CREATE TABLE role_user (
  role_id bigint(20) unsigned NOT NULL,
  user_id bigint(20) unsigned NOT NULL,
  created_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (role_id,user_id),
  FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);


