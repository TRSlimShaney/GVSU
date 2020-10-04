class Bug {
    constructor(id, title, user, desc, type, priority, status) {
        this.id = id
        this.title = title
        this.user = user
        this.desc = desc
        this.type = type
        this.priority = priority
        this.status = status
    }
}

module.exports = {
    Bug
}
