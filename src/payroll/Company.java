package payroll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Company class uses an array as a container to manage an employee database
 * @author Matthew Schilling and Gordon Miller
 * 
 */
public class Company {
    private Employee[] empList;
    private int numEmployee;
    private static final int GROW_SIZE = 4;
    private static final int EMPLOYEE_NOT_FOUND = -1;
    
    /**
     * The default constructor for the Company class creates a new array of
     * employee class objects and assigns it to the emplist variable.
     */
    public Company (){
        empList = new Employee[GROW_SIZE];
        numEmployee = 0;
    }
    
    /**
     * The find employee method searches for a given employee in the list. It 
     * assumes the employee is not in the list, and only updates its return
     * if the employee is found.
     * @param employee the employee we are looking for
     * @return the index of the employee in the list, or -1 if not found
     */
    private int find (Employee employee) {
        int index = EMPLOYEE_NOT_FOUND;
        Profile findEmployee = employee.getProfile();
        for (int i = 0; i < numEmployee; i++){
            Profile checkThisEmployee = empList[i].getProfile();
            if (checkThisEmployee.equals(findEmployee))
                index = i;
        }
        return index;
    }
    
    /**
     * The grow method is automatically called when the employee list is full
     */
    private void grow(){
        int newLength = empList.length + GROW_SIZE;
        Employee[] tempBag = new Employee[newLength];
        for(int i = 0; i < empList.length; i++){
        	tempBag[i] = empList[i];
        }
        empList = tempBag;
    }
    
    /**
     * The add method first checks the list to make sure employee doesn't 
     * already exist, then adds them if they don't exist.
     * @param employee the employee to be added
     * @return true if successfully added, false if they already existed.
     */
    public boolean add(Employee employee){
        int checkExists = find(employee);
        if(checkExists != EMPLOYEE_NOT_FOUND)
            return false;
        if(empList.length == numEmployee)
            grow();
        empList[numEmployee] = employee;
        numEmployee += 1;
        return true;
    }
    
    /**
     * The remove method searches the employee list and removes the given 
     * employee if found
     * @param employee the employee we are looking for to remove
     * @return true if found and removed, false if they did not exist.
     */
    public boolean remove(Employee employee){
        int getIndex = find(employee);
        if(getIndex == -1)
            return false;
        Employee[] tempBag = new Employee[empList.length - 1];
        for(int i = 0, j = 0; i < empList.length; i++){
            if(i != getIndex)
                tempBag[j++] = empList[i];
        }
        empList = tempBag;
        numEmployee -= 1;
    	return true;
    }
    
    /**
     * The setHours method sets the hours worked for a part time employee
     * @param employee the employee who's hours we are adjusting
     * @return true if successfully found and updated. False otherwise
     */
    public boolean setHours(Employee employee){
        int getIndex = find(employee);
        if(getIndex == -1){
            return false;
        }
        int workedHours = ((Parttime)employee).getHours();
        ((Parttime)empList[getIndex]).setHours(workedHours);
        return true;
    }
    
    /**
     * The processPayments method loops through the list of employees and 
     * calculates how much they should be paid this period.
     */
    public void processPayments(){
        for (int i = 0; i < numEmployee; i++){
            empList[i].calculatePayment();
        }
    } //process payments for all employees
    
    /**
     * The print method loops through the employee list and prints info for
     * each employee using the toString method.
     */
    public String print(){
        String output = "--Printing earning statements for all employees--\n";
        for (int i = 0; i < numEmployee; i++){
            output += empList[i].toString() + "\n";
        } 
        return output;
    }
    
    /**
     * The printByDepartment method loops through the employee list and sorts
     * by the department code, and then prints all employees.
     */
    public String printByDepartment(){
    	String output = "";
    	Employee tempEmp;
        for(int i = 0; i < empList.length; i++){
            for(int j = 1; j < numEmployee; j++){
                Employee empOne = ((Employee)empList[j-1]);
                Employee empTwo = ((Employee)empList[j]);
                String empOneDeptCode = empOne.getDepartment();
                String empTwoDeptCode = empTwo.getDepartment();
                if(empTwoDeptCode.compareToIgnoreCase(empOneDeptCode)<0){
                    tempEmp = empList[j-1];
                    empList[j-1] = empList[j];
                    empList[j] = tempEmp;
                }
            }
        }
        output += "--Printing earning statements by department--\n";
        for (int i = 0; i < numEmployee; i++){
            output += empList[i].toString() + "\n";
        }
        return output;
    }
    
    /**
     * The printByDate method sorts the employee list by their hire date then
     * prints the list of employees using the .toString() method
     */
    public String printByDate(){           
    	String output = "";
    	Employee tempEmp;
        for( int i = 0; i < empList.length; i++ ){
            for( int j = 1; j<numEmployee; j++ ){
                Date empOneDate = empList[j-1].getDate();
                Date empTwoDate = empList[j].getDate();
                if(empOneDate.compareTo(empTwoDate) < 0 ){
                    tempEmp = empList[j-1];
                    empList[j-1] = empList[j];
                    empList[j] = tempEmp;
                }
            }
        }
        output += "--Printing earning statements by date hired--\n";
        for (int i = 0; i < numEmployee; i++){
            output += empList[i].toString() + "\n";
        }
        return output;
    }
    public boolean checkEmpty(){
        return numEmployee == 0;
    }
    
    void exportDatabase() {
    	try {
    		File exportData = new File("storedData.txt");
    		exportData.createNewFile();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	try {
    		FileWriter writer = new FileWriter("storedData.txt");
    		for (int i = 0; i < numEmployee; i++) {
    			writer.write(empList[i].toString() + "\n");
    		}
    		writer.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
   	}
}