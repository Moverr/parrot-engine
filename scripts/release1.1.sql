-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               PostgreSQL 11.5, compiled by Visual C++ build 1914, 64-bit
-- Server OS:                    
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table default.account
DROP TABLE IF EXISTS "account";
CREATE TABLE IF NOT EXISTS "account" (
	"id" BIGINT NOT NULL COMMENT E'',
	"owner" BIGINT NOT NULL COMMENT E'',
	"name" CHARACTER VARYING NOT NULL COMMENT E'',
	"created_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"updated_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"author_id" BIGINT NOT NULL COMMENT E'',
	"updated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	"external_id" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.departments
DROP TABLE IF EXISTS "departments";
CREATE TABLE IF NOT EXISTS "departments" (
	"id" BIGINT NOT NULL COMMENT E'',
	"name" CHARACTER VARYING NOT NULL COMMENT E'',
	"code" CHARACTER VARYING NOT NULL COMMENT E'',
	"office_id" BIGINT NOT NULL COMMENT E'',
	"parent_department" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.employees
DROP TABLE IF EXISTS "employees";
CREATE TABLE IF NOT EXISTS "employees" (
	"id" BIGINT NOT NULL COMMENT E'',
	"names" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"gender" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"created_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"updated_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"author_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	"updated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.employee_station
DROP TABLE IF EXISTS "employee_station";
CREATE TABLE IF NOT EXISTS "employee_station" (
	"id" BIGINT NOT NULL COMMENT E'',
	"employee_id" BIGINT NOT NULL COMMENT E'',
	"office_id" BIGINT NOT NULL COMMENT E'',
	"station_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.kiosks
DROP TABLE IF EXISTS "kiosks";
CREATE TABLE IF NOT EXISTS "kiosks" (
	"id" BIGINT NOT NULL COMMENT E'',
	"reference" CHARACTER VARYING NOT NULL COMMENT E'',
	"details" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"device_token" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"created_on" TIMESTAMP WITHOUT TIME ZONE NOT NULL COMMENT E'',
	"updated_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"author_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	"udated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	"station_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.offices
DROP TABLE IF EXISTS "offices";
CREATE TABLE IF NOT EXISTS "offices" (
	"id" BIGINT NOT NULL COMMENT E'',
	"name" CHARACTER VARYING NOT NULL COMMENT E'',
	"dateopened" DATE NOT NULL COMMENT E'',
	"parent_office" BIGINT NULL DEFAULT NULL COMMENT E'',
	"created_on" TIMESTAMP WITHOUT TIME ZONE NOT NULL COMMENT E'',
	"updated_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"author_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	"updated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.office_station
DROP TABLE IF EXISTS "office_station";
CREATE TABLE IF NOT EXISTS "office_station" (
	"id" BIGINT NOT NULL COMMENT E'',
	"office_id" BIGINT NOT NULL COMMENT E'',
	"station_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.permissions
DROP TABLE IF EXISTS "permissions";
CREATE TABLE IF NOT EXISTS "permissions" (
	"id" BIGINT NOT NULL COMMENT E'',
	"code" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"name" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"grouping" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.permission_scope
DROP TABLE IF EXISTS "permission_scope";
CREATE TABLE IF NOT EXISTS "permission_scope" (
	"id" BIGINT NOT NULL COMMENT E'',
	"key" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"value" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.profile
DROP TABLE IF EXISTS "profile";
CREATE TABLE IF NOT EXISTS "profile" (
	"id" BIGINT NOT NULL COMMENT E'',
	"user_id" BIGINT NOT NULL COMMENT E'',
	"firstname" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"lastname" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"author_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	"created_on" TIME WITHOUT TIME ZONE NOT NULL COMMENT E'',
	"updated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	"dateupdated" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.role
DROP TABLE IF EXISTS "role";
CREATE TABLE IF NOT EXISTS "role" (
	"id" BIGINT NOT NULL COMMENT E'',
	"code" CHARACTER VARYING(50) NOT NULL COMMENT E'',
	"name" CHARACTER VARYING(200) NOT NULL COMMENT E'',
	"is_system" BOOLEAN NOT NULL COMMENT E'',
	"created_on" TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT E'',
	"updated_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"author_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	"updated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.role_permission
DROP TABLE IF EXISTS "role_permission";
CREATE TABLE IF NOT EXISTS "role_permission" (
	"id" BIGINT NOT NULL COMMENT E'',
	"role_id" BIGINT NOT NULL COMMENT E'',
	"permission_id" BIGINT NOT NULL COMMENT E'',
	"created_on" TIMESTAMP WITHOUT TIME ZONE NOT NULL COMMENT E'',
	"updated_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"author_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	"updated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	"scope_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.stations
DROP TABLE IF EXISTS "stations";
CREATE TABLE IF NOT EXISTS "stations" (
	"id" BIGINT NOT NULL COMMENT E'',
	"account_id" BIGINT NOT NULL COMMENT E'',
	"name" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	"code" CHARACTER VARYING NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.users
DROP TABLE IF EXISTS "users";
CREATE TABLE IF NOT EXISTS "users" (
	"id" BIGINT NOT NULL COMMENT E'',
	"username" CHARACTER VARYING(255) NOT NULL COMMENT E'',
	"password" CHARACTER VARYING(255) NOT NULL COMMENT E'',
	"author_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	"created_on" TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT E'',
	"updated_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"updated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table default.user_role
DROP TABLE IF EXISTS "user_role";
CREATE TABLE IF NOT EXISTS "user_role" (
	"id" BIGINT NOT NULL COMMENT E'',
	"user_id" BIGINT NOT NULL COMMENT E'',
	"role_id" BIGINT NOT NULL COMMENT E'',
	"status" CHARACTER VARYING(50) NOT NULL COMMENT E'',
	"author_id" BIGINT NULL DEFAULT NULL COMMENT E'',
	"created_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"updated_on" TIMESTAMP WITHOUT TIME ZONE NULL DEFAULT NULL COMMENT E'',
	"updated_by" BIGINT NULL DEFAULT NULL COMMENT E'',
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
