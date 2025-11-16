import { useState } from 'react';
import { useSweets } from '../hooks/useSweets';
import SweetCard from '../components/SweetCard';
import SweetModal from '../components/SweetModal';

const AdminPanel = () => {
  const { sweets, loading, error, addSweet, updateSweet, deleteSweet, restockSweet } = useSweets();
  const [showModal, setShowModal] = useState(false);
  const [editingSweet, setEditingSweet] = useState(null);
  const [notification, setNotification] = useState('');

  const handleAddClick = () => {
    setEditingSweet(null);
    setShowModal(true);
  };

  const handleEditClick = (sweet) => {
    setEditingSweet(sweet);
    setShowModal(true);
  };

  const handleSave = async (data) => {
    try {
      if (editingSweet?.id) {
        await updateSweet(editingSweet.id, data);
        showNotification('Sweet updated successfully!');
      } else {
        await addSweet(data);
        showNotification('Sweet added successfully!');
      }
      setShowModal(false);
      setEditingSweet(null);
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.message || 'Operation failed';
      showNotification('Operation failed: ' + errorMsg, true);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this sweet?')) {
      try {
        await deleteSweet(id);
        showNotification('Sweet deleted successfully!');
      } catch (err) {
        const errorMsg = err.response?.data?.message || err.message || 'Delete failed';
        showNotification('Delete failed: ' + errorMsg, true);
      }
    }
  };

  const handleRestock = async (id, qty) => {
    try {
      await restockSweet(id, qty);
      showNotification('Sweet restocked successfully!');
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.message || 'Restock failed';
      showNotification('Restock failed: ' + errorMsg, true);
    }
  };

  const showNotification = (message, isError = false) => {
    setNotification({ message, isError });
    setTimeout(() => setNotification(''), 3000);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-pink-50 to-red-50">
      <div className="container mx-auto px-4 py-8">
        <div className="flex justify-between items-center mb-8">
          <div>
            <h1 className="text-4xl font-bold text-gray-800 mb-2">Admin Panel</h1>
            <p className="text-gray-600">Manage your sweet inventory</p>
          </div>
          <button
            onClick={handleAddClick}
            className="bg-gradient-to-r from-pink-500 to-red-500 text-white px-6 py-3 rounded-lg hover:from-pink-600 hover:to-red-600 transition font-medium shadow-lg"
          >
            + Add New Sweet
          </button>
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
            <p className="text-2xl text-gray-600">No sweets in inventory</p>
            <button
              onClick={handleAddClick}
              className="mt-4 text-pink-600 hover:text-pink-700 font-medium"
            >
              Add your first sweet
            </button>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {sweets.map((sweet) => (
              <SweetCard
                key={sweet.id}
                sweet={sweet}
                onEdit={handleEditClick}
                onDelete={handleDelete}
                onRestock={handleRestock}
                isAdmin={true}
              />
            ))}
          </div>
        )}

        {showModal && (
          <SweetModal
            sweet={editingSweet}
            onClose={() => {
              setShowModal(false);
              setEditingSweet(null);
            }}
            onSave={handleSave}
          />
        )}
      </div>
    </div>
  );
};

export default AdminPanel;
