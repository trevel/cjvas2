--*****************************************************************************************
-- Student Names: Laurie Shields (034448142)
--                Mark Lindan (063336143)
--  CJV805 - SECURITY_TABLE.sql
--*****************************************************************************************

-- Drop the existing table if it happens to be there
DROP TABLE Security;

-- Create the new Security table
CREATE TABLE Security (
   employee_id	NUMBER(6,0) CONSTRAINT sec_emp_id_pk PRIMARY KEY
, sec_id VARCHAR2(20)
, sec_password VARCHAR2(20)
, sec_status CHAR(1) 
, CONSTRAINT sec_sec_status CHECK (sec_status IN ('A','I'))
, CONSTRAINT sec_emp_id_fk FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

-- Insert the 3 entries as given in the assignment
INSERT INTO Security
(employee_id, sec_id, sec_password, sec_status)
VALUES
(201,'servlet','servlet','I');

INSERT INTO Security
(employee_id, sec_id, sec_password, sec_status)
VALUES
(202,'java','java','I');

INSERT INTO Security
(employee_id, sec_id, sec_password, sec_status)
VALUES
(203,'hr','hr','A');


