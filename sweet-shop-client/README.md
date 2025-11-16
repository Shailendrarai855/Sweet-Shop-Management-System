# Sweet Shop Client

A modern React frontend for the Sweet Shop Management System built with Vite, JavaScript, and Tailwind CSS.

## Tech Stack

- **React 18** - UI library
- **JavaScript (ES6+)** - Programming language
- **Vite** - Build tool and dev server
- **Tailwind CSS** - Utility-first CSS framework
- **React Router** - Client-side routing
- **Axios** - HTTP client

## Quick Start

### Prerequisites

- Node.js 18+ and npm
- Backend server running on http://localhost:8050

### Installation

```bash
cd sweet-shop-client
npm install
```

### Development

```bash
npm run dev
```

The application will be available at http://localhost:3000

### Build for Production

```bash
npm run build
npm run preview
```

## Features

### User Features
- 🔐 Secure authentication (register/login) with JWT
- 🍬 Browse all available sweets
- 🔍 Search and filter sweets by name, category, and price range
- 🛒 Purchase sweets (updates inventory in real-time)
- 📊 View stock levels

### Admin Features
- ➕ Add new sweets to inventory
- ✏️ Edit sweet details
- 🗑️ Delete sweets
- 📦 Restock inventory
- 🎛️ Dedicated admin panel

## Project Structure

```
sweet-shop-client/
├── public/              # Static assets
│   └── candy.svg       # App icon
├── src/
│   ├── components/     # Reusable UI components
│   │   ├── Navbar.jsx
│   │   ├── SearchBar.jsx
│   │   ├── SweetCard.jsx
│   │   ├── SweetModal.jsx
│   │   └── ProtectedRoute.jsx
│   ├── pages/          # Page components
│   │   ├── Home.jsx
│   │   ├── Login.jsx
│   │   ├── Register.jsx
│   │   └── AdminPanel.jsx
│   ├── services/       # API services
│   │   ├── api.js
│   │   ├── authService.js
│   │   └── sweetService.js
│   ├── hooks/          # Custom React hooks
│   │   └── useSweets.js
│   ├── context/        # React context
│   │   └── AuthContext.jsx
│   ├── App.jsx         # Main app component
│   ├── main.jsx        # Entry point
│   └── index.css       # Global styles
├── index.html          # HTML template
├── vite.config.ts      # Vite configuration
├── tailwind.config.js  # Tailwind configuration
└── package.json        # Dependencies and scripts
```

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## API Integration

The frontend connects to the Spring Boot backend at `http://localhost:8050`. All API calls are proxied through Vite's dev server.

### API Endpoints

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `GET /api/sweets` - Get all sweets
- `GET /api/sweets/search` - Search sweets
- `POST /api/sweets` - Add new sweet (protected)
- `PUT /api/sweets/:id` - Update sweet (protected)
- `DELETE /api/:id` - Delete sweet (admin only)
- `POST /sweets/:id/purchase` - Purchase sweet (protected)
- `POST /sweets/:id/restock` - Restock sweet (admin only)

## My AI Usage

This project was developed with assistance from AI tools to accelerate development while maintaining code quality and best practices.

### AI Tools Used

- **Kiro AI Assistant** - Used throughout the development process

### How AI Was Used

1. **Project Setup**: AI helped generate the initial project structure, configuration files (vite.config.ts, tailwind.config.js), and package.json with appropriate dependencies.

2. **Boilerplate Code**: AI generated boilerplate for:
   - API service layer with axios configuration
   - Authentication context and hooks
   - Component structure and initial implementations
   - React Router setup and protected routes

3. **Component Development**: AI assisted in creating reusable components like SweetCard, SearchBar, and SweetModal with proper state management.

4. **Styling**: AI provided the Tailwind configuration with custom color palette suitable for a sweet shop theme and helped implement responsive designs.

### Reflection on AI Impact

Using AI significantly accelerated the initial setup and boilerplate generation, allowing me to focus on:
- Business logic implementation
- User experience design
- Component architecture and state management
- Integration with the Spring Boot backend

The AI was particularly helpful in:
- Setting up the Vite + React + Tailwind stack
- Creating consistent component patterns
- Implementing authentication flow with JWT
- Providing best practices for React hooks and context
- Designing responsive UI layouts

However, I manually reviewed and adjusted all AI-generated code to ensure it met project requirements and followed best practices.


## Configuration

### Backend API URL

The app connects to the backend at `http://localhost:8050`. To change this, edit `src/services/api.js`:

```javascript
const api = axios.create({
  baseURL: 'http://your-backend-url',
  // ...
});
```

### Vite Proxy

The Vite dev server proxies API requests to avoid CORS issues. This is configured in `vite.config.ts`:

```javascript
server: {
  port: 3000,
  proxy: {
    '/api': {
      target: 'http://localhost:8050',
      changeOrigin: true,
    },
  },
}
```

## Troubleshooting

### Cannot connect to backend
- Ensure the Spring Boot backend is running on port 8050
- Check that CORS is properly configured in the backend
- Verify the API base URL in `src/services/api.js`

### Port 3000 is already in use
Vite will automatically try the next available port (3001, 3002, etc.)

### Module not found errors
Delete `node_modules` and reinstall:
```bash
rm -rf node_modules
npm install
```

### Login not working
- Make sure you registered first
- Check browser console for errors
- Verify JWT token is being stored in localStorage
- Check backend logs for authentication errors


## User Roles

The application supports two types of users:

### Customer (USER)
- Browse and search sweets
- Purchase sweets
- View inventory levels

### Admin (ADMIN)
- All customer features
- Access to Admin Panel
- Add new sweets
- Edit sweet details
- Delete sweets
- Restock inventory

### How to Register

**All users register as regular customers (USER role) by default.**

To create an admin account:

1. Register normally through the application
2. Update the user's role in the database:

```sql
-- Connect to MySQL database
mysql -u root -p

-- Use the sweetshop database
USE sweetshop;

-- Make user an admin
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
```

3. The user must logout and login again for changes to take effect

### Quick Admin Setup

**Step 1: Register a User**
```
1. Go to http://localhost:3000/register
2. Fill in your details
3. Click Register
```

**Step 2: Make User an Admin**
```sql
-- In MySQL
UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';
```

**Step 3: Login**
```
1. Logout if already logged in
2. Clear browser localStorage (F12 → Application → Local Storage → Clear)
3. Login again
4. You'll now see "Admin Panel" in the navbar
```

### Security Note

This approach ensures that:
- ✅ Users cannot self-register as admins
- ✅ Only database administrators can create admin accounts
- ✅ Admin access is controlled and auditable
- ✅ Suitable for production use
