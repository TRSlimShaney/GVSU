const projects = require('./projects')
const fs = require('fs')
const { execSync } = require('child_process')

//  users array
let users = [
    {id: 0, username: 'admin', password: 'admin', email: 'a@mail.com', projects: [0]}
]

//  load users from disk
loadUsers()
//  load projects from disk
projects.loadProjects()
console.log(users)
console.log(projects.projects)

//  user object
function User(user) {
    this.id = user.id
    this.username = user.username
    this.password = user.password
    this.email = user.email
    this.projects = []
    this.friends = []
    return this
}

//  response object
function ResObj(message, mode, user, project, projects) {
    this.message = message
    this.mode = mode
    this.user = user
    this.project = project
    this.projects = projects
    return this
}

//  load the users from disk
function loadUsers() {
    fs.readdirSync('./users').forEach(file => {
        const rawFile = fs.readFileSync(`./users/${file}`)
        const user = JSON.parse(rawFile)
        users.splice(user.id, 0, user)
    })
}

//  save a user to disk
function saveUser(user) {
    if (!fs.existsSync(`./users`)) {
        fs.mkdirSync(`./users`)
    }
    try {
        fs.writeFileSync(`./users/${user.username}`, JSON.stringify(user))
    }
    catch (err) {
        console.error(err)
    }
}

//  remove a user from disk
function removeUser(username) {
    if (fs.existsSync(`./users/${username}`)) {
        fs.unlinkSync(`./users/${username}`)
    }
}

//  find an available user id
function findUserID() {
    //  initial generated bug id
    let userID = 0
    //  generate a new id
    for (let i = 0; i < users.length + 1; i++) {
        //  id is equal to current iteration
        userID = i
        //  find a bug with this id
        let index = users.findIndex(user => user.id === userID)
        //  if we don't, this new id will work and we can break
        //  otherwise, we will need to try (loop) again
        if (index === -1) {
            break
        }
    }
    return userID
}

//  log the user in and advance their app to the project mode
function loginUser(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    //  authenticate the user first
    const userID = authUser(username, password)
    //  if the user isn't found or password doesn't match
    if (userID === -1) {
        return new ResObj('Username and password do not match an existing user.', 0)
    }
    //  find the index of the user
    const index = users.findIndex(u => u.id === userID)
    //  collect their projects into an object
    const a = collectUserProjects(userID)
    //  send them
    return new ResObj('Logged in successfully.', 1, users[index], undefined, a)
}

//  authenticate the user
function authUser(username, password) {
    //  find the index of the user with the matching username
    let userIndex = users.findIndex(user => user.username === username)
    //  if not found, fail auth
    if (userIndex === -1) {
        return -1
    }
    //  if the password does not match the user, fail auth
    const user = users[userIndex]
    if (password !== user.password) {
        return -1
    }
    //  otherwise, return the id of the user
    return user.id
}

//  abstract creating a new user
function createUser(req) {
    //  create a new user
    const newUser = new User(req.body)
    //  its id is derived
    newUser.id = findUserID()
    //  validate this user
    const valid = validateUser(newUser)
    if (!valid) {
        return new ResObj('Invalid new user!', 0)
    }
    //  add the user and its password
    users.splice(newUser.id, 0, newUser)
    //  save the user to disk
    saveUser(newUser)
    //  send it
    return new ResObj('User creation successful.  Click \'Start Coding\' to get started!', 1)
}

//  delete user based on id.  This is only accessible to the server
function deleteUser(userID) {
    //  find and delete the user
    let index = users.findIndex(aUser => aUser.id === userID)
    //  if the user is found
    if (index !== -1) {
        //  remove the user from disk
        removeUser(users[index].username)
        //  remove the user from the database
        users.splice(index, 1)
    }
    //  we also need to remove this user from all projects
    for (let i = 0; i < projects.projects.length; ++i) {
        //  check if the user is on this project
        index = projects.projects[i].owners.findIndex(owner => owner == userID)
        //  if they are, remove them, and if the project is now an orphan, delete it
        if (index !== -1) {
            //  remove the owner from the project in the database
            projects.projects[i].owners.splice(index, 1)
            //  if there are no more owners on the project
            if (projects.projects[i].owners.length == 0) {
                //  delete the project
                projects.deleteProject(projects.projects[i].id)
            }
        }
    }
}

