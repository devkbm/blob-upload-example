CREATE TABLE TEST
(
    id integer NOT NULL PRIMARY KEY auto_increment,
    file_name   VARCHAR(500),
    file_type   VARCHAR(500),
    blob_data  BLOB    
)