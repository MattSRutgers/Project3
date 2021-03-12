package payroll;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class PayrollController {
	private FileChooser fileChooser = new FileChooser();
	
	@FXML
    private Button addEmp, remove, setHours, tempPrint, importFile, exportFile, byDate, 
    byDept;
	
	@FXML
	private ToggleGroup department, empLevel, managerCode;

    @FXML
    private TextField empName, partTimePay, partTimeHours, salary;
    
    @FXML
    private DatePicker hireDate;

    @FXML
    private TextArea textArea;
    
    @FXML
    private HBox managerHBox;
    
    Company company = new Company();
    int mgt = 0;
    int dptHead = 1;
    int director = 2;
    
    @FXML
    private boolean checkName() {
    	boolean valid = true;
    	if(empName.getText().trim().isEmpty()) {
    		textArea.appendText("Please enter a name\n");
    		valid = false;
    	}
    	return valid;
    }


    private boolean checkDate() {
    	LocalDate tempDate = this.hireDate.getValue();
    	if (tempDate == null) {
    		textArea.appendText("Please enter a date\n");
    		return false;
    	}
    	return true;
    }
    
    private String getDate() {
    	LocalDate tempDate = this.hireDate.getValue();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    	String dateString = tempDate.format(formatter);
    	return dateString;
    }
    
    @FXML
    private boolean checkSalary() {
    	boolean valid = false;
    	try {
    		double temp = Double.parseDouble(salary.getText());
    		if(temp != 0)
    			valid = true;
    		if(temp == 0)
    			textArea.appendText("Salary can not be 0\n");
    	}
    	catch(NumberFormatException e) {
    		textArea.appendText("Invalid salary, please enter numbers above 0 only.\n");
    	}
    	return valid;
    }
    
    
    @FXML
    private boolean checkPayrate() {
    	boolean valid = false;
    	try {
    		double temp = Double .parseDouble(partTimePay.getText());
    		if(temp != 0)
    			valid = true;
    		if(temp == 0)
    			textArea.appendText("payrate can not be 0\n");
    	}
    	catch(NumberFormatException e) {
    		textArea.appendText("Invalid payrate, please enter numbers above 0 only.\n");
    	}
    	return valid;
    }
    
    @FXML
    void setHours() {
    	String dateString = null;
    	if(checkName()) {
    		if(checkDate()) {
    			if(checkHours()) {
    			dateString = this.getDate();
		    	RadioButton selectedDept = (RadioButton) department.getSelectedToggle();
		    	String deptCode = selectedDept.getText();
		    	String name = empName.getText();
		    	Date date = new Date(dateString);
		    	int hoursWorked = Integer.parseInt(partTimeHours.getText());
		    	if (company.checkEmpty()) {
		    		textArea.appendText("Employee database is empty.\n");
		    		return;
		    	}
		    	else if(hoursWorked < 0 || hoursWorked >100){
		    		textArea.appendText("Invalid Hours.\n");
		    		return;
		    	}
		    	else {
		    		Parttime updateHours = new Parttime(name,
		    				deptCode, date, hoursWorked);
		    		updateHours.setHours(hoursWorked);
		    		company.setHours(updateHours);
		    		textArea.appendText("Employee hours set.\n");
		    		clearFields();
		    	}
		    	return;
    			}
    		}
    	}
    }
    
    private boolean checkHours() {
    	boolean valid = false;
    	try {
    		double temp = Double .parseDouble(partTimeHours.getText());
    		if(temp != 0)
    			valid = true;
    		if(temp == 0)
    			textArea.appendText("Hours worked can not be 0\n");
    	}
    	catch(NumberFormatException e) {
    		textArea.appendText("Invalid hours, please enter numbers above 0 only.\n");
    	}
    	return valid;
    }
    
    
    @FXML
    void remove() {
    	String dateString = null;
    	if(checkName()) {
    		if(checkDate()) {
    			dateString = this.getDate();
		    	RadioButton selectedDept = (RadioButton) department.getSelectedToggle();
		    	String deptCode = selectedDept.getText();
		    	String name = empName.getText();
		    	Date date = new Date(dateString);
		    	Employee removeEmployee = new Employee(name,
                deptCode, date);
        if(company.remove(removeEmployee))
        	textArea.appendText("Employee removed.\n");
        else
        	textArea.appendText("Employee does not exist.\n");
    		}
    	}
    	return;
    }
    
    
    @FXML
    void addEmployee() {
    	String dateString = null;
    	if(checkName()) {
    		if(checkDate()) {
    			dateString = this.getDate();
		    	RadioButton selectedDept = (RadioButton) department.getSelectedToggle();
		    	String deptCode = selectedDept.getText();
		    	String name = empName.getText();
		    	RadioButton selectedLevel = (RadioButton) empLevel.getSelectedToggle();
		    	String employeeLevel = selectedLevel.getText();
		    	if(employeeLevel.equals("Part Time")) {
		    		if(checkPayrate())
		    			addPartTime(name, deptCode, dateString);
		    	}
		    	else if(employeeLevel.equals("Full Time")) {
		    		if(checkSalary())
		    			addFullTime(name, deptCode, dateString);
		    	}
		    	else if(employeeLevel.equals("Manager")) {
		    		if(checkSalary())
		    			addManager(name, deptCode, dateString);
		    	}
	    	}
    	}
    }
    

    void addManager(String name, String deptCode, String hireDate) {
    	int code = 0;
    	RadioButton mgmtSelected = (RadioButton) managerCode.getSelectedToggle();
    	String mgmtCode = mgmtSelected.getText();
    	if(mgmtCode.equals("Manager"))
    		code = mgt;
    	if(mgmtCode.equals("Department Head"))
    		code = dptHead;
    	if(mgmtCode.equals("Director"))
    		code = director;
    	double pay = Double.parseDouble(salary.getText());
    	Date date = new Date(hireDate);
    	
    	Employee newManager = new Management(name, deptCode, date, pay, code);
    	this.company.add(newManager);
    	textArea.appendText("Employee added\n");
    	clearFields();
    }
    

    void addFullTime(String name, String deptCode, String hireDate) {
    	double pay = Double.parseDouble(salary.getText());
    	Date date = new Date(hireDate);
    	Employee newFullTimer = new Fulltime(name, deptCode, date, pay);
    	this.company.add(newFullTimer);
    	textArea.appendText("Employee added\n");
    	clearFields();
    }
    

    void addPartTime(String name, String deptCode, String hireDate) {
    	double pay = Double.parseDouble(partTimePay.getText());
    	Date date = new Date(hireDate);
    	Employee newPartTimer = new Parttime(name, deptCode, date, pay);
    	this.company.add(newPartTimer);
    	textArea.appendText("Employee added\n");
    	clearFields();
    }
    
    
    @FXML
    void printTemp() {
    	if ( company.checkEmpty() ){
            textArea.appendText("Employee database is empty\n");
            return;
        }
    	textArea.appendText(company.print());
    }
    
    @FXML
    void printByDate() {
    	if ( company.checkEmpty() ){
            textArea.appendText("Employee database is empty\n");
            return;
        }
    	textArea.appendText(company.printByDate());
    }
    
    @FXML
    void printByDept() {
    	if ( company.checkEmpty() ){
            textArea.appendText("Employee database is empty\n");
            return;
        }
    	textArea.appendText(company.printByDepartment());
    }
    
    void clearFields() {
    	empName.clear();
    	this.hireDate.getEditor().clear();
    	this.partTimeHours.clear();
    	this.partTimePay.clear();
    	this.salary.clear();
    	
    }
    
    
    @FXML
    void limitToFullTime() {
    	salary.setDisable(false);
    	partTimePay.setDisable(true);
    	partTimeHours.setDisable(true);
    	managerHBox.setDisable(true);
    	
    }
    
    
    @FXML
    void limitToPartTime() {
    	salary.setDisable(true);
    	partTimePay.setDisable(false);
    	partTimeHours.setDisable(false);
    	managerHBox.setDisable(true);
    }
    
    
    @FXML
    void limitToManager() {
    	salary.setDisable(false);
    	partTimePay.setDisable(true);
    	partTimeHours.setDisable(true);
    	managerHBox.setDisable(false);
   }
    
    @FXML
    void importDatabase() {
    	Stage stage = new Stage();
    	fileChooser.setTitle("Import New Data");
    	File selectedFile = fileChooser.showOpenDialog(stage);
    	if (selectedFile != null) { 
	    	try {
	    	      Scanner myReader = new Scanner(selectedFile);
	    	      while (myReader.hasNextLine()) {
	    	        String data = myReader.nextLine();
	    	        company.add(dataToEmployee(data));
	    	      }
	    	      myReader.close();
	    	    } catch (FileNotFoundException e) {
	    	      textArea.appendText("File Not Found.");
	    	      e.printStackTrace();
	    	    }
	     	textArea.appendText("File Imported\n");
    	}
    }
    
    @FXML
    void exportDatabase() {
    	company.exportDatabase();
    	textArea.appendText("File Exported as storedData.txt \n");
   	}
    
    private static Employee dataToEmployee(String data) {
    	String employeeInfo[] = data.split(",");
    	String name = employeeInfo[1];
		String deptCode = employeeInfo[2];
		Date hireDate = new Date(employeeInfo[3]);
		Double rate = Double.parseDouble(employeeInfo[4]);
		Employee temp = new Employee(name, deptCode, hireDate);
    	if(employeeInfo[0].equals("P")) {
    		temp = new Parttime(name, deptCode, hireDate, rate);
    	}
    	else if(employeeInfo[0].equals("F")) {
    		temp = new Fulltime(name, deptCode, hireDate, rate);
    	}
    	else if(employeeInfo[0].equals("M")){
    		int position = Integer.parseInt(employeeInfo[5]);
    		temp = new Management(name, deptCode, hireDate, rate, (position-1));
    	}
    	return temp;
    }
}