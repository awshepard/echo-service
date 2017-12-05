CREATE TABLE greeting (
    uuid     VARCHAR(36)  NOT NULL PRIMARY KEY,
    greeting VARCHAR(128) NOT NULL
);

INSERT INTO greeting VALUES ('4de5f984-0be3-4db2-97a0-644048cd84f8', 'Hello!');
INSERT INTO greeting VALUES ('b871bee0-0eca-447a-ae3c-741d475d1a12', 'It is a pleasure to meet you.');
