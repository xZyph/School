"use strict";

var _express = _interopRequireDefault(require("express"));

var _routes = require("./routes");

var _env = require("./config/env");

var _db = require("./config/db");

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

const app = (0, _express.default)();
const port = 3000;
(0, _env.setEnvironment)(app);
(0, _db.connectToDB)();
(0, _routes.registerEvents)(app);
app.get('/', (req, res) => {
  if (process.env.NODE_ENV !== "production ") {
    return res.send('Hi thar! Running in development mode...');
  } else {
    return res.sendFile('index.html', {
      root: __dirname + '/../dist/'
    });
  }
});
app.listen(port, () => console.log("Eventsys backend listening on port: ".concat(port, " in ").concat(process.env.NODE_ENV, " mode!")));