//  delete a user with authentication.  This is accessible via the web-app
function authDeleteUser(req) {
    const username = req.body.username
    const password = req.body.password
    //  auth the user
    const userID = authUser(username, password)
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 0)
    }
    //  delete the user
    deleteUser(userID)
    //  send the response
    return new ResObj('User delete successful.', 1)
}

//  abstract updating an existing user
function updateUser(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    //  auth the user
    const userID = authUser(username, password)
    //  if fail
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 0)
    }
    //  find the old user
    const oldUser = users.find(user => user.id === userID)
    //  create a new user
    const updatedUser = new User(req.body.newUser)
    if (updatedUser === undefined) {
        return new ResObj('Browser-side script error.', 1)
    }
    //  transfer their projects over
    updatedUser.projects = oldUser.projects
    //  check if the user is valid
    const valid = validateUser(updatedUser)
    if (!valid) {
        return new ResObj('Invalid updated user!', 1)
    }
    //  delete the user if it already exists
    deleteUser(updatedUser.id)
    //  add the user
    users.splice(updatedUser.id, 0, updatedUser)
    //  save the updated user to disk
    saveUser(updatedUser)
    return new ResObj('Update user successful.', 1, updatedUser)
}

function createProject(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    const proj = req.body.proj

    //  auth the user
    const userID = authUser(username, password)
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 0)
    }
    //  look for a project with the same name
    const index = projects.projects.findIndex(p => p.name === proj.name)
    //  if the project isn't found
    if (index === -1) {
        //  create the project
        const proj2 = new projects.createProject([userID], proj.type, proj.name)
        //  find the user
        const userIndex = users.findIndex(u => u.id === userID)
        //  add the project to the user
        users[userIndex].projects.splice(proj2.id, 0, proj2.id)
        //  return the project
        return new ResObj('Project created.', 2, undefined, proj2, collectUserProjects(userID))
    }
    else {
        //  return error
        return new ResObj('A project with that name already exists.', 1)
    }
}

function deleteProject(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    //  auth the user
    const userID = authUser(username, password)
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 0)
    }
    //  get the project id
    const projID = req.body.proj.id
    //  delete the project from the database
    projects.deleteProject(projID)

    //  remove the project from all users
    for (let i = 0; i < users.length; ++i) {
        const index = users[i].projects.findIndex(p => p === projID)
        if (index !== -1) {
            users[i].projects.splice(index, 1)
        }
    }
    //  return response
    return new ResObj('Project deleted.', 1, undefined, undefined, collectUserProjects(userID))
}

function listProjects(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    //  auth the user
    const userID = authUser(username, password)
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 0)
    }
    //  collect the user's projects
    const a = collectUserProjects(userID)
    //  return the response
    return new ResObj('Project list updated.', 1, users[index], undefined, a)
}

//  collect a user's projects
function collectUserProjects(userID) {
    const a = []
    //  find the index of the user
    const index = users.findIndex(u => u.id === userID)
    //  get their projects
    const projs = users[index].projects
    //  build a list of objects of id and name where a user's projects matches an existing project
    for (let i = 0; i < projects.projects.length; ++i) {
        for (let j = 0; j < projs.length; ++j) {
            if (projs[j] === projects.projects[i].id) {
                a.push({ id: projs[j], name: projects.projects[i].name})
            }
        }
    }
    //  return the list
    return a
}


//  abstract submitting a file
function submitFile(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    //  auth the user
    const userID = authUser(username, password)
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 1)
    }
    //  get the project id
    const projID = req.body.project.id
    //  find the index of this project
    let index = projects.projects.findIndex(proj => proj.id === projID)
    if (index === -1) {
        return new ResObj('That project does not exist.', 2)
    }
    //  if the project is found, determine if the user has permission to work on it
    const perm = projects.projects[index].owners.findIndex(own => own === userID)
    //  if the user doesn't have permission
    if (perm === -1) {
        return new ResObj('You do not have permission to work on this project.', 2)
    }
    const proj = req.body.project
    index = proj.files.findIndex(file => file.name === req.body.aFile.name)
    if (index === -1) {
        //  add the file to the project
        proj.files.push(req.body.aFile)
    }
    else {
        proj.files.splice(index, 1, req.body.aFile)
    }
    //  update the project
    projects.updateProject(proj)
    //  return the response
    return new ResObj('File saved.', 2, undefined, proj)
}

