# Ticket Generator
We are going to generate the entire slip in an 18 x 9 matrix at once

For the entire slip we have:
- column 1: 9 numbers
- column 2: 10 numbers
- column 3: 10 numbers
- ....
- column 7: 10 numbers
- column 8: 10 numbers

The algorithm is the following:
1. Iterate over each each of the 6 tickets in the slip and for each column randomly, fill in one of the columns on 0, 1 or 2 position. (While doing so, check that we do not surpass the 5 tickets row).
2. Fill in 1 of numbers in the last column randomly.
3. Now we have 3 numbers left in the first and 4 numbers left in the other columns
4. Iterate 3 times by column and randomly insert in each column one number. Do this by also checking that in the inserted ticket the column doesn't exceed 2 (maxDepth=2) numbers per column.
5. Iterate 1 more time by column and randomly insert in each column one number. Now go with maxDepth=3.
6. There is a corner case where one or two numbers are not filled. Go in and find the faulty ticket and column and do a number shift in order to fill in the remaining one or two columns.

To execute the code run main class.

To run the tests, run SlipGeneratorTest.java

Possible (performance) improvements:
- Implement multithreading to make the first insertion of each ticket and each column parallel.
- Parallelize the insertions per column in point number 3 and 4 to be executed in parallel.
- This means that we will need to have a synchronized collection that will serve for storing the total sum of numbers per ticket for record keeping.
- Implement faster random generation algorithm (need to investigate)