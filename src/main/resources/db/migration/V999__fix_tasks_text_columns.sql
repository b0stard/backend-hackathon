ALTER TABLE tasks
ALTER COLUMN title TYPE TEXT USING convert_from(title, 'UTF8');

ALTER TABLE tasks
ALTER COLUMN description TYPE TEXT USING convert_from(description, 'UTF8');