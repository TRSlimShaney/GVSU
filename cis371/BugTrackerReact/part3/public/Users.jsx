// UserForm is the form shown for the creation and editing of users
function UserForm({ user, updateUser, formMode, submitCallback, cancelCallback }) {

  let cancelClicked = (event) => {
    event.preventDefault()
    cancelCallback()
  }

  // The form will have two different sets of buttons:
  // * A "Create" button when creating, and 
  // * An "Update" and "Cancel" button when updating.
  let renderButtons = () => {
    if (formMode === "new") {
      return (
        <button type="submit" className="btn btn-primary">Create</button>
      )
    }
    else {
      return (
        <div className="form-group">
          <button type="submit" className="btn btn-primary">Save</button>
          <button type="submit" className="btn btn-danger" onClick={cancelClicked} >Cancel</button>
        </div>
      )
    }
  }

  // In this version, the Users component needs access to the state so it can initialize the 
  // form fields when the edit button is clicked.  Therefore we move the state up.

  let formSubmitted = (event) => {
    // Prevent the browser from re-loading the page.
    event.preventDefault()
    submitCallback()
  }

  return (
    <div className="user-form">
      <h1> User </h1>
      <form onSubmit={formSubmitted}>
        <div className="form-group">
          <img className="col-md-3" src={user.thumb}></img>
        </div>
        <div className="form-group">
          <label>First Name:</label>
          <input type="text" className="form-control" autoComplete='given-name' name="fname" id="fname"
            placeholder="First Name" value={user.fname} onChange={(event) => updateUser('fname', event.target.value)} />
        </div>
        <div className="form-group">
          <label htmlFor="lname">Last Name:</label>
          <input type="text" className="form-control" autoComplete='family-name' name="lname" id="lname"
            placeholder="Last Name" value={user.lname} onChange={(event) => updateUser('lname', event.target.value)} />
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
        <br></br>
        {renderButtons()}
      </form>
    </div>
  )
}

function UserListItem({ user, onEditClicked, onDeleteClicked }) {
  return (
    <tr>
      <td className="col-md-3">{user.fname}</td>
      <td className="col-md-3">{user.lname}</td>
      <td className="col-md-3">{user.email}</td>
      <td className="col-md-3">
        <img className="col-md-3" src={user.thumb}></img>
      </td>
      <td className="col-md-3 btn-toolbar">
        <button className="btn btn-success btn-sm" onClick={event => onEditClicked(user)}>
          <i className="glyphicon glyphicon-pencil"></i> Edit
          </button>
        <button className="btn btn-danger btn-sm" onClick={event => onDeleteClicked(user)}>
          <i className="glyphicon glyphicon-remove"></i> Delete
          </button>
      </td>
    </tr>
  )
}

// UserList returns a table of the users and their respective properties
function UserList({ users, onEditClicked, onDeleteClicked }) {
  const userItems = users.map((user) => (
    <UserListItem key={user.id} user={user} onEditClicked={onEditClicked} onDeleteClicked={onDeleteClicked} />
  ));

  return (
    <div className="user-list">
      <table className="table table-hover">
        <thead>
          <tr>
            <th className="col-md-3">First Name</th>
            <th className="col-md-3">Last Name</th>
            <th className="col-md-3">Email</th>
            <th className="col-md-3">Avatar</th>
            <th className="col-md-3">Actions</th>
          </tr>
        </thead>
        <tbody>
          {userItems}
        </tbody>
      </table>
    </div>
  )
}

// Users is the entry-point for this app.
function Users() {

  // get the users from the server
  function getUsers() {
    fetch('/users/get')
    .then(res => res.json())
    .then(list => setUserList(list))
  }

  // post data to the server
  function postUser(route, user) {
    const postOptions = {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(user)
    }
    fetch(route, postOptions)
    .then(res => res.json())
    .then(getUsers())
  }

  // update a user
  function updateUser(field, value) {
    let newUser = { ...currentUser }
    newUser[field] = value
    setCurrentUser(newUser)
  }

  //  validate the user
function validateNewUser(user) {
  //  check if email is basically valid
  let valid = validateEmail(user.email)
  if (!valid) {
      return false
  }
  valid = validateName(user.fname, user.lname)
  if (!valid) {
    return false
  }
  return true
}

function validateExistingUser(user) {
  if (!blankName(user.fname, user.lname) && w3CompliantEmail(user.email)) {
    return true
  }
  return false
}

//  validate email
function validateEmail(email) {
  if (!w3CompliantEmail(email)) {
    return false
  }
  //  check if email is already in use
  let index = userList.findIndex(aUser => aUser.email === email)
  if (index !== -1) {
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

//  validate first and last name
function validateName(fname, lname) {
  if (blankName(fname, lname)) {
    return false
  }
  //  check if there is a user with the same first and last name
  let index = userList.findIndex(aUser => aUser.fname === fname)
  let index2 = userList.findIndex(aUser => aUser.lname === lname)
  if (index !== -1 && index2 !== -1 && index === index2) {
    return false
  }
  return true
}

function blankName(fname, lname) {
  //  check if first or last name is blank
  if (fname.length === 0 || lname.length === 0) {
    return true
  }
  return false
}

// this data is the state of our react app
let [userList, setUserList] = React.useState([])


// zk This needs to be in a useEffect hook so as not to create an infinite loop.
    getUsers()
// formMode is a string used to denote the type of form submission, stored in state
let [formMode, setFormMode] = React.useState("new")
// empty user template
let emptyUser = { fname: '', lname: '', email: '', thumb: 'default.png'}
// store this template in the state
let [currentUser, setCurrentUser] = React.useState(emptyUser)

  // when form submitted
  let formSubmitted = () => {
    if (formMode === "new") {
      if (validateNewUser(currentUser)) {
        postUser('/users/create', currentUser)
      }
    }
    else if (formMode === "update") {
      if (validateExistingUser(currentUser)) {
        postUser('/users/update', currentUser)
      }
    }
  }

  // when a edit button for a certain user is clicked, form mode is update
  let editClicked = (user) => {
    setFormMode("update")
    setCurrentUser(user)
  }
  // when cancel is clicked, form mode is new with an empty user
  let cancelClicked = () => {
    setFormMode("new")
    setCurrentUser(emptyUser)
  }
  // when delete clicked for user
  let deleteClicked = (user) => {
    postUser('/users/delete', user)
    // reset the form after someone clicks delete.
    cancelClicked()
  }

  return (
    <div className="users">
      <h1> Users </h1>
      <UserForm formMode={formMode} user={currentUser} updateUser={updateUser}
        submitCallback={formSubmitted} cancelCallback={cancelClicked} />
      <div />
      <UserList users={userList} onEditClicked={editClicked} onDeleteClicked={deleteClicked} />
    </div>
  )
}

//export default Users;
ReactDOM.render(<Users />, document.getElementById('root'));
