export default class QwixxController {
    constructor(model, view) {
        this.model = model
        this.view = view
        this.view.onRollButtonClicked(() => this.rollButtonClicked())
		this.view.onNumberBoxClicked((row, col) => this.numberBoxClicked(row, col))
		this.view.onPassClicked(() => this.passClicked())
		this.view.onViewClicked(() => this.viewClicked())
		this.view.onDoneClicked(() => this.doneClicked())
    }


    rollButtonClicked() {
        this.model.rollDice()
        this.view.update(this.model)
    }

	numberBoxClicked(row, col) {
		if (this.model.checkIfNBValid(row, col)) {
			this.model.numberBoxes[row][col].crossedOut = true
			this.model.tallyXs()
			this.view.update(this.model)
		}
	}

	passClicked() {
		this.model.pass()
		this.view.update(this.model)
	}

	viewClicked() {
		this.model.changeView()
		this.view.update(this.model)
	}

	doneClicked() {
		this.model.changePlayers()
		this.view.update(this.model)
	}
}