function deleteFile(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    //  auth the user
    const userID = authUser(username, password)
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 2)
    }
    //  get the project id
    const projID = req.body.proj.id
    //  find the index of this project
    let index = projects.projects.findIndex(proj => proj.id === projID)
    if (index === -1) {
        return new ResObj('That project does not exist.', 2)
    }
    //  if the project is found, determine if the user has permission to work on it
    const perm = projects.projects[index].owners.findIndex(own => own === userID)
    //  if the user doesn't have permission
    if (perm === -1) {
        return new ResObj('You do not have permission to work on this project.', 2)
    }
    //  get the project
    const proj = req.body.project
    //  find the index of the file matching the name we want to delete
    index = proj.files.findIndex(name => name === req.body.file.name)
    //  if the file is not found
    if (index === -1) {
        return new ResObj('File not found.', 2)
    }
    //  otherwise, remove the file from the project
    proj.files.splice(index, 1)
    //  put the new project in the database
    projects.updateProject(proj)
    //  return the response
    return new ResObj('File deleted.', 2)
}

function runProject(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    const projID = req.body.projID
    //  authenticate the user
    const userID = authUser(username, password)
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 0)
    }
    //  find the index of the project with the matching projID
    const index = projects.projects.findIndex(p => p.id === projID)
    //  if the project was not found
    if (index === -1) {
        return new ResObj('That project does not exist.', 1)
    }
    //  if the temp directory does not exist
    if (!fs.existsSync(`./temp`)) {
        //  make it
        fs.mkdirSync(`./temp`)
    }
    try {
        //  for each file in the project, write the file to the temp directory
        projects.projects[index].files.forEach(file => {
            fs.writeFileSync(`./temp/${file.name}`, file.code)
        })
    }
    catch (err) {
        console.error(err)
    }
    let err = ''
    let out = ''
    //  run the project
    if (projects.projects[index].type === 'js') {
        out = execSync('nodejs ./temp/app', function (error, stdout, stderr) {
            if (error) {
                err = error
            }
        })
    }
    else if (projects.projects[index].type === 'py') {
        out = execSync('python3 ./temp/app', function (error, stdout, stderr) {
            if (error) {
                err = error
            }
        })
    }
    //  for each file found in the temp directory, delete it
    try {
        fs.readdirSync('./temp').forEach(file => {
            fs.unlinkSync(`./temp/${file}`)
        })
    }
    catch (errr) {
        console.error(errr)
    }
    //  remove the directory
    fs.rmdirSync('./temp')
    //  return the project output
    return new ResObj('Project output: ' + out.toString(), 2)
}

//  load a project from the database
function loadProject(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    const projID = req.body.id
    //  auth the user
    const userID = authUser(username, password)
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 1)
    }
    //  find the index of the project
    let index = projects.projects.findIndex(proj => proj.id === projID)
    //  if the project isn't found
    if (index === -1) {
        return new ResObj('That project was not found', 1)
    }
    //  find the index of the user
    index = users.findIndex(u => u.id === userID)
    //  get the user's projects
    const projs = users[index].projects
    //  find the index of the project
    index = projs.findIndex(p => p === projID)
    //  make a copy
    const project = {...projects.projects[index]}
    //  send the project
    return new ResObj('Project loaded.', 2, undefined, project, undefined)
}

