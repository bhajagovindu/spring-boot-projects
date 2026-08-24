5 Topics explained with flow diagrams:

Topic 1 — What Spring Security is and how the filter chain works
Topic 2 — What JWT is, its 3 parts (Header · Payload · Signature), and the full login flow
Topic 3 — BCrypt password hashing — why plain text passwords are never stored
Topic 4 — Role-Based Access Control (ROLE_USER vs ROLE_ADMIN)
Topic 5 — How all 4 security classes connect together as an assembly line

14 Steps with full copy-paste code:

Step 1 — Create project + add JWT dependency to pom.xml
Step 2 — application.properties with JWT secret config
Steps 3–6 — Project structure, Role enum, User entity, DTOs, Repository
Step 7 — JwtUtils.java — generates and validates tokens
Step 8 — CustomUserDetailsService.java — bridge between your DB and Spring Security
Step 9 — JwtAuthFilter.java — intercepts every request
Step 10 — SecurityConfig.java — master config with public vs secured routes
Steps 11–13 — AuthController, TestController with @PreAuthorize, DataLoader
Step 14 — Full test sequence in curl/Postman (register → login → use token → test roles)