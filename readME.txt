SIMPLE CAL 2 (now with keypad and keypad entry)

A few things to note. 

The goal of this project was to build a simple javaFX application. 
objectives: 
1. Build a simple GUI calculator in JavaFX, with basic operator options and decimal inputs. 
2. Use a single text feild to populate data 
3. Build a keypad using loops and factory methods 
4. Use keyevents to operate keypad via hardware 
5. Prevent input of strings 
Overall I'd day it served the objectives with some exceptions for 2. and 4.   



Having said, the following prompts for further development are as follows: 

for objective 2: redevelop temporary "storage" of text field entries between operations and post equals function. Model standard calculator equals function where repeat operation of second input is executed. 
EXAMPLE: 
currently operations are saved respectively and execution is repeated, not updated.

"5" "+" "2" "=" results in 7, yet pressing "=" again results in 12 not 9. 

for objective 4. Find ways to deactivate default selection of buttons via arrow keys. Also access and override the "enter" button to execute equals. Currently, the enter key acts to arm whatever button on the GUI is selected (this is profoundly stupid). 