//  add an owner to a project
function addOwner(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    const projID = req.body.projID
    const addID = parseInt(req.body.addRemoveID)
    //  auth the user
    const userID = authUser(username, password)

    if (userID === addID) {
        return new ResObj('You can\'t add yourself to the project.', 2)
    }
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 2)
    }
    //  find the index of the project
    let index = projects.projects.findIndex(p => p.id === projID)
    //  save the index
    const projIndex = index
    //  if the project doesn't exist
    if (index === -1) {
        return new ResObj('That project doesn\'t exist.', 2)
    }
    //  find the index of the user in the owners of this project
    index = projects.projects[index].owners.findIndex(o => o === userID)
    //  if we can't find them, that means this user isn't allowed to add owners
    if (index === -1) {
        return new ResObj('You don\'t have permission to work on this project.', 2)
    }
    //  find the index of the user we are trying to add
    index = users.findIndex(u => u.id === addID)
    //  if user is not found
    if (index === -1) {
        return new ResObj('That user does not exist.', 2)
    }
    //  check if that user is already assigned to the project
    let index2 = users[index].projects.findIndex(p => p === projID)
    //  if they are
    if (index2 !== -1) {
        return new ResObj('That user is already assigned to this project.', 2)
    }
    //  add the owner
    projects.addOwner(projID, addID)
    //  add the project to the user
    users[index].projects.push(projID)
    //  return the updated project
    return new ResObj('User added to project.', 2, undefined, projects.projects[projIndex])
}

function removeOwner(req) {
    const username = req.body.auth.username
    const password = req.body.auth.password
    const projID = req.body.projID
    const remID = req.body.addRemoveID
    //  auth the user
    const userID = authUser(username, password)
    //  if auth fails
    if (userID === -1) {
        return new ResObj('Authentication failure:  Please reload the app and login again.', 2)
    }
    //  find the project
    let index = projects.projects.findIndex(p => p.id === projID)
    //  save the index
    const projIndex = index
    //  if the project isn't found
    if (index === -1) {
        return new ResObj('That project doesn\'t exist.', 2)
    }
    //  find the user trying to remove
    index = projects.projects[index].owners.findIndex(o => o === userID)
    //  if they aren't working on this project, they can't remove a user
    if (index === -1) {
        return new ResObj('You don\'t have permission to work on this project.', 2)
    }
    //  find the user we are trying to remove
    index = users.findIndex(u => u.id === remID)
    //  if we don't find this user
    if (index === -1) {
        return new ResObj('That user does not exist.', 2)
    }
    //  find the user if they are assigned to the project
    let index2 = users[index].projects.findIndex(p => p === projID)
    //  if they are not, we can remove them
    if (index2 !== -1) {
        projects.removeOwner(projID, remID)
        users[index].projects.splice(index2, 1)
        return new ResObj('User removed from project.', 2, undefined, projects.projects[projIndex])
    }
    //  else, we can't
    return new ResObj('That user is not assigned to this project.', 2)
}












//  validate the user
function validateUser(user) {
    //  check if email is basically valid
    let valid = validateEmail(user.email)
    if (!valid) {
        return false
    }
    //  check if username is basically valid
    valid = validateUsername(user.username)
    if (!valid) {
        return false
    }
    //  check if the password is bascially valid
    valid = validatePassword(user.password)
    if (!valid) {
        return false
    }
    return true
}

//  validate email
function validateEmail(email) {
    //  check if email is already in use
    let index = users.findIndex(aUser => aUser.email === email)
    if (index !== -1) {
        return false
    }
    // w3 spec for validating email addresses
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
        return true
    }
    return false
}

//  validate username
function validateUsername(username) {
    //  check if username is blank
    if (username.length === 0) {
        return false
    }
    //  check if there is a user with the same username
    let index = users.findIndex(aUser => aUser.username === username)
    if (index !== -1) {
        return false
    }
    return true
}

//  validate password
function validatePassword(password) {
    if (password.length === 0) {
        return false
    }
    return true
}

exports.createUser = createUser
exports.authDeleteUser = authDeleteUser
exports.updateUser = updateUser
exports.loginUser = loginUser
exports.submitFile = submitFile
exports.deleteFile = deleteFile
exports.runProject = runProject
exports.createProject = createProject
exports.deleteProject = deleteProject
exports.loadProject = loadProject
exports.listProjects = listProjects
exports.addOwner = addOwner
exports.removeOwner = removeOwner
exports.User = User
exports.authUser = authUser
exports.ResObj = ResObj
exports.findUserID = findUserID
exports.deleteUser = deleteUser
exports.users = users
exports.validateUser = validateUser