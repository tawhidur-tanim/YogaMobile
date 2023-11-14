import {useEffect} from 'react';
import {auth} from '../firebase'

const useAuthChanged = (callBack) => {
    useEffect(() => {
        return auth.onAuthStateChanged(user => {
            if (callBack && typeof callBack == "function") {
                callBack(user);
            }
        })
    }, []);
}

export default useAuthChanged