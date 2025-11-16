# Admin Account Setup Guide

## Quick Start: Create Your First Admin

Follow these simple steps to create an admin account:

### Step 1: Register a User Account

1. Start the application:
   ```bash
   npm run dev
   ```

2. Open your browser and go to: `http://localhost:3000/register`

3. Fill in the registration form:
   - Name: Your name
   - Email: admin@example.com (or your preferred email)
   - Password: Your secure password
   - Confirm Password: Same password

4. Click "Register"

5. You'll be redirected to the login page

### Step 2: Update Role in Database

**Option A: Using MySQL Command Line**

```bash
# Connect to MySQL
mysql -u root -p

# Enter your MySQL password when prompted
```

```sql
-- Select the database
USE sweetshop;

-- Update the user's role to ADMIN
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';

-- Verify the change
SELECT id, name, email, role FROM users WHERE email = 'admin@example.com';

-- You should see role = 'ADMIN'

-- Exit MySQL
EXIT;
```

**Option B: Using MySQL Workbench**

1. Open MySQL Workbench
2. Connect to your database
3. Select the `sweetshop` database
4. Run this query:
   ```sql
   UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
   ```
5. Verify:
   ```sql
   SELECT * FROM users WHERE email = 'admin@example.com';
   ```

**Option C: Using phpMyAdmin**

1. Open phpMyAdmin
2. Select `sweetshop` database
3. Click on `users` table
4. Find your user
5. Click "Edit"
6. Change `role` from `USER` to `ADMIN`
7. Click "Go" to save

### Step 3: Clear Browser Cache and Login

1. If you're already logged in, click "Logout"

2. Clear browser localStorage:
   - Press `F12` to open Developer Tools
   - Go to "Application" tab (Chrome) or "Storage" tab (Firefox)
   - Click "Local Storage" → `http://localhost:3000`
   - Click "Clear All" or delete individual items

3. Go to login page: `http://localhost:3000/login`

4. Login with your credentials

5. You should now see "Admin Panel" link in the navbar!

### Step 4: Verify Admin Access

1. Click "Admin Panel" in the navbar
2. You should see the admin dashboard
3. Try adding a new sweet to verify everything works

## Creating Additional Admins

To create more admin accounts, repeat the process:

1. Have the user register normally
2. Update their role in the database:
   ```sql
   UPDATE users SET role = 'ADMIN' WHERE email = 'another-admin@example.com';
   ```
3. User must logout and login again

## Troubleshooting

### Issue: "Admin Panel" link not showing after login

**Solution:**
1. Verify role in database:
   ```sql
   SELECT email, role FROM users WHERE email = 'your-email@example.com';
   ```
2. Make sure it shows `ADMIN` (not `USER`)
3. Logout completely
4. Clear localStorage (F12 → Application → Local Storage → Clear)
5. Login again

### Issue: Can't access /admin route (redirects to home)

**Cause:** User doesn't have ADMIN role

**Solution:**
1. Check database: `SELECT role FROM users WHERE email = 'your-email@example.com';`
2. Update if needed: `UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';`
3. Logout and login again

### Issue: Getting 403 Forbidden errors

**Cause:** Backend doesn't recognize user as admin

**Solution:**
1. Check JWT token payload:
   - F12 → Application → Local Storage
   - Look at `accessToken`
   - Decode it at jwt.io
   - Verify `role` claim is `ADMIN`
2. If role is wrong, logout and login again
3. If still wrong, check database role

### Issue: Database connection error

**Cause:** MySQL not running or wrong credentials

**Solution:**
1. Start MySQL service
2. Verify connection details in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sweetshop
   spring.datasource.username=root
   spring.datasource.password=your-password
   ```

## Security Best Practices

### For Development
- ✅ Current setup is fine
- ✅ Create admins manually via database
- ✅ Keep admin credentials secure

### For Production
Consider implementing:

1. **Admin Invitation System**
   - Existing admins can invite new admins
   - Invitation links expire after 24 hours
   - Email verification required

2. **Two-Factor Authentication**
   - Extra security for admin accounts
   - Use authenticator app or SMS

3. **Audit Logging**
   - Log all admin actions
   - Track who did what and when
   - Store logs securely

4. **Role Hierarchy**
   - Super Admin (can create other admins)
   - Admin (can manage inventory)
   - User (can purchase)

5. **Password Requirements**
   - Minimum 12 characters
   - Must include uppercase, lowercase, numbers, symbols
   - Password expiry every 90 days

## Quick Reference

### Check User Role
```sql
SELECT id, name, email, role FROM users;
```

### Make User Admin
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'user@example.com';
```

### Make Admin Regular User
```sql
UPDATE users SET role = 'USER' WHERE email = 'admin@example.com';
```

### Delete User
```sql
DELETE FROM users WHERE email = 'user@example.com';
```

### List All Admins
```sql
SELECT id, name, email FROM users WHERE role = 'ADMIN';
```

### Count Users by Role
```sql
SELECT role, COUNT(*) as count FROM users GROUP BY role;
```

## Need Help?

If you're still having issues:

1. Check backend logs for errors
2. Check browser console (F12) for JavaScript errors
3. Verify MySQL is running: `mysql -u root -p`
4. Verify backend is running: `curl http://localhost:8050/api/sweets`
5. Check `USER_ROLES_GUIDE.md` for detailed information
