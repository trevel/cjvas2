CJV805 Assignment 2 – Winter 2015
Assignment Submission Form
==========================================================================
I/we declare that the attached assignment is my/our own work in accordance
with Seneca Academic Policy. No part of this assignment has been copied
manually or electronically from any other source (including web sites) or
distributed to other students.

Name(s) 		Student ID(s) 	Seneca LearnID(s)
Laurie Shields   034448142		llshields	
--------------------------------------------------------------------------
Mark Lindan		 063336143		melindan
--------------------------------------------------------------------------

Description
===========
Assignment 2 is a Java web application called HR Management which leverages
our DBAccessHelper code and Employee bean class from assignment 1. The 
DBAccessHelper class was modified to implement connection pools for the 
JDBC connections. Several new methods were also added to support additional 
features implemented for the assignment 2 user interface.

The application provides the following:
1. A login page
2. The Employees List Page and display of the selected list
3. The Employees detail page for adding/modifying/removing employees
4. The Search for Employees Page and display of search results.
5. A logout page
6. A status/error page to display status and error messages.

We have made use of HTML5 input types and built our pages using a CSS file
called main.css. We used the template.html file provided in the class as
a guide for the structure of our pages. We also implemented session 
management so that if the user has multiple tabs open while logged in, if
they log out of the one tab, then the other tabs will return to the login
page when they attempt any operations.

Our submission file is A2-shields.laurie.war. Our login page can be 
accessed at http://localhost:8080/A2_HRManagement/index.html

Our javadocs can be accessed from the A2_HRManagement/doc/index.html file.

Other notes
===========

No known bugs. Our assignment connects to our Oracle database from 
assignment 1 on the Seneca Neptune server. It requires the Tomcat v8.0 
Server container to run.
