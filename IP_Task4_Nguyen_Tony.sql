
CREATE TABLE Customer ( 
    name varchar(64), 
    address varchar(255), 
    category int, 
    PRIMARY KEY(name))

CREATE TABLE Assembly ( 
    assembly_id int, 
    customer_name varchar(64),  
    date_ordered varchar(15), 
    assembly_details varchar(1024), 
    PRIMARY KEY(assembly_id), 
    FOREIGN KEY (customer_name) REFERENCES Customer(name))

CREATE TABLE Department ( 
    department_number int, 
    department_data varchar(1024), 
    PRIMARY KEY(department_number)) 

CREATE TABLE Process ( 
    process_id int, 
    department int, 
    process_data varchar(64), 
    PRIMARY KEY(process_id), 
    FOREIGN KEY (department) REFERENCES Department(department_number)) 

CREATE TABLE Fit ( 
    process_id int REFERENCES Process(process_id), 
    fit_type varchar(15)) 

CREATE TABLE Paint ( 
    process_id int REFERENCES Process(process_id), 
    paint_type varchar(20), 
    painting_method varchar(20))

CREATE TABLE Cut ( 
    process_id int REFERENCES Process(process_id), 
    cutting_type varchar(20), 
    machine_type varchar(20))

CREATE TABLE Account ( 
    account_number int, 
    date_established varchar(10), 
    PRIMARY KEY (account_number)) 

CREATE TABLE Assembly_Account ( 
    account_number int REFERENCES Account(account_number), 
    assembly_id int, 
    details_1 int, 
    FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id)) 

CREATE TABLE Department_Account ( 
    account_number int REFERENCES Account(account_number), 
    department_number int, 
    details_2 int, 
    FOREIGN KEY (department_number) REFERENCES Department(department_number)) 

CREATE TABLE Process_Account ( 
    account_number int REFERENCES Account(account_number), 
    process_id int, 
    details_3 int, 
    FOREIGN KEY (process_id) REFERENCES Process(process_id))

CREATE TABLE Job ( 
    job_number int, 
    assembly_id int, 
    process_id int, 
    job_commenced varchar(15), 
    job_completed varchar(15), 
    PRIMARY KEY (job_number), 
    FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id), 
    FOREIGN KEY (process_id) REFERENCES Process(process_id)) 

CREATE TABLE Fit_Job ( 
    job_number int REFERENCES Job(job_number), 
    labor_time int) 

CREATE TABLE Paint_Job ( 
    job_number int REFERENCES Job(job_number), 
    color varchar(10), 
    volume int, 
    labor_time int) 

CREATE TABLE Cut_Job ( 
    job_number int REFERENCES Job(job_number), 
    machine_type varchar(20), 
    machine_time_used int, 
    materials_used varchar(1024), 
    labor_time int) 

CREATE TABLE Updates ( 
    transaction_number int, 
    account_number int, 
    sup_cost int, 
    PRIMARY KEY (transaction_number), 
    FOREIGN KEY (account_number) REFERENCES Account(account_number))