import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { sweetService } from '../services/sweetService'
import Button from './Button'
import Modal from './Modal'

const SweetCard = ({ sweet, onUpdate, onDelete }) => {
  const { isAdmin } = useAuth()
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)
  const [showDeleteModal, setShowDeleteModal] = useState(false)
  const [showRestockModal, setShowRestockModal] = useState(false)
  const [restockQuantity, setRestockQuantity] = useState('')

  const handlePurchase = async () => {
    if (sweet.quantity <= 0) return
    
    setLoading(true)
    try {
      await sweetService.purchaseSweet(sweet.id)
      onUpdate()
    } catch (error) {
      alert(error.response?.data?.message || 'Purchase failed')
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async () => {
    setLoading(true)
    try {
      await sweetService.deleteSweet(sweet.id)
      onDelete(sweet.id)
      setShowDeleteModal(false)
    } catch (error) {
      alert(error.response?.data?.message || 'Delete failed')
    } finally {
      setLoading(false)
    }
  }

  const handleRestock = async () => {
    if (!restockQuantity || restockQuantity <= 0) {
      alert('Please enter a valid quantity')
      return
    }

    setLoading(true)
    try {
      await sweetService.restockSweet(sweet.id, parseInt(restockQuantity))
      setShowRestockModal(false)
      setRestockQuantity('')
      onUpdate()
    } catch (error) {
      alert(error.response?.data?.message || 'Restock failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <>
      <div className="card hover:shadow-xl transition-shadow duration-300">
        <div className="flex justify-between items-start mb-4">
          <h3 className="text-xl font-bold text-gray-900 dark:text-white">
            {sweet.name}
          </h3>
          <span className={`px-3 py-1 rounded-full text-sm font-medium ${
            sweet.quantity > 10 
              ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200'
              : sweet.quantity > 0
              ? 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200'
              : 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
          }`}>
            {sweet.quantity > 0 ? `${sweet.quantity} in stock` : 'Out of stock'}
          </span>
        </div>

        <p className="text-gray-600 dark:text-gray-400 mb-4 line-clamp-2">
          {sweet.description}
        </p>

        <div className="flex items-center justify-between mb-4">
          <span className="text-2xl font-bold text-primary-600 dark:text-primary-400">
            ${sweet.price?.toFixed(2)}
          </span>
          {sweet.category && (
            <span className="text-sm text-gray-500 dark:text-gray-400">
              {sweet.category}
            </span>
          )}
        </div>

        <div className="flex gap-2">
          {!isAdmin() ? (
            <Button
              variant="primary"
              onClick={handlePurchase}
              disabled={sweet.quantity <= 0 || loading}
              className="flex-1"
            >
              {loading ? 'Processing...' : sweet.quantity <= 0 ? 'Out of Stock' : 'Purchase'}
            </Button>
          ) : (
            <>
              <Button
                variant="secondary"
                onClick={() => navigate(`/admin/edit-sweet/${sweet.id}`)}
                className="flex-1"
              >
                Edit
              </Button>
              <Button
                variant="success"
                onClick={() => setShowRestockModal(true)}
                className="flex-1"
              >
                Restock
              </Button>
              <Button
                variant="danger"
                onClick={() => setShowDeleteModal(true)}
              >
                Delete
              </Button>
            </>
          )}
        </div>
      </div>

      {/* Delete Confirmation Modal */}
      <Modal
        isOpen={showDeleteModal}
        onClose={() => setShowDeleteModal(false)}
        title="Delete Sweet"
      >
        <p className="text-gray-600 dark:text-gray-400 mb-6">
          Are you sure you want to delete "{sweet.name}"? This action cannot be undone.
        </p>
        <div className="flex gap-3 justify-end">
          <Button
            variant="secondary"
            onClick={() => setShowDeleteModal(false)}
            disabled={loading}
          >
            Cancel
          </Button>
          <Button
            variant="danger"
            onClick={handleDelete}
            disabled={loading}
          >
            {loading ? 'Deleting...' : 'Delete'}
          </Button>
        </div>
      </Modal>

      {/* Restock Modal */}
      <Modal
        isOpen={showRestockModal}
        onClose={() => setShowRestockModal(false)}
        title="Restock Sweet"
      >
        <div className="mb-6">
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
            Quantity to Add
          </label>
          <input
            type="number"
            min="1"
            value={restockQuantity}
            onChange={(e) => setRestockQuantity(e.target.value)}
            className="input"
            placeholder="Enter quantity"
          />
        </div>
        <div className="flex gap-3 justify-end">
          <Button
            variant="secondary"
            onClick={() => setShowRestockModal(false)}
            disabled={loading}
          >
            Cancel
          </Button>
          <Button
            variant="success"
            onClick={handleRestock}
            disabled={loading}
          >
            {loading ? 'Restocking...' : 'Restock'}
          </Button>
        </div>
      </Modal>
    </>
  )
}

export default SweetCard
