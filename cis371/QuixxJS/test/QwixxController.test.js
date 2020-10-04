import QwixxController from '../QwixxController.js'

describe("QwixxController", () => {

    let model
    let view
    let controller

    // Set up the test object and the mocks
    beforeEach(() => {
        model = {
            rollDice: jest.fn(),
			changeView: jest.fn(),
			changePlayers: jest.fn(),
			pass: jest.fn(),
			checkIfNBValid: jest.fn(),
			findCol: jest.fn(),
			tallyXs: jest.fn(),
			findValue: jest.fn(),
			clearArray: jest.fn(),
        }

        view = {
            onRollButtonClicked: jest.fn(),
			onNumberBoxClicked: jest.fn(),
			onPassClicked: jest.fn(),
			onViewClicked: jest.fn(),
			onDoneClicked: jest.fn(),
            update: jest.fn()
        }

        controller = new QwixxController(model, view)
    })

    describe("#rollButtonClicked", () => {
        it("Tells the model to roll the dice", () => {
            controller.rollButtonClicked();
            expect(model.rollDice).toHaveBeenCalledTimes(1)
        })
        it("Tells the view to update", () => {
            controller.rollButtonClicked()
            expect(view.update).toHaveBeenCalledWith(model)
        })
    })

	describe('#numberBoxClicked', () => {
		it('Tells the model to click a numberbox', () => {
			controller.numberBoxClicked(0, 0)
			expect(model.checkIfNBValid).toHaveBeenCalledTimes(1)
		})
	})

	describe('#passClicked', () => {
		it('Tells the model to pass', () => {
			controller.passClicked()
			expect(model.pass).toHaveBeenCalledTimes(1)
		})
		it('Tells the model to pass', () => {
			controller.passClicked()
			expect(view.update).toHaveBeenCalledWith(model)
		})
	})

	describe('#viewClicked', () => {
		it('Tells the model to change views to other player board', () => {
			controller.viewClicked()
			expect(model.changeView).toHaveBeenCalledTimes(1)
		})
		it('Tells the model to change views to other player board', () => {
			controller.viewClicked()
			expect(view.update).toHaveBeenCalledWith(model)
		})
	})
})
