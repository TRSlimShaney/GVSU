// CreateUser
function Briefing() {
    return (
      <div className="container">
        <h3>CodeToGo is a web app that lets you code anywhere!</h3>
        <p>Experience a lightweight and hasslefree NodeJS environment in your browser:</p>
            <ul>
                <li>NodeJS Framework supported</li>
                <li>Collaborative Features</li>
                <li>Code runs on the server</li>
            </ul>
      </div>
    )
}

function Navigation() {
    function renderLinks() {
        return (
            <div>
                <a href='/app'>Start Coding...</a>
                <br/>
                <a href='/reg'>Create Account...</a>
                <br/>
                <a href='https://drive.google.com/open?id=1lpz2A5PW8ylKzAPCTh0WLW_IbI5Kkxgn'>Documentation</a>
                <br/>
                <a href='https://github.com/TRSlimShaney/CIS371/tree/master/SemesterProject'>GitHub</a>
            </div>
        )
    }
  
    return (
      <div className="container">
          {renderLinks()}
      </div>
    )
}
  
// Users is the entry-point for this app.
function CreateUser() {
  
    return (
        <div className="container">
            <br/>
            <h1> Welcome </h1>
            <Briefing />
            <br></br>
            <Navigation />
        </div>
    )
}
  
  //export default Users;
  ReactDOM.render(<CreateUser />, document.getElementById('root'));