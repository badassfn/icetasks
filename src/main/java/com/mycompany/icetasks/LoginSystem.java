/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.icetasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ortie
 */
public class LoginSystem {
    
    private static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<String> pins = new ArrayList<>(); //
    private static ArrayList<String> roles = new ArrayList<>();
    public static int tries = 0;
    
    
    public static void main(String[] args) {
      
     loadFileusers();     
     
        while (true){
        String choice = JOptionPane.showInputDialog("--MENU--\n\n 1)Create Account\n 2)Login \n 3)Exit");
        
        if (choice.equals("1")) {
            createAccount();
        } 
        else if(choice.equals("2")){
            login();
        } 
        else if (choice.equals("3")){
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "ïnvalid choice");
        }
        }
    } 
    public static void loadFileusers(){
        try {
            File file = new File("users.txt");
            
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));//to read the text file
            String line;
            while ((line = reader.readLine())!= null) {                
                
                String parts[] = line.split(",");
                
                usernames.add(parts[0]);
                pins.add(parts[1]);
                roles.add(parts[2]);

            }
            reader.close();//!!!!idk 
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage());
        }
        
    }
    
    
    public static void createAccount(){
        String username = JOptionPane.showInputDialog("Create username:");
        String pin = JOptionPane.showInputDialog("Create PIN:");
        
        while (pin.length()<6) {            
            JOptionPane.showMessageDialog(null, "Äccount reated");
        }
    }
    public static void login(){
        
        if (tries >= 3 ) {
            
            JOptionPane.showMessageDialog(null, "Too many  wrong attempts, Program will close.");
            System.exit(0);
        }
        
        String username= JOptionPane.showInputDialog("Enter username:");
        String pin = JOptionPane.showInputDialog("ënter pin");
        
        //boolean found = false;
        int foundIndex = -1;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(username) && pins.get(i).equals(pin)) {
                //found = true;
                foundIndex = i;
                break;
            }
            if (foundIndex != -1) {
                String userRole = roles.get(foundIndex);
                //JOptionPane.showMessageDialog(null, "Login Successful");
                JOptionPane.showMessageDialog(null, "WELCOME " + username + "!\nRole: " + userRole);
                tries =0;
                
                if (userRole.equals("admin")) {
                    adminMenu();
                }
                else if (userRole.equals("staff")) {
                   JOptionPane.showMessageDialog(null, "STAFF ACCESS GRANTED"); 
                }
                else if (userRole.equals("visitor")) {
                    JOptionPane.showMessageDialog(null, "VISITOR ACCESS GRANTED");
                }
                else { tries++;
           
            
            int left = 3 - tries;
            JOptionPane.showMessageDialog(null, "INVALID LOGIN!\nAttempts left: " + left);
        }
            }
        }
    }
    public static void adminMenu(){
        String adminChoice = JOptionPane.showInputDialog("DMIN MENU \n 1. View All Users\n 2. Add New User\n 3. Logout");
        
        if (adminChoice.equals("1")) {
            viewUsers();
            
        } else if (adminChoice.equals("2")) {
            addUser();
        } else if (adminChoice.equals("3")) {
            JOptionPane.showMessageDialog(null, "Logged out");
        }
        else {

            JOptionPane.showMessageDialog(null, "Invalid choice!");
        }
        
    }
    public static void viewUsers() {
        String message = "ALL USERS \n\n";
        
        for (int i = 0; i < usernames.size(); i++) {
            message += "Username: " + usernames.get(i) + "\n";
            message += "Role: " + roles.get(i) + "\n";
            message += "-------------------\n";
        }
        
        JOptionPane.showMessageDialog(null, message);
    }
    
    public static void addUser() {
        String newUser = JOptionPane.showInputDialog("New username:");
        String newPin = JOptionPane.showInputDialog("New PIN:");
        String newRole = JOptionPane.showInputDialog("Role (admin/staff/visitor):");
        
        // Add to arrays
        usernames.add(newUser);
        pins.add(newPin);
        roles.add(newRole);
        
        // Save to file
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("users.txt", true));
            writer.println(newUser + "," + newPin + "," + newRole);
            writer.close();
            JOptionPane.showMessageDialog(null, "User added and saved to file!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving: " + e.getMessage());
        }
        adminMenu(); // to return to admin menu

    }
}
