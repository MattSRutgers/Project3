package payroll;

/**
* The Parttime class extends the Employee class and includes specific data and
* operations to a part-time employee.
* @author Matthew Schilling and Gordon Miller
*/
public class Parttime extends Employee {
	private double hourlyRate;
	private int hoursWorked;
	private final int OVERTIME = 80;
	private final double OVERTIMEPAY = 1.5;

	/**
     * The 4 param constructor will be called when a part time employee is added
     * @param employeeName
     * @param deptCode employees department code
     * @param hireDate employees date hired
     * @param rate employee's hourly rate
     */
	public Parttime(String employeeName, String deptCode, Date hireDate,
			double rate) {
		super(employeeName, deptCode, hireDate);
		this.hourlyRate = rate;
	}

	/**
	 * @return an employee's total hours for the current payment period
	 */
	public int getHours() {
		return this.hoursWorked;
	}

	/**
	 * @param hours value which hoursWorked will be set to
	 */
	public void setHours(int hours) {
		this.hoursWorked = hours;
	}

	/**
	 * The calulatePayment method calculates the payment for a part time
	 * employee in the current payment period
	 */
	@Override
	public void calculatePayment() {
		double pay;
		if(this.hoursWorked <= OVERTIME) {
			pay = this.hoursWorked * this.hourlyRate;
		}
		else {
			pay = (OVERTIME * this.hourlyRate) + (((hoursWorked - OVERTIME) *
					this.hourlyRate) * OVERTIMEPAY);
		}
		super.setPayment(pay);
	}

	 /**
     * The toString method formats the employees data neatly and returns it as
     * one string object
     * @return a string containing the employees details.
     */
    @Override
    public String toString(){
		this.calculatePayment();
        String empDetails = super.toString() + "::PART TIME::Hourly Rate $" +
        		doubleToDollar(this.hourlyRate) + "::Hours worked this period: "
        		+ hoursWorked;
        return empDetails;
    }

    /**
     * The equals method compares two full time employee profiles to determine
     * if they match
     * @param obj the employee we are looking for
     * @return true if this employee is a match, false otherwise
     */
    @Override
    public boolean equals(Object obj){
    	Parttime object = (Parttime) obj;
    	if(this.getProfile().equals(object.getProfile()))
    		return true;

    	return false;
    }
}