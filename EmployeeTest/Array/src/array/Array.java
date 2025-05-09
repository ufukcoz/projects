/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package array;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class Array {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      ArrayList<String>items=new ArrayList();
      items.add("ufuk");
      items.add("ali");
      items.add("can");
      items.remove("ufuk");
      
        System.out.println(items.contains("ufuk"));
    }
    
}
