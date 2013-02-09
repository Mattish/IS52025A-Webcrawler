IS52025A-Webcrawler
===================

Webcrawler for Goldsmith's IS52025A

Original Brief:
"Write a complete Java program that takes a url and a domain name as command line arguments that crawls recursively through all urls starting at args[0]. 
It only checks web-pages in the domain given by args[1]. It stores all links in a database. 
The database should hold data in such a way that we can reconstruct the `graph' of the links.

Write another program that reads the database and converts the data to an object of type Graph (remember from your Algorithms Course!).
It then prints out all the links in a form input for the GraphViz program.

Secondly, the `Goldsmiths Ranking' of a Web page x is the shortest distance from the Goldsmith's Home page to x.
Write a program which works out the the Goldsmith's ranking of any web page on the Goldsmiths site."