import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * @author Tony Nguyen
 * Individual project
 */
public class main
{
    /**
     * Main method to run the Java-SQL program.
     * @param args
     */
    public static void main(String[] args) throws SQLException
    {
        // Setup to connect with the SQL database.
        final String hostName = "nguy0132-sql-server.database.windows.net";
        final String dbName = "cs-dsa-4513-sql-db";
        final String user = "nguy0132";
        final String password = "sN8!CVD$NBvib*7J9L23";
        final String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
                        + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
        
        // Display query options for user.
        System.out.println("WELCOME TO THE JOB-SHOP ACCOUNTING DATABASE SYSTEM");
        displayOption();
        
        // Making changes to SQL database.
        try (final Connection connection = DriverManager.getConnection(url)) {
            final String schema = connection.getSchema();
            
            // Created a scanner object for strings.
            Scanner optionScanner = new Scanner(System.in);
            // Created a scanner object for ints.
            Scanner intScanner = new Scanner(System.in);
            
            String option = optionScanner.nextLine();
            
            while (!option.equals("18"))
            {
                // Option 18. Exit the program.
                if (option.equals("18"))
                {
                    optionScanner.close();
                    intScanner.close();
                    System.out.println("THANK YOU FOR USING THE JOB-SHOP ACCOUNTING DATABASE SYSTEM");
                    System.out.println("Closing system...");
                    System.exit(0);
                }
                // Option Query 1. Enter a new customer.
                if (option.equals("1"))
                {
                    System.out.println("Please enter customer name:");
                    String customerName = optionScanner.nextLine();
                    
                    System.out.println("Please enter customer address:");
                    String customerAddress = optionScanner.nextLine();
                    
                    System.out.println("Please enter a category from 1-10:");
                    int category = intScanner.nextInt();
                    
                    PreparedStatement newCustomer = connection.prepareStatement(
                            "INSERT INTO Customer (name, address, category) "
                            + "VALUES ('"+customerName+"', '"+customerAddress+"', '"+category+"')");
                    
                    newCustomer.execute();
                }
                // Option Query 2. Enter a new department.
                else if (option.equals("2"))
                {   
                    System.out.println("Please enter a department number:");
                    int departmentNumber = intScanner.nextInt();
                    
                    System.out.println("Please enter department data:");
                    String departmentData = optionScanner.nextLine();
                    
                    PreparedStatement newDepartment = connection.prepareStatement(
                            "INSERT INTO Department (department_number, department_data) "
                            + "VALUES ('"+departmentNumber+"', '"+departmentData+"')");
                    
                    newDepartment.execute();
                }
                // Option Query 3. Enter a new assembly connected with customer name.
                else if (option.equals("3"))
                {
                    System.out.println("Please enter assembly ID:");
                    int assemblyID = intScanner.nextInt();
                    
                    System.out.println("Please enter an existing customer name to add the assembly to:");
                    String customerName = optionScanner.nextLine();
                    
                    System.out.println("Please enter date ordered:");
                    String dateOrdered = optionScanner.nextLine();
                    
                    System.out.println("Please enter assembly details:");
                    String assemblyDetails = optionScanner.nextLine();
                    
                    PreparedStatement newAssembly = connection.prepareStatement(
                            "INSERT INTO Assembly (assembly_id, customer_name, date_ordered, assembly_details) "
                            + "VALUES ('"+assemblyID+"', '"+customerName+"', '"+dateOrdered+"', '"+assemblyDetails+"')");
                    
                    newAssembly.execute();
                }
                // Option Query 4. Enter a new process.
                else if (option.equals("4"))
                {
                    System.out.println("Please enter a new process ID:");
                    int processID = intScanner.nextInt();
                    
                    System.out.println("Please enter an existing department number to add process to:");
                    int departmentNumber = intScanner.nextInt();
                    
                    System.out.println("Please enter process data:");
                    String processData = optionScanner.nextLine();
                    
                    // Insert process into database.
                    PreparedStatement newProcess = connection.prepareStatement(
                            "INSERT INTO Process (process_id, department, process_data) "
                            + "VALUES ('"+processID+"', '"+departmentNumber+"', '"+processData+"')");
                    
                    // Get what type of process is it: Fit, Paint, Cut.
                    System.out.println("What type of process is it? Enter:");
                    System.out.println("1 for Fit");
                    System.out.println("2 for Paint");
                    System.out.println("3 for Cut");
                    int processType = intScanner.nextInt();
                    
                    // Starting inserting the type into database.
                    if (processType == 1)
                    {
                        System.out.println("Please enter a fit type:");
                        String fitType = optionScanner.nextLine();
                        
                        PreparedStatement newFit = connection.prepareStatement(
                                "INSERT INTO Fit (process_id, fit_type) "
                                + "VALUES ('"+processID+"', '"+fitType+"')");
                                
                        newProcess.execute();
                        newFit.execute();
                    }
                    else if (processType == 2)
                    {
                        System.out.println("Please enter a paint type:");
                        String paintType = optionScanner.nextLine();
                        System.out.println("Please enter painting method:");
                        String paintingMethod = optionScanner.nextLine();
                        
                        PreparedStatement newPaint = connection.prepareStatement(
                                "INSERT INTO Paint (process_id, paint_type, painting_method) "
                                + "VALUES ('"+processID+"', '"+paintType+"', '"+paintingMethod+"')");
                        
                        newProcess.execute();
                        newPaint.execute();
                    }
                    else if (processType == 3)
                    {
                        System.out.println("Please enter a cut type:");
                        String cutType = optionScanner.nextLine();
                        System.out.println("Please enter machine type:");
                        String machineType = optionScanner.nextLine();
                        
                        PreparedStatement newCut = connection.prepareStatement(
                                "INSERT INTO Cut (process_id, cutting_type, machine_type) "
                                + "VALUES ('"+processID+"', '"+cutType+"', '"+machineType+"')");
                        
                        newProcess.execute();
                        newCut.execute();
                    }
                }
                // Option Query 5. Enter a new account.
                else if (option.equals("5"))
                {
                    System.out.println("Enter a new account number:");
                    int accountNumber = intScanner.nextInt();
                    
                    System.out.println("Date account established:");
                    String dateEstablished = optionScanner.nextLine();
                    
                    PreparedStatement newAccount = connection.prepareStatement(
                            "INSERT INTO Account (account_number, date_established) "
                            + "VALUES ('"+accountNumber+"', '"+dateEstablished+"')");
                    
                    newAccount.execute();
                    
                    // Get what account is it for: assembly, department, process.
                    System.out.println("What type of account is it? Enter:");
                    System.out.println("1 for Assembly");
                    System.out.println("2 for Department");
                    System.out.println("3 for Process");
                    int accountType = intScanner.nextInt();
                    
                    // Starting inserting the type into database.
                    if (accountType == 1)
                    {
                        System.out.println("Please enter assembly account details:");
                        String details_1 = optionScanner.nextLine();
                        
                        PreparedStatement assemblyDetails = connection.prepareStatement(
                                "INSERT INTO Assembly_Account (account_number, details_1) "
                                + "VALUES ('"+accountNumber+"', '"+details_1+"')");
                                
                        assemblyDetails.execute();
                    }
                    else if (accountType == 2)
                    {
                        System.out.println("Please enter department account details:");
                        String details_2 = optionScanner.nextLine();
                        
                        PreparedStatement departmentDetails = connection.prepareStatement(
                                "INSERT INTO Department_Account (account_number, details_2) "
                                + "VALUES ('"+accountNumber+"', '"+details_2+"')");
                        
                        departmentDetails.execute();
                    }
                    else if (accountType == 3)
                    {
                        System.out.println("Please enter process account details:");
                        String details_3 = optionScanner.nextLine();
                        
                        PreparedStatement processDetails = connection.prepareStatement(
                                "INSERT INTO Process_Account (account_number, details_3) "
                                + "VALUES ('"+accountNumber+"', '"+details_3+"')");
                        
                        processDetails.execute();
                    }
                }
                // Option Query 6. Enter a new job with process and assembly id as well as when it started.
                else if (option.equals("6"))
                {
                    System.out.println("Please enter a new job number:");
                    String jobNumber = optionScanner.nextLine();
                    System.out.println("Please enter date of job commenced:");
                    String jobStart = optionScanner.nextLine();
                    
                    System.out.println("Please enter an existing assembly ID to add new job to:");
                    String assemblyID = optionScanner.nextLine();
                    System.out.println("Please enter an existing process ID to add new job to:");
                    String processID = optionScanner.nextLine();
                    
                    PreparedStatement newJob = connection.prepareStatement(
                            "INSERT INTO Job (job_number, assembly_id, process_id, job_commenced) "
                            + "VALUES ('"+jobNumber+"', '"+assemblyID+"', '"+processID+"', '"+jobStart+"')");
                    
                    newJob.execute();
                }
                // Option Query 7. Enter date of job completion.
                else if (option.equals("7"))
                {
                    // Update table.
                    System.out.println("Please enter job number that was completed:");
                    String jobNumber = optionScanner.nextLine();
                    System.out.println("Please enter the date of job completion:");
                    String dateCompleted = optionScanner.nextLine();
                    
                    PreparedStatement jobCompletion = connection.prepareStatement(
                            "UPDATE Job "
                            + "SET job_completed = '"+dateCompleted+"' "
                            + "WHERE job_number = '"+jobNumber+"'");
                    
                    jobCompletion.execute();
                    
                    // Get what job type it is: Fit_Job, Paint_Job, Cut_Job.
                    System.out.println("What type of job is it? Enter:");
                    System.out.println("1 for Fit job");
                    System.out.println("2 for Paint job");
                    System.out.println("3 for Cut job");
                    int jobType = intScanner.nextInt();
                    
                    if (jobType == 1)
                    {
                        System.out.println("Please enter the labor time (minutes) of that job:");
                        int laborTime = intScanner.nextInt();
                        
                        PreparedStatement jobFit = connection.prepareStatement(
                                "INSERT INTO Fit_Job (job_number, labor_time) "
                                + "VALUES ('"+jobNumber+"', '"+laborTime+"')");
                        
                        jobFit.execute();
                    }
                    else if (jobType == 2)
                    {
                        System.out.println("Please enter the paint color used on that job:");
                        String color = optionScanner.nextLine();
                        System.out.println("Please enter the volume (liters) of paint on that job:");
                        int volume = intScanner.nextInt();
                        System.out.println("Please enter the labor time (minutes) of that job:");
                        int laborTime = intScanner.nextInt();
                        
                        PreparedStatement jobPaint = connection.prepareStatement(
                                "INSERT INTO Paint_Job (job_number, color, volume, labor_time) "
                                + "VALUES ('"+jobNumber+"', '"+color+"', '"+volume+"', '"+laborTime+"')");
                        
                        jobPaint.execute();
                    }
                    else if (jobType == 3)
                    {
                        System.out.println("Please enter the machine type used on that job:");
                        String machineType = optionScanner.nextLine();
                        System.out.println("Please enter the time spent (minutes) on that machine on that job:");
                        int machineTimeUsed = intScanner.nextInt();
                        System.out.println("Please enter materials used on that job:");
                        String materialsUsed = optionScanner.nextLine();
                        System.out.println("Please enter the labor time (minutes) of that job:");
                        int laborTime = intScanner.nextInt();
                        
                        PreparedStatement jobCut = connection.prepareStatement(
                                "INSERT INTO Cut_Job (job_number, machine_type, machine_time_used, materials_used, labor_time) "
                                + "VALUES ('"+jobNumber+"', '"+machineType+"', '"+machineTimeUsed+"', '"+materialsUsed+"', '"+laborTime+"')");
                        
                        jobCut.execute();
                    }
                }
                // Option Query 8. Transactions and cost entering.
                else if (option.equals("8"))
                {
                    System.out.println("Please enter a new transaction number:");
                    int transactionNumber = intScanner.nextInt();
                    System.out.println("Please enter the sup cost in that transaction:");
                    int supCost = intScanner.nextInt();
                    System.out.println("Please enter the account number this transaction is a part of:");
                    int accountNumber = intScanner.nextInt();
                    
                    PreparedStatement transaction = connection.prepareStatement(
                            "INSERT INTO Updates (transaction_number, account_number, sup_cost) "
                            + "VALUES ('"+transactionNumber+"', '"+accountNumber+"', '"+supCost+"')");
                    
                    transaction.execute();
                    
                    // Updating accounts.
                    // Get what account type it is: Assembly, Process, Department.
                    System.out.println("What type of job is it? Enter:");
                    System.out.println("1 for Assembly");
                    System.out.println("2 for Process");
                    System.out.println("3 for Department");
                    int accountType = intScanner.nextInt();
                    
                    if (accountType == 1)
                    {
                        System.out.println("Please enter the assembly ID associated with the transaction:");
                        int assemblyID = intScanner.nextInt();
                        
                        // Inserting account number and assembly ID and leaving details_1 as null.
                        PreparedStatement updateAssemblyAccount = connection.prepareStatement(
                                "INSERT INTO Assembly_Account (account_number, assembly_id) "
                                + "VALUES ('"+accountNumber+"', '"+assemblyID+"')");
                        
                        updateAssemblyAccount.execute();
                        
                        // Update the sup cost in the specified assembly account.
                        PreparedStatement assemblyCost = connection.prepareStatement(
                                "Update Assembly_Account "
                                + "SET details_1 += '"+supCost+"' "
                                + "WHERE assembly_id = '"+assemblyID+"' and account_number = '"+accountNumber+"'");
                        
                        assemblyCost.execute();
                    }
                    else if (accountType == 2)
                    {
                        System.out.println("Please enter the process ID associated with the transaction:");
                        int processID = intScanner.nextInt();
                    }
                    else if (accountType == 3)
                    {
                        System.out.println("Please enter the department associated with the transaction:");
                        int departmentNumber = intScanner.nextInt();
                    }
                    
                }
                // Option Query 9. Get the cost incurred on an assembly ID.
                else if (option.equals("9"))
                {
                    System.out.println("Enter an assembly_id to retrieve the cost of:");
                    int assemblyID = intScanner.nextInt();
                    
                    // Define retrieve cost to create and query to get cost of a given assembly ID. 
                    ResultSet retrieveCost;
                    Statement statement = connection.createStatement();
                    
                    // Execute the query.
                    retrieveCost = statement.executeQuery("SELECT details_1 from Assembly_Account WHERE assembly_id = '"+assemblyID+"'");
                    
                    // Get the cost in an assembly ID if it exists. Else try again.
                    if (retrieveCost.next())
                    {
                        Object object = retrieveCost.getObject(1);
                        System.out.printf("%s", object.toString());
                        System.out.printf("%n");
                    }
                    else 
                    {
                        System.out.println("No such assembly ID exists. Try again.");
                    }
                }
                // Option Query 10. Retrieve total labor time within a department for jobs completed during a given date.
                else if (option.equals("10"))
                {
                    System.out.println("Please enter a department to get total labor time of jobs:");
                    int departmentNumber = intScanner.nextInt();
                    System.out.println("Please enter job date of completion in MM/DD/YY format:");
                    String dateCompleted = optionScanner.nextLine();
                    
                    //select process_id from process where department = departmenNumber
                }
                // Option Query 11. Enter an assembly_id and get the processes it has.
                else if (option.equals("11"))
                {
                    System.out.println("Please enter an assembly ID to get the processes it has passed:");
                    String assemblyID = optionScanner.nextLine();
                }
                // Option Query 12. Retrieve the jobs completed during a given date in a given department.
                else if (option.equals("12"))
                {
                    System.out.println("Please enter a date of job completion:");
                    String jobCompletionDate = optionScanner.nextLine();
                    System.out.println("Please enter the department of that job:");
                    String jobDepartment = optionScanner.nextLine();
                }
                // Option Query 13. Retrieve customers (in name order) whose category is in a given range.
                // Also Query 17 but export customers to data file and not to the screen.
                else if (option.equals("13") || option.equals("17"))
                {
                    System.out.println("Please enter lower bound category number (1-10):");
                    int lowerCategory = intScanner.nextInt();
                    System.out.println("Please enter upper bound category number (1-10):");
                    int upperCategory = intScanner.nextInt();
                    
                    /**
                     * Code inside if statement inspired by this StackOverflow link.
                     * https://stackoverflow.com/questions/23291346/printing-all-columns-from-select-query-in-java
                     */
                    if (lowerCategory < upperCategory)
                    {                        
                        // Define retrieveCustomer to add results to and create a statement to execute query.
                        ResultSet retrieveCustomer;
                        Statement statement = connection.createStatement();
                        
                        retrieveCustomer = statement.executeQuery("SELECT name from Customer "
                                + "WHERE category between '"+lowerCategory+"' AND '"+upperCategory+"'");
                        
                        // Getting column data.
                        ResultSetMetaData metaData = retrieveCustomer.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        
                        // If option is 13 then print to the screen.
                        if (option.equals("13"))
                        {
                            // While there is another data in column.
                            while (retrieveCustomer.next())
                            {
                                // Print out all columns as long as there is one.
                                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
                                {
                                    Object object = retrieveCustomer.getObject(columnIndex);
                                    System.out.printf("%s", object == null ? "NULL" : object.toString());
                                }
                                System.out.printf("%n");
                            }
                        }
                        
                        // If option is 17 then send output to a file.
                        if (option.equals("17"))
                        {
                            System.out.println("Please enter a text file name to export customer data to:");
                            String fileName = optionScanner.nextLine();
                            String customerData = "";
                            
                            try {
                                 File file = new File(fileName);
                                 FileOutputStream fileStream = new FileOutputStream(file);
                                 PrintWriter writer = new PrintWriter(fileStream);
                                 
                                 // While there is another data in column.
                                 while (retrieveCustomer.next())
                                 {
                                     // Print out all columns as long as there is one.
                                     for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
                                     {
                                         Object object = retrieveCustomer.getObject(columnIndex);
                                         customerData = customerData + object.toString() + ",";
                                     }
                                 }
                                 
                                 // Write to a specified file and then close and flush everything.
                                 writer.write(customerData);
                                 writer.flush();
                                 writer.close();
                                 fileStream.close();
                            }
                            catch (Exception e) 
                            {
                                System.out.println("Can't create file.");
                            }

                        }
                    }
                    else 
                    {
                        System.out.println("Upper bound category number must be greater. Try again.");
                    }
                }
                // Option Query 14. Delete all cut jobs whose job numbers fall in a certain range.
                else if (option.equals("14"))
                {
                    System.out.println("Please enter lower bound job number:");
                    int lowerJobNumber = intScanner.nextInt();
                    System.out.println("Please enter upper bound job number:");
                    int upperJobNumber = intScanner.nextInt();
                    
                    if (lowerJobNumber < upperJobNumber)
                    {
                        PreparedStatement deleteCutJob = connection.prepareStatement(
                                "DELETE from Cut_Job "
                                + "WHERE job_number between '"+lowerJobNumber+"' AND '"+upperJobNumber+"'");
                        deleteCutJob.execute();
                    }
                    else 
                    {
                        System.out.println("Upper bound job number must be greater. Try again.");
                    }
                }
                // Option Query 15. Change color of a paint job.
                else if (option.equals("15"))
                {
                    System.out.println("Please enter the paint job number to change that color of:");
                    int jobNumber = intScanner.nextInt();
                    System.out.println("Please enter the color to change to:");
                    String newColor = optionScanner.nextLine();
                    
                    PreparedStatement updateColor = connection.prepareStatement(
                            "UPDATE Paint_Job "
                            + "SET color = '"+newColor+"' "
                            + "WHERE job_number = '"+jobNumber+"'");
                    
                    updateColor.execute();
                }
                // Option Query 16. Importing customer info from a text file and entering it in the database.
                else if (option.equals("16"))
                {
                    System.out.println("Please enter an input file name:");
                    String fileName = optionScanner.nextLine();
                    
                    Scanner readFile;
                    try 
                    {
                        // Open the file.
                        readFile = new Scanner(new File(fileName));
                        
                        // Read the file. 
                        // Assuming everything is in order with spaces and no commas and one word name and addresses.
                        while (readFile.hasNext())
                        {
                            String customerName = readFile.next();
                            String customerAddress = readFile.next();
                            int category = readFile.nextInt();
                            
                            PreparedStatement newCustomer = connection.prepareStatement(
                                    "INSERT INTO Customer (name, address, category) "
                                    + "VALUES ('"+customerName+"', '"+customerAddress+"', '"+category+"')");
                            
                            newCustomer.execute();
                        }
                        
                        // Close the file.
                        readFile.close();
                    }
                    catch (Exception e) 
                    {
                        System.out.println("File was not found. Try again.");
                    }
                }
                else 
                {
                    System.out.println("Not a valid option. Please enter a number 1-18.");
                }
                
                // Get another user input as they didn't enter a value from 1-18.
                System.out.println("Please pick another option below:");
                displayOption();
                option = optionScanner.nextLine();
            }
        }
        
        System.out.println("THANK YOU FOR USING THE JOB-SHOP ACCOUNTING DATABASE SYSTEM");
        System.out.println("Closing system...");
    }
    
