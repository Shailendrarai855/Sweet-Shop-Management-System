# Frontend Setup Instructions

## Sweet Shop Client - React Frontend

This document provides instructions for setting up and running the React frontend application.

## Prerequisites

- Node.js 18 or higher
- npm (comes with Node.js)
- Backend server running on http://localhost:8050

## Installation

1. Navigate to the frontend directory:
```bash
cd sweet-shop-client
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

4. Open your browser and visit:
```
http://localhost:3000
```

## First Time Usage

### Register a User
1. Click "Register" in the navigation bar
2. Fill in your name, email, and password
3. Submit the form

### Login
1. Click "Login" in the navigation bar
2. Enter your email and password
3. You'll be redirected to the home page

### For Admin Access
To access admin features, you need to set the user role to 'ADMIN' in the database after registration.

## Features Overview

### User Features
- Browse all sweets
- Search by name, category, or price range
- Purchase sweets (inventory updates automatically)
- View stock levels

### Admin Features
- All user features
- Add new sweets
- Edit sweet details
- Delete sweets
- Restock inventory

## Technology Stack

- **React 18** - Modern UI library with hooks
- **Vite** - Fast build tool and dev server
- **Tailwind CSS** - Utility-first CSS framework
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls

## Project Structure

```
sweet-shop-client/
├── src/
│   ├── components/     # Reusable UI components
│   ├── pages/          # Page views
│   ├── services/       # API integration
│   ├── hooks/          # Custom React hooks
│   ├── context/        # Global state management
│   └── App.jsx         # Main application
├── public/             # Static assets
└── package.json        # Dependencies
```

## Available Commands

```bash
npm run dev      # Start development server
npm run build    # Build for production
npm run preview  # Preview production build
npm run lint     # Run ESLint
```

## API Integration

The frontend communicates with the Spring Boot backend through these endpoints:

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/sweets` - Get all sweets
- `GET /api/sweets/search` - Search sweets
- `POST /api/sweets` - Add sweet (protected)
- `PUT /api/sweets/:id` - Update sweet (protected)
- `DELETE /api/:id` - Delete sweet (admin only)
- `POST /sweets/:id/purchase` - Purchase sweet (protected)
- `POST /sweets/:id/restock` - Restock sweet (admin only)

## Troubleshooting

**Cannot connect to backend:**
- Verify backend is running on port 8050
- Check CORS configuration in backend

**Port 3000 already in use:**
- Vite will automatically use the next available port

**Login issues:**
- Clear browser localStorage
- Check browser console for errors
- Verify backend is accessible

## Building for Production

```bash
npm run build
```

The production build will be created in the `dist` folder.

## Additional Documentation

For more detailed information, see `sweet-shop-client/README.md`
