// CreateUser
function LoginForm({ auth, updateAuth, submitCallback }) {

    function renderButtons() {
        return (
            <div>
                <button type="submit" className="btn btn-primary">Login</button>
            </div>
        )
    }
  
    function formSubmitted(event) {
        // Prevent the browser from re-loading the page.
        event.preventDefault()
        submitCallback()
    }
  
    return (
      <div className="container">
        <h3> Login: </h3>
        <form onSubmit={formSubmitted}>
          <div className="form-group">
            <img className="avatar" src='/default.png'></img>
          </div>
          <div className="form-group">
            <label htmlFor="username">Username:</label>
            <input type="text" className="form-control" autoComplete='family-name' name="username" id="username"
              placeholder="Username" value={auth.username} onChange={(event) => updateAuth('username', event.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password:</label>
            <input type="text" className="form-control" autoComplete='family-name' name="password" id="password"
              placeholder="Password" value={auth.password} onChange={(event) => updateAuth('password', event.target.value)} />
          </div>
          <p>Note:  This site is NOT SECURE.  Do NOT use passwords you use for other sites and do NOT expect confidentiality.</p>
          <br></br>
          {renderButtons()}
        </form>
      </div>
    )
}

function ViewUserForm({ user, submitCallback }) {

    function editSubmitted() {
        event.preventDefault()
        submitCallback()
    }

    function renderButtons() {
        return (
            <div>
                <button className='btn btn-primary' onClick={editSubmitted}>Edit User</button>
            </div>
        )
    }

    return (
        <div className='container'>
            <img className="avatar" src='/default.png'></img>
            <h2>Welcome, {user.username}.</h2>
            <p>Your user id is {user.id}.</p>
            {renderButtons()}
        </div>
    )
}

function EditUserForm({ user, updateUser, submitCallback }) {

    function editSubmitted() {
        event.preventDefault()
        submitCallback()
    }

    function renderButtons() {
        return (
            <div>
                <button className='btn btn-primary' onClick={editSubmitted}>Save</button>
            </div>
        )
    }

    return (
        <div className='container'>
            <h3>Edit User:</h3>
            <img className="col-md-3" src='/default.png'></img>
            <div className="form-group">
                <label htmlFor="username">Username:</label>
                <input type="text" className="form-control" name="username" id="username"
                placeholder={user.username} value={user.username} onChange={(event) => updateUser('username', event.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="username">Password:</label>
            <input type="text" className="form-control" name="password" id="password"
            placeholder={user.password} value={user.password} onChange={(event) => updateUser('password', event.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="email">Email:</label>
            <input type="text" className="form-control" name="email" id="email"
            placeholder={user.email} value={user.email} onChange={(event) => updateUser('email', event.target.value)} />
          </div>
            <p>Your user id is {user.id} and you cannot change this.</p>
            <br/>
          {renderButtons()}
        </div>
    )
}

function Header({ status }) {

    return (
        <div className='header'>
            <h1>&nbsp;&nbsp;&nbsp;CodeToGo </h1>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{status}</span>
            <br/>
        </div>
    )
}

function NavigationButtons( { projects, files, code, editOwners, editUser } ) {
    return (
        <nav className='navbar navbar-expand-sm bg-dark navbar-dark'>
            <a className='navbar-brand' gref='#'>Nav</a>
            <button className='navbar-toggler' type='button' data-toggle='collapse' data-target='collapisbleNavbar'>
                <span className='navbar-toggler-icon'></span>
            </button>
            <div className='collapse navbar-collapse' id='collapsibleNavbar'>
                <button className='btn btn-primary' onClick={projects}>Projects</button>
                <button className='btn btn-primary' onClick={files}>Files</button>
                <button className='btn btn-primary' onClick={code}>Code</button>
                <button className='btn btn-primary' onClick={editOwners}>Add/Remove Owners</button>
                <button className='btn btn-primary' onClick={editUser}>Edit User</button>
            </div>
        </nav>
    )
}

function CodeToGoForm({proj, file, updateProject, updateFile, submitCallback}) {

    function formSubmitted(event) {
        // Prevent the browser from re-loading the page.
        event.preventDefault()
        submitCallback()
    }

    function renderButtons() {
        return (
            <div>
                <button type="submit" className="btn btn-primary">Save</button>
            </div>
        )
    }

    return (
        <div className='container'>
            <form onSubmit={formSubmitted}>
                <h3>Working on project:  {proj.name} ({proj.type})</h3>
                <p>Filename:</p>
                <input type="text" className="form-control" name="filename" id="filename"
                placeholder="name your file..." value={file.name} onChange={(event) => updateFile('name', event.target.value)} />
                <br/>
                <br/>
                <textarea value={file.code} className="form-control" placeholder='code here' onChange={(event) => updateFile('code', event.target.value)}></textarea>
                <br/>
                <br/>
                <br/>
                {renderButtons()}
            </form>
        </div>
    )
}

function ProjectsForm( {projects, project, updateProject, createProject, loadProject, deleteProject} ) {
    const projs = projects.map((aProj) => (
        <Item item={aProj} onLoadClicked={loadProject} onDeleteClicked={deleteProject} />
    ))

    function jsProject() {
        event.preventDefault()
        createProject('js')
    }

    function pythonProject() {
        event.preventDefault()
        createProject('py')
    }

    function renderButtons() {
        return (
            <div>
                <button className='btn btn-primary' onClick={jsProject}>New JS Project</button>
                <button className='btn btn-primary' onClick={pythonProject}>New Python3 Project</button>

            </div>
        )
    }

    return (
        <div className='container'>
            <h3>Projects:</h3>
            <ul>
                {projs}
            </ul>
            <input type="text" className="form-control" name="name" id="name"
            placeholder="name your project..." value={project.name} onChange={(event) => updateProject('name', event.target.value)} />
            {renderButtons()}
        </div>
    )
}

function FilesForm( {project, file, updateProject, updateFile, createFile, loadFile, deleteFile, ownerCallback, runProject} ) {
    const files = project.files.map((aFile) => (
        <Item item={aFile} onLoadClicked={loadFile} onDeleteClicked={deleteFile} />
    ))

    function renderButtons() {
        return (
        <div>
            <button className='btn btn-primary' onClick={createFile}>New File</button>
            <button className='btn btn-primary' onClick={ownerCallback}>Manage Project Users</button>
            <button className='btn btn-primary' onClick={runProject}>Run Project</button>
        </div>
        )
    }

    return (
        <div className='container'>
            <h3>{project.name} ({project.type}) Files:</h3>
            <ul>
                {files}
            </ul>
            {renderButtons()}
        </div>
    )
}

function Item( { item, onLoadClicked, onDeleteClicked } ) {
    return (
        <li>
            <label htmlFor=''>{item.name}</label>
            <button className='btn btn-primary' onClick={event => onLoadClicked(item)}>Load</button>
            <button className='btn btn-primary' onClick={event => onDeleteClicked(item)}>Delete</button>
        </li>
    )

}

function OwnerForm({anID, updateID, submitCallback}) {
    function addSubmitted() {
        // Prevent the browser from re-loading the page.
        event.preventDefault()
        submitCallback(0)
    }

    function removeSubmitted() {
        // Prevent the browser from re-loading the page.
        event.preventDefault()
        submitCallback(1)
    }

    function renderButtons() {
        return (
            <div>
                <button className='btn btn-primary' onClick={addSubmitted}>Add User to Project</button>
                <button className='btn btn-primary' onClick={removeSubmitted}>Remove User from Project</button>
            </div>
        )
    }

    return (
        <div className='container'>
            <h3>Manage Project Users:</h3>
            <br/>
            <input type="text" className="form-control" name="owner" id="owner"
            placeholder="name your project..." value={anID} placeholder='enter the id of the user you want to add/remove' onChange={(event) => updateID(event.target.value)} />
            <br/>
            <br/>
            {renderButtons()}
        </div>
    )
}
  
// Users is the entry-point for this app.
function CodeToGo() {

    // empty user template
    let emptyAuth = { username: '', password: ''}
    let emptyUser = { username: '', email: '', id: undefined}
    let emptyProject = { id: undefined, owners : [], type : '', name : '', files : []}
    let emptyFile = { name: '', code: '' }
    // store this template in the state
    let [auth, setAuth] = React.useState(emptyAuth)
    let [user, setUser] = React.useState(emptyUser)
    let [status, setStatus] = React.useState('')
    let [mode, setMode] = React.useState(0)
    let [project, setProject] = React.useState(emptyProject)
    let [projects, setProjects] = React.useState([])
    let [file, setFile] = React.useState({})
    let [addRemoveID, setAddRemoveID] = React.useState(undefined)

    //  post options streamlined
    function jsonPost(data) {
        this.method = 'POST'
        this.headers = {'Content-Type': 'application/json'}
        this.body = JSON.stringify(data)
        return this
    }
  
    // login user to the server
    function postAuth(route) {
        fetch(route, new jsonPost({auth}))
        .then(res => res.json())
        .then(response => updateState(response))
    }

    function postUser(route, newUser) {
        fetch(route, new jsonPost( {auth, newUser}))
        .then(res => res.json())
        .then(response => updateState(response))
    }

    function postNewProject(route, proj) {
        fetch(route, new jsonPost({ auth, proj}))
        .then(res => res.json())
        .then(response => updateState(response))
    }

    function postRemoveProject(route, proj) {
        fetch(route, new jsonPost({ auth, proj }))
        .then(res => res.json())
        .then(response => updateState(response))
    }

    function postFile(route, aFile) {
        fetch(route, new jsonPost({ auth, project, aFile }))
        .then(res => res.json())
        .then(response => updateState(response))
    }

    function postOwner(route) {
        const projID = project.id
        fetch(route, new jsonPost({ auth, projID, addRemoveID}))
        .then(res => res.json())
        .then(response => updateState(response))
    }

    function postRunProject(route) {
        const projID = project.id
        fetch(route, new jsonPost({ auth, projID}))
        .then(res => res.json())
        .then(response => updateState(response))
    }

    function updateProject(field, value) {
        let newProj = {...project}
        newProj[field] = value
        setProject(newProj)
    }

    function loadProject(proj) {
        const id = proj.id
        fetch('/projects/load', new jsonPost({ auth, id}))
        .then(res => res.json())
        .then(response => updateState(response))
    }

    function createFile() {
        setFile(emptyFile)
        setMode(3)
    }

    function updateFile(field, value) {
        let newFile = {...file}
        newFile[field] = value
        setFile(newFile)
    }

    function loadFile(aFile) {
        const index = project.files.findIndex(f => f === aFile)
        if (index === -1) {
            return
        }
        setFile(aFile)
        setMode(3)
    }

    function deleteFile(aFile) {
        postFile('/projects/deleteFile', aFile)
    }

    function updateARID(value) {
        setAddRemoveID(value)
    }

    function updateState(data) {
        if (data.message !== undefined) {
            setStatus(data.message)
        }
        if (data.mode !== undefined) {
            setMode(data.mode)
        }
        if (data.user !== undefined) {
            setUser(data.user)
        }
        if (data.project !== undefined) {
            setProject(data.project)
        }
        if (data.file !== undefined) {
            setFile(data.file)
        }
        if (data.projects !== undefined) {
            setProjects(data.projects)
        }
    }
  
    // update the authentication
    function updateAuth(field, value) {
        let newAuth = { ...auth }
        newAuth[field] = value
        setAuth(newAuth)
    }

    //  update the user
    function updateUser(field, value) {
        let newUser = {...user}
        newUser[field] = value
        setUser(newUser)
    }
  
    // when form submitted
    function loginSubmitted() {
        if (validateLogin(auth)) {
          postAuth('/users/login')
        }
    }

    function userSubmitted() {
        if (validateLogin(user) && validateEmail(user.email)) {
            postUser('/users/update', user)
        }
    }

    function fileSubmitted() {
        console.log(file)
        postFile('/projects/submitFile', file)
    }

    function createProject(type) {
        const proj = {...emptyProject}
        proj.id = undefined
        proj.name = project.name
        proj.owners = [user.id]
        if (type === 'js') {
            proj.type = 'js'
        }
        else if (type === 'py') {
            proj.type = 'py'
        }
        postNewProject('/projects/create', proj)
    }

    function deleteProject(proj) {
        postRemoveProject('/projects/delete', proj)
    }

    // when owner add/removed
    function ownerSubmitted(i) {
        if (i === 0) {
            postOwner('/projects/addOwner')
        }
        else if (i === 1) {
            postOwner('/projects/removeOwner')
        }
    }

    function ownerMode() {
        if (project.id === undefined) {
            setStatus('You can\'t add or remove users to the project until have you have a project loaded.')
            return
        }
        setMode(4)
    }

    function editUserMode() {
        setMode(5)
    }

    function setProjectsMode() {
        setMode(1)
    }

    function setFilesMode() {
        setMode(2)
    }

    function setCodeMode() {
        setMode(3)
    }

    function runProject() {
        setStatus('Project is running, please wait!')
        postRunProject('/projects/run')
    }

    //  validate the Login
    function validateLogin(login) {
        //  check if email is basically valid
        let blank = blankName(login.username)
        if (blank) {
            return false
        }
        blank = blankName(login.password)
        if (blank) {
            return false
        }
        return true
    }
  
    function blankName(username) {
        //  check if first or last name is blank
        if (username.length === 0) {
            return true
        }
        return false
    }

    //  validate email
    function validateEmail(email) {
        // w3 spec for validating email addresses
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
            return true
        }
        return false
    }
  
    if (mode === 0) {
        return (
            <div className="login">
              <Header status={status} />
              <LoginForm auth={auth} updateAuth={updateAuth} submitCallback={loginSubmitted} />
            </div>
          )   
    }
    else if (mode === 1) {
        return (
            <div className='projects'>
                <NavigationButtons projects={setProjectsMode} files={setFilesMode} code={setCodeMode} editOwners={ownerMode} editUser={editUserMode} />
                <br/>
                <Header status={status} />
                <br/>
                <ViewUserForm user={user} submitCallback={editUserMode}/>
                <br/>
                <ProjectsForm projects={projects} project={project} updateProject={updateProject} createProject={createProject} loadProject={loadProject} deleteProject={deleteProject} />
            </div>
        )
    }
    else if (mode === 2) {
        return (
            <div className='files'>
                <NavigationButtons projects={setProjectsMode} files={setFilesMode} code={setCodeMode} editOwners={ownerMode} editUser={editUserMode} />
                <br/>
                <Header status={status} />
                <br/>
                <ViewUserForm user={user} submitCallback={editUserMode}/>
                <FilesForm project={project} file={file} updateProject={updateProject} updateFile={updateFile} createFile={createFile} loadFile={loadFile} deleteFile={deleteFile} ownerCallback={ownerMode} runProject={runProject} />
            </div>
        )
    }
    else if (mode === 3) {
        return (
            <div className='code'>
                <NavigationButtons projects={setProjectsMode} files={setFilesMode} code={setCodeMode} editOwners={ownerMode} editUser={editUserMode} />
                <br/>
                <Header status={status} />
                <br/>
                <ViewUserForm user={user} submitCallback={editUserMode}/>
                <br/>
                <CodeToGoForm proj={project} file={file} updateProject={updateProject} updateFile={updateFile} submitCallback={fileSubmitted} />
            </div>
        )
    }
    else if (mode === 4) {
        return (
            <div className='owner'>
                <NavigationButtons projects={setProjectsMode} files={setFilesMode} code={setCodeMode} editOwners={ownerMode} editUser={editUserMode} />
                <br/>
                <Header status={status} />
                <br/>
                <ViewUserForm user={user} submitCallback={editUserMode}/>
                <OwnerForm anID={addRemoveID} updateID={updateARID} submitCallback={ownerSubmitted} />
            </div>
        )
    }
    else if (mode === 5) {
        return (
            <div className='editUser'>
                <NavigationButtons projects={setProjectsMode} files={setFilesMode} code={setCodeMode} editOwners={ownerMode} editUser={editUserMode} />
                <br/>
                <Header status={status} />
                <br/>
                <EditUserForm user={user} updateUser={updateUser} submitCallback={userSubmitted} />
            </div>
        )
    }
}
  
  //export default Users;
  ReactDOM.render(<CodeToGo />, document.getElementById('root'));