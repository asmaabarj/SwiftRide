# 🚖 Taxi Service Management API 🚖
**Welcome aboard!** This is your one-stop destination for managing a fleet of taxis, tracking drivers, and optimizing reservations. Say goodbye to paperwork and hello to digital efficiency! 🎉

---

## 🌟 Features That Drive Success 🌟

### 🚀 Reservations Management
- 📝 **CRUD Magic**: Effortlessly create, edit, delete, and view reservations.
- 💰 **Dynamic Pricing**: Automatically calculate fares based on distance and vehicle type.
- 👨‍✈️ **Availability Check**: Ensure drivers and vehicles are ready to roll.
- 🔄 **Status Updates**: Keep tabs on reservations: CREATED, CONFIRMED, TERMINATED, or CANCELED.

- **Analytics Wizardry**:
    - 📊 Average price per kilometer.
    - 🚗 Average trip distance.
    - ⏰ Time slot popularity (e.g., 8 AM–9 AM: 15 reservations).
    - 🌍 Most requested pickup/drop-off zones.

---

### 🧑‍✈️ Driver Management
- 🚗 **One Vehicle Per Driver**: No vehicle swapping chaos!
- 📅 **Availability at a Glance**: Manage driver schedules seamlessly.
- 📵 **One Job at a Time**: No double-booking allowed.

- **Insightful Analytics**:
    - 🔥 Occupancy rate (% time drivers are on duty).
    - ⏰ Availability slot analysis.
    - 📊 Driver status stats (e.g., 10 AVAILABLE, 5 BUSY).

---

### 🚘 Vehicle Management
- 🛠 **CRUD with Vroom**: Keep vehicle details up to date.
- 💸 **Type-Based Fares**:
    - **BERLINE**: 5 MAD/km (4–5 seats).
    - **VAN**: 7 MAD/km (7–9 seats).
    - **MINIBUS**: 9 MAD/km (10+ seats).
- 🚦 **Smart Allocation**: Each vehicle gets one driver and one active reservation.

- **Fleet Insights**:
    - 🛣 Average mileage by type.
    - 🚗 Utilization rate by type (% time vehicles are in use).
    - 📋 Fleet status report (e.g., 15 AVAILABLE, 8 BUSY).

---

## 🌐 SWAGGER UI

- **Swagger UI** is a tool that visually presents the API documentation. It provides a user-friendly interface for exploring the API endpoints and their functionalities.
- To access the **Swagger UI** for **SwiftRide**, run the application and navigate to `http://localhost:8087/swagger-ui/index.html` in your browser.

## 🧩 Postman Collection

- **Postman** is a popular API client that allows you to test API endpoints and monitor responses.
- To access the **Postman Collection** for **SwiftRide**, click [here](https://swiftride.postman.co/workspace/swiftRide-Workspace~6a0684b1-c2e4-4273-81b6-afde2de9ad7b/collection/33948002-ffdbdae3-e5fa-490c-92f0-55a653518f8a).


## 🛠 Under the Hood

### 📁 Project Structure

Here's an overview of the project structure for **SwiftRide**:

- `config`: Contains configuration classes for **Spring Boot** and **Exceptions**.
- `controllers`: Handles HTTP requests for managing trainings, trainers, learners, and classes.
- `dto`: Data Transfer Objects facilitating data transfer between layers.
- `exceptions`: Custom exceptions for handling errors.
- `models`: Classes representing core entities: `Training`, `Trainers`, `Classes` , `Learners`, and associated enums.
- `repositories`: Managing database queries.
- `services`: Business logic layer for managing trainings, trainers, learners, and classes.
- `resources`: Contains configuration files such as `application.properties` and database setup scripts.


### Backend Magic 🪄
- **Java 8**: Bringing modern and functional programming features.
- **Spring Boot**: REST APIs done right.
- **Spring Data JPA**: Because raw SQL is sooo 2010.
- **Spring Profiles**: Switch environments like a pro:
    - `dev`: H2 database (local testing).
    - `QA`: MySQL database (pre-production).
    - `prod`: PostgreSQL database (live action).

### Testing
- **JUnit & Mockito**: Test like a boss.
- **Postman**: Your API’s best buddy.
- **Swagger**: For those who love clean docs.

### Cool Tools & Utilities
- **Git**: Because version control is life.
- **SonarLint**: Keep your code sparkling clean.
- **Lombok**: Less boilerplate, more brilliance.
- **Spring Boot DevTools**: Instant refresh = Instant productivity.
- **JIRA**: Agile project management with flair.

---

## 🚀 Getting Started

### Prerequisites
- Java 8
- Maven
- Git
- Your favorite IDE (Eclipse, IntelliJ, or NetBeans)
- Database setup (H2/MySQL/PostgreSQL)

### Running the Application

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/asmaabarj/SwiftRide.git
   cd SwiftRide
   ```

2. Update the `application.properties` file in the `resources` directory with your preferred profile, and update the database configuration accordingly.

3. Build and run the application using Maven:
   ```bash
   mvn clean install
   ```

## 🎉 Get Started with SwiftRide Today!

For any questions, feedback, or suggestions, feel free to reach out to us. 📧
