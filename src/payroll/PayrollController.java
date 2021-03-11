package payroll;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
//import project2.Company;

public class PayrollController {
	
	@FXML
    private Button addEmp, remove, setHours, tempPrint;
	
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
    		double temp = Double .parseDouble(salary.getText());
    		if(temp != 0)
    			valid = true;
    		if(temp == 0)
    			textArea.appendText("Salary can not be 0\n");
    	}
    	catch(NumberFormatException e) {
    		textArea.appendText("Invaild salary, please enter numbers above 0 only.\n");
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
    		textArea.appendText("Invaild payrate, please enter numbers above 0 only.\n");
    	}
    	return valid;
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
    	int code =0;
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
    	textArea.appendText("Added " + " " + name + " " + deptCode + " " + hireDate + " " + pay + mgmtCode +"\n");
    	clearFields();
    }
    

    void addFullTime(String name, String deptCode, String hireDate) {
    	double pay = Double.parseDouble(salary.getText());
    	textArea.appendText("Added " + name + deptCode + hireDate + pay +"\n");
    	Date date = new Date(hireDate);
    	Employee newFullTimer = new Fulltime(name, deptCode, date, pay);
    	this.company.add(newFullTimer);
    	clearFields();
    }
    

    void addPartTime(String name, String deptCode, String hireDate) {
    	double pay = Double.parseDouble(partTimePay.getText());
    	Date date = new Date(hireDate);
    	Employee newPartTimer = new Parttime(name, deptCode, date, pay);
    	this.company.add(newPartTimer);
    	textArea.appendText("Added " + name + deptCode + hireDate + pay+"\n");
    	clearFields();
    }
    
    
    @FXML
    void printTemp() {
    	if ( company.checkEmpty() ){
            textArea.appendText("Employee database is empty\n");
            return;
        }
        company.print();
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
}
