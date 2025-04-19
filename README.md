🚆 Train Ticket Booking System
A console-based Java application for booking train tickets using JDBC and MySQL. This project simulates a real-world train reservation system where users can search trains between stations, view available seats, and book tickets dynamically.
🔧 Features
🔍 Train Search: Find trains between two stations based on route order (source → destination).

🎫 Seat Booking: Real-time seat reservation with availability checks.

📊 Live Updates: Updates total available seats after each booking.

🧾 Booking Details: Retrieve and display user booking information.

🗃️ Database Integration: Connected to MySQL using JDBC for persistent data storage.

🧠 Optimized Logic: Utilized HashMap, HashSet, and ArrayList for efficient in-memory operations.

🛠️ Tech Stack
Java (Core)

JDBC

MySQL

Data Structures (Collections)

SQL (Joins, Subqueries, DDL/DML)

📂 Modules

Module	Description
TrainService	Handles train search, booking logic, and database operations
UserService	Manages user interaction and booking data
DBUtil	Provides database connection utility
Train	Java model class representing train objects
🏁 How to Run
Clone the repository

Set up the MySQL schema and insert sample data

Update DB credentials in DBUtil.java

Compile and run the Main.java class

📸 Sample Flow
markdown
Copy
Edit
1. Search Train
2. Book Ticket
3. View Booking
4. Exit
📝 Future Enhancements
UI using JavaFX or Spring Boot REST API

Authentication and user registration

PDF ticket generation

Cancel bookings and refund logic

Feel free to fork and build upon it!
📌 Contributions welcome.
