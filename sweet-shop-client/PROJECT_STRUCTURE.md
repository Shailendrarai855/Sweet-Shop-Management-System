# Sweet Shop Management System - Complete File Structure

## 📂 Root Level Files

```
sweet-shop-frontend/
├── index.html                    # HTML entry point
├── package.json                  # Dependencies and scripts
├── vite.config.js               # Vite configuration
├── tailwind.config.js           # Tailwind CSS configuration
├── postcss.config.js            # PostCSS configuration
├── .env                         # Environment variables
├── .env.example                 # Environment variables template
├── .gitignore                   # Git ignore rules
├── README.md                    # Project documentation
└── PROJECT_STRUCTURE.md         # This file
```

## 📂 Source Directory Structure

```
src/
├── main.jsx                     # React app entry point
├── App.jsx                      # Main app with routing
├── index.css                    # Global styles + Tailwind
│
├── components/                  # Reusable UI components
│   ├── AdminRoute.jsx          # Admin route protection wrapper
│   ├── Button.jsx              # Reusable button with variants
│   ├── Input.jsx               # Reusable input with validation
│   ├── Layout.jsx              # Main layout with navbar
│   ├── LoadingSpinner.jsx      # Loading indicator
│   ├── Modal.jsx               # Modal dialog component
│   ├── Navbar.jsx              # Navigation bar with auth
│   ├── PrivateRoute.jsx        # Auth route protection
│   ├── SearchBar.jsx           # Search input with clear
│   └── SweetCard.jsx           # Sweet product card
│
├── context/                     # React Context providers
│   └── AuthContext.jsx         # Authentication state management
│
├── pages/                       # Page components
│   ├── LoginPage.jsx           # User login page
│   ├── RegisterPage.jsx        # User registration page
│   ├── DashboardPage.jsx       # Main dashboard with sweets
│   └── admin/                  # Admin-only pages
│       ├── AddSweetPage.jsx    # Add new sweet form
│       └── EditSweetPage.jsx   # Edit sweet form
│
└── services/                    # API service layer
    ├── api.js                  # Axios instance + interceptors
    ├── authService.js          # Authentication API calls
    └── sweetService.js         # Sweet management API calls
```

## 🎯 Component Breakdown

### Core Components (10 files)
1. **AdminRoute.jsx** - Protects admin-only routes
2. **Button.jsx** - Styled button with primary/secondary/danger/success variants
3. **Input.jsx** - Form input with label, error handling, validation
4. **Layout.jsx** - Page wrapper with Navbar
5. **LoadingSpinner.jsx** - Animated loading indicator
6. **Modal.jsx** - Reusable modal dialog with backdrop
7. **Navbar.jsx** - Top navigation with user info, dark mode toggle, logout
8. **PrivateRoute.jsx** - Protects authenticated routes
9. **SearchBar.jsx** - Search input with submit and clear functionality
10. **SweetCard.jsx** - Product card with purchase/edit/delete/restock actions

### Pages (5 files)
1. **LoginPage.jsx** - Email/password login form
2. **RegisterPage.jsx** - User registration with validation
3. **DashboardPage.jsx** - Main page showing all sweets with search
4. **AddSweetPage.jsx** - Admin form to create new sweet
5. **EditSweetPage.jsx** - Admin form to update existing sweet

### Services (3 files)
1. **api.js** - Axios configuration with JWT interceptors
2. **authService.js** - Login and register API calls
3. **sweetService.js** - CRUD operations for sweets

### Context (1 file)
1. **AuthContext.jsx** - Global auth state with login/logout/register

## 🔌 API Endpoints Integration

### Authentication Endpoints
- `POST /api/auth/register` → authService.register()
- `POST /api/auth/login` → authService.login()

### Sweet Management Endpoints
- `GET /api/sweets` → sweetService.getAllSweets()
- `GET /api/sweets/search?query=` → sweetService.searchSweets()
- `GET /api/sweets/:id` → sweetService.getSweetById()
- `POST /api/sweets` → sweetService.createSweet() [Admin]
- `PUT /api/sweets/:id` → sweetService.updateSweet() [Admin]
- `DELETE /api/sweets/:id` → sweetService.deleteSweet() [Admin]
- `POST /api/sweets/:id/purchase` → sweetService.purchaseSweet()
- `POST /api/sweets/:id/restock` → sweetService.restockSweet() [Admin]

## 🎨 Styling System

### Tailwind CSS Classes
- Custom button variants: `btn-primary`, `btn-secondary`, `btn-danger`, `btn-success`
- Custom input styles: `input` class with focus states
- Custom card styles: `card` class with dark mode support
- Responsive grid layouts for sweet cards
- Dark mode support throughout

### Color Scheme
- Primary: Red shades (Sweet shop theme)
- Gray scale for neutral elements
- Success: Green for positive actions
- Danger: Red for destructive actions
- Dark mode: Gray-900 backgrounds

## 🔐 Authentication Flow

1. User enters credentials on LoginPage/RegisterPage
2. AuthContext calls authService methods
3. JWT token received and stored in localStorage
4. Token attached to all API requests via Axios interceptor
5. PrivateRoute checks authentication before rendering
6. AdminRoute checks admin role for admin pages
7. 401 responses trigger auto-logout and redirect

## 🛣️ Routing Structure

```
/ → Redirect to /dashboard
/login → LoginPage (public)
/register → RegisterPage (public)
/dashboard → DashboardPage (protected)
/admin/add-sweet → AddSweetPage (admin only)
/admin/edit-sweet/:id → EditSweetPage (admin only)
* → Redirect to /dashboard
```

## 📦 Dependencies

### Production
- react: ^18.2.0
- react-dom: ^18.2.0
- react-router-dom: ^6.20.0
- axios: ^1.6.2

### Development
- vite: ^5.0.8
- @vitejs/plugin-react: ^4.2.1
- tailwindcss: ^3.3.6
- autoprefixer: ^10.4.16
- postcss: ^8.4.32
- eslint: ^8.55.0

## 🚀 Quick Start Commands

```bash
# Install dependencies
npm install

# Start development server (http://localhost:3000)
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

## ✅ Features Checklist

### Customer Features
- ✅ User registration with validation
- ✅ User login with JWT
- ✅ Browse all sweets
- ✅ Search sweets by name
- ✅ Purchase sweets (disabled when quantity = 0)
- ✅ Responsive design
- ✅ Dark mode toggle

### Admin Features
- ✅ Add new sweets
- ✅ Edit existing sweets
- ✅ Delete sweets with confirmation
- ✅ Restock sweets with custom quantity
- ✅ Protected admin routes
- ✅ Admin badge in navbar

### Technical Features
- ✅ JWT token authentication
- ✅ Axios interceptors for auth
- ✅ Protected routes
- ✅ Context API for state
- ✅ Form validation
- ✅ Error handling
- ✅ Loading states
- ✅ Modal dialogs
- ✅ Responsive UI
- ✅ Dark mode support

## 📝 Total File Count

- **Root Config Files**: 9
- **Source Files**: 19
- **Total**: 28 files

All files are complete and production-ready!
