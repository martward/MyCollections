MyCollections
=============

This app will enable a user to create small databases used
for collecting items etc. The user will be able to chose the features
of the items he/she collects and to specify what kind of data 
(text, number, etc) the feature holds. The user will be able to add items by
filling out a wizard in which the user will be asked to fill in the data
he/she chose as relevant when creating the database.


The data is stored in a SQL database. 
There will be six activities:
  - Home_Activity:	Here the user can choose or add a collection/database
  - Collection_Activity: 		Here the user can choose or add an Item
  - Item_Activity:		Here the user can see or choose to edit an Item
  - Edit_Item_Activity:		Here the user can edit an object
  - Create_Collection_Activity:	Here the user can create a new database
  - Add_Item_Activity:		Here the user can add an item to a database

Every activity also needs an adapter

There are three classe: 
  - DB_List:  to manipulate the list of collections
  - Item:   to create an item with certain features
  - DBConnect:  which handles the database

Features:
  - Multiple databases/collections
  - Fields can contain text, numbers 
  - User can edit an item
  - User can export a collection as a .csv file to the internal storage of the phone



Home screen:

![oms](https://github.com/martward/MyCollections/raw/master/doc/Home.png)

Options collection:

![oms](https://github.com/martward/MyCollections/raw/master/doc/home_options.png)

Showing the items of a collection:

![oms](https://github.com/martward/MyCollections/raw/master/doc/Collection.png)

Options item:

![oms](https://github.com/martward/MyCollections/raw/master/doc/item_options.png)

Adding/editing an item:

![oms](https://github.com/martward/MyCollections/raw/master/doc/add_item.png)

Showing an item:

![oms](https://github.com/martward/MyCollections/raw/master/doc/Item.png)
