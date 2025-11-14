# Sweet Shop Management System - Frontend

A modern, production-ready React frontend application for managing a sweet shop with customer and admin functionalities.

## 🚀 Tech Stack

- **React 18** - UI library with functional components and hooks
- **Vite** - Fast build tool and dev server
- **React Router v6** - Client-side routing
- **Tailwind CSS** - Utility-first CSS framework
- **Axios** - HTTP client for API calls
- **Context API** - State management for authentication

## ✨ Features

### Customer Features
- User registration and login with JWT authentication
- Browse all available sweets
- Search and filter sweets by name
- Purchase sweets (disabled when out of stock)
- Responsive design with dark mode support

### Admin Features
- Add new sweets to inventory
- Edit existing sweet details
- Delete sweets from inventory
- Restock sweets with custom quantities
- Protected admin routes

## 📁 Project Structure

```
sweet-shop-frontend/
├── src/
│   ├── components/
│   │   ├── AdminRoute.jsx       # Admin-only route protection
│   │   ├── Button.jsx           # Reusable button component
│   │   ├── Input.jsx            # Reusable input component
│   │   ├── Layout.jsx           # Main layout wrapper
│   │   ├── LoadingSpinner.jsx   # Loading indicator
│   │   ├── Modal.jsx            # Modal dialog component
│   │   ├── Navbar.jsx           # Navigation bar
│   │   ├── PrivateRoute.jsx     # Auth route protection
│   │   ├── SearchBar.jsx        # Search functionality
│   │   └── SweetCard.jsx        # Sweet product card
│   ├── context/
│   │   └── AuthContext.jsx      # Authentication context
│   ├── pages/
│   │   ├── admin/
│   │   │   ├── AddSweetPage.jsx    # Add sweet form
│   │   │   └── EditSweetPage.jsx   # Edit sweet form
│   │   ├── DashboardPage.jsx       # Main dashboard
│   │   ├── LoginPage.jsx           # Login form
│   │   └── RegisterPage.jsx        # Registration form
│   ├── services/
│   │   ├── api.js               # Axios instance with interceptors
│   │   ├── authService.js       # Authentication API calls
│   │   └── sweetService.js      # Sweet management API calls
│   ├── App.jsx                  # Main app component with routes
│   ├── main.jsx                 # App entry point
│   └── index.css                # Global styles with Tailwind
├── index.html
├── package.json
├── tailwind.config.js
├── vite.config.js
├── .env                         # Environment variables
└── README.md
```

## 🔧 Installation & Setup

### Prerequisites
- Node.js (v16 or higher)
- npm or yarn
- Backend API running on `http://localhost:8080`

### Steps

1. **Clone or extract the project**

2. **Install dependencies**
```bash
npm install
```

3. **Configure environment variables**

Create a `.env` file in the root directory:
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

4. **Start the development server**
```bash
npm run dev
```

The application will open at `http://localhost:3000`

## 🎯 Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build locally

## 🔐 Authentication

The app uses JWT token-based authentication:
- Tokens are stored in `localStorage`
- Automatically attached to API requests via Axios interceptors
- Auto-redirect to login on 401 responses
- Protected routes for authenticated users
- Admin-only routes for admin users

## 🎨 UI Features

- **Responsive Design** - Works on mobile, tablet, and desktop
- **Dark Mode** - Toggle between light and dark themes
- **Loading States** - Spinners for async operations
- **Error Handling** - User-friendly error messages
- **Form Validation** - Client-side validation with error display
- **Modals** - Confirmation dialogs for destructive actions

## 📡 API Integration

### Backend Endpoints Used

**Authentication**
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

**Sweets Management**
- `GET /api/sweets` - Get all sweets
- `GET /api/sweets/search?query=` - Search sweets
- `GET /api/sweets/:id` - Get sweet by ID
- `POST /api/sweets` - Create sweet (admin)
- `PUT /api/sweets/:id` - Update sweet (admin)
- `DELETE /api/sweets/:id` - Delete sweet (admin)
- `POST /api/sweets/:id/purchase` - Purchase sweet
- `POST /api/sweets/:id/restock` - Restock sweet (admin)

### Expected API Response Format

**Login/Register Response:**
```json
{
  "token": "jwt-token-here",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": "ADMIN" // or "USER"
  }
}
```

**Sweet Object:**
```json
{
  "id": 1,
  "name": "Chocolate Truffle",
  "description": "Rich dark chocolate truffle",
  "price": 2.99,
  "quantity": 50,
  "category": "Chocolate"
}
```

## 🎭 User Roles

- **Customer** - Can browse and purchase sweets
- **Admin** - Full access to add, edit, delete, and restock sweets

Admin users are identified by `role: "ADMIN"` in the user object.

## 🌐 Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## 📝 Notes

- Make sure the backend API is running before starting the frontend
- Default API URL is `http://localhost:8080/api` (configurable in `.env`)
- The app uses localStorage for token persistence
- Dark mode preference is not persisted (resets on page reload)

## 🚀 Production Build

To create a production build:

```bash
npm run build
```

The optimized files will be in the `dist/` directory, ready for deployment.

## 🤝 Contributing

This is a production-ready template. Feel free to customize and extend based on your needs.

## 📄 License

MIT License - feel free to use this project for learning or commercial purposes.
