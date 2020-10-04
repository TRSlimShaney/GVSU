const express = require('express')
const app = express()
const port = 3000
app.set('views','./views')
app.set('view engine','pug')
app.use(express.static('public'));


//  setup the quixx board with the query
app.get('/quixx', function (req, res) {
    res.render('quixx', {row: req.query.row, col: req.query.col})
    })

app.listen(port)
