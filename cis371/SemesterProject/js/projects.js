const fs = require('fs')

//  projects array
let projects = [
    new Project(0, [0], 'js', 'sample')
]

function Project(id, owners, type, name) {
    this.id = id
    this.owners = owners
    this.type = type
    this.name = name
    this.files = [new ProjectFile('app', type, '')]
    return this
}

function ProjectFile(name, type, code) {
    this.name = name
    this.type = type
    this.code = code
    return this
}

//  find an available user id
function findProjectID() {
    //  initial generated bug id
    let projID = 0
    //  generate a new id
    for (let i = 0; i < projects.length + 1; i++) {
        //  id is equal to current iteration
        projID = i
        //  find a bug with this id
        let index = projects.findIndex(proj => proj.id === projID)
        //  if we don't, this new id will work and we can break
        //  otherwise, we will need to try (loop) again
        if (index === -1) {
            break
        }
    }
    return projID
}

//  abstract creating a new project
function createProject(owners, type, name) {
    //  find a potential project id
    projID = findProjectID()
    //  create a project
    const proj = new Project(projID, owners, type, name)
    //  splice it in and return it
    projects.splice(proj.id, 0, proj)
    //  save it to disk
    saveProject(proj)
    return proj
}

//  delete a project based on id.  This is only accessbile to the server
function deleteProject(id) {
    //  find the index of the project with the id
    const index = projects.findIndex(proj => proj.id === id)
    if (index === -1) {
        return
    }
    //  remove it
    removeProject(projects[index].name)
    projects.splice(index, 1)
}

//  update an existing project
function updateProject(project) {
    const index = projects.findIndex(proj => proj.id === project.id)
    if (index === -1) {
        return
    }
    projects.splice(index, 1, project)
    saveProject(project)
}

//  add another owner to the project
function addOwner(id, owner) {
    //  find the index of project with the id
    const index = projects.findIndex(proj => proj.id === id)
    if (index === -1) {
        return
    }
    //  splice in another owner
    projects[index].owners.splice(owner, 0, owner)
}

//  remove an owner from a project
function removeOwner(id, owner) {
    //  find the index of project with the id
    const index = projects.findIndex(proj => proj.id === id)
    if (index === -1) {
        return
    }
    //  find the index of owner
    const index2 = projects[index].owners.findIndex(own => own === owner)
    if (index2 === -1) {
        return
    }
    //  remove the owner
    projects[index].owners.splice(index2, 1)
    //  if the project is now an orphan, delete it
    if (projects[index].owners.length === 0) {
        deleteProject(projects[index].id)
    }
    //  save project to disk
    saveProject(projects[index])
}

//  save project to disk
function saveProject(proj) {
    if (!fs.existsSync(`./projects`)) {
        fs.mkdirSync(`./projects`)
    }
    try {
        fs.writeFileSync(`./projects/${proj.name}`, JSON.stringify(proj))
    }
    catch (err) {
        console.error(err)
    }
}

//  remove project from disk
function removeProject(name) {
    if (fs.existsSync(`./projects/${name}`)) {
        fs.unlinkSync(`./projects/${name}`)
    }
}

//  load projects from disk
function loadProjects() {
    fs.readdirSync('./projects').forEach(file => {
        const rawFile = fs.readFileSync(`./projects/${file}`)
        const proj = JSON.parse(rawFile)
        projects.splice(proj.id, 0, proj)
    })
}



exports.createProject = createProject
exports.deleteProject = deleteProject
exports.updateProject = updateProject
exports.projects = projects
exports.addOwner = addOwner
exports.removeOwner = removeOwner
exports.loadProjects = loadProjects
exports.Project = Project
exports.findProjectID = findProjectID