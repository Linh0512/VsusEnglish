USE vsusenglish;

SELECT 
    c.TABLE_NAME AS `Table`,
    c.ORDINAL_POSITION AS `Column_Order`,
    c.COLUMN_NAME AS `Column`,
    c.COLUMN_TYPE AS `Data_Type`,
    c.IS_NULLABLE AS `Nullable`,
    c.COLUMN_DEFAULT AS `Default_Value`,
    c.EXTRA AS `Extra`,
    
    -- Primary Key
    CASE WHEN pk.COLUMN_NAME IS NOT NULL THEN 'YES' ELSE '' END AS `Primary_Key`,
    
    -- Unique Constraint
    CASE WHEN uk.CONSTRAINT_NAME IS NOT NULL THEN 'YES' ELSE '' END AS `Unique`,
    
    -- Foreign Key
    CASE WHEN fk.CONSTRAINT_NAME IS NOT NULL THEN 'YES' ELSE '' END AS `Foreign_Key`,
    fk.REFERENCED_TABLE_NAME AS `References_Table`,
    fk.REFERENCED_COLUMN_NAME AS `References_Column`

FROM information_schema.COLUMNS c

-- Primary Key
LEFT JOIN (
    SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME
    FROM information_schema.KEY_COLUMN_USAGE
    WHERE CONSTRAINT_NAME = 'PRIMARY'
) pk ON c.TABLE_SCHEMA = pk.TABLE_SCHEMA 
    AND c.TABLE_NAME = pk.TABLE_NAME 
    AND c.COLUMN_NAME = pk.COLUMN_NAME

-- Unique Constraints (không phải PK)
LEFT JOIN (
    SELECT DISTINCT kcu.TABLE_SCHEMA, kcu.TABLE_NAME, kcu.COLUMN_NAME, tc.CONSTRAINT_NAME
    FROM information_schema.KEY_COLUMN_USAGE kcu
    JOIN information_schema.TABLE_CONSTRAINTS tc 
        ON kcu.CONSTRAINT_SCHEMA = tc.CONSTRAINT_SCHEMA 
        AND kcu.TABLE_NAME = tc.TABLE_NAME 
        AND kcu.CONSTRAINT_NAME = tc.CONSTRAINT_NAME
    WHERE tc.CONSTRAINT_TYPE = 'UNIQUE'
) uk ON c.TABLE_SCHEMA = uk.TABLE_SCHEMA 
    AND c.TABLE_NAME = uk.TABLE_NAME 
    AND c.COLUMN_NAME = uk.COLUMN_NAME

-- Foreign Keys
LEFT JOIN information_schema.KEY_COLUMN_USAGE fk 
    ON c.TABLE_SCHEMA = fk.TABLE_SCHEMA 
    AND c.TABLE_NAME = fk.TABLE_NAME 
    AND c.COLUMN_NAME = fk.COLUMN_NAME 
    AND fk.REFERENCED_TABLE_NAME IS NOT NULL

WHERE c.TABLE_SCHEMA = 'vsusenglish'
  AND c.TABLE_NAME NOT IN ('information_schema', 'performance_schema', 'mysql', 'sys')

ORDER BY c.TABLE_NAME, c.ORDINAL_POSITION;