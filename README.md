# techroverMongo

(1)Restore Database backup in your system

(2)Set database name in SimpleMongoConfig.java


Postman API Description



USER API

(1)getAllUsers

http://localhost:8080/user/getAllUsers

will give you all registered users in database.

(2)AddUser

http://localhost:8080/user/saveUser/60f973041c618533f5318a11

will add user to system. 60f973041c618533f5318a11 is admin id since we are not implementing token based / Any authentication
need static admin user while adding users. 

(3)Optional Adding Admin user

http://localhost:8080/user/saveAdminUser


ITEM API
(1)getItems

http://localhost:8080/item/getItems

will give you available items in database

(2)getItemById

http://localhost:8080/item/getItems/60f979a96e98577f107917ce

will give you item 
params -> item id
return -> item or nothing

(3)getInvoicesByItemId

http://localhost:8080/item/getInvoices/60f979dc6e98577f107917cf

will give you list of invoices associated with item 
params -> item id
return -> invoice list


(4)saveItem

http://localhost:8080/item/saveItem

will save Item in our database.

Invoice API

(1)getAllInvoices

http://localhost:8080/invoice/getAllInvoices

will give you list of invoices

(2)getAllInvoices By UserId

http://localhost:8080/invoice/getAllInvoices/60f973041c618533f5318a11

will give you list of invoices associated with UserId in case of ADMIN User all invoices.
params -> user id
return -> invoice list

(3)SaveInvoice

http://localhost:8080/invoice/saveInvoiceInformation/60f975373c998643474fcf53

will add invoice to our database with GST Amount.
params -> user id


For POST Method request body information available in Attached postman collection



===================END====================================
