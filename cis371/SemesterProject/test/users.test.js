const users = require('../js/users')
const projects = require ('../js/projects')
const fs = require('fs')



function scrubFS() {
    //  if the temp directory exists
    if (fs.existsSync(`./temp`)) {
        //  remove it
        fs.rmdirSync(`./temp`)
    }

    try {
        //  for each file in the users directory, remove it
        fs.readdirSync('./users').forEach(file => {
            fs.unlinkSync(`./users/${file}`)
        })

        //  for each file in the projects directory, remove it
        fs.readdirSync('./projects').forEach(file => {
            fs.unlinkSync(`./projects/${file}`)
        })
    }
    catch (err) {
        console.error(err)
    }
}

function restore() {
    users.users = [
        {id: 0, username: 'admin', password: 'admin', email: 'a@mail.com', projects: [0]}
    ]

    const newUser = users.User({id: 0, username: 'admin', password: 'admin', email: 'a@mail.com', projects: [0]})
    const req = {
        body: newUser
    }
    users.createUser(req)

    projects.projects = [
        new projects.Project(0, [0], 'kt', 'sample')
    ]
    scrubFS()
}





describe("users", () => {
    describe("User", () => {
        it("Returns a user", () => {
            restore()      
			let test = {
							id: 999,
							username: 'test',
							password: 'test',
							email: 'amail@mail.com',
							projects: []
						}
			let user = new users.User(test)
            expect(user.id).toEqual(999)
			expect(user.username).toEqual('test')
			expect(user.password).toEqual('test')
			expect(user.email).toEqual('amail@mail.com')
            expect(user.projects).toEqual([])
            restore()
        })
    })
    
    describe("ResObj", () => {
        it("Returns a response object", () => {     
            restore()
            const test = {
                id: 999,
                username: 'test',
                password: 'test',
                email: 'amail@mail.com',
                projects: []
            }
            let user = new users.User(test)
            
            
			let res = new users.ResObj('a message', 1, user, projects.projects[0], [])
            expect(res.message).toEqual('a message')
			expect(res.mode).toEqual(1)
			expect(res.user).toEqual(user)
			expect(res.project).toEqual(projects.projects[0])
            expect(res.projects).toEqual([])
            restore()
        })
    })

    describe("findUserID", () => {
        it("returns the first available user id", () => {
            restore()
            const id = users.findUserID()
            expect(id).toEqual(1)
            restore()
        })
    })

    describe("loginUser", () => {
        it("log the user in and advance the app to project mode", () => {
            restore()
            let auth = {
                username: 'admin',
                password: 'admin'
            }
            let body = {
                auth: auth,
            }
            let req = {
                body: body
            }
            let res = users.loginUser(req)
            expect(res.mode).toEqual(1)
            auth = {
                username: 'notreal',
                password: 'notreal'
            }
            body = {
                auth: auth,
            }
            req = {
                body: body
            }
            res = users.loginUser(req)
            expect(res.mode).toEqual(0)
            restore()
        })
    })

    describe("authUser", () => {
        it("authenticates the user", () => {
            restore()
            let username = 'admin'
            let password = 'admin'
            let res = users.authUser(username, password)
            expect(res).toEqual(0)
            username = 'notreal'
            password = 'notreal'
            res = users.authUser(username, password)
            expect(res).toEqual(-1)
            restore()
        })
    })

    describe("createUser", () => {
        it("returns a response object indicating a user was created or not", () => {
            restore()
            let body = {
                id: undefined,
                username: 'test',
                password: 'test',
                email: 'amail@mail.com',
            }
            let req = {
                body: body
            }
            let newUser = new users.User(req.body)
            newUser.id = users.findUserID()
            expect(users.createUser(req).mode).toEqual(1)
            restore()
        })
    })

    describe("authDeleteUser", () => {
        it("deletes a user with authorization", () => {
            restore()
            let body = {
                username: 'admin',
                password: 'admin'
            }
            let req = {
                body: body
            }
            let res = users.authDeleteUser(req)
            expect(res.mode).toEqual(1)

            body = {
                username: 'fake',
                password: 'fake'
            }
            req = {
                body: body
            }
            res = users.authDeleteUser(req)
            expect(res.mode).toEqual(0)
            restore()
        })
    })

    describe("updateUser", () => {
        it("updates user", () => {
            restore()
            let auth = {
                username: 'admin',
                password: 'admin'
            }
            let newUser = new users.User({id: 1, username: 'admin', password: 'test', email: 'afakemail@mail.com'})
            let body = {
                auth: auth,
                newUser: newUser
            }
            let req = {
                body: body
            }
            let res = users.updateUser(req)
            expect(res.mode).toEqual(1)

            auth = {
                username: 'fake',
                password: 'fake'
            }
            newUser = new users.User({id: 1, username: 'admin', password: 'admin', email: 'afakemail@mail.com'})
            body = {
                auth: auth,
                newUser: newUser
            }
            req = {
                body: body
            }
            res = users.authDeleteUser(req)
            expect(res.mode).toEqual(0)
            restore()
        })
    })

    describe("createProject", () => {
        it("create project", () => {
            restore()
            let auth = {
                username: 'admin',
                password: 'admin'
            }
            let project = {
                name: 'test'
            }
            let body = {
                auth: auth,
                project: project
            }
            let req = {
                body: body
            }
            let res = users.createProject(req)
            expect(res.mode).toEqual(2)

            project = {
                name: 'sample'
            }
            body = {
                auth: auth,
                project: project
            }
            req = {
                body: body
            }
            res = users.createProject(req)
            expect(res.mode).toEqual(1)
            restore()
        })
    })

    describe("validateUser", () => {
        it("checks if the user is valid", () => {
            restore()
            let newUser = new users.User({id: 5, username: 'free', password: 'free', email: 'afakemail56@mail.com'})
            let valid = users.validateUser(newUser)
            expect(valid).toEqual(true)

            newUser = new users.User({id: 5, username: 'admin', password: 'test', email: 'afakemail56@mail.com'})
            valid = users.validateUser(newUser)
            expect(valid).toEqual(false)

            newUser = new users.User({id: 5, username: '', password: 'free', email: 'afakemail56@mail.com'})
            valid = users.validateUser(newUser)
            expect(valid).toEqual(false)

            newUser = new users.User({id: 5, username: 'free', password: '', email: 'afakemail56@mail.com'})
            valid = users.validateUser(newUser)
            expect(valid).toEqual(false)

            newUser = new users.User({id: 5, username: 'free', password: 'free', email: 'google.com'})
            valid = users.validateUser(newUser)
            expect(valid).toEqual(false)
            
            restore()
        })
    })
    

})
