CREATE TABLE IF NOT EXISTS file_meta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(50) NOT NULL,
    path VARCHAR(1000) NOT NULL,
    is_directory BOOLEAN NOT NULL,
    pinyin VARCHAR(50) NOT NULL,
    pinyin_first VARCHAR(50) NOT NULL,
    size BIGINT NOT NULL,
    last_modified TIMESTAMP NOT NULL
);
SELECT * FROM file_meta;