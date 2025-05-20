# 💱 Currency Converter - Java Console App

A simple console-based currency converter developed in Java. This application consumes the [ExchangeRate-API](https://www.exchangerate-api.com/) to fetch real-time currency exchange rates and performs conversions. It also keeps a log of your transactions in a JSON file.

---

## 📦 Features

- ✅ Real-time currency conversion using ExchangeRate-API.
- ✅ Save every conversion into a local `transactions.json` file.
- ✅ Reads secure API key from `.env` file using dotenv.
- ✅ Handles invalid inputs and connection errors.
- ✅ Supports multiple conversions in one run.
- ✅ Displays available currencies dynamically.

---

## 🛠 Project Structure

```bash
conversor-de-monedas/
├── src/
│   └── com/leorodriguezs/conversordemonedas/
│       ├── api/
│       │   ├── CurrencyApiClient.java
│       │   └── CurrencyResponse.java
│       ├── modelos/
│       │   └── Transaction.java
│       └── Main.java
├── lib/
│   ├── gson-2.13.1.jar
│   └── dotenv-java-3.2.0.jar
├── .env
├── transactions.json
├── .gitignore
└── README.md

⚙️ How to Run
1. Compile:
javac -cp "lib/*" -d out src/com/leorodriguezs/conversordemonedas/**/*.java
2. Run:
java -cp "lib/*;out" com.leorodriguezs.conversordemonedas.Main
🔁 On Unix-based systems (Linux/macOS), replace ; with : in the classpath.

🔐 Environment Variables
Create a .env file in the project root:
API_KEY=your_api_key_here
You can get a free API key at https://www.exchangerate-api.com.

🧪 Example Conversion
Welcome to the Currency Converter!
Loading available currencies...
Available currencies: [USD, EUR, MXN, ...]
Enter the local currency: USD
Enter the destination currency: EUR
Enter the amount: 100
100.00 USD = 91.23 EUR

💾 Transaction Log Format
The app stores every successful conversion in transactions.json like this:
[
  {
    "localCurrency": "USD",
    "destinationCurrency": "EUR",
    "originalAmount": 100.0,
    "convertedAmount": 91.23
  }
]

🙌 Credits
Developed by Leonardo Rodríguez as part of a learning project in the ONE (Oracle Next Education) program.
