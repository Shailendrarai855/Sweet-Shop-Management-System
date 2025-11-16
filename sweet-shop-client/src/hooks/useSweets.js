import { useState, useEffect } from 'react';
import { sweetService } from '../services/sweetService';

export const useSweets = () => {
  const [sweets, setSweets] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchSweets = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await sweetService.getAllSweets();
      setSweets(data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch sweets');
    } finally {
      setLoading(false);
    }
  };

  const searchSweets = async (params) => {
    setLoading(true);
    setError(null);
    try {
      const data = await sweetService.searchSweets(params);
      setSweets(data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to search sweets');
    } finally {
      setLoading(false);
    }
  };

  const addSweet = async (sweet) => {
    setError(null);
    try {
      const newSweet = await sweetService.addSweet(sweet);
      setSweets((prev) => [...prev, newSweet]);
      return newSweet;
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to add sweet');
      throw err;
    }
  };

  const updateSweet = async (id, sweet) => {
    setError(null);
    try {
      const updated = await sweetService.updateSweet(id, sweet);
      setSweets((prev) => prev.map((s) => (s.id === id ? updated : s)));
      return updated;
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update sweet');
      throw err;
    }
  };

  const deleteSweet = async (id) => {
    setError(null);
    try {
      await sweetService.deleteSweet(id);
      setSweets((prev) => prev.filter((s) => s.id !== id));
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete sweet');
      throw err;
    }
  };

  const purchaseSweet = async (id, qty = 1) => {
    setError(null);
    try {
      await sweetService.purchaseSweet(id, qty);
      setSweets((prev) =>
        prev.map((s) => (s.id === id ? { ...s, quantity: s.quantity - qty } : s))
      );
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to purchase sweet');
      throw err;
    }
  };

  const restockSweet = async (id, qty) => {
    setError(null);
    try {
      await sweetService.restockSweet(id, qty);
      setSweets((prev) =>
        prev.map((s) => (s.id === id ? { ...s, quantity: s.quantity + qty } : s))
      );
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to restock sweet');
      throw err;
    }
  };

  useEffect(() => {
    fetchSweets();
  }, []);

  return {
    sweets,
    loading,
    error,
    fetchSweets,
    searchSweets,
    addSweet,
    updateSweet,
    deleteSweet,
    purchaseSweet,
    restockSweet,
  };
};
