// Import the functions you need from the SDKs you need
// this is taken from firebase console

import {initializeApp} from "firebase/app";
import {
    initializeAuth,
    getReactNativePersistence,
    createUserWithEmailAndPassword,
    signInWithEmailAndPassword
} from "firebase/auth/react-native"
import ReactNativeAsyncStorage from "@react-native-async-storage/async-storage"

import {getFirestore} from 'firebase/firestore'
import AsyncStorage from "@react-native-async-storage/async-storage";

const firebaseConfig = {
    apiKey: "AIzaSyBdKRvEnTYHviGRtPufmCvNbv3b_lwJjnI",
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

const auth = initializeAuth(app, {
  persistence: getReactNativePersistence(ReactNativeAsyncStorage)
});

// Initialize Cloud Firestore and get a reference to the service
const db = getFirestore(app);

const ACCOUNT_AUTHENTICATED = "ACCOUNT_AUTHENTICATED"

export {app, auth, createUserWithEmailAndPassword, signInWithEmailAndPassword, db, ACCOUNT_AUTHENTICATED}