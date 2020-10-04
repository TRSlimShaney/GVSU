const validate = require('./public/userValidation')
const express = require('express')
const path = require('path')
const app = express()
const port = 3000
const bodyParser = require('body-parser')
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))
app.use(express.static('./public'))

let users = [
        { id: 0, lname: 'Shaney', fname: 'Slim', email: 'atotallyreal@mail.com', thumb: 'default.png'},
        { id: 1, lname: 'Jones', fname: 'Bob', email: 'donteventry@mail.com', thumb: 'default.png'},
        { id: 2, lname: 'Stacy', fname: 'Shane', email: 'areyoureadingthese@mail.com', thumb: 'default.png'}
    ]

let findUserID = () => {
    //  initial generated bug id
    let userID = 0
    //  generate a new id
    for (let i = 0; i < users.length + 1; i++) {
        //  id is equal to current iteration
        userID = i
        //  find a bug with this id
        let index = users.find(user => user.id === userID)
        //  if we don't, this new id will work and we can break
        //  otherwise, we will need to try (loop) again
        if (index === -1) {
            break
        }
    }
    return userID
}

//  create user object from request
function ReqUser(req) {
    this.id = req.body.id
    this.fname = req.body.fname
    this.lname = req.body.lname
    this.email = req.body.email
    this.thumb = req.body.thumb
    return this
}

//  delete user with id
let deleteUser = (user) => {
    let index = users.findIndex(aUser => aUser.id === user.id)
    if (index !== -1) {
        users.splice(index, 1)
    }
}

//  send the app
app.get('', (req, res) => {
    res.sendFile(path.join(__dirname + '/public/users.html'));
})

//  send the users
app.get('/users/get', (req, res) => {
    res.send(users)
})

//  create a user
app.post('/users/create', (req, res) => {
    //  create a user object
    const currentUser = new ReqUser(req)
    //  find an available id for it
    currentUser.id = findUserID()
    //  validate this potential new user
    const valid = validate.validateUser(currentUser, users)
    if (!valid) {
        return
    }
    //  add it to users
    users.splice(currentUser.id, 0, currentUser)
})

//  update a user
app.post('/users/update', (req, res) => {
    const currentUser = new ReqUser(req)
    //  delete previous version of this user matching id
    deleteUser(currentUser)
    //  validate this potential new user
    const valid = validate.validateUser(currentUser, users)
    if (!valid) {
        return
    }
    //  add it to users
    users.splice(currentUser.id, 0, currentUser)
})

//  delete a user
app.post('/users/delete', (req, res) => {
    const currentUser = new ReqUser(req)
    //  delete the user matching the id
    deleteUser(currentUser)
})


app.listen(port)
