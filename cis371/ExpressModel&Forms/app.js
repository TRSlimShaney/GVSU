const {Bug} = require('./classes/bug.js')
const express = require('express')
const app = express()
const bodyParser = require('body-parser')
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))
const port = 3000
app.set('views','./views')
app.set('view engine','pug')
app.use(express.static('public'))

//  some sample bugs
let bug1 = new Bug(1, 'Styling Needed', 'Anonymous', 'This site needs to be jazzed up!', 'Feature', 'High', 'Open')
let bug2 = new Bug(2, 'Validation Not Working', 'Anonymous', 'The title validation check is not firing!', 'Issue', 'High', 'Open')
let bug3 = new Bug(3, 'Crasheroo', 'Alice Smith', 'Divide by zero error.', 'Issue', 'Medium', 'Closed')

//  make an array
let bugs = new Array()

//  put the bugs into it
bugs.push(bug1)
bugs.push(bug2)
bugs.push(bug3)


//  render all the bugs
app.get('/bugs', function (req, res) {
    res.render('bugs', {bugs: bugs})
    })

//  create a bug form
app.get('/bugs/newBug', function (req, res) {
    res.render('newBug')
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
    const user = req.body.user
    const desc = req.body.desc
    const type = req.body.type
    const priority = req.body.priority
    const status = req.body.status
    bug = new Bug(bugID, title, user, desc, type, priority, status)
    bugs.push(bug)
    res.redirect('/bugs')
})

//  update an existing bug
app.post('/bugs/:id/update', function (req, res) {
    const id = req.params.id
    const title = req.body.title
    const user = req.body.user
    const desc = req.body.desc
    const type = req.body.type
    const priority = req.body.priority
    const status = req.body.status
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
        status: bug.status 
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
