const {Bug} = require('./classes/bug.js')
const {User} = require('./classes/user.js')
const express = require('express')
const multer = require('multer')
const sharp = require('sharp')
const path = require('path')
const fs = require('fs')
const app = express()
const bodyParser = require('body-parser')
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))
const port = 3000
app.set('views','./views')
app.set('view engine','pug')
app.use(express.static('./public'))

let storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, './public/images')
    },
    filename: function (req, file, cb) {
        cb(null, file.fieldname + '-' + Date.now())
    }
})

let upload = multer({storage:storage})





//  some sample users
let user1 = new User(1, 'Stacy', 'Shane', 'fake@email.com', 'default')
let user2 = new User(2, 'Shaney', 'Slim', 'anotherfake@email.com', 'default')
let user3 = new User(3, 'Jones', 'Bob', 'bjones@email.com', 'default')

//  put the users into the array
let users = new Array()
users.push(user1)
users.push(user2)
users.push(user3)



//  some sample bugs
let bug1 = new Bug(1, 'Styling Needed', user1, 'This site needs to be jazzed up!', 'Feature', 'High', 'Open')
let bug2 = new Bug(2, 'Validation Not Working', user2, 'The title validation check is not firing!', 'Issue', 'High', 'Open')
let bug3 = new Bug(3, 'Crasheroo', user3, 'Divide by zero error.', 'Issue', 'Medium', 'Closed')

//  make a bug array
let bugs = new Array()

//  put the bugs into it
bugs.push(bug1)
bugs.push(bug2)
bugs.push(bug3)



//  render all the users
app.get('/users', function(req, res) {
    res.render('users', {users: users})
})

//  edit a user
app.get('/users/:id/edit', function(req, res) {
    //  get the user id from the params
    const userID = req.params.id
    //  get the user from the array based on id
    const user = users.find(user => user.id == userID)
    res.render('editUser', {
        id: user.id,
        fname: user.fname,
        lname: user.lname,
        email: user.email,
        img: user.img
    })
})

//  delete a user
app.get('/users/:id/destroy', function(req, res) {
    const id = req.params.id
    //  get the index of the matching user
    const index = users.findIndex(user => user.id == id)
    users.splice(index, 1)
    res.redirect('/users')
})

//  get a new user form
app.get('/users/newUser', function(req, res) {
    res.render('newUser')
})

//  post a user
app.post('/users/create', upload.single('img'), async function(req, res) {
    //  initial generated bug id
    let userID = 1
    //  generate a new id
    for (let i = 1; i < users.length + 2; i++) {
        //  id is equal to current iteration
        userID = i
        //  find a bug with this id
        let user = users.find(user => user.id == userID)
        //  if we don't, this new id will work and we can break
        //  otherwise, we will need to try (loop) again
        if (user === -1) {
            break
        }
    }
    const fname = req.body.fname
    const lname = req.body.lname
    const email = req.body.email
    let img = 'default'

    user = users.findIndex(user => user.email == email)
    if ((user !== -1) && user != userID) {
        res.render('createUserError')
        return
    }    

    if (req.file) {
        const {filename:image} = req.file
        await sharp(req.file.path)
        .resize(50)
        .jpeg({quality:90})
        .toFile(
            path.resolve(req.file.destination, 'resized', image)
        )
        fs.unlinkSync(req.file.path)
        const img = req.file.filename
    }


    user = new User(userID, lname, fname, email, img)
    users.push(user)
    res.redirect('/users')
})

//  update an existing user
app.post('/users/:id/update', upload.single('img'), async function(req, res) {
    const id = req.params.id
    const fname = req.body.fname
    const lname = req.body.lname
    const email = req.body.email
    let img = 'default'

    user = users.findIndex(user => user.email == email)
    if ((user !== -1) && user != id) {
        res.render('editUserError')
        return
    }

    if (req.file) {
        const {filename:image} = req.file
        await sharp(req.file.path)
        .resize(50)
        .jpeg({quality:90})
        .toFile(
            path.resolve(req.file.destination, 'resized', image)
        )
        fs.unlinkSync(req.file.path)
        img = req.file.filename
    }

    user = new User(id, lname, fname, email, img)
    const index = users.findIndex(user => user.id == id)
    //  splice in new bug at the index of the old one
    users.splice(index, 1, user)
    //  redirect to the bugs show page
    res.redirect('/users')
})

//  look at a user
app.get('/users/:id', function(req, res) {
    //  get the user id from the params
    const userID = req.params.id
    //  get the user from the array based on id
    const user = users.find(user => user.id == userID)
    res.render('user', {
        id: user.id,
        fname: user.fname,
        lname: user.lname,
        email: user.email,
        img: user.img
    })
})

//  render all the bugs
app.get('/bugs', function (req, res) {
    res.render('bugs', {bugs: bugs})
})

//  create a bug form
app.get('/bugs/newBug', function (req, res) {
    res.render('newBug', {users: users})
})

//  post a bug
app.post('/bugs/create', function (req, res) {
    //  initial generated bug id
    let bugID = 1
    //  generate a new id
    for (let i = 1; i < bugs.length + 2; i++) {
        //  id is equal to current iteration
        bugID = i
        //  find a bug with this id
        let bug = bugs.find(bug => bug.id == bugID)
        //  if we don't, this new id will work and we can break
        //  otherwise, we will need to try (loop) again
        if (bug === -1) {
            break
        }
    }
    const title = req.body.title
    const userID = req.body.user
    const desc = req.body.desc
    const type = req.body.type
    const priority = req.body.priority
    const status = req.body.status

    //  get the user from the array based on id
    const user = users.find(user => user.id == userID)

    bug = new Bug(bugID, title, user, desc, type, priority, status)
    bugs.push(bug)
    res.redirect('/bugs')
})

//  update an existing bug
app.post('/bugs/:id/update', function (req, res) {
    const id = req.params.id
    const title = req.body.title
    const userID = req.body.user
    const desc = req.body.desc
    const type = req.body.type
    const priority = req.body.priority
    const status = req.body.status

    const user = users.find(user => user.id == userID)

    bug = new Bug(id, title, user, desc, type, priority, status)
    const index = bugs.findIndex(bug => bug.id == id)
    //  splice in new bug at the index of the old one
    bugs.splice(index, 1, bug)
    //  redirect to the bugs show page
    res.redirect('/bugs')
})

//  edit a bug
app.get('/bugs/:id/edit', function(req, res) {
    //  get the bug id
    const bugID = req.params.id
    //  get the bug from the array based on id
    const bug = bugs.find(bug => bug.id == bugID)
    res.render('editBug', {
        id: bug.id,
        title: bug.title,
        user: bug.user,
        desc: bug.desc,
        type: bug.type, 
        priority: bug.priority,
        status: bug.status,
        users: users
    })
})

//  render just one bug
app.get('/bugs/:id', function(req, res) {
    //  get the bug id from the params
    const bugID = req.params.id
    //  get the bug from the array based on id
    const bug = bugs.find(bug => bug.id == bugID)
    res.render('bug', {
        id: bug.id,
        title: bug.title,
        user: bug.user,
        desc: bug.desc,
        type: bug.type, 
        priority: bug.priority,
        status: bug.status
    })
})

//  remove a bug
app.get('/bugs/:id/destroy', function (req, res) {
    const id = req.params.id
    //  get the index of the matching bug
    const index = bugs.findIndex(bug => bug.id == id)
    bugs.splice(index, 1)
    res.redirect('/bugs')
})


app.listen(port)
