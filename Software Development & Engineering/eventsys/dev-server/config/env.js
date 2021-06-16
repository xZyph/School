import express from 'express';
import morgan from 'morgan';
import cors from 'cors';
import bodyParser from 'body-parser';

export function setEnvironment(app) {
  // FIXED BUG! For some reason it reads the extra space from NODE_ENV
  if(process.env.NODE_ENV !== "production ") {
    setDevEnv(app);
  }
  else {
    setProdEnv(app);
  }
}

function setDevEnv(app) {
  process.env.NODE_ENV = 'development';
  process.env.DB_URL = 'mongodb://localhost:27017/eventsys-dev-db';
  process.env.TOKEN_SECRET = 'BH*LnaUG=s!xgs.I9qT6v~#M{Hz4B8hk<pFj+tigS}M71[&Jq8@H)Y_pmO*UogU';
  app.use(bodyParser.json());
  app.use(morgan('dev'));
  app.use(cors());
}

function setProdEnv(app) {
  process.env.DB_URL = 'mongodb+srv://eventsys:wOlTCw8sCAR3hhPo@eventsys-rubfw.mongodb.net/test?retryWrites=true&w=majority';
  process.env.TOKEN_SECRET = 'p4Y4)7/X-v_IMipcvfn7yu}Ud[pg`QEF7d^tCBZk!^{+1QZT+{Re_b5Q@V3r42K';
  app.use(bodyParser.json());
  app.use(express.static(__dirname + "/../dist"));
}