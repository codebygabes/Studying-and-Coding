const mysql = require('mysql2/promise')

// O pool reaproveita conexões; cada transação reserva uma delas.
module.exports = mysql.createPool({
  host: process.env.DB_HOST || '127.0.0.1',
  port: Number(process.env.DB_PORT || 3306),
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  connectionLimit: 10,
  waitForConnections: true,
  queueLimit: 50,
  decimalNumbers: true,
  charset: 'utf8mb4'
})
