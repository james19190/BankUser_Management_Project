# Bank User-Management_Project
This simple Java project implements a basic Bank User Management System. It utilizes custom exception classes to handle runtime input errors, such as invalid passwords, negative balances, and user not found scenarios.

## Overview
The program reads account data from a data.txt file, creates user objects, and adds them to the bank's user database. Users can log in, and custom exceptions are thrown if there are issues with the login process, like invalid passwords or negative balances.

## Usage
1) Data Initialization:
    - The program reads user account data from a data.txt file.
    - Each line in the file represents a user and includes information like ID, password, account number, balance, card availability, and cash.

2) User Login:
    - The Bank class handles the user login logic.
    - If a user is not found, a UserNotFoundException is thrown.
    - If an invalid password is provided, an InvalidPasswordException is thrown.
    - If a user has a negative balance, a NegativeBalanceException is thrown.

3) Exception Handling:
    - Custom exception classes (InvalidPasswordException, NegativeBalanceException, and UserNotFoundException) are used to handle specific error scenarios.
    - The program catches these exceptions and prints user-friendly error messages.

## Custom Exception Classes
- InvalidPasswordException: Thrown when the user provides an incorrect password during login.
- NegativeBalanceException: Thrown when a user's balance becomes negative.
- UserNotFoundException: Thrown when a user tries to log in with an ID that does not exist in the system.

## Contributing
Feel free to contribute to the project by submitting issues or pull requests. Your input and suggestions are highly valuable!
