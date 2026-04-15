Planning part 2. 
Copied code to generate the jar file and run the file from terminal, for testing
Prepared questions about the sample code.
In this example, how come the process says the duration is 10, but then, it only runs for less than 10 moments

When we were working on the homework, I was confused how we were supposed to tell how much units a process has left, because the process doesnt make that information public. 
Breakdown
Process: runUnit runs it for one units of time
Returns false if it has no time left. /
ProcessGenerator: If after runUnit returns false, process yields
Otherwise, clock.semaphore
?? does that mean that process yields is supposed to kick something from a queue and not return it, 

Otherwise, the next clock cycle will kick and add to the back of the queue?
