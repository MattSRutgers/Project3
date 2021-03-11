package payroll;

/**
* The Fulltime class extends the Employee class and includes specific data and
* operations to a full-time employee.
* @author Matthew Schilling and Gordon Miller
*/
public class Fulltime extends Employee {
	private double annualSalary;
	private final int NUM_PAY_PERIODS = 26;

	/**
     * The 4 param constructor will be called when a full time employee is added
     * @param employeeName
     * @param deptCode employees department code
     * @param hireDate employees date hired
     * @param salary employee's annual salary
     */
	public Fulltime(String employeeName, String deptCode, Date hireDate,
			double salary) {
		super(employeeName, deptCode, hireDate);
		this.annualSalary = salary;
	}

	/**
	 * @return an employee's annual salary
	 */
	public double getAnnualSalary() {
		return annualSalary;
	}

	/**
	 * @param annualSalary value which annualSalary will be set to
	 */
	public void setAnnualSalary(double annualSalary) {
		this.annualSalary = annualSalary;
	}

	/**
	 * The calulatePayment method calculates the payment for a full time
	 * employee in the current payment period
	 */
	public void calculatePayment() {
		double pay = this.annualSalary / NUM_PAY_PERIODS;
		super.setPayment(pay);
	}

	 /**
     * The toString method formats the employees data neatly and returns it as
     * one string object
     * @return a string containing the employees details.
     */
    @Override
    public String toString(){
        String empDetails = super.toString() + "::FULL TIME::Annual Salary "
        		+ doubleToDollar(this.annualSalary);
        return empDetails;
    }

    /**
     * The equals method compares two full time employee profiles to determine if they
     * match
     * @param obj the employee we are looking for
     * @return true if this employee is a match, false otherwise
     */
    @Override
    public boolean equals(Object obj){
    	Fulltime object = (Fulltime) obj;
    	if(this.getProfile().equals(object.getProfile()))
    		return true;
    	return false;
    }

}
