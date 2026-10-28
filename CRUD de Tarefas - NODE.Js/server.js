import http from 'node:http'
import { randomUUID } from 'node:crypto'

const tasks = []

async function readBody(req) {
  const chunks = []
  for await (const chunk of req) chunks.push(chunk)
  try {
    return JSON.parse(Buffer.concat(chunks).toString())
  } catch {
    return {}
  }
}

function send(res, status, body) {
  res.writeHead(status, { 'Content-Type': 'application/json' })
  res.end(body === undefined ? undefined : JSON.stringify(body))
}

const server = http.createServer(async (req, res) => {
  const url = new URL(req.url, 'http://localhost')
  const [resource, id, action] = url.pathname.split('/').filter(Boolean)

  if (resource !== 'tasks') return send(res, 404, { error: 'Rota não encontrada' })

  // POST /tasks
  if (req.method === 'POST' && !id) {
    const { title, description } = await readBody(req)
    if (!title || !description) {
      return send(res, 400, { error: 'title e description são obrigatórios' })
    }
    const now = new Date().toISOString()
    const task = {
      id: randomUUID(),
      title,
      description,
      completed_at: null,
      created_at: now,
      updated_at: now,
    }
    tasks.push(task)
    return send(res, 201, task)
  }

  // GET /tasks?title=...&description=...
  if (req.method === 'GET' && !id) {
    const title = url.searchParams.get('title')?.toLowerCase()
    const description = url.searchParams.get('description')?.toLowerCase()
    const result = tasks.filter(
      (t) =>
        (!title || t.title.toLowerCase().includes(title)) &&
        (!description || t.description.toLowerCase().includes(description))
    )
    return send(res, 200, result)
  }

  const task = tasks.find((t) => t.id === id)

  // PUT /tasks/:id
  if (req.method === 'PUT' && id && !action) {
    if (!task) return send(res, 404, { error: 'Tarefa não encontrada' })
    const { title, description } = await readBody(req)
    if (!title && !description) {
      return send(res, 400, { error: 'Informe title e/ou description' })
    }
    if (title) task.title = title
    if (description) task.description = description
    task.updated_at = new Date().toISOString()
    return send(res, 200, task)
  }

  // DELETE /tasks/:id
  if (req.method === 'DELETE' && id && !action) {
    if (!task) return send(res, 404, { error: 'Tarefa não encontrada' })
    tasks.splice(tasks.indexOf(task), 1)
    return send(res, 204)
  }

  // PATCH /tasks/:id/complete
  if (req.method === 'PATCH' && id && action === 'complete') {
    if (!task) return send(res, 404, { error: 'Tarefa não encontrada' })
    const now = new Date().toISOString()
    task.completed_at = task.completed_at ? null : now
    task.updated_at = now
    return send(res, 200, task)
  }

  return send(res, 404, { error: 'Rota não encontrada' })
})

server.listen(3333, () => console.log('Servidor rodando em http://localhost:3333'))
