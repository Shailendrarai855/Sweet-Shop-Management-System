import { useState, useEffect } from 'react'
import { sweetService } from '../services/sweetService'
import SweetCard from '../components/SweetCard'
import SearchBar from '../components/SearchBar'
import LoadingSpinner from '../components/LoadingSpinner'

const DashboardPage = () => {
  const [sweets, setSweets] = useState([])
  const [filteredSweets, setFilteredSweets] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [searchQuery, setSearchQuery] = useState('')

  useEffect(() => {
    fetchSweets()
  }, [])

  const fetchSweets = async () => {
    setLoading(true)
    setError('')
    try {
      const data = await sweetService.getAllSweets()
      setSweets(data)
      setFilteredSweets(data)
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to load sweets')
    } finally {
      setLoading(false)
    }
  }

  const handleSearch = async (query) => {
    setSearchQuery(query)
    
    if (!query.trim()) {
      setFilteredSweets(sweets)
      return
    }

    try {
      const data = await sweetService.searchSweets(query)
      setFilteredSweets(data)
    } catch (err) {
      setError(err.response?.data?.message || 'Search failed')
    }
  }

  const handleUpdate = () => {
    fetchSweets()
  }

  const handleDelete = (id) => {
    setSweets(sweets.filter(sweet => sweet.id !== id))
    setFilteredSweets(filteredSweets.filter(sweet => sweet.id !== id))
  }

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-[60vh]">
        <LoadingSpinner size="large" />
      </div>
    )
  }

  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
          Sweet Shop Dashboard
        </h1>
        <p className="text-gray-600 dark:text-gray-400">
          Browse and purchase delicious sweets
        </p>
      </div>

      <div className="mb-6">
        <SearchBar onSearch={handleSearch} />
      </div>

      {error && (
        <div className="mb-6 p-4 bg-red-100 border border-red-400 text-red-700 rounded-lg">
          {error}
        </div>
      )}

      {filteredSweets.length === 0 ? (
        <div className="text-center py-12">
          <span className="text-6xl mb-4 block">🍭</span>
          <h3 className="text-xl font-semibold text-gray-900 dark:text-white mb-2">
            No sweets found
          </h3>
          <p className="text-gray-600 dark:text-gray-400">
            {searchQuery ? 'Try a different search term' : 'Check back later for new sweets'}
          </p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredSweets.map((sweet) => (
            <SweetCard
              key={sweet.id}
              sweet={sweet}
              onUpdate={handleUpdate}
              onDelete={handleDelete}
            />
          ))}
        </div>
      )}
    </div>
  )
}

export default DashboardPage
