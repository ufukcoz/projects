/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employeetest;

/**
 *
 * @author hp
 */
public class EmployeeOperation {
    private int amount;
    private int hour;
    private int years;
    private Employee [] employee;

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getAmount() {
        return amount;
    }

    public int getHour() {
        return hour;
    }

    public EmployeeOperation(int amount, int hour,Employee []emloyee,int year) {
        this.amount = amount;
        this.hour = hour;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getYears() {
        return years;
    }
        
    public  double extra (){
       if(hour>45){
           amount+=200*(hour-45)+100*(hour-30);}
           else if (30>hour&&hour<45){
                   amount+=100*(hour-30);}
                   
                   
      return amount;
        
                   }
    public double salaryToyear(){
        if(years<=5){
            amount=amount;}
             if(5<=years&&years<10){
                  amount+=2000;  
                    }if (years>=10){
                        amount+=5000;
                    }
        
    return amount;}

 
       
           
       
    }

