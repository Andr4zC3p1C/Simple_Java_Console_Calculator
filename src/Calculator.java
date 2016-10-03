import java.util.Scanner;

public class Calculator {

	private String calc;
	private Scanner scan;
	
	private int[] opers;
	private double[] nums;
	
	private int nextOper = 0;
	
	public static final int ADD = 0;
	public static final int SUB = 1;
	public static final int MULTI = 2;
	public static final int DIVIDE = 3;
	
	public Calculator(){
		scan = new Scanner(System.in);
		
        System.out.println("\n");
		System.out.println("Very Basic Java Calculator\n----------------------------------------------------------------------\nTo exit write '/exit' anytime");
		System.out.println("\nYou must put spaces betwen operators & numbers e.g. '6 + 7 * 2' !!!");
		System.out.println("Supported operations: +, -, *, /");
		
		while(true){
			System.out.println("\nEnter a calculation:");
			calc = scan.nextLine();
			
			// checking for exit
			if(calc.equals("/exit")){
				System.out.println("Exitting the program . . .");
				break;
			}
			
			parseCalc(calc);
			double result = calculate();
			System.out.println("Result = " + result);
		}
	}
	
	private void parseCalc(String calc){
		calc.trim();
		String[] calcOrder = calc.split(" ");
		int calcLength = calcOrder.length;

		// initializing operations
		int operCount = 0;
		for(int i=0; i < calcLength; i++){
			String el = calcOrder[i];
			
			switch(el){
			case "+":
			case "-":
			case "*":
			case "/": 
				operCount++;
			}
		}
		
		opers = new int[operCount];
		nums = new double[calcLength - operCount];
		int operC = 0;
		int numC = 0;
		for(int i=0; i < calcLength; i++){
			String el = calcOrder[i];
			
			switch(el){
			case "+":
				opers[operC] = ADD;
				operC++;
				break;
				
			case "-":
				opers[operC] = SUB;
				operC++;
				break;
				
			case "*":
				opers[operC] = MULTI;
				operC++;
				break;
				
			case "/":
				opers[operC] = DIVIDE;
				operC++;
				break;
				
			default:
				// A number
				nums[numC] = Double.valueOf(el);
				numC++;
			}
		}
	}
	
	private double calculate(){
		double result = nums[0];
		
		nextOper = 0;
		for(int i=0; i < opers.length; i++){
			if(i == nextOper){
				if(opers[i] == ADD){
					double right = priorities(i+1);
					result += right;
					nextOper++;
				}else if(opers[i] == SUB){
					double right = priorities(i+1);
					result -= right;
					nextOper++;
				}else if(opers[i] == MULTI){
					double right = priorities(i+1);
					result *= right;
					nextOper++;
				}else if(opers[i] == DIVIDE){
					double right = nums[i+1];
					result /= right;
					nextOper++;
				}
			}
		}
		
		return result;
	}
	
	private double priorities(int i){
		double p = nums[i];
		
		// if out of bounds
		if(i == opers.length || i == nums.length){
			return p;
		}
		
		if(opers[i] == MULTI){
			double right = priorities(i+1);
			p *= right;
			nextOper++;
		}else if(opers[i] == DIVIDE){
			double right = nums[i+1];
			p /= right;
			nextOper++;
		}
		
		return p;
	}
	
	public static void main(String[] args) {
		new Calculator();
	}

}
