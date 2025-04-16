DROP PROCEDURE IF EXISTS fetch_employees;

DELIMITER $$

CREATE PROCEDURE fetch_employees(
IN employee_name varchar(255),
IN department varchar(255),
IN page_number int,
IN page_size int
)
BEGIN

    DECLARE condition_query TEXT DEFAULT '';
    DECLARE conditions VARCHAR(10) DEFAULT ' WHERE ';

    IF employee_name is not null AND employee_name <> '' THEN
        SET condition_query=CONCAT(condition_query,conditions,' employee_name = ',QUOTE(employee_name));
        SET conditions= ' AND ';
    END IF;

    IF department is not null AND department <> '' THEN
         SET condition_query=CONCAT(condition_query,conditions,' department = ',QUOTE(department));
         SET conditions= ' AND ';
    END IF;

    SET @count_query=CONCAT('SELECT count(*) as total_records from employee ',condition_query);

    PREPARE stmt FROM @count_query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    SET @var_page_size=page_size;
    SET @offset=page_size*page_number;

    SET @query=CONCAT('SELECT * from employee ',condition_query,' LIMIT ? OFFSET ?');

    PREPARE stmt FROM @query;
    EXECUTE stmt using @var_page_size, @offset;
    DEALLOCATE PREPARE stmt;

END $$

DELIMITER ;