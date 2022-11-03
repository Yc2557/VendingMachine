# SOFT2412 Assignment 2 Vending Machine

*Built by Michael Hitchcock, Kevin Hou, Kevin Kong, Matan Cohen, Yu Hong Chen*

This is a Java-based GUI Vending Machine with snacks maintained under four different categories,
namely drinks, chocolates, chips and lollies. The Vending Machine allows four unique roles to operate
the machine; Regular User, Seller, Cashier and Owner, in which the Regular User is able to select
and pay for snacks using cash or card. Data changes through purchases/stock fills and is stored
in .json files.

## GUI

## Use Notes

To run the program, use the following command:
```aidl
gradle run
```

To run test cases on the program, use the following command:
```aidl
gradle test
```

To generate jacoco reports, use the following command:
```aidl
gradle build jacocoTestReport
```

## Unique Users

### Regular User

The GUI supports the following functionality:
- User can login or create an account through the "Login" button
- User can scroll through item list based on category and select product
and amount to add to cart
- User can checkout and pay by cash or card

### Seller

The GUI supports the following functionality:
- Seller can select any item from the list and modify name, category,
quantity and price (i.e. changing TextField then press "Modify")
- Seller can download item summary and current inventory report

### Cashier

The GUI supports the following functionality:
- Cashier can change the amount of each coin/note in the vending machine
- Cashier can download current available change report and a summary transaction
report

### Owner

The GUI supports the following functionality:
- Owner can add/remove Seller, Cashier or other Owner uses
- Owner can enter Cashier or Seller modes
- Owner can download cancelled transaction reports as well as a list of
the current roles/users