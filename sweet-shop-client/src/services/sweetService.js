import api from './api'

export const sweetService = {
  getAllSweets: async () => {
    const response = await api.get('/sweets')
    return response.data
  },

  searchSweets: async (query) => {
    const response = await api.get('/sweets/search', {
      params: { query }
    })
    return response.data
  },

  getSweetById: async (id) => {
    const response = await api.get(`/sweets/${id}`)
    return response.data
  },

  createSweet: async (sweetData) => {
    const response = await api.post('/sweets', sweetData)
    return response.data
  },

  updateSweet: async (id, sweetData) => {
    const response = await api.put(`/sweets/${id}`, sweetData)
    return response.data
  },

  deleteSweet: async (id) => {
    const response = await api.delete(`/sweets/${id}`)
    return response.data
  },

  purchaseSweet: async (id, quantity = 1) => {
    const response = await api.post(`/sweets/${id}/purchase`, { quantity })
    return response.data
  },

  restockSweet: async (id, quantity) => {
    const response = await api.post(`/sweets/${id}/restock`, { quantity })
    return response.data
  },
}
