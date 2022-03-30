DROP TABLE company CASCADE CONSTRAINTS;
DROP TABLE customer CASCADE CONSTRAINTS;
DROP TABLE hotel_belongs CASCADE CONSTRAINTS;
DROP TABLE customer_stay CASCADE CONSTRAINTS;
DROP TABLE level_salary CASCADE CONSTRAINTS;
DROP TABLE department_has CASCADE CONSTRAINTS;
DROP TABLE bill_pays CASCADE CONSTRAINTS;
DROP TABLE membership_applies CASCADE CONSTRAINTS;
DROP TABLE partTimeWorker CASCADE CONSTRAINTS;
DROP TABLE fullTimeWorker CASCADE CONSTRAINTS;
DROP TABLE room_contains CASCADE CONSTRAINTS;
DROP TABLE worker_works CASCADE CONSTRAINTS;
DROP TABLE manager CASCADE CONSTRAINTS;


create table company
(
    name        VARCHAR2(255) not null    primary key,
    marketPrice NUMBER        not null,
    builtTime   DATE          not null,
    address     VARCHAR2(255) not null
);

INSERT INTO company VALUES ('Motel Inc.', '117', TO_DATE('2022-01-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '2075 West Mall');
INSERT INTO company VALUES ('Sunday', '288', TO_DATE('2008-06-16 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '3000 Red Dog Road');
INSERT INTO company VALUES ('CanadaHotels', '344', TO_DATE('2002-12-03 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '122 West Ontario');
INSERT INTO company VALUES ('HotelsPlus', '455', TO_DATE('2018-04-28 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '001 North Ontario');
INSERT INTO company VALUES ('Hotelmania', '566', TO_DATE('2019-02-19 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '1000 North Vancouver');


create table customer
(
    driving_license VARCHAR2(255) not null  primary key,
    name            VARCHAR2(255) not null
);

INSERT INTO customer VALUES ('111-111-111', 'Linda');
INSERT INTO customer VALUES ('111-111-112', 'Nancy');
INSERT INTO customer VALUES ('111-111-113', 'Sim');
INSERT INTO customer VALUES ('111-111-114', 'Aiden');
INSERT INTO customer VALUES ('111-111-115', 'Jerry');
INSERT INTO customer VALUES ('111-111-116', 'Helen');
INSERT INTO customer VALUES ('111-111-117', 'Michael');
INSERT INTO customer VALUES ('111-111-118', 'Jack');
INSERT INTO customer VALUES ('111-111-119', 'David');
INSERT INTO customer VALUES ('111-111-120', 'Lucy');


create table hotel_belongs
(
    id          NUMBER        not null  primary key,
    hotelName   VARCHAR2(255) not null,
    companyName VARCHAR2(255) not null  references company(name)  on delete cascade,
    revenue     NUMBER(20, 5) not null,
    address     VARCHAR2(255) not null,
    builtTime   DATE          not null,
    rating      NUMBER(5, 2)  not null
);

INSERT INTO hotel_belongs VALUES ('12345', 'Holiday In', 'Motel Inc.', '13500', '8013 Cambie Avenue', TO_DATE('1996-01-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '4.2');
INSERT INTO hotel_belongs VALUES ('12346', 'Best Eastern', 'Sunday', '104800', '456 East Road', TO_DATE('2008-06-16 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '3.9');
INSERT INTO hotel_belongs VALUES ('12347', 'Canada Plus', 'CanadaHotels', '68000', '123 Ontario Street', TO_DATE('2002-12-03 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '4.5');
INSERT INTO hotel_belongs VALUES ('12348', 'Shangri Lah', 'HotelsPlus', '503405', '4961 Woodbridge Lane', TO_DATE('2018-04-28 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '4.8');
INSERT INTO hotel_belongs VALUES ('12349', 'Fairblont', 'Hotelmania', '4067800', '3194 Red Dog Road', TO_DATE('2019-02-19 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '3.5');


create table customer_stay
(
    cID             NUMBER        not null    primary key,
    hotel_id        NUMBER        not null    references hotel_belongs(id)  on delete cascade,
    address         VARCHAR2(255) not null,
    phone_number    VARCHAR2(255) not null,
    driving_license VARCHAR2(255) not null    references customer(driving_license)   on delete cascade,
    checkin_time    DATE          not null,
    checkout_time   DATE          not null
);


INSERT INTO customer_stay VALUES ('1', '12345', '4346 Kinney Street, SCAMMON BAY, Alaska', '413-538-5990', '111-111-111', TO_DATE('2022-03-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2022-03-02 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO customer_stay VALUES ('2', '12346', '1519 Clarence Court, Wilmington, North Carolina', '910-218-5058', '111-111-112', TO_DATE('2022-03-02 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2022-03-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO customer_stay VALUES ('3', '12347', '1053 Fulton Street, Toronto', '304-417-7589', '111-111-113', TO_DATE('2022-03-05 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2022-03-08 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO customer_stay VALUES ('4', '12348', '176 Holly Street, Des moines', '706-232-7347', '111-111-114', TO_DATE('2022-03-07 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2022-03-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO customer_stay VALUES ('5', '12349', '3326 Woodlawn Drive, Chicago ', '414-718-6421', '111-111-115', TO_DATE('2022-03-09 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2022-03-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO customer_stay VALUES ('6', '12345', '175 Holly Street', '232-332-2221', '111-111-116', TO_DATE('2022-03-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2022-03-11 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO customer_stay VALUES ('7', '12345', '177 Fulton Street', '211-223-5644', '111-111-117', TO_DATE('2022-03-11 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2022-03-12 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));


create table level_salary
(
    work_level  VARCHAR2(255) not null primary key,
    salary NUMBER        not null
);

INSERT INTO level_salary VALUES ('Freshman', '4000');
INSERT INTO level_salary VALUES ('Sophomore', '5000');
INSERT INTO level_salary VALUES ('Junior', '7000');
INSERT INTO level_salary VALUES ('Senior', '8000');
INSERT INTO level_salary VALUES ('Associate manager', '9000');


create table department_has
(
    dID     NUMBER        not null  primary key,
    name    VARCHAR2(255) not null,
    hotelID NUMBER        not null  references hotel_belongs(id)  on delete cascade
);

INSERT INTO department_has VALUES ('500', 'Electrical', '12345');
INSERT INTO department_has VALUES ('501', 'Customer Service', '12345');
INSERT INTO department_has VALUES ('502', 'Cleaning', '12347');
INSERT INTO department_has VALUES ('503', 'Flood', '12345');
INSERT INTO department_has VALUES ('504', 'Management', '12349');

create table manager
(
    mID       NUMBER        not null  primary key,
    dID       NUMBER        not null  references department_has(dID) on delete cascade,
    name      VARCHAR2(255) not null,
    work_level     VARCHAR2(255) not null  references level_salary(work_level)   on delete cascade,
    birthDate DATE          not null
);

INSERT INTO manager VALUES ('180', '500', 'Cheryl J Marshall', 'Junior', TO_DATE('1998-10-11 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO manager VALUES ('181', '501', 'Kent I Church', 'Freshman', TO_DATE('2000-01-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO manager VALUES ('182', '502', 'Dorothy T Addison', 'Junior', TO_DATE('1977-06-20 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO manager VALUES ('183', '503', 'TQian Lan', 'Senior', TO_DATE('1975-07-19 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO manager VALUES ('184', '504', 'Si Yuan Sim', 'Associate manager', TO_DATE('1989-11-28 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));


create table bill_pays
(
    bID           NUMBER        not null  primary key,
    cID           NUMBER        not null  references customer_stay(cID)  on delete cascade,
    price         NUMBER        not null,
    state         VARCHAR2(255) not null,
    paymentDate   DATE          not null,
    paymentMethod VARCHAR2(255) not null
);

INSERT INTO bill_pays VALUES ('130', '1', '515', 'accepted', TO_DATE('2022-02-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Credit Card');
INSERT INTO bill_pays VALUES ('143', '2', '175', 'accepted', TO_DATE('2022-01-13 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Debit Card');
INSERT INTO bill_pays VALUES ('175', '3', '144', 'accepted', TO_DATE('2022-02-26 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Cash');
INSERT INTO bill_pays VALUES ('179', '4', '230', 'pending', TO_DATE('2022-02-25 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Credit Card');
INSERT INTO bill_pays VALUES ('199', '5', '78', 'pending', TO_DATE('2022-03-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Debit Card');

create table membership_applies
(
    mbID     NUMBER   not null
        primary key,
    cID      NUMBER   not null    references customer_stay(cID)  on delete cascade,
    joinDate DATE     not null,
    discount FLOAT(2) not null,
    credit   LONG     not null
);

INSERT INTO membership_applies VALUES ('23223221', '3', TO_DATE('2022-01-12 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '10', '1000');
INSERT INTO membership_applies VALUES ('23223222', '6', TO_DATE('2022-02-12 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '10', '1000');
INSERT INTO membership_applies VALUES ('23223223', '5', TO_DATE('2022-02-13 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '10', '1000');
INSERT INTO membership_applies VALUES ('23223224', '1', TO_DATE('2022-02-19 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '5', '500');
INSERT INTO membership_applies VALUES ('23223225', '6', TO_DATE('2022-02-24 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '5', '200');

create table partTimeWorker
(
    wID      NUMBER        not null   primary key,
    hourWage NUMBER(20, 2) not null
);

INSERT INTO partTimeWorker VALUES ('1', '20');
INSERT INTO partTimeWorker VALUES ('5', '20');
INSERT INTO partTimeWorker VALUES ('10', '20');
INSERT INTO partTimeWorker VALUES ('3', '20');
INSERT INTO partTimeWorker VALUES ('8', '20');


create table fullTimeWorker
(
    wID    NUMBER        not null primary key,
    salary NUMBER(20, 2) not null
);

INSERT INTO fullTimeWorker VALUES ('2', '5000');
INSERT INTO fullTimeWorker VALUES ('4', '2000');
INSERT INTO fullTimeWorker VALUES ('6', '1000');
INSERT INTO fullTimeWorker VALUES ('7', '1000');
INSERT INTO fullTimeWorker VALUES ('9', '1000');

create table room_contains
(
    roomNumber NUMBER        not null primary key,
    price      NUMBER(20, 2) not null,
    kind       VARCHAR2(255) not null,
    state      VARCHAR2(255) not null,
    hotel_id   NUMBER        not null primary key references hotel_belongs(id)  on delete cascade
);

INSERT INTO room_contains VALUES ('1', '177', 'Standard', 'Occupied', '12345');
INSERT INTO room_contains VALUES ('2', '236', 'Single Room', 'Available', '12345');
INSERT INTO room_contains VALUES ('3', '888', 'Luxury', 'Occupied', '12346');
INSERT INTO room_contains VALUES ('4', '288', 'Double Room', 'Occupied', '12346');
INSERT INTO room_contains VALUES ('5', '177', 'Standard', 'Available', '12347');


create table  worker_works 
(
    wID                NUMBER        not null  primary key,
    dID                NUMBER        not null  references  department_has(dID)  on delete cascade,
    name               VARCHAR2(255) not null,
    birthDate          DATE          not null,
    sex                VARCHAR2(255) not null,
    department         VARCHAR2(255) not null,
    contractStartTime  DATE          not null
);

INSERT INTO worker_works VALUES ('1', '500', 'John Smith', TO_DATE('1983-06-18 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Male', 'Maintenance', TO_DATE('2007-08-21 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('2', '501', 'Jason Lim', TO_DATE('2002-11-12 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Male', 'Customer Service', TO_DATE('2016-11-18 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('3', '502', 'Jennie Kim', TO_DATE('1996-01-16 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Female', 'Cleaning', TO_DATE('2019-06-05 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('4', '503', 'Michael Ting', TO_DATE('1989-09-02 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Male', 'Foods', TO_DATE('2020-01-04 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('5', '504', 'Lilian Yang', TO_DATE('1994-06-06 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Female', 'Security', TO_DATE('2020-02-20 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('6', '504', 'Jack Li', TO_DATE('1994-06-06 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Male', 'Security', TO_DATE('2020-07-13 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('7', '503', 'Robin Chen', TO_DATE('1978-08-04 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Male', 'Foods', TO_DATE('2020-10-19 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('8', '502', 'Sharon Potts', TO_DATE('1995-09-27 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Male', 'Cleaninng', TO_DATE('2019-02-09 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('9', '502', 'Allan Huang', TO_DATE('2001-01-11 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Male', 'Cleaning', TO_DATE('2020-01-23 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO worker_works VALUES ('10', '501', 'Robert Evans', TO_DATE('1969-12-03 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), 'Male', 'CustomerService', TO_DATE('2022-02-27 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));

