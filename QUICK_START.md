# VedicVastram - Quick Start Guide

## ✅ Application Ready!

All issues have been fixed and the application should now start successfully.

## Build and Run Commands

### Option 1: Clean Build and Run
```bash
cd /Users/rishirajyadav/Desktop/Projects/VedicVastram
./mvnw clean compile
./mvnw spring-boot:run
```

### Option 2: Build as JAR
```bash
./mvnw clean package
java -jar target/VedicVastram-0.0.1-SNAPSHOT.jar
```

### Option 3: Quick Run (without cleaning)
```bash
./mvnw spring-boot:run
```

## Access the Application

Once running, the application will be available at:
- **Main API:** http://localhost:8080
- **H2 Database Console:** http://localhost:8080/h2-console

## Default Users

The application initializes with the following test users (see `data.sql`):

1. **Admin User**
   - Email: `admin@gmail.com`
   - Role: ADMIN
   - Status: APPROVED

2. **Seller User**
   - Email: `seller@gmail.com`
   - Role: SELLER
   - Status: PENDING

3. **Buyer User**
   - Email: `buyer@gmail.com`
   - Role: BUYER
   - Status: APPROVED

All default passwords are: `$2a$10$VcP8t/HWQ71SlQNQe0uIAuXu.Y6vUJ.e0VUP1fgKX6/i/yVVfCnmS` (BCrypt hashed)

## What Was Fixed

### 1. Spring Security Integration Issues ✅
- Fixed `UserDetailsService` interface to properly extend Spring's implementation
- Removed custom `UserDetails` interface that conflicted with Spring Security
- Updated all components to use Spring Security interfaces

### 2. Bean Dependency Resolution ✅
- Resolved `UnsatisfiedDependencyException` for `jwtFilter`
- Fixed entity manager factory bean references
- All dependency injection now works correctly

### 3. Database Initialization ✅
- Fixed `data.sql` execution order
- Added `spring.jpa.defer-datasource-initialization=true`
- Tables are now created before data insertion

## Troubleshooting

### Port Already in Use
If port 8080 is in use, you can change it in `application.properties`:
```properties
server.port=8081
```

### Database Console Issues
The H2 console is enabled at `/h2-console`. Default connection:
- **Database URL:** `jdbc:h2:mem:kurti_db`
- **Username:** `SA`
- **Password:** (leave blank)

### Rebuild Issues
If you encounter build issues, try:
```bash
./mvnw clean install
```

## Project Structure

```
src/
├── main/
│   ├── java/org/example/vedicvastram/
│   │   ├── VedicVastramApplication.java
│   │   ├── config/
│   │   │   ├── Config.java
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   ├── service/
│   │   │   ├── CustomUserDetailsService.java (✅ Fixed)
│   │   │   └── CustomUserDetails.java (✅ Fixed)
│   │   ├── entity/
│   │   ├── util/
│   │   │   ├── JwtFilter.java (✅ Fixed)
│   │   │   └── JwtUtil.java (✅ Fixed)
│   │   └── respository/
│   └── resources/
│       ├── application.properties (✅ Updated)
│       └── data.sql
└── test/

```

## Support

All dependencies are configured in `pom.xml` and will be automatically downloaded on first build.

Happy coding! 🚀

