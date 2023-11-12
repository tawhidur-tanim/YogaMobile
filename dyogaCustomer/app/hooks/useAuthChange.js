import { useState,useEffect } from 'react';
import {auth} from '../firebase'



export default useAuthChanged  = (callBack) =>{

    useEffect(() => {
        const unsubscribe = auth.onAuthStateChanged(user => {
            if(callBack && typeof callBack == "function"){

                callBack(user);
            }

        })
    
        return unsubscribe
      }, []);

}