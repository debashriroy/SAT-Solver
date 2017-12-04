package ucf.cot.solver.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import com.sun.org.apache.xerces.internal.impl.xs.SubstitutionGroupHandler;


public class SATSolverProcessImplementation {
private String inpFileName="";
private int numberOfVariable;
private int numberOfClauses;
private List<List<String>> clauseList;
private List<String> variableList;
private Map<String, Integer> variableIndexMap;// A Map with Variable Name and its Index: Map<Var_Name, index>
private Map<String, String> variableAssginmentMap;
private List<List<String>> updatedClauseList;
private List<String> watchedLiteralSet;
private List<List<String>> allPossibleCombinationOfVariables;
private List<List<String>> watchedLiteralList;
private Map<String,String> decisionVariableMap;
	
	
	
	public String getInpFileName() {
		return inpFileName;
	}



	public void setInpFileName(String inpFileName) {
		this.inpFileName = inpFileName;
	}
	
	public int getNumberOfVariable() {
		return numberOfVariable;
	}



	public void setNumberOfVariable(int numberOfVariable) {
		this.numberOfVariable = numberOfVariable;
	}



	public int getNumberOfClauses() {
		return numberOfClauses;
	}



	public void setNumberOfClauses(int numberOfClauses) {
		this.numberOfClauses = numberOfClauses;
	}



	public List<List<String>> getClauseList() {
		return clauseList;
	}



	public void setClauseList(List<List<String>> clauseList) {
		this.clauseList = clauseList;
	}



	public List<String> getVariableList() {
		return variableList;
	}



	public void setVariableList(List<String> variableList) {
		this.variableList = variableList;
	}



	public Map<String, Integer> getVariableIndexMap() {
		return variableIndexMap;
	}



	public void setVariableIndexList(Map<String, Integer> variableIndexMap) {
		this.variableIndexMap = variableIndexMap;
	}



	public Map<String, String> getVariableAssginmentMap() {
		return variableAssginmentMap;
	}



	public void setVariableAssginmentMap(Map<String, String> variableAssginmentMap) {
		this.variableAssginmentMap = variableAssginmentMap;
	}



	public List<List<String>> getUpdatedClauseList() {
		return updatedClauseList;
	}



	public void setUpdatedClauseList(List<List<String>> updatedClauseList) {
		this.updatedClauseList = updatedClauseList;
	}



	public List<String> getWatchedLiteralSet() {
		return watchedLiteralSet;
	}



	public void setWatchedLiteralSet(List<String> watchedLiteralSet) {
		this.watchedLiteralSet = watchedLiteralSet;
	}



	public List<List<String>> getAllPossibleCombinationOfVariables() {
		return allPossibleCombinationOfVariables;
	}



	public void setAllPossibleCombinationOfVariables(
			List<List<String>> allPossibleCombinationOfVariables) {
		this.allPossibleCombinationOfVariables = allPossibleCombinationOfVariables;
	}



	public List<List<String>> getWatchedClauseList() {
		return watchedLiteralList;
	}



	public void setWatchedClauseList(List<List<String>> watchedClauseList) {
		this.watchedLiteralList = watchedClauseList;
	}



	public Map<String, String> getDecisionMap() {
		return decisionVariableMap;
	}



	public void setDecisionMap(Map<String, String> decisionMap) {
		this.decisionVariableMap = decisionMap;
	}



