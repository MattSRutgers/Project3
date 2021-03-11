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

public class PayrollController {
	
	@FXML
    private Button addEmp;
	
	@FXML
	private ToggleGroup department, empLevel, managerCode;

    @FXML
    private TextField empName, empDepartmentCode, partTimePay, partTimeHours, salary;
    
    @FXML
    private DatePicker hireDate;

    @FXML
    private TextArea textArea;
    
    @FXML
    private HBox managerHBox;
    
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
    		//valid = false;
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
    	RadioButton mgmtSelected = (RadioButton) managerCode.getSelectedToggle();
    	String mgmtCode = mgmtSelected.getText();
    	double pay = Double.parseDouble(salary.getText());
    	textArea.appendText("Added " + name + deptCode + hireDate + pay + mgmtCode +"\n");
    	
    }
    

    void addFullTime(String name, String deptCode, String hireDate) {
    	double pay = Double.parseDouble(salary.getText());
    	textArea.appendText("Added " + name + deptCode + hireDate + pay +"\n");
    }
    

    void addPartTime(String name, String deptCode, String hireDate) {
    	double pay = Double.parseDouble(partTimePay.getText());
    	textArea.appendText("Added " + name + deptCode + hireDate + pay+"\n");
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
