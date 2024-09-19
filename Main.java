import java.util.*;
abstract class Employee {
    private String name;
    private int id;

    
    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    
    public int getId() {
        return id;
    }

    
    public abstract void countSalary();
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;
    private int totalLeaves; 
    private int leavesTaken;
    private int remainingLeaves; 
    private double salary; 
    private double thisMonthSalary; 
    private int requestedLeave;
    private int rejectedLeave;


    public FullTimeEmployee(String name, int id, double monthlySalary, int totalLeaves) {
        super(name, id);
        this.monthlySalary = monthlySalary;
        this.totalLeaves = totalLeaves;
        this.leavesTaken = 0; 
        this.remainingLeaves = totalLeaves; 
        this.requestedLeave = 0; 
        this.thisMonthSalary = monthlySalary; 
        this.rejectedLeave=0;
    }

    public void countSalary() {
        this.salary = monthlySalary * 12; 
    }

    public double getMonthlySalary() {
        return thisMonthSalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.thisMonthSalary = monthlySalary;
    }

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public int getLeavesTaken() {
        return leavesTaken;
    }

    public int getRemainingLeaves() {
        return remainingLeaves;
    }

    public void setLeavesTaken(int leavesTaken) {
        if (leavesTaken >= 0 && leavesTaken <= totalLeaves) {
            this.leavesTaken = leavesTaken;
            this.remainingLeaves = totalLeaves - leavesTaken; 
        } else {
            System.out.println("Invalid leave value. It should be between 0 and " + totalLeaves);
        }
    }

    public double getSalary() {
        return salary;
    }

    public double getThisMonthSalary() {
        return thisMonthSalary;
    }

    public void approveLeave(int leaveDays) {
        if (leaveDays > 0 && leaveDays <= remainingLeaves) {
            leavesTaken += leaveDays;
            remainingLeaves -= leaveDays;
            System.out.println(leaveDays + " days leave approved. Remaining leave: " + remainingLeaves);
            this.rejectedLeave+=this.rejectedLeave+this.requestedLeave-leaveDays;
            System.out.println("leaves Rejected:"+this.rejectedLeave);
        } else {
            System.out.println("Leave request denied. Not enough leave balance.");
        }
    }

    public void requestLeave(int leaveDays, boolean isLwp) {
        if (leaveDays > 0 && leaveDays <= 5) {
            if (isLwp) {
                this.thisMonthSalary -= (monthlySalary / 30) * leaveDays; 
                System.out.println(leaveDays + " days leave without pay approved. Salary deducted.");
            } else {
                if (leaveDays <= remainingLeaves) {
                    this.requestedLeave = leaveDays;
                    System.out.println(leaveDays + " days casual leave requested.");
                } else {
                    System.out.println("Invalid leave request. Not enough leave balance.");
                }
            }
        } else {
            System.out.println("Invalid leave request. You can only request up to 5 days at a time.");
        }
    }

    public int getRequestedLeave() {
        return requestedLeave;
    }
    public int getRejectedLeaves() {
        return rejectedLeave;
    }

    public void resetRequestedLeave() {
        this.requestedLeave = 0;
    }
}

class PartTimeEmployee extends Employee {

    private double hourlyRate;
    private int hoursWorked;
    private double salary; 