	public void readCNFFile(){
		System.out.println("Reading the .cnf File...");
		clauseList= new ArrayList<List<String>>();
		variableList = new ArrayList<String>();
		variableAssginmentMap = new HashMap<String, String>();
		try{
		FileInputStream fstream = new FileInputStream(getInpFileName());
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			strLine=strLine.trim();
			if(strLine.startsWith("c")){// Ignoring some preambles attached with file
//			System.out.println("ingoring..");
			}
			else if(strLine.startsWith("p")){//Saving number of variables and clauses
				
				System.out.println("Stores number of variables and clauses");
//				System.out.println("1111111111..."+(strLine.indexOf("cnf ")+4)+" "+strLine.lastIndexOf(" "));
//				System.out.println("2222222..."+(strLine.lastIndexOf(" ")+1)+" "+strLine.length());
				setNumberOfVariable(Integer.parseInt(strLine.substring(strLine.indexOf("cnf ")+4,strLine.lastIndexOf(" ")).trim()));
				setNumberOfClauses(Integer.parseInt(strLine.trim().substring(strLine.lastIndexOf(" ")+1, strLine.length()).trim()));
				System.out.println("Number of variable:" +  getNumberOfVariable());
				System.out.println("Number of clauses:" +  getNumberOfClauses());
			}
			else{//Saving the clause list
				if(strLine.length()>1){
//				System.out.println("Saving the clause list");
					List<String> oneClause= new ArrayList<String>();
					String brkInd=" ";
					if(strLine.contains("\t")){
						brkInd= "\t";
					}
					strLine= strLine.trim();
					while(strLine.trim().contains(brkInd)){
						strLine= strLine.trim();
					//	System.out.println("Entering Here:"+strLine);
						String var= strLine.trim().substring(0,strLine.indexOf(brkInd));	
						
						oneClause.add(var);
					//	System.out.println("VARRRR:"+var);
						strLine=strLine.substring(strLine.trim().indexOf(brkInd),strLine.trim().length());
					//	System.out.println("strLine:"+strLine.trim());
						
						//Populating the Variable List and Variable Assignment Map
						if(var.contains("-")){
							var=var.substring(1, var.length());
						}
						if(!variableList.contains(var))
							variableList.add(var);
							variableAssginmentMap.put(var, "N");
						}
					
					
				//	System.out.println("TESTIMG::"+strLine.trim());
					//oneClause.add(strLine.trim());
					
	//				for(String s: oneClause){
	//				System.out.println(" "+s);
	//				}
					clauseList.add(oneClause);
				}
			}
		}
		br.close();
		in.close();
		fstream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void populateVariableIndexMap(){
		variableIndexMap = new HashMap<String, Integer>();
		for(int ind =0;ind<variableList.size();ind++){
			variableIndexMap.put(variableList.get(ind), ind);
		}
	}
	
	public void printList(List<String> stringList){
		for(String s: stringList)
			System.out.print(" "+s);
		System.out.println();
	}
	
	// Generate all possible combination of all variables 
	public void exploreAllPossibleCombination(){
		allPossibleCombinationOfVariables= new ArrayList<List<String>>();
		List<String> oneCombination=new ArrayList<String>();
		for(int oneIndex=0;oneIndex<variableList.size();oneIndex++){
			oneCombination.add("F");
		}		
		allPossibleCombinationOfVariables.add(oneCombination);
		for(int totalIndex=1; totalIndex<Math.pow(2,variableList.size());totalIndex++){
			oneCombination=new ArrayList<String>();
			oneCombination.addAll(allPossibleCombinationOfVariables.get(totalIndex-1));
//			printList(oneCombination);
			if(allPossibleCombinationOfVariables.get(totalIndex-1).get(variableList.size()-1).equals("F")){
//				System.out.println("Entering in IFF");
//				printList(allPossibleCombinationOfVariables.get(totalIndex-1));
//				oneCombination.remove(getNumberOfVariable()-1);
				oneCombination.set(variableList.size()-1, "T");
//				oneCombination.add(getNumberOfVariable()-1, "T");
//				System.out.println("Entering in IFF222");
//				printList(oneCombination);
			}
			else{// Last element is TRUE
//				System.out.println("Entering in ELSE");
				for(int ind1=variableList.size()-2;ind1>=0;ind1--){
					if(oneCombination.get(ind1).equals("F")){
						oneCombination.set(ind1, "T");
						for(int ind2=ind1+1;ind2<variableList.size();ind2++){
							oneCombination.set(ind2, "F");
//							System.out.println("Ind1:"+ind1+" and Ind2: "+ ind2);
						}
						break;
					}
				}
//				printList(oneCombination);
				
		//		printList(allPossibleCombinationOfVariables.get(totalIndex));
			}		
			allPossibleCombinationOfVariables.add(oneCombination);		
			
		}
	}
	
	//Satisfying all Clauses with Different Combinations
	public void satisfyClauses(){
		for(int combIndex=0;combIndex<allPossibleCombinationOfVariables.size();combIndex++){
			boolean allClausesVal=true;
			boolean oneClauseVal=true;
			//For all Clauses
			for(int clauseIndex=0;clauseIndex<clauseList.size();clauseIndex++){
				String oneLitValue="";
				oneClauseVal=false;
				//For a Single Clause
				for(int oneClauseIndex=0;oneClauseIndex<clauseList.get(clauseIndex).size();oneClauseIndex++){
//					allPossibleCombinationOfVariables.get(combIndex).
					oneLitValue="";
					if(!clauseList.get(clauseIndex).get(oneClauseIndex).contains("-")){ 
						oneLitValue=allPossibleCombinationOfVariables.get(combIndex).get(variableIndexMap.get(clauseList.get(clauseIndex).get(oneClauseIndex)));
//						System.out.print(oneLitValue+" ");
					}else{
//						System.out.print(clauseList.get(clauseIndex).get(oneClauseIndex).trim().substring(1));
						//***************************************************************************
						if(allPossibleCombinationOfVariables.get(combIndex).get(variableIndexMap.get(clauseList.get(clauseIndex).get(oneClauseIndex).trim().substring(1))).equals("T")){
							oneLitValue="F";
						}else
							oneLitValue="T";
//						System.out.print(oneLitValue+" ");
//						System.out.print(allPossibleCombinationOfVariables.get(combIndex).get(Integer.parseInt(clauseList.get(clauseIndex).get(oneClauseIndex).trim().substring(1))-1)+" ");
					}
					if(oneLitValue.equals("T"))
						oneClauseVal=true; // One Clause value will be true if any of it's literal is true
				}
//				System.out.println("oneClauseVal:"+ oneClauseVal+"\n");
				if(!oneClauseVal){
//					System.out.println("*****");
					allClausesVal=false;//Total CNF file will be satisfiable if only all clause values are true(i.e. at least one literal's value is TRUE for all clauses)
				}
			}
//			System.out.println("allClausesVal:"+ allClausesVal+"\n");
			if(allClausesVal){
				for(int index=0;index<variableList.size();index++){
					variableAssginmentMap.put(variableList.get(index),allPossibleCombinationOfVariables.get(combIndex).get(index));
				}
				break; //If satisfiable for one TRUE/FALSE combination then return that result and exit from the program
			}
//			System.out.println("****FOR ANOTHER COMBINATION");
		}
	}
	
	public void printAllPossibleCombination(){
		System.out.println("The Total Combination List is (Size with "+allPossibleCombinationOfVariables.size()+"):");
		for(List<String> oneCombination: allPossibleCombinationOfVariables){
			for(String s:oneCombination){
				System.out.print(s+" ");
			}
			System.out.println();
		}
	}
	
	//Populating watched literal's list with first two literals of updated clause list
	public void populateWatchedLiteralClause(){
		watchedLiteralList = new ArrayList<List<String>>();
		int noOfwatchedList=2;
		for(List<String> oneClause: updatedClauseList){
			List<String> watchedLitsOneClause =  new ArrayList<String>();
			noOfwatchedList=(oneClause.size()==1)?1:2;
			for(int i=0; i<noOfwatchedList;i++){
				watchedLitsOneClause.add(oneClause.get(i));
			}
			watchedLiteralList.add(watchedLitsOneClause);				
		}
	}
	
	
	// GENERATE ALL IMPLICATIONS- IF ONE VARIABLE IS IMPLIED BOTH TRUE AND FALSE THEN CONFLICT OCCURS AND BACKTRACKING IS DONE BY ASSIGING 
	//THE MOST RECENT VARIABLE AS OPPOSITE VALUE
	public boolean generateImplications(String variable, String varValue){
		Map <String, String> oneImplicationVarMap = new HashMap<String, String>();
		boolean conflictFlag=false;
		for(int i=0;i<updatedClauseList.size();i++){//Iterating over each clause of the benchmark			
			if(updatedClauseList.get(i).contains(variable)){//The Clauses containing the decision variable
				if(watchedLiteralList.get(i).get(0).contains("-")){//If the literal is negation of the variable
					if(varValue=="F"){//The literal is negation and variable is assigned as FALSE so total clause is being satisfied. The total clause is being eliminated
						
						//Assigning all the other variables in that clause as FALSE, as that clause is already satisfied
						for(int otherLits=0;otherLits<updatedClauseList.get(i).size();otherLits++){
							//If literal is negation
							if(updatedClauseList.get(i).get(otherLits).contains("-")){
//								System.out.println("Variable Map Size:"+variableAssginmentMap.size() +"Variable is: "+updatedClauseList.get(i).get(otherLits));
								if(variableAssginmentMap.get(updatedClauseList.get(i).get(otherLits).substring(1)).equals("N"))
									variableAssginmentMap.put(updatedClauseList.get(i).get(otherLits).substring(1), "T");
							}else{//If the Literal is not negation
//								System.out.println("Variable Map Size:"+variableAssginmentMap.size() +"Variable is: "+updatedClauseList.get(i).get(otherLits));
								if(variableAssginmentMap.get(updatedClauseList.get(i).get(otherLits)).equals("N"))
									variableAssginmentMap.put(updatedClauseList.get(i).get(otherLits), "F");
							}
						}
						updatedClauseList.remove(i);
						watchedLiteralList.remove(i);
						i--;
						
					}else{//The literal is negation and variable is assigned as TRUE so literal's value is FALSE, the we have to search for another watched literal or implication
						//implication is possible if all the other literal of a clause is FALSE except one
						if(updatedClauseList.get(i).size()==2){
							int implicationIndex=(updatedClauseList.get(i).get(0).contains(variable))?1:0;
							//Now the literal of implicationIndex position to be true to satisfy the clause
							if(updatedClauseList.get(i).get(implicationIndex).contains("-")){
								variableAssginmentMap.put(updatedClauseList.get(i).get(implicationIndex).substring(1), "F");
								if(!oneImplicationVarMap.containsKey(updatedClauseList.get(i).get(implicationIndex).substring(1)))
									oneImplicationVarMap.put(updatedClauseList.get(i).get(implicationIndex).substring(1), "F");
								else{
									conflictFlag=true;
//									System.out.println("Conflict1:::"+conflictFlag);
									return conflictFlag;
								}
							}
							else{
								variableAssginmentMap.put(updatedClauseList.get(i).get(implicationIndex), "T");
								if(!oneImplicationVarMap.containsKey(updatedClauseList.get(i).get(implicationIndex)))
									oneImplicationVarMap.put(updatedClauseList.get(i).get(implicationIndex), "T");
								else{
									conflictFlag=true;
//									System.out.println("Conflict2:::"+conflictFlag);
									return conflictFlag;
								}
							}
						}else{// When number of literals is greater than two in updated clause list, then another watched literal is added to maintain clause invaraint
							for(int j=0;j<updatedClauseList.get(i).size();j++){
								if(updatedClauseList.get(i).get(j).equals(watchedLiteralList.get(i).get(1))&& j<updatedClauseList.get(i).size()-1){
									watchedLiteralList.get(i).add(1, updatedClauseList.get(i).get(j+1).toString());
								}
							}
						}
					}
				}else{//The literal is not negation
					if(varValue=="T"){//The variable is assigned as TRUE so total clause is being satisfied. The total clause is being eliminated
						//Assigning all the other variables in that clause as FALSE, as that clause is already satisfied
						for(int otherLits=0;otherLits<updatedClauseList.get(i).size();otherLits++){
							//If literal is negation
							if(updatedClauseList.get(i).get(otherLits).contains("-")){
								if(variableAssginmentMap.get(updatedClauseList.get(i).get(otherLits).substring(1)).equals("N"))
									variableAssginmentMap.put(updatedClauseList.get(i).get(otherLits).substring(1), "T");
							}else{//If the Literal is not negation
								if(variableAssginmentMap.get(updatedClauseList.get(i).get(otherLits)).equals("N"))
									variableAssginmentMap.put(updatedClauseList.get(i).get(otherLits), "F");
							}
						}
						updatedClauseList.remove(i);
						watchedLiteralList.remove(i);
						i--;
						
					}else{//The variable is assigned as FALSE so literal's value is FALSE, the we have to search for another watched literal or implication
						//implication is possible if all the other literal of a clause is FALSE except one
						if(updatedClauseList.get(i).size()==2){
							int implicationIndex=(updatedClauseList.get(i).get(0).contains(variable))?1:0;
							//Now the literal of implicationIndex position to be true to satisfy the clause
							if(updatedClauseList.get(i).get(implicationIndex).contains("-")){// Implication indexed literal is a negation
								variableAssginmentMap.put(updatedClauseList.get(i).get(implicationIndex).substring(1), "F");
								if(!oneImplicationVarMap.containsKey(updatedClauseList.get(i).get(implicationIndex).substring(1)))
									oneImplicationVarMap.put(updatedClauseList.get(i).get(implicationIndex).substring(1), "F");
								else{
									conflictFlag=true;
//									System.out.println("Conflict3:::"+conflictFlag);
									return conflictFlag;
								}
							}
							else{// Implication indexed literal is not negation 
								variableAssginmentMap.put(updatedClauseList.get(i).get(implicationIndex), "T");
								if(!oneImplicationVarMap.containsKey(updatedClauseList.get(i).get(implicationIndex)))
									oneImplicationVarMap.put(updatedClauseList.get(i).get(implicationIndex), "T");
								else{
									conflictFlag=true;
//									System.out.println("Conflict4:::"+conflictFlag);
									return conflictFlag;
								}
							}
						}else{// When number of literals is greater than two in updated clause list, then another watched literal is added to maintain clause invaraint
							for(int j=0;j<updatedClauseList.get(i).size();j++){
								if(updatedClauseList.get(i).get(j).equals(watchedLiteralList.get(i).get(1)) && j<updatedClauseList.get(i).size()-1){
									watchedLiteralList.get(i).add(1, updatedClauseList.get(i).get(j+1).toString());
								}
							}
						}
					}
				}
			}
		}
//		System.out.println("Conflict5:::"+conflictFlag);
		return conflictFlag;
	}
	//IMPLEMENT TOTAL BINARY CONSTRAINTS PROPAGATION
	public void implementBCP(){
		decisionVariableMap = new HashMap<String, String>();
		populateWatchedLiteralClause();
		for(int i=0; i<watchedLiteralList.size();i++){
			List<String> oneClauseWatchedLit = watchedLiteralList.get(i);
			//Assigning first watched literal as FALSE
			for(int k=0;k<oneClauseWatchedLit.size();k++){
				String variable = (oneClauseWatchedLit.get(k).contains("-"))?oneClauseWatchedLit.get(k).substring(1):oneClauseWatchedLit.get(k);
//				System.out.println("The variable::" +variable);
				decisionVariableMap.put(variable,"F");
				
				if(generateImplications(variable, "F")){//This will return True value only if conflict occurs for any decision
					decisionVariableMap.put(variable,"F");
					System.out.println("*******CONFLICT***********");
					boolean flag= generateImplications(variable, "T"); //If conflict is generated by assigning watched literal as FALSE, then backtracking is done by assigning most recent decision variable as other value, i.e. TRUE here
//					System.out.println("The secon time flag is: "+flag);
					variableAssginmentMap.put(variable, "T");
				}else{
	//				System.out.println("Entering in Else:");
					variableAssginmentMap.put(variable,"F");// Assiging FALSE as no conflict by assigning FALSE 
	//				System.out.println(",,,,,,,,,,");
				}
				//If for one pass on the set of clause list, one variable is implied both TRUE and FALSE then conflict occurs, then we have to backtrack and change most updated decision variable map 
		}
	}
		
	}
	
	public void initializeUpdatedClauseList(){
		updatedClauseList= new ArrayList<List<String>>();
		//	updatedClauseList.addAll(clauseList);
			
			for(List<String> temp1:clauseList){
				List<String> temp3 = new ArrayList<String>();
				for(String temp2:temp1)
					temp3.add(temp2);
				updatedClauseList.add(temp3);
			}
	}
	
	public void propagateUnitClauses(){
		
			
		
		watchedLiteralSet = new ArrayList<String>();
		boolean propagationExists=true;
		while(propagationExists){
			propagationExists=false;
//			System.out.println("updatedClauseList.size()..."+updatedClauseList.size());
	//		printVariableAssignments();
			for(int i=0;i<updatedClauseList.size();i++){//Iterating over each clause of the benchmark			
				if(updatedClauseList.get(i).size()==1){//The Clause is of Unit Literal
					if(updatedClauseList.get(i).get(0).contains("-")){
						String var= updatedClauseList.get(i).get(0).substring(1, updatedClauseList.get(i).get(0).length());
						variableAssginmentMap.put(var, "F");
						watchedLiteralSet.add(var);
					}else{
						variableAssginmentMap.put(updatedClauseList.get(i).get(0), "T");
						watchedLiteralSet.add(updatedClauseList.get(i).get(0));
					}
					updatedClauseList.remove(i);
					propagationExists=true;
					i--;
				}else{// More literals in a Clause
//					for(int setInd=0;setInd<watchedLiteralSet.size();setInd++){
//						String setStr=watchedLiteralSet.get(setInd);
						for(int j=0; j<updatedClauseList.get(i).size();j++ ){// Iterating within a clause over all literals 
						if(watchedLiteralSet.contains(returnVariableName(updatedClauseList.get(i).get(j)))){
							String setStr=returnVariableName(updatedClauseList.get(i).get(j));
//							System.out.println("Containing Watched Clause:"+updatedClauseList.get(i)+";Watched Literal:"+setStr+"("+variableAssginmentMap.get(setStr)+")");
							if((!updatedClauseList.get(i).get(j).contains("-") && variableAssginmentMap.get(setStr).equals("T"))||(updatedClauseList.get(i).get(j).contains("-") && variableAssginmentMap.get(setStr).equals("F"))){
//								System.out.println("Entering in if (+ and T) or (- and F):"+updatedClauseList.get(i)+" "+setStr+" "+variableAssginmentMap.get(setStr));
							//	int index= (j==1)?0:1;
							//	variableAssginmentMap.put(returnVariableName(updatedClauseList.get(i).get(index)), "T/F");
								updatedClauseList.remove(i);
								i--;
								break;
							}else if(updatedClauseList.get(i).get(j).contains("-") && variableAssginmentMap.get(setStr).equals("T")||(!updatedClauseList.get(i).get(j).contains("-") && variableAssginmentMap.get(setStr).equals("F"))){
//								System.out.println("Entering in if (- and T) or (+ and F):"+updatedClauseList.get(i)+" "+setStr+" "+variableAssginmentMap.get(setStr));
	
								if(updatedClauseList.get(i).size()==2){// Clause is of 2 literals
									int index= (j==1)?0:1;
//									System.out.println("Index..."+index);
									if(!watchedLiteralSet.contains(returnVariableName(updatedClauseList.get(i).get(index)))){
//										System.out.println("returnVariableName(updatedClauseList.get(i).get(index))..."+returnVariableName(updatedClauseList.get(i).get(index)));
										watchedLiteralSet.add(returnVariableName(updatedClauseList.get(i).get(index)));
										variableAssginmentMap.put(returnVariableName(updatedClauseList.get(i).get(index)), updatedClauseList.get(i).get(index).contains("-")?"F":"T");
//										System.out.println("Added one Watched Literal:"+returnVariableName(updatedClauseList.get(i).get(index))+" "+variableAssginmentMap.get(returnVariableName(updatedClauseList.get(i).get(index))));
										List<String> oneClause = new ArrayList<String>();
										oneClause= updatedClauseList.get(i);
										oneClause.remove(index);
										updatedClauseList.remove(i);
								//		updatedClauseList.add(i,oneClause);
										propagationExists=true;
										i--;
										break;
									}
								}else{// Clause is of more than 2 literals
//									System.out.println("Entering in else");
									List<String> oneClause = new ArrayList<String>();
									oneClause= updatedClauseList.get(i);
									oneClause.remove(j);
									updatedClauseList.remove(i);
									updatedClauseList.add(i,oneClause);
									propagationExists=true;
									i--;
									break;
								}
							}
//							if(updatedClauseList.get(i).get(j).contains("-") && variableAssginmentMap.get(setStr).equals("F")){
//								System.out.println("Entering in if - and F:"+updatedClauseList.get(i)+" "+setStr+" "+variableAssginmentMap.get(setStr));
//								updatedClauseList.remove(i);
//								i--;
//								break;
//							}else if(updatedClauseList.get(i).get(j).contains("-") && variableAssginmentMap.get(setStr).equals("T")){
//								System.out.println("Entering in if - and T:"+updatedClauseList.get(i)+" "+setStr+" "+variableAssginmentMap.get(setStr));
//	
//								if(updatedClauseList.get(i).size()==2){
//									int index= (j==1)?0:1;
//									if(!watchedLiteralSet.contains(returnVariableName(updatedClauseList.get(i).get(index)))){
//										watchedLiteralSet.add(returnVariableName(updatedClauseList.get(i).get(index)));
//										variableAssginmentMap.put(returnVariableName(updatedClauseList.get(i).get(index)), updatedClauseList.get(i).get(index).contains("-")?"F":"T");
//										System.out.println("Added one Watched Literal:"+returnVariableName(updatedClauseList.get(i).get(index))+" "+variableAssginmentMap.get(returnVariableName(updatedClauseList.get(i).get(index))));
//										List<String> oneClause = new ArrayList<String>();
//										oneClause= updatedClauseList.get(i);
//										oneClause.remove(index);
//										updatedClauseList.remove(i);
//										updatedClauseList.add(i,oneClause);
//										propagationExists=true;
//										break;
//									}
//								}
//							}
						}
						
						}
						
	//				}
				}
				//CONFUSION
//				if(propagationExists)
//					i--;
			}
		}
		//printUpdatedClauseList();
//		printClauseList();
		
	}
	public void assignVariablesAndLearning(){
		String assignedVariable="";
		String assignedVariableValue="T";
		for(int ind=0;ind<variableAssginmentMap.size();ind++){
			if(variableAssginmentMap.get(variableList.get(ind)).equals("N")){//Just assigning first unassigned variable to "TRUE"
				assignedVariable=variableAssginmentMap.get(variableList.get(ind));
				//countAssignedVar++;
				//variableAssginmentMap.put(variableList.get(ind),"T");
				if(checkConflict(assignedVariable, assignedVariableValue)){// If the "TRUE" value causes conflict then assign it to "FALSE"
					assignedVariableValue="F";
					if(!checkConflict(assignedVariable, assignedVariableValue))// If the "FALSE" value then no conflict and return the result
						break; // Else the program will continue and will assign next unassigned element as assignedVariable
				}
				else{
					break;// If the "FALSE" value the no conflict and return result
				}
				
			}
			
		}
	}
	public boolean checkConflict(String assignedVariable, String assignedVariableValue){
		boolean conflictOccurs=false;
		for(int i=0;i<updatedClauseList.size();i++){//Iterating over each clause in updated clause list
			if(updatedClauseList.get(i).contains(assignedVariable)){
				if(updatedClauseList.get(i).size()==2){
					System.out.println("Size : 2");
				}
				for(int j=0; j <updatedClauseList.get(i).size();j++){
					
				}
			}
		}
		return conflictOccurs;
		
	}
	public void printClauseList(){
		System.out.println("The clause List is:");
		for(List<String> oneClause: clauseList){
			for(String s:oneClause){
				System.out.print(s+" ");
			}
			System.out.println();
		}
	}
	public void printUpdatedClauseList(){
		System.out.println("The Updated clause List is:");
		for(List<String> oneClause: updatedClauseList){
			for(String s:oneClause){
				System.out.print(s+" ");
			}
			System.out.println();
		}
	}
	
	public void printVariableAssignments(){
		System.out.println("Printing the total Variables:"+variableList.size());
		int lineCount=0;
		for(String var: variableList){
			System.out.print(" "+var);
			lineCount++;
			if(lineCount%10==0)
				System.out.println("\n");
		}
		lineCount=0;
		System.out.println("\nPrinting Variable Assignment Map:");
		for(String var: variableList){
			System.out.print("V"+var+":"+variableAssginmentMap.get(var)+"; ");
			lineCount++;
			if(lineCount%10==0)
				System.out.println("\n");
		}
	}
	public boolean checkSatisfiability(){
		boolean isStatisfiable=true;
		if(variableAssginmentMap.containsValue("N"))
			isStatisfiable=false;
		return isStatisfiable;
	}
	public void printSatisfiability(){
		int countAssignedVar=0;
		for(int ind=0;ind<variableAssginmentMap.size();ind++){
			if(!variableAssginmentMap.get(variableList.get(ind)).equals("N")){
				countAssignedVar++;
			}
		}
		if(variableAssginmentMap.containsValue("N"))
			System.out.println("\n\nThe CNF Input is UNSATISFIABLE("+countAssignedVar+" Assigned Variables among "+numberOfVariable+" total variables)");
		else{//If the Assignment is satisfying total clause set
			if(satisfyCNF())
				System.out.println("\n\nThe CNF Input is SATISFIABLE");
			else 
				System.out.println("\n\nThe CNF Input is UNSATISFIABLE");
		}	
	}
	
	//Satisfying all Clauses with Different Combinations
		public boolean satisfyCNF(){
//			for(int combIndex=0;combIndex<allPossibleCombinationOfVariables.size();combIndex++){
				boolean allClausesVal=true;
				boolean oneClauseVal=false;
				//For all Clauses
		//		printClauseList();
				for(int clauseIndex=0;clauseIndex<clauseList.size();clauseIndex++){
					String oneLitValue="";
					oneClauseVal=false;
					//For a Single Clause
					for(int oneClauseIndex=0;oneClauseIndex<clauseList.get(clauseIndex).size();oneClauseIndex++){
//						allPossibleCombinationOfVariables.get(combIndex).
						oneLitValue="";
						if(!clauseList.get(clauseIndex).get(oneClauseIndex).contains("-")){ //Literal is not negation
							oneLitValue=variableAssginmentMap.get(clauseList.get(clauseIndex).get(oneClauseIndex));
//							System.out.print(oneLitValue+" ");
						}else{//Literal is a negation
//							System.out.print(clauseList.get(clauseIndex).get(oneClauseIndex).trim().substring(1));
							//***************************************************************************
							oneLitValue=variableAssginmentMap.get(clauseList.get(clauseIndex).get(oneClauseIndex).substring(1)).equals("T")?"F":"T";
//							System.out.println("oneLitValue "+oneLitValue+" ");
//							System.out.print(allPossibleCombinationOfVariables.get(combIndex).get(Integer.parseInt(clauseList.get(clauseIndex).get(oneClauseIndex).trim().substring(1))-1)+" ");
						}
						if(oneLitValue.equals("T"))
							oneClauseVal=true; // One Clause value will be true if any of it's literal is true
					}
				//	System.out.println("\n oneClauseVal:"+ oneClauseVal+"\n");
					if(!oneClauseVal){
//						System.out.println("*****");
						allClausesVal=false;//Total CNF file will be satisfiable if only all clause values are true(i.e. at least one literal's value is TRUE for all clauses)
						return allClausesVal;
					}
				}
//				System.out.println("allClausesVal:"+ allClausesVal+"\n");
//				if(allClausesVal){
//					for(int index=0;index<variableList.size();index++){
//						variableAssginmentMap.put(variableList.get(index),allPossibleCombinationOfVariables.get(combIndex).get(index));
//					}
//					break; //If satisfiable for one TRUE/FALSE combination then return that result and exit from the program
//				}
//				System.out.println("****FOR ANOTHER COMBINATION");
//			}
				return allClausesVal;
		}
	//WRITING THE OUTPUT IN A FILE
	public void writeResultToFile(long spentTime){
		int countAssignedVar=0;
		for(int ind=0;ind<variableAssginmentMap.size();ind++){
			if(!variableAssginmentMap.get(variableList.get(ind)).equals("N")){
				countAssignedVar++;
			}
		}
		try{
			File file = new File(getInpFileName().substring(0, getInpFileName().length()-4)+".out");// Cutting .cnf file extension and adding .out file extension to store the result

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\n Benchmark File Name:("+getInpFileName()+")");
			bw.write("\n Number of Variables:"+variableList.size());
//			bw.write("\n Variable List Size:"+variableList.size());
			bw.write("\n Number of Clauses:"+getNumberOfClauses());
			if(variableAssginmentMap.containsValue("N"))
				bw.write("\nThe CNF Input is UNSATISFIABLE("+countAssignedVar+" Assigned Variables among "+numberOfVariable+" total variables)");
			else{
				if(satisfyCNF())
					bw.write("\n\nThe CNF Input is SATISFIABLE");
				else 
					bw.write("\n\nThe CNF Input is UNSATISFIABLE");
//				bw.write("\nThe CNF Input is SATISFIABLE");
			}
			int lineCount=0;
			bw.write("\nPrinting Variable Assignment Map(T:'TRUE'; F:'FALSE'; N:'NOT ASSIGNED'):\n");
			for(String var: variableList){
				bw.write("V"+var+":"+variableAssginmentMap.get(var)+"; ");
				lineCount++;
				if(lineCount%10==0)
					bw.write("\n");
			}
			bw.write("\n The required time: "+(spentTime/1000)+" seconds");
			bw.close();
			
			System.out.println("\n File Writing Done\n");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//Returning the variable name without negation operator
	public String returnVariableName(String literal){
		if(literal.contains("-"))
			literal=literal.substring(1);
		return literal;
		
	}
	
	
}
