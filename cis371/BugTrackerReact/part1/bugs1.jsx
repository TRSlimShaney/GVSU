const users = [
    { id: 0, lname: 'Shaney', fname: 'Slim', email: 'atotallyreal@mail.com', thumb: 'default.jpg'},
    { id: 1, lname: 'Jones', fname: 'Bob', email: 'donteventry@mail.com', thumb: 'default.jpg'},
    { id: 2, lname: 'Stacy', fname: 'Shane', email: 'areyoureadingthese@mail.com', thumb: 'default.jpg'}
    ]

    function User(props) {
        return <li> {props.id} {props.lname} {props.fname} {props.email} {props.thumb} </li>
    }

    function UserRows(props) {
        return <ul className='userRow'> {
            props.users.map((user, index) => (
                <User
                    id={user.id}
                    lname={user.lname}
                    fname={user.fname}
                    email={user.email}
                    thumb={user.thumb}
                />
            ))}
        </ul>
    }

    function UsersList(props) {
        return (
            <div>
                <h1> Users </h1>
                <UserRows users={props.theUsers}/>
            </div>
        )
    }



//  render the document
ReactDOM.render(<UsersList theUsers={users} />, document.getElementById('main'));
