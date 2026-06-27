Here’s a clean, polished, professional **README** for your Hotel Reservation Project — structured exactly the way real-world GitHub projects present themselves. It reflects the architecture, behavior, and logic of *your* implementation.

---

# 🏨 Hotel Reservation Application  
A console‑based Java application for managing customers, rooms, and reservations with full date‑conflict detection and alternative booking recommendations.

---

## 📌 Overview  
The **Hotel Reservation Application** is a modular Java program that allows users to:

- Create customer accounts  
- Search for available rooms  
- Reserve rooms with validated date ranges  
- View existing reservations  
- Receive recommended alternative dates when no rooms are available  

The system is built using a clean separation of concerns across **API**, **Service**, and **Model** layers.

---

## 🚀 Features

### 👤 Customer Management
- Create new customer accounts with email validation  
- Retrieve customer details  
- View all reservations for a customer  

### 🏨 Room Management
- Add new rooms (with duplicate‑room protection)  
- Retrieve rooms by room number  
- View all rooms in the system  

### 📅 Reservation System
- Search for available rooms within a date range  
- Prevent overlapping reservations using robust conflict logic  
- Reserve rooms for valid customers  
- Display all reservations  

### 🔁 Intelligent Recommendations
If no rooms are available for the requested dates:

- The system automatically checks availability **+7 days** from the user’s input  
- Recommended rooms are displayed  
- User can accept or decline the alternative  
- Accepted recommendations are booked using the correct shifted dates  

### 🛡 Input Validation
- Prevents booking past dates  
- Ensures check‑out is after check‑in  
- Limits invalid email attempts  
- Handles invalid room selections gracefully  

---

## 🧩 Architecture

### **API Layer**
- `HotelResource`  
- `AdminResource`  

Provides a clean interface between the UI and the service layer.

### **Service Layer**
- `ReservationService`  
- `CustomerService`  

Handles business logic:
- Room storage  
- Reservation conflict detection  
- Customer management  
- Date shifting and availability calculations  

### **Model Layer**
- `Customer`  
- `Room` / `IRoom`  
- `Reservation`  
- `RoomType`  

Represents the core domain objects.

---

## 🔍 Room Availability Logic

A room is considered unavailable if the requested dates overlap with an existing reservation:

```
overlap = checkIn < existingCheckOut AND checkOut > existingCheckIn
```

If overlap exists, the room is removed from the available list.

---

## 🧠 Recommendation Logic

When no rooms are available:

1. The system shifts the user’s requested dates by **+7 days**  
2. It checks availability for the new date range  
3. Recommended rooms are displayed  
4. If the user accepts, the reservation is created using the **shifted dates**, not the original ones  

---

## 🖥 User Flow

1. Launch the main menu  
2. Choose an action:  
   - Find and reserve a room  
   - See reservations  
   - Create an account  
   - Access admin menu  
3. Enter check‑in and check‑out dates  
4. View available rooms or receive recommendations  
5. Select a room  
6. Enter a valid customer email  
7. Reservation is created and displayed  

---

## 🛠 Technologies Used
- **Java 8+**  
- **Collections Framework** (`Map`, `HashSet`, `List`)  
- **Date & Calendar API**  
- **Object‑Oriented Design**  
- **Console-based UI**  

---

## 📂 Project Structure

```
src/
 ├── api/
 │    ├── HotelResource.java
 │    └── AdminResource.java
 ├── service/
 │    ├── ReservationService.java
 │    └── CustomerService.java
 ├── model/
 │    ├── Customer.java
 │    ├── Room.java
 │    ├── IRoom.java
 │    ├── Reservation.java
 │    └── RoomType.java
 └── ui/
 │    ├── AdminMenu.java
 │    ├── HotelApplication.java
 │    ├── MainMenu.java
```

---

## 🧪 Future Enhancements
- Support for room cancellation  
- Support for multiple hotels  
- GUI or web interface  
- Persistent storage (database or file-based)  
- Dynamic recommendation logic (not fixed +7 days)  

---
