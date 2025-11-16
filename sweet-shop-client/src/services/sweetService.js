import api from './api';

export const sweetService = {
  async getAllSweets() {
    const response = await api.get('/api/sweets');
    return response.data;
  },

  async searchSweets(params) {
    const response = await api.get('/api/sweets/search', { params });
    return response.data;
  },

  async addSweet(sweet) {
    const response = await api.post('/api/sweets', sweet);
    return response.data;
  },

  async updateSweet(id, sweet) {
    const response = await api.put(`/api/sweets/${id}`, sweet);
    return response.data;
  },

  async deleteSweet(id) {
    await api.delete(`/api/${id}`);
  },

  async purchaseSweet(id, qty = 1) {
    const response = await api.post(`/sweets/${id}/purchase`, null, { params: { qty } });
    return response.data;
  },

  async restockSweet(id, qty) {
    const response = await api.post(`/sweets/${id}/restock`, null, { params: { qty } });
    return response.data;
  },
};
