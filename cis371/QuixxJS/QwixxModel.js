// the Qwixx Model class
export default class QwixxModel {
    constructor(numRows, maxColumn) {
        this.numRows = numRows
        this.maxColumn = maxColumn
        this.wdice = [1, 2]
        this.dice = [3, 4, 5, 6]
		this.alreadyPassed = false
		//  1 is need to roll
		//  2 is need to use white dice or pass
		//  3 is need to use colored die or take -5		
		this.phase = 1
		
		//  setup the first player's number boxes
		let numberBoxes1 = new Array(this.numRows)
		for (let i = 0; i < this.numRows; ++i) {
			numberBoxes1[i] = new Array(this.maxColumn)
		}
		for (let i = 0; i < this.numRows; ++i) {
			for (let j = 0; j < this.maxColumn; ++j) {
				numberBoxes1[i][j] = new NumberBox()
				if (j === this.maxColumn - 1) {
					numberBoxes1[i][j].lockBox = true
				}
			}
		}

		//  setup the second player's number boxes
		let numberBoxes2 = new Array(this.numRows)
		for (let i = 0; i < this.numRows; ++i) {
			numberBoxes2[i] = new Array(this.maxColumn)
		}
		for (let i = 0; i < this.numRows; ++i) {
			for (let j = 0; j < this.maxColumn; ++j) {
				numberBoxes2[i][j] = new NumberBox()
				if (j === this.maxColumn - 1) {
					numberBoxes2[i][j].lockBox = true
				}
			}
		}
		
		//  create the players
		let player1 = new Player(0, numberBoxes1)
		let player2 = new Player(1, numberBoxes2)
		this.players = [player1, player2]
		//  the first player will go first
		this.currentView = 0
		this.currentPlayer = this.players[0]
		//  setup the instances to whoever is the current player
		
		//  chnage the object references
		this.numberBoxes = this.currentPlayer.numberBoxes
		this.totals = this.currentPlayer.totals
		//  primitives value copying
		this.total = this.currentPlayer.total
		this.penalty = this.currentPlayer.penalty
		//  shared variables
		this.Xs = [0, 0, 0, 0]
		this.otherHasGone = false
    }

	// change the view to the other player's board
	changeView() {
		if (this.currentView === 0) {
			this.players[0].total = this.total
			this.players[0].totals = this.totals
			this.players[0].penalty = this.penalty
			this.currentView = 1
		}
		else if (this.currentView === 1) {
			this.players[1].total = this.total
			this.players[1].totals = this.totals
			this.players[1].penalty = this.penalty
			this.currentView = 0
		}
		this.numberBoxes = this.players[this.currentView].numberBoxes
		this.totals = this.players[this.currentView].totals
		this.total = this.players[this.currentView].total
		this.penalty = this.players[this.currentView].penalty
	}

	// change the players
	changePlayers() {
		// don't let change if roll hasn't happened or other player hasn't gone
		if (this.phase === 1 || this.otherHasGone === false) {
			return
		}
		// apply a penalty for changing turns if not enough checks
		if (this.phase === 2 || (this.phase === 3 && this.alreadyPassed)) {
			this.penalty -= 5
		}
		// change the players
		if (this.currentPlayer.num === 0) {
			this.currentPlayer = this.players[1]
		}
		else if (this.currentPlayer.num === 1) {
			this.currentPlayer = this.players[0]
		}
		// reset shared variables
		this.Xs = this.clearArray(this.Xs)
		this.otherHasGone = false
		this.alreadyPassed = false
		this.phase = 1
		// if the view and player don't match, switch the view
		if (this.currentView !== this.currentPlayer.num) {
			this.changeView()
		}
	}

	// roll the dice
    rollDice() {
		// don't allow rolling if the view and player don't match or if the phase isn't 1
		if (this.currentPlayer.num !== this.currentView || this.phase !== 1) {
			return
		}
        this.wdice = this.wdice.map(() => QwixxModel.rollDie());
        this.dice = this.dice.map(() => QwixxModel.rollDie());
		this.phase = 2
    }

