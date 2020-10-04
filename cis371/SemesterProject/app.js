//  import the users code
const users = require('./js/users')
//  import modules
const express = require('express')
const path = require('path')
const app = express()
const port = 3000
const bodyParser = require('body-parser')
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))
app.use(express.static('./public'))

//  send welcome page
app.get('', (req, res) => {
    res.sendFile(path.join(__dirname + '/public/welcome/welcome.html'));
})

//  send the registration app
app.get('/reg', (req, res) => {
    res.sendFile(path.join(__dirname + '/public/reg/reg.html'));
})

//  send the app
app.get('/app', (req, res) => {
    res.sendFile(path.join(__dirname + '/public/app/app.html'));
})

//  login a user
app.post('/users/login', (req, res) => {
    res.send(users.loginUser(req))
})

//  create a user
app.post('/users/create', (req, res) => {
    res.send(users.createUser(req))
})

//  update a user
app.post('/users/update', (req, res) => {
    res.send(users.updateUser(req))
})

//  delete a user
app.post('/users/delete', (req, res) => {
    res.send(users.authDeleteUser(req))
})

//  submit a file to a project
app.post('/projects/submitFile', (req, res) => {
    res.send(users.submitFile(req))
})

//  delete a file from a project
app.post('/projects/deleteFile', (req, res) => {
    res.send(users.deleteFile(req))
})

//  run a project
app.post('/projects/run', (req, res) => {
    res.send(users.runProject(req))
})

//  start a project
app.post('/projects/create', (req, res) => {
    res.send(users.createProject(req))
})


//  delete a project
app.post('/projects/delete', (req, res) => {
    res.send(users.deleteProject(req))
})

// load a project
app.post('/projects/load', (req, res) => {
    res.send(users.loadProject(req))
})

// list projects
app.post('/projects/list', (req, res) => {
    res.send(users.listProjects(req))
})

// add owner to a project
app.post('/projects/addOwner', (req, res) => {
    res.send(users.addOwner(req))
})

// remove owner to a project
app.post('/projects/removeOwner', (req, res) => {
    res.send(users.removeOwner(req))
})

app.listen(port)
