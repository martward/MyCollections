MyCollections
=============

This app will enable a user to create small databases used
for collecting items etc. The user will be able to chose the features
of the items he/she collects and to specify what kind of data 
(text, number, etc) the feature holds. The user will be able to add items by
filling out a wizard in which the user will be asked to fill in the data
he/she chose as relevant when creating the database.


The data will be stored in a SQL database. 
There will be six activities:
  - Home_Activity:	Here the user can choose or add a collection/database
  - Collection_Activity: 		Here the user can choose or add an Item
  - Item_Activity:		Here the user can see or choose to edit an Item
  - Edit_Item_Activity:		Here the user can edit an object
  - Create_Collection_Activity:	Here the user can create a new database
  - Add_Item_Activity:		Here the user can add an item to a database

Almost evere activity will also need an adapter, further classes will not be necessary.

Features:
  - Multiple databases/collections
  - Fields can contain text, numbers 
  - User can edit an item


Optional features:
  - Export .CSV to Google Drive / SD card / Dropbox
  - Field can be declared as a choice menu (so the user can choose between pre defined values)
  - Field can be declared as a date
  - User can find an object by name
  - User can find list of object which satisfy a certain criteria
  - User can choose to sort objects by a certain feature

Home screen:

![oms](https://github.com/martward/MyCollections/raw/master/doc/Activity_Home.png)

Creating a collection:

![oms](https://github.com/martward/MyCollections/raw/master/doc/Activity_Create_Collection.png)

Showing the items of a collection:

![oms](https://github.com/martward/MyCollections/raw/master/doc/Activity_Collection.png)

Adding, showing and editing an item will have very similar layouts, only the
editText fields (adding / editing) and TextViews (showing) will be different.

![oms](https://github.com/martward/MyCollections/raw/master/doc/Activity_Add_Item.png)
