package ucf.cot.solver.process;

import java.sql.Time;
import java.util.Date;

public class SATSolverProcess{
	
	private String inpFileName="";
	
	
	
	public String getInpFileName() {
		return inpFileName;
	}



	public void setInpFileName(String inpFileName) {
		this.inpFileName = inpFileName;
	}



	public void implementSATSolver(){
		System.out.println("Implementing SAT Solver...");
		SATSolverProcessImplementation satSolverProcess = new SATSolverProcessImplementation();
		satSolverProcess.setInpFileName(getInpFileName());
		satSolverProcess.readCNFFile();//Reading the *.cnf files
		satSolverProcess.printClauseList();//Printing the Clause List
//		Date date = new Date();
		
		long bTime1 = new Date().getTime();
		long aTime1;
		long spentTime;
//		if(satSolverProcess.getNumberOfVariable()<=20){
//			satSolverProcess.exploreAllPossibleCombination();// Creating all Possible Combinations for all Variables
//			satSolverProcess.populateVariableIndexMap();// Populating a Map for the ease of retriving variables values
////			satSolverProcess.printAllPossibleCombination();// Printing all Possible Combinations for all Variables
//			satSolverProcess.satisfyClauses();//Satisfying all Clauses with Different Combinations
//			aTime1= new Date().getTime();
//			
//		}
//		else{
//		System.out.println("BEFORE UNIT PROPAGATION");
//			satSolverProcess.printClauseList();
		satSolverProcess.initializeUpdatedClauseList();
			satSolverProcess.propagateUnitClauses();// Propagating Unit Clauses
//			satSolverProcess.printUpdatedClauseList();//Printing the Updated Clause List
//			System.out.println("BEFORE BCP");
//			satSolverProcess.printClauseList();
			satSolverProcess.implementBCP();// Calling Binary constrains propagation
//			System.out.println("AFTER BCP");
//			satSolverProcess.printClauseList();
//			satSolverProcess.printVariableAssignments(); //Printing the Status of Variable Assignment
//			satSolverProcess.printSatisfiability(); //Printing whether Satisfiable or not
			//satSolverProcess.printUpdatedClauseList();//Printing the updated clause list
			//satSolverProcess.printSatisfiability(); //Printing whether Satisfiable or not
			
			
//			while(satSolverProcess.checkSatisfiability()){//So conflict occurs 
//				satSolverProcess.assignVariablesAndLearning();// Assgining new variables and learing the clause sets
//			}
			aTime1= new Date().getTime();
//		}
		satSolverProcess.printVariableAssignments(); //Printing the Status of Variable Assignment
		satSolverProcess.printSatisfiability(); //Printing whether Satisfiable or not
		spentTime=(aTime1-bTime1);
		System.out.println("The required time: "+(spentTime)+" miliseconds");
	
		satSolverProcess.writeResultToFile(spentTime);// Writting the result to a file
		
	}

}

	
