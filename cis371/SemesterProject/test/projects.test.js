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

    projects.projects = [
        new projects.Project(0, [0], 'kt', 'sample')
    ]
    scrubFS()
}


describe("projects", () => {
    describe("project", () => {
        it("returns a project", () => {
            restore()      
			let proj = new projects.Project(999, [0], 'js', 'test')
            expect(proj.id).toEqual(999)
			expect(proj.name).toEqual('test')
			expect(proj.type).toEqual('js')
			expect(proj.owners).toEqual([0])
            expect(proj.files[0].name).toEqual('app')
            restore()
        })
    })

    describe("findProjectID", () => {
        it("returns an available projectID", () => {
            restore()      
			expect(projects.findProjectID()).toEqual(1)
            restore()
        })
    })

    describe("deleteProject", () => {
        it("delete project based on id", () => {
            restore()
            projects.deleteProject(0)
			expect(projects.findProjectID()).toEqual(0)
            restore()
        })
    })


})