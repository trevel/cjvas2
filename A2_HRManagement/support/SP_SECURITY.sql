--*****************************************************************************************
-- Student Names: Laurie Shields (034448142)
--                Mark Lindan (063336143)
--  CJV805 - SP_SECURITY.sql
--*****************************************************************************************
-- Drop the P_SECURITY package if it already exists
DROP PACKAGE P_SECURITY;

-- Create the package
CREATE OR REPLACE PACKAGE P_SECURITY AS
	TYPE cur_EmpInfo IS REF CURSOR;
 	FUNCTION F_SECURITY(
 		P_SECID IN SECURITY.SEC_ID%TYPE,
 		P_SECPASSWORD IN SECURITY.SEC_PASSWORD%TYPE)
 		RETURN NUMBER;
 	PROCEDURE P_EMP_INFO (
 		P_EMPLOYEEID IN EMPLOYEES.EMPLOYEE_ID%TYPE,
 		p_info OUT cur_EmpInfo);
END P_SECURITY;
/

-- Create the package body

CREATE OR REPLACE PACKAGE BODY P_SECURITY AS
 	FUNCTION F_SECURITY(
 		P_SECID IN SECURITY.SEC_ID%TYPE,
 		P_SECPASSWORD IN SECURITY.SEC_PASSWORD%TYPE)
 	RETURN NUMBER IS
 		v_emp_id SECURITY.EMPLOYEE_ID%TYPE := 0;
 	BEGIN
	 	SELECT employee_id INTO v_emp_id
	 	FROM Security
	 	WHERE SEC_ID = P_SECID 
	 	AND SEC_PASSWORD = P_SECPASSWORD
	 	AND SEC_STATUS = 'A';
	 	return v_emp_id;
	EXCEPTION
		WHEN OTHERS then
			-- select returned no result that matched
			return 0;
	END F_SECURITY;
	PROCEDURE P_EMP_INFO (
 		P_EMPLOYEEID IN EMPLOYEES.EMPLOYEE_ID%TYPE,
 		p_info OUT cur_EmpInfo) IS
 	BEGIN
	 	OPEN p_info FOR 
	 	SELECT * FROM Employees WHERE EMPLOYEE_ID = P_EMPLOYEEID; 
	END P_EMP_INFO;
END P_SECURITY;
/
