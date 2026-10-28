import fs from 'node:fs'
import { parse } from 'csv-parse'

const csvPath = new URL('./tasks.csv', import.meta.url)

const parser = fs
  .createReadStream(csvPath)
  .pipe(parse({ columns: true, skip_empty_lines: true, trim: true }))

for await (const { title, description } of parser) {
  await fetch('http://localhost:3333/tasks', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, description }),
  })
}

console.log('Importação concluída!')
