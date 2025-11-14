import { useState } from 'react'

const SearchBar = ({ onSearch, placeholder = 'Search sweets...' }) => {
  const [query, setQuery] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()
    onSearch(query)
  }

  const handleClear = () => {
    setQuery('')
    onSearch('')
  }

  return (
    <form onSubmit={handleSubmit} className="relative">
      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        placeholder={placeholder}
        className="input pr-20"
      />
      <div className="absolute right-2 top-1/2 -translate-y-1/2 flex gap-2">
        {query && (
          <button
            type="button"
            onClick={handleClear}
            className="text-gray-400 hover:text-gray-600 dark:hover:text-gray-200"
          >
            ✕
          </button>
        )}
        <button
          type="submit"
          className="px-4 py-1 bg-primary-600 text-white rounded-md hover:bg-primary-700 transition-colors"
        >
          Search
        </button>
      </div>
    </form>
  )
}

export default SearchBar
