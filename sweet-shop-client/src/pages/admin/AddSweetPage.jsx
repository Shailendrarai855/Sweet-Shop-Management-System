import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { sweetService } from '../../services/sweetService'
import Input from '../../components/Input'
import Button from '../../components/Button'

const AddSweetPage = () => {
  const navigate = useNavigate()
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    price: '',
    quantity: '',
    category: ''
  })
  const [errors, setErrors] = useState({})
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
    setErrors({ ...errors, [e.target.name]: '' })
  }

  const validateForm = () => {
    const newErrors = {}

    if (!formData.name.trim()) {
      newErrors.name = 'Name is required'
    }

    if (!formData.description.trim()) {
      newErrors.description = 'Description is required'
    }

    if (!formData.price || formData.price <= 0) {
      newErrors.price = 'Price must be greater than 0'
    }

    if (!formData.quantity || formData.quantity < 0) {
      newErrors.quantity = 'Quantity must be 0 or greater'
    }

    return newErrors
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    const newErrors = validateForm()
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors)
      return
    }

    setLoading(true)

    try {
      await sweetService.createSweet({
        name: formData.name,
        description: formData.description,
        price: parseFloat(formData.price),
        quantity: parseInt(formData.quantity),
        category: formData.category || null
      })
      navigate('/dashboard')
    } catch (error) {
      setErrors({ 
        general: error.response?.data?.message || 'Failed to create sweet' 
      })
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="max-w-2xl mx-auto">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
          Add New Sweet
        </h1>
        <p className="text-gray-600 dark:text-gray-400">
          Create a new sweet product
        </p>
      </div>

      <div className="card">
        {errors.general && (
          <div className="mb-4 p-3 bg-red-100 border border-red-400 text-red-700 rounded-lg">
            {errors.general}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <Input
            label="Sweet Name"
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="e.g., Chocolate Truffle"
            required
            error={errors.name}
          />

          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
              Description <span className="text-red-500">*</span>
            </label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="Describe the sweet..."
              required
              rows="4"
              className={`input ${errors.description ? 'border-red-500' : ''}`}
            />
            {errors.description && (
              <p className="mt-1 text-sm text-red-600">{errors.description}</p>
            )}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <Input
              label="Price"
              type="number"
              name="price"
              value={formData.price}
              onChange={handleChange}
              placeholder="0.00"
              step="0.01"
              min="0"
              required
              error={errors.price}
            />

            <Input
              label="Quantity"
              type="number"
              name="quantity"
              value={formData.quantity}
              onChange={handleChange}
              placeholder="0"
              min="0"
              required
              error={errors.quantity}
            />
          </div>

          <Input
            label="Category"
            type="text"
            name="category"
            value={formData.category}
            onChange={handleChange}
            placeholder="e.g., Chocolate, Candy, Gummy"
          />

          <div className="flex gap-3 justify-end mt-6">
            <Button
              type="button"
              variant="secondary"
              onClick={() => navigate('/dashboard')}
              disabled={loading}
            >
              Cancel
            </Button>
            <Button
              type="submit"
              variant="primary"
              disabled={loading}
            >
              {loading ? 'Creating...' : 'Create Sweet'}
            </Button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default AddSweetPage