	// if the player chooses to pass
	pass() {
		// if the view and player don't match or if it's the end of the turn, cannot pass
		if (this.currentPlayer.num !== this.currentView || this.phase === 4) {
			return
		}
		// if already passed, there is a penalty and go to the end of the turn
		if (this.alreadyPassed) {
			this.penalty -= 5
			this.phase = 4
		}
		else if (this.phase === 2) {
			// if phase is 2, go to phase 3 and note the player has passed
			this.phase = 3
			this.alreadyPassed = true
		}
		else if (this.phase === 3) {
			// if the phase is 3, go to phase 4 (end of turn)
			this.phase = 4
		}
	}

	// check if the other player's check is valid based on current player's roll
	checkIfNBValidOther(col, foundCol, foundVal) {
		if (this.otherHasGone === true) {
			return false
		}
		if (foundCol >= col || this.wdice[0] + this.wdice[1] !== foundVal) {
			return false
		}
		this.otherHasGone = true
		return true
	}

	// check if the player's check is valid
	checkIfNBValid(row, col) {
		const foundCol = this.findCol(row)
		const foundVal = this.findValue(row, col)
		// if the current view and player don't match, it must be the other player doing their move
		if (this.currentPlayer.num !== this.currentView) {
			return this.checkIfNBValidOther(col, foundCol, foundVal)
		}
		// if the lockbox is crossed out in either player's row, the move is not valid
		if (this.players[0].numberBoxes[row][this.maxColumn - 1].crossedOut || this.players[1].numberBoxes[row][this.maxColumn - 1].crossedOut) {
			return false
		}
		// if the col is a lockbox col, check if there are enough Xs checked off
		if (col === this.maxColumn - 1) {
			if (this.Xs[row] >= 5) {
				return true
			}
		}
		// otherwise, proceed with main move logic
		if (this.phase === 2) {
			if (foundCol >= col || this.wdice[0] + this.wdice[1] !== foundVal) {
				return false
			}
			this.phase = 3
			return true
		}
		else if (this.phase === 3) {
			if (foundCol >= col || ((this.wdice[0] + this.dice[row]) !== foundVal && (this.wdice[1] + this.dice[row]) !== foundVal)) {
				return false
			}
			this.phase = 4
			return true
		}
		return false
	}

	// find the last checked off box in a row
	findCol(row) {
		let foundCol = -1
		for (let i = 0; i < this.numberBoxes[row].length; ++i) {
			if (this.numberBoxes[row][i].crossedOut === true) {
				foundCol = i
			}
		}
		return foundCol
	}

	// tally the Xs in each row and update the number of Xs and the totals for each row
	tallyXs() {
		this.total = 0
		this.Xs = this.clearArray(this.Xs)
		this.totals = this.clearArray(this.totals)
		for (let i = 0; i < this.numberBoxes.length; ++i) {
			for (let j = 0; j < this.numberBoxes[i].length; ++j) {
				if (this.numberBoxes[i][j].crossedOut === true) {
					this.Xs[i] += 1
					const val = this.findValue(i, j)
					this.totals[i] += val
					this.total += val
				}
			}
		}
	}

	// find the value of the row and col
	findValue(row, col) {
		const firstHalf = col + 2
		const secHalf = this.maxColumn - col
		if (row < this.numRows/2) {
			return firstHalf
		}
		else {
			return secHalf
		}
		return 0
	}

	// clear a given array
	clearArray(array) {
		for (let i = 0; i < array.length; ++i) {
			array[i] = 0
		}
		return array
	}

	// return a random integer in the range [1, 6]
    static rollDie() {
        return Math.floor(Math.random() * 6 + 1);
    }
}

// a number box
class NumberBox {
	constructor() {
		this.crossedOut = false
		this.lockBox = false
	}
}

// a player
class Player {
	constructor(num, numberBoxes) {
		this.num = num
		this.numberBoxes = numberBoxes
		this.total = 0
		this.penalty = 0
		this.totals = [0, 0, 0, 0]
	}
}
