
# README: COMPX301 [A3] - Pattern Search

Name: Eugene Chew - ID: 1351553

Name: Bhavit Wadhwa - ID: 1516846

  

## Tested (WORKING) Functionality:

### Both together (compile and search):
    java REcompile "(tx)|z" | java REsearch BrownCorpus.txt > out.txt

> Testing input is the brown corpus provided text file

> Out.txt matches 

### REcompile:

> Grammar:

	E -> T
	E -> TE

	T -> F
	T -> F*
	T -> F+ 
	T -> F?
	T -> F | T

	F -> .
	F -> \
	F -> (E)
	F -> [A]

> Tested basic regular expressions:

    e.g
    a(bra)+cada(bra)+
    aa*bc
    aa+bc
    abc
    a*
    abc?d
    a*b|b

> Alternation will be treated with brackets

	e.g.
	ab*|c will be treated as (ab*)|c


### REsearch:

Successfully tested the following commands to retrieve successful output:

#### [1]: REGEX EXP: " a(bra)+cada(bra)+ "
    cat abracadabraFSM.txt | java REsearch abraTestInput.txt > out.txt

> Testing input is:

    abracadabra
    abrabrabracadabra
    abrabracada
    acadabra
    abracadabrabrabra
    abrabrbracadabra

> Out.txt returns 3 successful matches:

    abracadabra
    abrabrabracadabra
    abracadabrabrabra

#### [2]: REGEX EXP: " brown|test"
    cat brownTestFSM.txt | java REsearch abraTestInput.txt > out.txt

> Testing input is:

    Mary detested her little brown lamb
    brown
    brow
    retested

> Out.txt returns 3 successful matches:

    Mary detested her little brown lamb
    brown
    retested


