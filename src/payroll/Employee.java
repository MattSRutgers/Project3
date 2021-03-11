package payroll;

import java.text.DecimalFormat;

/**
 * The Employee class defines the common data and operations for all employee
 * type; each employee has a profile that uniquely identifies the employee.
 * @author Matthew Schilling and Gordon Miller
 */
public class Employee {
    //private final String FIST_NAME;
    //private final String LAST_NAME;
	private final double DEFAULT_PAYMENT_DUE = 0.0;
    private final String EMPLOYEE_NAME;
    private final String DEPARTMENT_CODE;
    private final Date HIRE_DATE;
    private double paymentDue;
    private final Profile employeeProfile;

    /**
     * The 3 param constructor will be called when an employee is added
     * @param employeeName
     * @param deptCode employees department code
     * @param hireDate employees date hired
     */
    public Employee(String employeeName, String deptCode, Date hireDate){
        this.EMPLOYEE_NAME = employeeName;
        this.DEPARTMENT_CODE = deptCode;
        this.HIRE_DATE = hireDate;
        this.employeeProfile = new Profile(employeeName, deptCode, hireDate);
        this.paymentDue = DEFAULT_PAYMENT_DUE;
    }

    /**
     * The toString method formats the employees data neatly and returns it as
     * one string object
     * @return a string containing the employees details.
     */
    @Override
    public String toString(){
    	this.calculatePayment();
        String empDetails = EMPLOYEE_NAME + "::" + DEPARTMENT_CODE
        		+ "::" + HIRE_DATE + "::Payment $" +
        		doubleToDollar(this.paymentDue);
        return empDetails;
    }

    /**
     * The equals method compares two employee profiles to determine if they
     * match
     * @param obj the employee we are looking for
     * @return true if this employee is a match, false otherwise
     */
    @Override
    public boolean equals(Object obj){
    	Employee object = (Employee) obj;
    	if(this.employeeProfile.equals(object.employeeProfile))
    		return true;

    	return false;
    }

    /**
	 * The calulatePayment method calculates the payment for this employee in
	 * the current payment period
	 */
	public void calculatePayment() {
	}

	/**
	 * @return the payment value for an employee in the current payment
	 * period
	 */
	public double getPayment() {
		this.calculatePayment();
		return this.paymentDue;
	}

	/**
	 * Sets the payment value for an employee in the current payment period
	 * @param paymentDue value which paymentDue will be set to
	 */
	public void setPayment(double paymentDue) {
		this.paymentDue = paymentDue;
	}

	/**
	 * @return the employee profile
	 */
	public Profile getProfile() {
		return employeeProfile;
	}

	/**
	 * The doubleToDollar method is a helper method which converts a given
	 * double to a string that matches US dollar conventions
	 * @param value the double value to be converted
	 * @return a string that is in US dollar conventions
	 */
	public static String doubleToDollar(double value) {
		if(value == 0) {
			return "0.00";
		}
		DecimalFormat dollarFormatter = new DecimalFormat("#.00");
    	dollarFormatter.setGroupingUsed(true);
    	dollarFormatter.setGroupingSize(3);
    	String valueStr = dollarFormatter.format(value);
    	return valueStr;
	}

	/**
	 * @return an employee's department code
	 */
    public String getDepartment(){
        return this.DEPARTMENT_CODE;
    }

    /**
     * @return an employee's hire date
     */
    public Date getDate(){
        return this.HIRE_DATE;
    }

}
