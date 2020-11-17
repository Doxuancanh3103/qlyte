-- ######### organization
--  world
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (1, 'FIFA', 'International Federation of Association Football', null, 'ACTIVE');

-- continental
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (10, 'AFC', 'Asian Football Confederation', 1, 'ACTIVE');
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (20, 'UEFA', 'Union of European Football Associations', 1, 'ACTIVE');
 
 
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (100, 'AFF', 'Asian Football Federation', 10, 'ACTIVE');
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (101, 'EAFF', ' East Asian Football Federation', 10, 'ACTIVE');
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (102, 'WAFF', 'West Asian Football Federation', 10, 'ACTIVE');
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (103, 'CAFA', 'Central Asian Football Association', 10, 'ACTIVE');
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (104, 'SAFF', 'South Asian Football Federation', 10, 'ACTIVE');

 
-- A
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (1000, 'VFF', 'Vietnam', 100, 'ACTIVE');
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (1001, 'FAT', 'Thailand', 100, 'ACTIVE');
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (1002, 'KFA', 'Korean', 100, 'ACTIVE');
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (1003, 'JFA', 'Japan', 100, 'ACTIVE');

-- Au
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (2000, 'FA', 'England', 20, 'ACTIVE'); -- England
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (2001, 'DFB ', 'Germany', 20, 'ACTIVE');  -- Germany
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (2002, 'FIGC ', 'Italy', 20, 'ACTIVE'); -- Italy
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (2003, 'FFF ', 'France', 20, 'ACTIVE'); -- France
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (2004, 'RFEF ', 'Spain', 20, 'ACTIVE'); -- Spain
INSERT INTO organizations(id, name, description, parent_id, status) VALUE (2005, 'FPF ', 'Poland', 20, 'ACTIVE'); -- Poland

 
