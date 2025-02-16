import sys
import yfinance as yf
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense, Dropout
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.callbacks import EarlyStopping

# Get stock ticker from Java
if len(sys.argv) < 2:
    print("Error: No stock ticker provided.")
    sys.exit(1)

stock_ticker = sys.argv[1].upper()
start_date = "2018-01-01"
end_date = "2024-01-01"

# Load historical stock data
stock_data = yf.download(stock_ticker, start=start_date, end=end_date)
stock_data = stock_data[['Close']]

# Normalize the data
scaler = MinMaxScaler(feature_range=(0,1))
scaled_data = scaler.fit_transform(stock_data)

# Prepare data for LSTM-DNN
def create_sequences(data, seq_length):
    X, y = [], []
    for i in range(len(data) - seq_length):
        X.append(data[i:i+seq_length])
        y.append(data[i+seq_length])
    return np.array(X), np.array(y)

sequence_length = 60
X, y = create_sequences(scaled_data, sequence_length)

# Reshape for LSTM
X = X.reshape((X.shape[0], X.shape[1], 1))

# Split into train and test sets
train_size = int(len(X) * 0.8)
X_train, X_test = X[:train_size], X[train_size:]
y_train, y_test = y[:train_size], y[train_size:]

# Build LSTM-DNN Model
model = Sequential([
    LSTM(128, return_sequences=True, input_shape=(sequence_length, 1)),
    Dropout(0.2),
    LSTM(64, return_sequences=False),
    Dropout(0.2),
    Dense(64, activation="relu"),
    Dense(32, activation="relu"),
    Dense(1)
])

# Compile the model
model.compile(optimizer=Adam(learning_rate=0.001), loss="mean_squared_error")

# Train the model
early_stop = EarlyStopping(monitor='val_loss', patience=10, restore_best_weights=True)
model.fit(X_train, y_train, epochs=50, batch_size=32, validation_data=(X_test, y_test), callbacks=[early_stop])

# Predict the next two years (730 days)
future_days = 730
last_sequence = scaled_data[-sequence_length:]
predictions = []

for _ in range(future_days):
    next_day_pred = model.predict(last_sequence.reshape(1, sequence_length, 1))
    predictions.append(next_day_pred[0][0])
    last_sequence = np.roll(last_sequence, -1)
    last_sequence[-1] = next_day_pred

# Transform predictions back to original scale
future_dates = pd.date_range(start=stock_data.index[-1] + pd.Timedelta(days=1), periods=future_days)
predicted_prices = scaler.inverse_transform(np.array(predictions).reshape(-1, 1))

# Plot and save the result
plt.figure(figsize=(12,6))
plt.plot(stock_data.index, stock_data['Close'], label="Actual Prices", color='blue')
plt.plot(future_dates, predicted_prices, label="Predicted Prices", color='red', linestyle='dashed')

plt.xlabel("Date")
plt.ylabel("Stock Price")
plt.title(f"Stock Price Prediction for {stock_ticker} (6 Years Actual + 2 Years Prediction)")
plt.legend()

# Save the image in Spring Boot static folder
image_path = f"src/main/resources/static/{stock_ticker}_prediction.png"
plt.savefig(image_path)
plt.close()

# Print the image path for Java to read
print(image_path)
