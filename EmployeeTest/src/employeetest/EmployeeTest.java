/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package employeetest;

/**
 *
 * @author hp
 */
public class EmployeeTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          Employee emp1=new Employee("ufuk","çöz",15);
        Employee emp2=new Employee("can","kaya",15);
        Employee emp3=new Employee("hilmi","ugur",48);
        Employee emp4=new Employee("sülo","can",35);
        Employee emp5=new Employee("yiğit","kaya",35);
      Employee [] employee={emp1,emp2,emp3,emp4,emp5};
        EmployeeOperation emop1=new EmployeeOperation(22000,46,employee,5);
        EmployeeOperation emop2=new EmployeeOperation(22000,15,employee,9);
        EmployeeOperation emop3=new EmployeeOperation(22000,35,employee,18);
        EmployeeOperation emop4=new EmployeeOperation(22000,28,employee,25);
        EmployeeOperation emop5=new EmployeeOperation(22000,46,employee,45);
         
        
        System.out.println(emp1.toString());
        System.out.println(emop1.extra());
        emp1.exit();
        System.out.println(emp1);
    }
    
}
