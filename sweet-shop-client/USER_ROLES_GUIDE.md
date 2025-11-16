# User Roles Guide - Admin vs Regular User

## Current Implementation: Secure Registration (All Users as Customers) ✅

**All users register as regular customers (USER role) by default.**

Admin accounts must be created manually by updating the database. This ensures security and prevents unauthorized admin access.

### How It Works

1. **Registration Page** (`/register`)
   - User fills in name, email, password
   - Backend automatically creates user with USER role
   - No option to select admin role during registration

2. **Login**
   - User logs in with email and password
   - Backend returns JWT token with role information
   - Frontend extracts role from JWT and stores in localStorage

3. **Access Control**
   - Regular users see: Home page with purchase functionality
   - Admin users see: Home page + Admin Panel link in navbar
   - Admin Panel route is protected (only accessible to ADMIN role)

## Alternative Approaches

### Option 1: Default USER, Manual Database Update (Current) ✅
**How it works:**
1. All users register as USER by default
2. Admin manually updates role in database:
   ```sql
   UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
   ```

**Pros:**
- More secure - admins are manually approved
- Simple implementation

**Cons:**
- Requires database access
- Manual process

**Best for:** Most applications, production-ready

### Option 2: Admin Invitation System (Enterprise)
**How it works:**
1. Regular users register normally (USER role)
2. Existing admin sends invitation link with token
3. New admin registers using special link
4. Backend validates token and creates ADMIN user

**Pros:**
- Secure - only invited users become admins
- Professional approach
- Audit trail of who invited whom

**Cons:**
- More complex to implement
- Requires email system

**Best for:** Large enterprise applications

### Option 3: First User is Admin
**How it works:**
1. First registered user automatically becomes ADMIN
2. All subsequent users are USER by default
3. Existing admins can promote other users

**Pros:**
- Simple initial setup
- Secure after first user

**Cons:**
- Race condition if multiple users register simultaneously

**Best for:** New applications with single initial admin

## Current Setup Instructions

### Create an Admin Account

**Step 1: Register as Regular User**
1. Go to `/register`
2. Fill in your details (name, email, password)
3. Click Register
4. Account is created with USER role

**Step 2: Promote to Admin in Database**
```sql
-- Connect to MySQL
mysql -u root -p

-- Select database
USE sweetshop;

-- Update user role
UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';

-- Verify the change
SELECT id, name, email, role FROM users WHERE email = 'your-email@example.com';
```

**Step 3: Login as Admin**
1. If already logged in, logout first
2. Clear browser localStorage:
   - Press F12 to open DevTools
   - Go to Application tab
   - Click Local Storage → http://localhost:3000
   - Click "Clear All"
3. Login with your credentials
4. You'll now see "Admin Panel" link in navbar

### Register as Regular User
1. Go to `/register`
2. Fill in your details
3. Click Register
4. Login with your credentials
5. You can browse and purchase sweets

## Verifying User Role

### Check in Browser
After logging in, you can verify your role:
1. Open DevTools (F12)
2. Go to Application tab
3. Click Local Storage → http://localhost:3000
4. Look at the "user" key
5. You should see: `{"id":1,"name":"...","email":"...","role":"ADMIN"}`

### Check in Database
```sql
SELECT id, name, email, role FROM users;
```

### Change User Role in Database
```sql
-- Make user an admin
UPDATE users SET role = 'ADMIN' WHERE email = 'user@example.com';

-- Make user a regular customer
UPDATE users SET role = 'USER' WHERE email = 'admin@example.com';
```

## Security Considerations

### Current Implementation (Production-Ready) ✅
- ✅ Role is stored in database
- ✅ Role is included in JWT token
- ✅ Backend validates role for protected endpoints
- ✅ Users cannot self-register as admin
- ✅ Only database administrators can create admin accounts
- ✅ Secure and suitable for production

### For Production
Consider implementing:
1. **Admin Invitation System**
   - Only existing admins can invite new admins
   - Invitation tokens expire after 24 hours

2. **Email Verification**
   - Verify email before activating account
   - Prevents fake admin accounts

3. **Two-Factor Authentication**
   - Extra security for admin accounts
   - SMS or authenticator app

4. **Audit Logging**
   - Log all admin actions
   - Track who did what and when

5. **Role Hierarchy**
   - Super Admin (can create other admins)
   - Admin (can manage inventory)
   - User (can purchase)

## Optional Enhancements for Production

### Step 1: Create Admin Promotion Endpoint
```java
// In backend
@PostMapping("/admin/promote-user")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> promoteUser(@RequestParam Long userId) {
    userService.promoteToAdmin(userId);
    return ResponseEntity.ok("User promoted to admin");
}
```

### Step 2: Create Admin Management Page
- List all users
- Button to promote USER to ADMIN
- Button to demote ADMIN to USER
- Only accessible to existing admins

## Testing Different Roles

### Test as Regular User
1. Register with "Customer" role
2. Login
3. Verify you can:
   - ✅ Browse sweets
   - ✅ Search sweets
   - ✅ Purchase sweets
   - ❌ Cannot access `/admin`
   - ❌ No "Admin Panel" link in navbar

### Test as Admin
1. Register with "Admin" role
2. Login
3. Verify you can:
   - ✅ Browse sweets
   - ✅ Access `/admin` route
   - ✅ See "Admin Panel" link in navbar
   - ✅ Add new sweets
   - ✅ Edit sweets
   - ✅ Delete sweets
   - ✅ Restock inventory

## Troubleshooting

### Issue: Registered as admin but can't access admin panel
**Solution:**
1. Logout
2. Clear localStorage (F12 → Application → Local Storage → Clear)
3. Login again
4. Check if "Admin Panel" link appears in navbar

### Issue: Want to change existing user's role
**Solution:**
```sql
-- In MySQL database
UPDATE users SET role = 'ADMIN' WHERE email = 'user@example.com';
```
Then user must logout and login again.

### Issue: Backend returns 403 Forbidden
**Cause:** User doesn't have ADMIN role
**Solution:** 
1. Check role in database
2. Update if needed
3. Logout and login again

## Summary

**Current Setup (Secure & Production-Ready):**
- ✅ All users register as regular customers (USER)
- ✅ No role selection in registration form
- ✅ Admin accounts created manually via database
- ✅ Prevents unauthorized admin access
- ✅ Suitable for production use

**Optional Enhancements:**
- Implement admin invitation system for easier admin creation
- Add email verification for all accounts
- Consider two-factor authentication for admin accounts
- Create admin management UI for promoting users
