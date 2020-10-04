// CreateUser
function CreateUserForm({ user, updateUser, submitCallback, cancelCallback }) {

    function cancelClicked(event) {
      event.preventDefault()
      cancelCallback()
    }

    function renderButtons() {
        return (
            <div>
                <button type="submit" className="btn btn-primary">Create</button>
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
        <form onSubmit={formSubmitted}>
          <div className="form-group">
            <img className="col-md-3" src={user.thumb}></img>
          </div>
          <div className="form-group">
            <label htmlFor="username">Username:</label>
            <input type="text" className="form-control" autoComplete='family-name' name="username" id="username"
              placeholder="Username" value={user.username} onChange={(event) => updateUser('username', event.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password:</label>
            <input type="text" className="form-control" autoComplete='family-name' name="password" id="password"
              placeholder="Password" value={user.password} onChange={(event) => updateUser('password', event.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="email">Email Address:</label>
            <input type="email" className="form-control" autoComplete='email' name="email" id="email"
              placeholder="name@example.com" value={user.email} onChange={(event) => updateUser('email', event.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="thumb">Avatar:</label>
            <select className="form-group" name='img' id="img" onChange={(event) => updateUser('thumb', event.target.value)}>
              <option value={user.thumb}>default.png</option>
            </select>
          </div>
          <p>Note:  This site is NOT SECURE.  Do NOT use passwords you use for other sites and do NOT expect confidentiality.</p>
          <br></br>
          {renderButtons()}
        </form>
      </div>
    )
}
  
// Users is the entry-point for this app.
function CreateUser() {

    // empty user template
    let emptyUser = { username: '', password: '', email: '', thumb: ''}
    // store this template in the state
    let [currentUser, setCurrentUser] = React.useState(emptyUser)
    let [status, setStatus] = React.useState('')
  
    // post data to the server
    function postUser(route, user) {
        const postOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(user)
        }
        fetch(route, postOptions)
        .then(res => res.json())
        .then(data => setStatus(data.message))
    }
  
    // update a user
    function updateUser(field, value) {
        let newUser = { ...currentUser }
        newUser[field] = value
        setCurrentUser(newUser)
    }
  
    // when form submitted
    function formSubmitted() {
        if (validateNewUser(currentUser)) {
          postUser('/users/create', currentUser)
        }
    }

    // when cancel is clicked, form mode is new with an empty user
    function cancelClicked() {

    }

    //  validate the user
    function validateNewUser(user) {
        //  check if email is basically valid
        let valid = w3CompliantEmail(user.email)
        if (!valid) {
            return false
        }
        let blank = blankName(user.username)
        if (blank) {
            return false
        }
        return true
    }
  
    function w3CompliantEmail(email) {
        // w3 spec for validating email addresses
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
            return true
        }
        return false
    }
  
    function blankName(username) {
        //  check if first or last name is blank
        if (username.length === 0) {
            return true
        }
        return false
    }
  
    return (
      <div className="container">
        <br/>
        <h1> New User </h1>
        <CreateUserForm user={currentUser} updateUser={updateUser} submitCallback={formSubmitted} cancelCallback={cancelClicked} />
        <br/>
        <span>{status}</span>
        <br/>
        <br/>
        <a href='/app/app.html'>Start Coding...</a>
      </div>
    )
}
  
  //export default Users;
  ReactDOM.render(<CreateUser />, document.getElementById('root'));