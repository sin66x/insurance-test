Insurance Test
===

In this code, we have a file contains JSON objects represent 
events of insurance contracts:
- Create
- Increase
- Decrease
- Terminate

We should read the events and calculate:
- ___Number of contracts___: The number of contracts that started but not yet been terminated.
- ___Expected gross written premium (EGWP)___: The expected sum of all premiums for the year.
- ___Actual gross written premium (AGWP)___: The accumulated premium that should have been
paid in every month.


How to read this code
===
The code has 3 main packages:
* `datareader`: The package that contains code for reading json objects. this code may be replaced by a query on a relational or NoSQL DB
* `eventprocessor`: The main package contains the main class: `EventProcessor`
* `exceptions`: A package of custom exceptions

If you want to read this code, the best way is to start with `com.behnam.insurancetest.eventprocessor.EventProcessor.mainAnswer()`

TODO:
===
We can write more tests. this was just a test project and just to prove
skills on concepts of Collections, Algorithm, UnitTests && etc.

We could write more useful logs.

The code could be refactored by changing class names. The tests could help us 
to make sure that the refactoring won't damage the result.