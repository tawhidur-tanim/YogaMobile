// Import the functions you need from the SDKs you need
// this is taken from firebase console

import { initializeApp } from "firebase/app";
import {getAuth,createUserWithEmailAndPassword, signInWithEmailAndPassword} from 'firebase/auth'
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyCbvouckcJZUSIHP2LbxtJKnOLz8wKXvzE",
  authDomain: "dyoga-68ec6.firebaseapp.com",
  databaseURL: "https://dyoga-68ec6-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "dyoga-68ec6",
  storageBucket: "dyoga-68ec6.appspot.com",
  messagingSenderId: "13903446448",
  appId: "1:13903446448:web:f92a404e035e8ebde89675",
  measurementId: "G-R322QYPMVY"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
//const analytics = getAnalytics(app);

const auth =  getAuth(app);

export {app, auth, createUserWithEmailAndPassword, signInWithEmailAndPassword}