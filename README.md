# SAT-Solver
CNF Satisfiability Solver

Efficient implementation of Satisfiability solver has gained more and more relevance with huge advancement of Electronic Design Automation and Artificial Intelligence fields. Satisfiability problem in computer science is known to be the first NP complete problem recognized by Cook. Various kind of heuristic based implementation has been proposed and encouraged throughout the years. MINISAT is an established SAT solver used in number of fields EDA and AI. Purpose of this project was to study and implement MINISAT solver and compare it with original version. The reported implementation is in Java and producing results SAT 2006 Competition benchmarks(upto 224K variables)

Instructions to run:
1. Export or clone the repo in the eclipse distribution.
2. One UI would appear. Input the following file: i) Enter the benchmark file (*.cnf). 
3. One OUTPUT file will be generated with same filename and *.out alias. 
4. The OUTPUT file will contain: i) whether the cnf is satisfiable or not ii) One Truth assignment, in case it is satisfiable.
