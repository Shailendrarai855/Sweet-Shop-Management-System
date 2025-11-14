import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { useState } from 'react'

const Navbar = () => {
  const { user, logout, isAdmin } = useAuth()
  const navigate = useNavigate()
  const [isDarkMode, setIsDarkMode] = useState(false)

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const toggleDarkMode = () => {
    setIsDarkMode(!isDarkMode)
    document.documentElement.classList.toggle('dark')
  }

  return (
    <nav className="bg-white dark:bg-gray-800 shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          <Link to="/dashboard" className="flex items-center space-x-2">
            <span className="text-2xl">🍬</span>
            <span className="text-xl font-bold text-primary-600 dark:text-primary-400">
              Sweet Shop
            </span>
          </Link>

          <div className="flex items-center space-x-4">
            {isAdmin() && (
              <Link
                to="/admin/add-sweet"
                className="btn btn-primary text-sm"
              >
                + Add Sweet
              </Link>
            )}

            <button
              onClick={toggleDarkMode}
              className="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
              aria-label="Toggle dark mode"
            >
              {isDarkMode ? '🌞' : '🌙'}
            </button>

            <div className="flex items-center space-x-3">
              <div className="text-right">
                <p className="text-sm font-medium text-gray-900 dark:text-white">
                  {user?.name || user?.email}
                </p>
                <p className="text-xs text-gray-500 dark:text-gray-400">
                  {isAdmin() ? 'Admin' : 'Customer'}
                </p>
              </div>
              <button
                onClick={handleLogout}
                className="btn btn-secondary text-sm"
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
