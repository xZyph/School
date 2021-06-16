import express from 'express'
import { registerEvents } from "./routes";
import { setEnvironment } from "./config/env";
import { connectToDB } from './config/db';


const app = express()
const port = 3000

setEnvironment(app);
connectToDB();
registerEvents(app);

app.get('/', (req, res) => {
  if(process.env.NODE_ENV !== "production ") {
    return res.send('Hi thar! Running in development mode...');
  }
  else {
    return res.sendFile('index.html', { root: __dirname + '/../dist/' });
  }
});

app.listen(port, () => console.log(`Eventsys backend listening on port: ${port} in ${process.env.NODE_ENV} mode!`))