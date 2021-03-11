package payroll;

/**
* The Management class extends the Fulltime class and includes specific data and
* operations to a full-time employee with a management role.
* @author Matthew Schilling and Gordon Miller
*/
public class Management extends Fulltime {
	private int position;
	private final String[] POSITION_TITLE = {"Manager", "Department Head",
			"Director"};
	private final double[] COMPENSATION = {5000, 9500, 12000};
	private final int NUM_PAY_PERIODS = 26;

	/**
     * The 5 param constructor will be called when a full time employee with a
     * management position is added
     * @param employeeName
     * @param deptCode employees department code
     * @param hireDate employees date hired
     * @param salary employee's annual salary
     * @param position an Integer value correlated to one of 3 management
     * positions
     */
	public Management(String employeeName, String deptCode, Date hireDate,
			double salary, int position) {
		super(employeeName, deptCode, hireDate, salary);
		this.position = position;
	}

	/**
	 * The calulatePayment method calculates the payment for a full time
	 * employee with a management position in the current payment period
	 */
	@Override
	public void calculatePayment() {
		double pay = super.getAnnualSalary() / NUM_PAY_PERIODS;
		pay += this.COMPENSATION[position] / NUM_PAY_PERIODS;
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
        String empDetails = super.toString() + "::" + POSITION_TITLE[position] +
        		" compensation $" + doubleToDollar(this.COMPENSATION[position]);
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
    	Management object = (Management) obj;
    	if(this.getProfile().equals(object.getProfile()))
    		return true;

    	return false;
    }

}