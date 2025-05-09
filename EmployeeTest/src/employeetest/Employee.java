/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employeetest;

/**
 *
 * @author hp
 */
class Employee {
      private String name;
    private String surName;
  private int yas;
     private  static int   empcounter;
   

    public Employee(String name, String surName, int yas) {
        this.name = name;
        this.surName = surName;
        this.yas = yas;
        
        empcounter++;
    }

public void exit(){
    empcounter--;
    System.out.println("employee was exit");
}
    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public int getYas() {
        return yas;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setYas(int yas) {
        this.yas = yas;
    

    
}

    @Override
    public String toString() {
        return "Employee{" + "name=" + name + ", surName=" + surName + ", yas=" + yas + ", empcounter=" + empcounter + '}';
    }
}