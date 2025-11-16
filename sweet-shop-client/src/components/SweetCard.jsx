import { useState } from 'react';

const SweetCard = ({ sweet, onPurchase, onRestock, onEdit, onDelete, isAdmin }) => {
  const [quantity, setQuantity] = useState(1);
  const [restockQty, setRestockQty] = useState(0);
  const [showRestock, setShowRestock] = useState(false);

  const handlePurchase = async () => {
    if (quantity > 0 && quantity <= sweet.quantity) {
      await onPurchase(sweet.id, quantity);
      setQuantity(1);
    }
  };

  const handleRestock = async () => {
    if (restockQty > 0) {
      await onRestock(sweet.id, restockQty);
      setRestockQty(0);
      setShowRestock(false);
    }
  };

  return (
    <div className="bg-white rounded-xl shadow-lg overflow-hidden hover:shadow-xl transition-shadow">
      <div className="bg-gradient-to-br from-pink-400 to-red-400 h-48 flex items-center justify-center">
        <span className="text-6xl">🍬</span>
      </div>
      
      <div className="p-6">
        <h3 className="text-xl font-bold text-gray-800 mb-2">{sweet.name}</h3>
        <p className="text-gray-600 mb-2">
          <span className="font-semibold">Category:</span> {sweet.category}
        </p>
        <p className="text-2xl font-bold text-pink-600 mb-2">
          ${sweet.price.toFixed(2)}
        </p>
        <p className={`text-sm mb-4 ${sweet.quantity === 0 ? 'text-red-600' : 'text-green-600'}`}>
          {sweet.quantity === 0 ? 'Out of Stock' : `${sweet.quantity} in stock`}
        </p>

        {!isAdmin && onPurchase && (
          <div className="space-y-2">
            <div className="flex gap-2">
              <input
                type="number"
                min="1"
                max={sweet.quantity}
                value={quantity}
                onChange={(e) => setQuantity(parseInt(e.target.value) || 0)}
                onFocus={(e) => e.target.select()}
                className="w-20 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-pink-500 focus:border-transparent"
                disabled={sweet.quantity === 0}
              />
              <button
                onClick={handlePurchase}
                disabled={sweet.quantity === 0}
                className="flex-1 bg-pink-600 text-white px-4 py-2 rounded-lg hover:bg-pink-700 disabled:bg-gray-300 disabled:cursor-not-allowed transition font-medium"
              >
                Purchase
              </button>
            </div>
          </div>
        )}

        {isAdmin && onEdit && onDelete && onRestock && (
          <div className="space-y-2">
            <div className="flex gap-2">
              <button
                onClick={() => onEdit(sweet)}
                className="flex-1 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition font-medium"
              >
                Edit
              </button>
              <button
                onClick={() => onDelete(sweet.id)}
                className="flex-1 bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition font-medium"
              >
                Delete
              </button>
            </div>
            
            {!showRestock ? (
              <button
                onClick={() => setShowRestock(true)}
                className="w-full bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 transition font-medium"
              >
                Restock
              </button>
            ) : (
              <div className="flex gap-2">
                <input
                  type="number"
                  min="0"
                  value={restockQty}
                  onChange={(e) => setRestockQty(parseInt(e.target.value) || 0)}
                  onFocus={(e) => e.target.select()}
                  autoFocus
                  className="w-20 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
                />
                <button
                  onClick={handleRestock}
                  className="flex-1 bg-green-600 text-white px-2 py-2 rounded-lg hover:bg-green-700 transition text-sm font-medium"
                >
                  Confirm
                </button>
                <button
                  onClick={() => setShowRestock(false)}
                  className="px-3 py-2 bg-gray-300 rounded-lg hover:bg-gray-400 transition text-sm"
                >
                  Cancel
                </button>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default SweetCard;
