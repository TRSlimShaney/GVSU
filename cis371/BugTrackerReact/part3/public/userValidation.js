//  validate the user
let validateUser = (user, users) => {
    //  check if email is basically valid
    let valid = validateEmail(user.email, users)
    if (!valid) {
        return false
    }
    valid = validateName(user.fname, user.lname, users)
    if (!valid) {
        return false
    }
    return true
  }

//  validate email
let validateEmail = (email, users) => {
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

//  validate first and last name
let validateName = (fname, lname, users) => {
    //  check if first or last name is blank
    if (fname.length === 0 || lname.length === 0) {
        return false
    }
    //  check if there is a user with the same first and last name
    let index = users.findIndex(aUser => aUser.fname === fname)
    let index2 = users.findIndex(aUser => aUser.lname === lname)
    if (index !== -1 && index2 !== -1 && index === index2) {
        return false
    }
    return true
}

exports.validateUser = validateUser