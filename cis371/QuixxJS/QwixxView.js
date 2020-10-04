export default class QwixxView {
    constructor() {}

    // Ideally, this view class should be the only class interacting with the DOM.
    // However, it is the controller that responds to the click.
    // One solution is for the controller to call a View method that adds
    // a callback of the Controller's choosing.
    onRollButtonClicked(callback) {
        document.getElementById('roll').addEventListener('click', callback)
    }

	onNumberBoxClicked(callback) {
		// number boxes
		document.getElementById('nB0-0').addEventListener('click', function() {callback(0, 0)})
		document.getElementById('nB0-1').addEventListener('click', function() {callback(0, 1)})
		document.getElementById('nB0-2').addEventListener('click', function() {callback(0, 2)})
		document.getElementById('nB0-3').addEventListener('click', function() {callback(0, 3)})
		document.getElementById('nB0-4').addEventListener('click', function() {callback(0, 4)})
		document.getElementById('nB0-5').addEventListener('click', function() {callback(0, 5)})
		document.getElementById('nB0-6').addEventListener('click', function() {callback(0, 6)})
		document.getElementById('nB0-7').addEventListener('click', function() {callback(0, 7)})
		document.getElementById('nB0-8').addEventListener('click', function() {callback(0, 8)})
		document.getElementById('nB0-9').addEventListener('click', function() {callback(0, 9)})
		document.getElementById('nB0-10').addEventListener('click', function() {callback(0, 10)})
		document.getElementById('nB1-0').addEventListener('click', function() {callback(1, 0)})
		document.getElementById('nB1-1').addEventListener('click', function() {callback(1, 1)})
		document.getElementById('nB1-2').addEventListener('click', function() {callback(1, 2)})
		document.getElementById('nB1-3').addEventListener('click', function() {callback(1, 3)})
		document.getElementById('nB1-4').addEventListener('click', function() {callback(1, 4)})
		document.getElementById('nB1-5').addEventListener('click', function() {callback(1, 5)})
		document.getElementById('nB1-6').addEventListener('click', function() {callback(1, 6)})
		document.getElementById('nB1-7').addEventListener('click', function() {callback(1, 7)})
		document.getElementById('nB1-8').addEventListener('click', function() {callback(1, 8)})
		document.getElementById('nB1-9').addEventListener('click', function() {callback(1, 9)})
		document.getElementById('nB1-10').addEventListener('click', function() {callback(1, 10)})
		document.getElementById('nB2-10').addEventListener('click', function() {callback(2, 10)})
		document.getElementById('nB2-9').addEventListener('click', function() {callback(2, 9)})
		document.getElementById('nB2-8').addEventListener('click', function() {callback(2, 8)})
		document.getElementById('nB2-7').addEventListener('click', function() {callback(2, 7)})
		document.getElementById('nB2-6').addEventListener('click', function() {callback(2, 6)})
		document.getElementById('nB2-5').addEventListener('click', function() {callback(2, 5)})
		document.getElementById('nB2-4').addEventListener('click', function() {callback(2, 4)})
		document.getElementById('nB2-3').addEventListener('click', function() {callback(2, 3)})
		document.getElementById('nB2-2').addEventListener('click', function() {callback(2, 2)})
		document.getElementById('nB2-1').addEventListener('click', function() {callback(2, 1)})
		document.getElementById('nB2-0').addEventListener('click', function() {callback(2, 0)})
		document.getElementById('nB3-10').addEventListener('click', function() {callback(3, 10)})
		document.getElementById('nB3-9').addEventListener('click', function() {callback(3, 9)})
		document.getElementById('nB3-8').addEventListener('click', function() {callback(3, 8)})
		document.getElementById('nB3-7').addEventListener('click', function() {callback(3, 7)})
		document.getElementById('nB3-6').addEventListener('click', function() {callback(3, 6)})
		document.getElementById('nB3-5').addEventListener('click', function() {callback(3, 5)})
		document.getElementById('nB3-4').addEventListener('click', function() {callback(3, 4)})
		document.getElementById('nB3-3').addEventListener('click', function() {callback(3, 3)})
		document.getElementById('nB3-2').addEventListener('click', function() {callback(3, 2)})
		document.getElementById('nB3-1').addEventListener('click', function() {callback(3, 1)})
		document.getElementById('nB3-0').addEventListener('click', function() {callback(3, 0)})
		// lock boxes
		document.getElementById('nB0-5').addEventListener('click', function() {callback(0, 11)})
		document.getElementById('nB1-5').addEventListener('click', function() {callback(1, 11)})
		document.getElementById('nB2-5').addEventListener('click', function() {callback(2, 11)})
		document.getElementById('nB3-5').addEventListener('click', function() {callback(3, 11)})
	}

	onPassClicked(callback) {
		document.getElementById('pass').addEventListener('click', callback)
	}

	onViewClicked(callback) {
		document.getElementById('view').addEventListener('click', callback)
	}

	onDoneClicked(callback) {
		document.getElementById('done').addEventListener('click', callback)
	}

    // Update the board according to the current state of the model.
    // IMPORTANT:  The View should not modify the model.  All accesses
    // to the model should be read only.
    update(model) {
            model.wdice.forEach((die, index) => {
                document.getElementById(`die-w${index}`).innerHTML = die
            })

            model.dice.forEach((die, index) => {
                document.getElementById(`die-${index}`).innerHTML = die
            })

			model.totals.forEach((total, index) => {
				document.getElementById(`${index}-Tot`).innerHTML = total
			})

			document.getElementById(`0-wTot`).innerHTML = model.penalty
			document.getElementById(`1-wTot`).innerHTML = model.total
			document.getElementById(`curPlayer`).innerHTML = model.currentPlayer.num + 1
			document.getElementById(`curView`).innerHTML = model.currentView + 1

			for (let i = 0; i < model.numberBoxes.length; ++i) {
				for (let j = 0; j < model.numberBoxes[i].length; ++j) {
					if (model.numberBoxes[i][j].crossedOut === true) {
						document.getElementById(`nB${i}-${j}`).innerHTML = 'X'
					}
					else if (model.numberBoxes[i][j].lockBox === true) {
						document.getElementById(`nB${i}-${j}`).innerHTML = '&#128274;'
					}
					else {
						document.getElementById(`nB${i}-${j}`).innerHTML = model.findValue(i, j)
					}
				}
			}
        }
}
