# Programming Standards 
General guidelines for organizing and writing code. 

## Naming conventions 
* Classes: Pascal case
  * E.g., ClassName
* Variables: Camel case
  * E.g., variableName
* Constants: All caps 
  * E.g., CONSTANT_NAME

## Persistance Organization
* Each object should have an database interface associated with it.
* Each database interface should have two  implementations: 
  1. A real database
  2. A stub database. 