    /**
     * Method when called will display query options 1 to 18.
     */
    public static void displayOption()
    {
        // Let them choose options (1-18) on what they want to do to the database.
        System.out.println("(1) Enter a new customer (30/day).");
        System.out.println("(2) Enter a new department (infrequent).");
        System.out.println("(3) Enter a new assembly with its customer-name, assembly-details, assembly-id, "
                + "and date-ordered (40/day).");
        System.out.println("(4) Enter a new process-id and its department together with its type "
                + "and information relevant to the type (infrequent).");
        System.out.println("(5) Create a new account and associate it with the process, assembly, "
                + "or department to which it is applicable (10/day).");
        System.out.println("(6) Enter a new job, given its job-no, assembly-id, process-id, "
                + "and date the job commenced (50/day).");
        System.out.println("(7) At the completion of a job, enter the date it completed "
                + "and the information relevant to the type of job (50/day).");
        System.out.println("(8) Enter a transaction-no and its sup-cost and update all the costs (details) of the affected accounts by "
                + "adding sup-cost to their current values of details (50/day).");
        System.out.println("(9) Retrieve the cost incurred on an assembly-id (200/day).");
        System.out.println("(10) Retrieve the total labor time within a department for jobs completed "
                + "in the department during a given date (20/day).");
        System.out.println("(11) Retrieve the processes through which a given assembly-id has passed so far "
                + "(in date-commenced order) and the department responsible for each process (100/day).");
        System.out.println("(12) Retrieve the jobs (together with their type information and assembly-id) "
                + "completed during a given date in a given department (20/day).");
        System.out.println("(13) Retrieve the customers (in name order) whose category is in a given range (100/day).");
        System.out.println("(14) Delete all cut-jobs whose job-no is in a given range (1/month).");
        System.out.println("(15) Change the color of a given paint job (1/week).");
        System.out.println("(16) Import: enter new customers from a data file until the file is empty "
                + "(the user must be asked to enter the input file name).");
        System.out.println("(17) Export: Retrieve the customers (in name order) "
                + "whose category is in a given range and output them to a data file instead of screen "
                + "(the user must be asked to enter the output file name).");
        System.out.println("(18) Quit");
    }
}