    public PartTimeEmployee(String name, int id, double hourlyRate, int hoursWorked) {
        super(name, id); 
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    public void countSalary() {
        this.salary = hourlyRate * hoursWorked; 
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getSalary() {
        return salary;
    }
}

abstract class Admin {

    private String name;
    private String password;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void viewEmployeeData(List<Employee> employees); 

    public abstract void approveLeave(Employee employee,int leaves_approved);

    public abstract void editSalary(Employee employee, double newSalary); 
}

class HRManager extends Admin {
    public HRManager(String name, String password) {
        super(name, password);
    }

    public void viewEmployeeData(List<Employee> employees) {
        System.out.println("HR Manager: Viewing all employee data.");
        System.out.println("====================================================================================================================================");
        System.out.println("ID | Name         | Type          | Salary    | Leaves Taken | Remaining Leaves | Requested Leave|Rejected Leave|");
        System.out.println("====================================================================================================================================");
        for (Employee employee : employees) {
            if (employee instanceof FullTimeEmployee) {
                FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
                System.out.printf("%-3d| %-12s| %-13s| %-9.2f| %-13d| %-16d| %-15d| %-15d%n",
                        fullTimeEmployee.getId(), fullTimeEmployee.getName(), "Full Time", fullTimeEmployee.getThisMonthSalary(),
                        fullTimeEmployee.getLeavesTaken(), fullTimeEmployee.getRemainingLeaves(), fullTimeEmployee.getRequestedLeave(),fullTimeEmployee.getRejectedLeaves());
            } else if (employee instanceof PartTimeEmployee) {
                PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
                System.out.printf("%-3d| %-12s| %-13s| %-9.2f| %-13s| %-16s| %-15s| %-15s%n",
                        partTimeEmployee.getId(), partTimeEmployee.getName(), "Part Time", partTimeEmployee.getHourlyRate(),
                        "N/A", "N/A", "N/A","NA");
            }
        }
        System.out.println("====================================================================================================================================");
    }
  
    public void approveLeave(Employee employee,int leaves_approved) {
        System.out.println("HR Manager cannot approve leave.");
    }

    public void editSalary(Employee employee, double newSalary) {
        System.out.println("HR Manager cannot edit salary.");
    }
}

class Manager extends Admin {
    public Manager(String name, String password) {
        super(name, password);
    }

    public void viewEmployeeData(List<Employee> employees) {
        System.out.println("Manager: Viewing all employee data.");
        System.out.println("====================================================================================================================================");
        System.out.println("ID | Name         | Type          | Salary    | Leaves Taken | Remaining Leaves | Requested Leave|Rejected Leave|");
        System.out.println("====================================================================================================================================");
        for (Employee employee : employees) {
            if (employee instanceof FullTimeEmployee) {
                FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
                System.out.printf("%-3d| %-12s| %-13s| %-9.2f| %-13d| %-16d| %-15d| %-15d%n",
                        fullTimeEmployee.getId(), fullTimeEmployee.getName(), "Full Time", fullTimeEmployee.getThisMonthSalary(),
                        fullTimeEmployee.getLeavesTaken(), fullTimeEmployee.getRemainingLeaves(), fullTimeEmployee.getRequestedLeave(),fullTimeEmployee.getRejectedLeaves());
            } else if (employee instanceof PartTimeEmployee) {
                PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
                System.out.printf("%-3d| %-12s| %-13s| %-9.2f| %-13s| %-16s| %-15s| %-15s%n",
                        partTimeEmployee.getId(), partTimeEmployee.getName(), "Part Time", partTimeEmployee.getHourlyRate(),
                        "N/A", "N/A", "N/A","NA");
            }
        }
        System.out.println("====================================================================================================================================");
    }
    
    public void approveLeave(Employee employee,int leaves_approved) {
        if (employee instanceof FullTimeEmployee) {
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
            // int requestedLeave = fullTimeEmployee.getRequestedLeave();
            if (leaves_approved > 0) {
                fullTimeEmployee.approveLeave(leaves_approved);
                fullTimeEmployee.resetRequestedLeave();
            } else {
                System.out.println("No leave requested by " + fullTimeEmployee.getName());
            }
        } else {
            System.out.println("Manager can only approve leave for full-time employees.");
        }
    }

    public void editSalary(Employee employee, double newSalary) {
        System.out.println("Manager cannot edit salary.");
    }
}

class Accountant extends Admin {
    public Accountant(String name, String password) {
        super(name, password); 
    }

    public void viewEmployeeData(List<Employee> employees) {
        System.out.println("Accountant viewing employee data.");
        System.out.println("====================================================================================================================================");
        System.out.println("ID | Name         | Type          | Salary    | Leaves Taken | Remaining Leaves | Requested Leave|Rejected Leave|");
        
        System.out.println("====================================================================================================================================");
        for (Employee employee : employees) {
            if (employee instanceof FullTimeEmployee) {
                FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
                System.out.printf("%-3d| %-12s| %-13s| %-9.2f| %-13d| %-16d| %-15d| %-15d%n",
                        fullTimeEmployee.getId(), fullTimeEmployee.getName(), "Full Time", fullTimeEmployee.getThisMonthSalary(),
                        fullTimeEmployee.getLeavesTaken(), fullTimeEmployee.getRemainingLeaves(), fullTimeEmployee.getRequestedLeave(),fullTimeEmployee.getRejectedLeaves());
            } else if (employee instanceof PartTimeEmployee) {
                PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
                System.out.printf("%-3d| %-12s| %-13s| %-9.2f| %-13s| %-16s| %-15s| %-15s%n",
                        partTimeEmployee.getId(), partTimeEmployee.getName(), "Part Time", partTimeEmployee.getHourlyRate(),
                        "N/A", "N/A", "N/A","NA");
            }
        }
        System.out.println("====================================================================================================================================");
      }
    
    public void approveLeave(Employee employee,int leaves_approved) {
        System.out.println("Accountant cannot approve leaves. Only Manager can approve leaves.");
    }

    public void editSalary(Employee employee, double newSalary) {
        System.out.println("Accountant editing salary for employee: " + employee.getName());
        employee.countSalary(); // Ensure salary is up-to-date
        if (employee instanceof FullTimeEmployee) {
            ((FullTimeEmployee) employee).setMonthlySalary(newSalary);
            System.out.println("Monthly salary updated to: " + ((FullTimeEmployee) employee).getMonthlySalary());
        } else if (employee instanceof PartTimeEmployee) {
            ((PartTimeEmployee) employee).setHourlyRate(newSalary / ((PartTimeEmployee) employee).getHoursWorked());
            System.out.println("Hourly rate updated to: " + ((PartTimeEmployee) employee).getHourlyRate());
        }
        System.out.println("Salary updated successfully.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Employee> employees = new ArrayList<>();
        employees.add(new FullTimeEmployee("John Doe", 1, 5000,10));
        employees.add(new FullTimeEmployee("Alice Smith", 2, 6000, 10));
        employees.add(new FullTimeEmployee("Bob Johnson", 3, 5500, 10));
        employees.add(new FullTimeEmployee("Emily Brown", 4, 5200, 10));
        employees.add(new FullTimeEmployee("Michael Wilson", 5, 5800, 10));
        employees.add(new FullTimeEmployee("Sophia Miller", 6, 5300, 10));
        employees.add(new FullTimeEmployee("Daniel Garcia", 7, 5700, 10));
        employees.add(new FullTimeEmployee("Olivia Martinez", 8, 5400, 10));
        employees.add(new FullTimeEmployee("James Lopez", 9, 5600, 10));
        employees.add(new FullTimeEmployee("Emma Gonzalez", 10, 5900, 10));
        employees.add(new PartTimeEmployee("William Davis", 11, 25, 80));
        employees.add(new PartTimeEmployee("Mia Rodriguez", 12, 22, 90));
        employees.add(new PartTimeEmployee("Alexander Hernandez", 13, 20, 95));
        employees.add(new PartTimeEmployee("Sofia Wilson", 14, 24, 85));
        employees.add(new PartTimeEmployee("Matthew Anderson", 15, 26, 75));
        employees.add(new PartTimeEmployee("Isabella Taylor", 16, 23, 88));
        employees.add(new PartTimeEmployee("Ethan Thomas", 17, 21, 92));
        employees.add(new PartTimeEmployee("Amelia Moore", 18, 27, 70));
        employees.add(new PartTimeEmployee("Lucas Jackson", 19, 28, 65));
        employees.add(new PartTimeEmployee("Ava White", 20, 29, 82));

        List<HRManager> hrManagers = new ArrayList<>();
        hrManagers.add(new HRManager("HR Manager 1", "hr1@1234"));
        hrManagers.add(new HRManager("HR Manager 2", "hr2@5678"));

        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager("Manager 1", "manager1@1234"));
        managers.add(new Manager("Manager 2", "manager2@5678"));

        List<Accountant> accountants = new ArrayList<>();
        accountants.add(new Accountant("Accountant 1", "accountant1@1234"));
        accountants.add(new Accountant("Accountant 2", "accountant2@5678"));

        System.out.println("Welcome to Salary Management System");
        System.out.println("Press any key to continue...");
        scanner.nextLine(); 

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1 -> Employee");
            System.out.println("2 -> Admin");
            System.out.println("3 -> Exit");
            int choice = scanner.nextInt();

            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    System.out.println("Enter Employee Name:");
                    String empName = scanner.nextLine();
                    System.out.println("Enter Employee ID:");
                    int empId = scanner.nextInt();
                    scanner.nextLine(); 

                    boolean employeeFound = false;
                    for (Employee employee : employees) {
                        if (employee.getName().equalsIgnoreCase(empName) && employee.getId() == empId) {
                            employeeFound = true;
                            System.out.println("Employee found: " + employee.getName());
                            if (employee instanceof FullTimeEmployee) {
                                FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
                                System.out.println("Monthly Salary: " + fullTimeEmployee.getMonthlySalary());
                                System.out.println("Annual Salary: " + fullTimeEmployee.getSalary());
                                System.out.println("This Month's Salary: " + fullTimeEmployee.getThisMonthSalary());
                                System.out.println("Leaves Taken: " + fullTimeEmployee.getLeavesTaken());
                                System.out.println("Remaining Leaves: " + fullTimeEmployee.getRemainingLeaves());
                                System.out.println("Requested Leave: " + fullTimeEmployee.getRequestedLeave());
                                System.out.println("Rejected Leave: " + fullTimeEmployee.getRejectedLeaves());
                                System.out.println("Do you want to request leave? (yes/no)");
                                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                                    System.out.println("Choose leave type: 1 -> Leave Without Pay (LWP), 2 -> Casual Leave (CL)");
                                    int leaveType = scanner.nextInt();
                                    System.out.println("Enter number of leave days to request (up to 5):");
                                    int leaveDays = scanner.nextInt();
                                    scanner.nextLine();

                                    if (leaveType == 1) {
                                        fullTimeEmployee.requestLeave(leaveDays, true); 
                                    } else if (leaveType == 2) {
                                        fullTimeEmployee.requestLeave(leaveDays, false); 
                                    } else {
                                        System.out.println("Invalid leave type selected.");
                                    }
                                }
                            } else if (employee instanceof PartTimeEmployee) {
                                PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
                                System.out.println("Hourly Rate: " + partTimeEmployee.getHourlyRate());
                                System.out.println("Hours Worked: " + partTimeEmployee.getHoursWorked());
                            }
                        }
                    }
                    if (!employeeFound) {
                        System.out.println("Employee not found. Please check the name and ID.");
                    }
                    break;
                case 2:
                    System.out.println("Enter Admin Type (1 -> HR Manager, 2 -> Manager, 3 -> Accountant):");
                    int adminType = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.println("Enter Admin Name:");
                    String adminName = scanner.nextLine();
                    System.out.println("Enter Admin Password:");
                    String adminPassword = scanner.nextLine();

                    Admin admin = null;
                    switch (adminType) {
                        case 1:
                            for (HRManager hrManager : hrManagers) {
                                if (hrManager.getName().equalsIgnoreCase(adminName) && hrManager.getPassword().equals(adminPassword)) {
                                    admin = hrManager;
                                    break;
                                }
                            }
                            break;
                        case 2:
                            for (Manager manager : managers) {
                                if (manager.getName().equalsIgnoreCase(adminName) && manager.getPassword().equals(adminPassword)) {
                                    admin = manager;
                                    break;
                                }
                            }
                            break;
                        case 3:
                            for (Accountant accountant : accountants) {
                                if (accountant.getName().equalsIgnoreCase(adminName) && accountant.getPassword().equals(adminPassword)) {
                                    admin = accountant;
                                    break;
                                }
                            }
                            break;
                        default:
                            System.out.println("Invalid admin type selected.");
                    }

                    if (admin != null) {
                        admin.viewEmployeeData(employees);
                        if (admin instanceof Manager) {
                            System.out.println("Do you want to approve leave requests? (yes/no)");
                            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                                System.out.println("Enter Employee ID to approve leave:");
                                int approveEmpId = scanner.nextInt();
                                scanner.nextLine(); 
                                System.out.println("how many leaves u want to approve");
                                int leaves_approved = scanner.nextInt();
                                scanner.nextLine(); 
                                for (Employee employee : employees) {
                                    if (employee instanceof FullTimeEmployee && employee.getId() == approveEmpId) {
                                        Manager manager = (Manager) admin;
                                        manager.approveLeave((FullTimeEmployee) employee,leaves_approved);
                                    }
                                }
                            }
                        } else if (admin instanceof Accountant) {
                            System.out.println("Do you want to edit an employee's salary? (yes/no)");
                            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                                System.out.println("Enter Employee ID:");
                                int empIdToEdit = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Enter new monthly salary:");
                                double newMonthlySalary = scanner.nextDouble();
                                scanner.nextLine(); 
                                for (Employee employee : employees) {
                                    if (employee.getId() == empIdToEdit && employee instanceof FullTimeEmployee) {
                                        admin.editSalary(employee, newMonthlySalary);
                                        System.out.println("Salary updated for employee ID " + empIdToEdit);
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Admin not found. Please check the name and password.");
                    }
                    break;     
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
