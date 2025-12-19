# Student Management (Servlet/JSP/JDBC)

Java 17 Student Management web application using Servlets, JSP, JDBC, and PostgreSQL (Neon).

## Features
- Student registration with frontend + backend validation
- Admin login (hardcoded)
- View & delete students
- Clean admin dashboard UI

## Tech
- Java 17
- Jakarta Servlets & JSP (no frameworks)
- JDBC with PostgreSQL driver
- Apache Tomcat 10/11

## Setup
1. Connect to your Neon PostgreSQL cluster and ensure you have a database to use.

2. Run the SQL script in `db/schema.sql` to create the `student_details` table in the connected database. Example using `psql`:

```bash
# connect with provided Neon connection string (substitute host/user/password as needed)
psql "jdbc:postgresql://ep-curly-art-a456yhbk-pooler.us-east-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require" -U neondb_owner -W -h ep-curly-art-a456yhbk-pooler.us-east-1.aws.neon.tech -d neondb -f db/schema.sql
```

3. Recommended: export the DB credentials as environment variables so the app reads them securely.

Set environment variables (Windows PowerShell) using the Neon values you provided:

```powershell
$env:DB_URL = 'jdbc:postgresql://ep-curly-art-a456yhbk-pooler.us-east-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require'
$env:DB_USER = 'neondb_owner'
$env:DB_PASSWORD = 'npg_KpDh4oXLFAG8'
```

Alternatively you may keep credentials in Tomcat context parameters; `src/main/webapp/WEB-INF/web.xml` was updated with the example Neon values.

4. Build WAR and deploy to Tomcat:

```bash
mvn clean package
# copy target/StudentManagement.war to Tomcat's webapps folder
```

5. Open in browser:

```
http://localhost:8080/StudentManagement/
```

## Admin credentials
- Username: `iamneo`
- Password: `iamneo123`

## Notes
- The app looks for DB credentials in environment variables. You can also configure Tomcat context parameters.
- All JDBC uses `PreparedStatement` to prevent SQL injection.
- The project uses Jakarta package names (Tomcat 10+).

Enjoy.
