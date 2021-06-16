import store from '../store';
import axios from 'axios';
import * as Auth from '../services/AuthService';

export function http() {
  return axios.create({
    baseURL: store.state.apiUrl,
    headers: {
      Authorization: Auth.getToken()
    }
  })
}