import QwixxModel from '../QwixxModel.js'

describe("QwixxModel", () => {
    describe(".rollDie", () => {
        it("Returns a value between 1 and 6", () => {         
            let result = QwixxModel.rollDie()
            expect(result).toBeGreaterThanOrEqual(1)
            expect(result).toBeLessThanOrEqual(6)
        })
    })

    describe("#rollDice", () => {
        let model
        beforeEach(() => {
            // Mock rollDie so it will return a precise 
            // sequence of integers.
            QwixxModel.rollDie = jest.fn()
                .mockImplementationOnce(() => 1)
                .mockImplementationOnce(() => 2)
                .mockImplementationOnce(() => 3)
                .mockImplementationOnce(() => 4)
                .mockImplementationOnce(() => 5)
                .mockImplementationOnce(() => 6)
            model = new QwixxModel(4, 12)
        })

        it("sets all the dice", () => {
            // Verify that the values returned by rollDie end up 
            // in the wdice and dice arrays
            model.rollDice()
			expect(model.phase).toEqual(2)
            expect(model.wdice).toEqual([1, 2])
            expect(model.dice).toEqual([3, 4, 5, 6])
			model.rollDice()
			expect(model.phase).toEqual(2)
        })
    })

	describe('#pass', () => {
		let model
		beforeEach(() => {
			QwixxModel.pass = jest.fn()
			model = new QwixxModel(4, 12)
		})

		it('passes with no initial roll', () => {
			model.pass()
			expect(model.phase).toEqual(1)
			expect(model.alreadyPassed).toEqual(false)
		})
		it('passes after an initial roll', () => {
			model.rollDice()
			model.pass()
			expect(model.phase).toEqual(3)
			expect(model.alreadyPassed).toEqual(true)
			expect(model.penalty).toEqual(0)
		})
		it('passes twice after an initial roll', () => {
			model.rollDice()
			model.pass()
			expect(model.alreadyPassed).toEqual(true)
			model.pass()
			expect(model.phase).toEqual(4)
			expect(model.penalty).toEqual(-5)
		})
	})

	describe('#changeView', () => {
		let model
		beforeEach(() => {
			QwixxModel.changeView = jest.fn()
			model = new QwixxModel(4, 12)
		})

		it('check if current view has changed', () => {
			expect(model.currentView).toEqual(0)		
			model.changeView()
			expect(model.currentView).toEqual(1)
			model.changeView()
			expect(model.currentView).toEqual(0)
		})
	})

	describe('#changePlayers', () => {
		let model
		beforeEach(() => {
			QwixxModel.rollDie = jest.fn()
                .mockImplementationOnce(() => 1)
                .mockImplementationOnce(() => 2)
                .mockImplementationOnce(() => 3)
                .mockImplementationOnce(() => 4)
                .mockImplementationOnce(() => 5)
                .mockImplementationOnce(() => 6)
			QwixxModel.changePlayers = jest.fn()
			model = new QwixxModel(4, 12)
		})

		it('check if current player has changed', () => {
			model.rollDice()
			model.pass()
			model.pass()
			expect(model.currentPlayer.num).toEqual(0)
			model.changePlayers()
			expect(model.currentPlayer.num).toEqual(0)
			model.otherHasGone = true
			model.changePlayers()
			expect(model.currentPlayer.num).toEqual(1)
			model.rollDice()
			model.pass()
			model.pass()
			model.otherHasGone = true
			model.changePlayers()
			expect(model.currentPlayer.num).toEqual(0)
		})
	})

	describe('#checkifNBValidOther', () => {
		let model
		beforeEach(() => {
			QwixxModel.rollDie = jest.fn()
                .mockImplementationOnce(() => 1)
                .mockImplementationOnce(() => 2)
                .mockImplementationOnce(() => 3)
                .mockImplementationOnce(() => 4)
                .mockImplementationOnce(() => 5)
                .mockImplementationOnce(() => 6)
			QwixxModel.checkIfNBValid = jest.fn()
			model = new QwixxModel(4, 12)
		})

		it('check if the other player can check off during current player turn', () => {
			model.rollDice()
			model.changeView()
			expect(model.checkIfNBValid(0, 0)).toEqual(false)
			expect(model.checkIfNBValid(0, 1)).toEqual(true)
		})
	})

	describe('#checkIfNBValid', () => {
		let model
		beforeEach(() => {
			QwixxModel.rollDie = jest.fn()
                .mockImplementationOnce(() => 1)
                .mockImplementationOnce(() => 2)
                .mockImplementationOnce(() => 3)
                .mockImplementationOnce(() => 4)
                .mockImplementationOnce(() => 5)
                .mockImplementationOnce(() => 6)
			QwixxModel.checkIfNBValid = jest.fn()
			QwixxModel.tallyXs = jest.fn()
			QwixxModel.pass = jest.fn()
			QwixxModel.changePlayers = jest.fn()
			model = new QwixxModel(4, 12)
		})

		it('check off box in the first half', () => {
			model.rollDice()
			expect(model.checkIfNBValid(0, 0)).toEqual(false)
			model.tallyXs()
			expect(model.Xs).toEqual([0, 0, 0, 0])
			expect(model.numberBoxes[0][1].crossedOut).toEqual(false)
			expect(model.checkIfNBValid(0, 1)).toEqual(true)
			model.numberBoxes[0][1].crossedOut = true
			model.tallyXs()
			expect(model.Xs).toEqual([1, 0, 0, 0])
			expect(model.numberBoxes[0][1].crossedOut).toEqual(true)
		})
		it('check off box in the second half', () => {
			model.rollDice()
			expect(model.checkIfNBValid(2, 2)).toEqual(false)
			model.tallyXs()
			expect(model.Xs).toEqual([0, 0, 0, 0])
			expect(model.numberBoxes[2][2].crossedOut).toEqual(false)
			expect(model.checkIfNBValid(2, 9)).toEqual(true)
			model.numberBoxes[2][9].crossedOut = true
			model.tallyXs()
			expect(model.Xs).toEqual([0, 0, 1, 0])
			expect(model.numberBoxes[2][9].crossedOut).toEqual(true)
		})
		it('check off a lockbox', () => {
			model.rollDice()
			expect(model.checkIfNBValid(0, 11)).toEqual(false)
			model.numberBoxes[0][0].crossedOut = true
			model.numberBoxes[0][1].crossedOut = true
			model.numberBoxes[0][2].crossedOut = true
			model.numberBoxes[0][3].crossedOut = true
			model.numberBoxes[0][4].crossedOut = true
			model.tallyXs()
			expect(model.checkIfNBValid(0, 11)).toEqual(true)
			model.pass()
			model.pass()
			model.otherHasGone = true
			model.changePlayers()
			model.rollDice()
			expect(model.checkIfNBValid(0, 1)).toEqual(false)
		})
	})

	describe('#findCol', () => {
		let model
		beforeEach(() => {
			QwixxModel.rollDie = jest.fn()
                .mockImplementationOnce(() => 1)
                .mockImplementationOnce(() => 2)
                .mockImplementationOnce(() => 3)
                .mockImplementationOnce(() => 4)
                .mockImplementationOnce(() => 5)
                .mockImplementationOnce(() => 6)
			QwixxModel.findCol = jest.fn()
			QwixxModel.checkIfNBValid = jest.fn()
			model = new QwixxModel(4, 12)
		})

		it('find the last checked off box in the first half', () => {
			model.rollDice()
			expect(model.findCol(0)).toEqual(-1)
			expect(model.checkIfNBValid(0, 1)).toEqual(true)
			model.numberBoxes[0][1].crossedOut = true
			expect(model.findCol(0)).toEqual(1)
			
		})
		it('find the last checked off box in the second half', () => {
			model.rollDice()
			expect(model.findCol(2)).toEqual(-1)
			expect(model.checkIfNBValid(2, 9)).toEqual(true)
			model.numberBoxes[2][9].crossedOut = true
			expect(model.findCol(2)).toEqual(9)
		})
	})

	describe('#findValue', () => {
		let model
		beforeEach(() => {
			QwixxModel.findValue = jest.fn()
			model = new QwixxModel(4, 12)
		})

		it('find the value of a row and col', () => {
			expect(model.findValue(0, 0)).toEqual(2)
			expect(model.findValue(0, 10)).toEqual(12)
			expect(model.findValue(0, 2)).toEqual(4)
			expect(model.findValue(2, 0)).toEqual(12)
			expect(model.findValue(2, 10)).toEqual(2)
			expect(model.findValue(2, 2)).toEqual(10)
		})
	})

	describe('#clearArray', () => {
		let model
		beforeEach(() => {
			QwixxModel.clearArray = jest.fn()
			model = new QwixxModel(4, 12)
		})

		it('clear an array with zeroes', () => {
			let test = [-1, 2, 0, 9]
			expect(model.clearArray(test)).toEqual([0, 0, 0, 0])
		})
	})

	
})
