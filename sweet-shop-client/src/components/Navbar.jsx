import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
  const { user, isAuthenticated, isAdmin, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="bg-gradient-to-r from-pink-500 to-red-500 text-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          <Link to="/" className="text-2xl font-bold flex items-center gap-2">
            🍬 Sweet Shop
          </Link>

          <div className="flex items-center gap-6">
            {isAuthenticated ? (
              <>
                <Link to="/" className="hover:text-pink-200 transition">
                  Home
                </Link>
                {isAdmin && (
                  <Link to="/admin" className="hover:text-pink-200 transition">
                    Admin Panel
                  </Link>
                )}
                <div className="flex items-center gap-4">
                  <span className="text-sm">
                    Welcome, {user?.name} {isAdmin && '(Admin)'}
                  </span>
                  <button
                    onClick={handleLogout}
                    className="bg-white text-pink-600 px-4 py-2 rounded-lg hover:bg-pink-100 transition font-medium"
                  >
                    Logout
                  </button>
                </div>
              </>
            ) : (
              <>
                <Link
                  to="/login"
                  className="hover:text-pink-200 transition font-medium"
                >
                  Login
                </Link>
                <Link
                  to="/register"
                  className="bg-white text-pink-600 px-4 py-2 rounded-lg hover:bg-pink-100 transition font-medium"
                >
                  Register
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
