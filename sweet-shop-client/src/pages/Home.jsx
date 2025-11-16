import { useState } from 'react';
import { useSweets } from '../hooks/useSweets';
import { useAuth } from '../context/AuthContext';
import SearchBar from '../components/SearchBar';
import SweetCard from '../components/SweetCard';

const Home = () => {
  const { sweets, loading, error, searchSweets, purchaseSweet, fetchSweets } = useSweets();
  const { isAdmin } = useAuth();
  const [notification, setNotification] = useState('');

  const handleSearch = async (params) => {
    if (Object.keys(params).length === 0) {
      await fetchSweets();
    } else {
      await searchSweets(params);
    }
  };

  const handlePurchase = async (id, qty) => {
    try {
      await purchaseSweet(id, qty);
      showNotification('Purchase successful!');
    } catch (err) {
      showNotification('Purchase failed: ' + err.message, true);
    }
  };

  const showNotification = (message, isError = false) => {
    setNotification({ message, isError });
    setTimeout(() => setNotification(''), 3000);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-pink-50 to-red-50">
      <div className="container mx-auto px-4 py-8">
        <div className="text-center mb-8">
          <h1 className="text-5xl font-bold text-gray-800 mb-4">
            Welcome to Sweet Shop 🍬
          </h1>
          <p className="text-xl text-gray-600">
            Discover our delicious collection of sweets
          </p>
        </div>

        {notification && (
          <div
            className={`fixed top-20 right-4 px-6 py-3 rounded-lg shadow-lg z-50 ${
              notification.isError
                ? 'bg-red-500 text-white'
                : 'bg-green-500 text-white'
            }`}
          >
            {notification.message}
          </div>
        )}

        <SearchBar onSearch={handleSearch} />

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg mb-6">
            {error}
          </div>
        )}

        {loading ? (
          <div className="text-center py-12">
            <div className="text-2xl text-gray-600">Loading sweets...</div>
          </div>
        ) : sweets.length === 0 ? (
          <div className="text-center py-12">
            <p className="text-2xl text-gray-600">No sweets found</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {sweets.map((sweet) => (
              <SweetCard
                key={sweet.id}
                sweet={sweet}
                onPurchase={handlePurchase}
                isAdmin={isAdmin}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;
