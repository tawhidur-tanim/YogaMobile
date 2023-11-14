import {auth, db} from '../firebase'
import {collection, getDocs, setDoc, doc,updateDoc} from 'firebase/firestore';

const collectionName = "classInstances";

const getAllClasses = (callback) => {
    const querySnapshot = getDocs(collection(db, collectionName));
    querySnapshot.then((snapshot) => {
        if (typeof callback != "function") return;
        if (snapshot.empty) {
            callback([])
            return;
        }
        const result = [];
        snapshot.forEach((doc) => {
            result.push({...doc.data(), docId: doc.id});
        });
        callback(result);
    }).catch((error) => {
        console.error("Error getting documents: ", error);
    });
}

const updateUserBookedClasses = (data) => {
    const subDocumentName = "data"
    const documentName = auth.currentUser.uid.substring(0, 6) + "_bookedClassInstances"

    return setDoc(doc(db, documentName, subDocumentName), data);
}

const getAllBookedClasses = (callback) => {
    const subDocumentName = "data"
    const documentName = auth.currentUser.uid.substring(0, 6) + "_bookedClassInstances"
    const querySnapshot = getDocs(collection(db, documentName));
    querySnapshot.then((snapshot) => {
        if (typeof callback != "function") return;
        if (snapshot.empty) {
            callback([])
            return;
        }
        let result = [];
        snapshot.forEach((doc) => {
            result = doc.data()
        });
        callback(result);
    }).catch((error) => {
        console.error("Error getting documents: ", error);
    });
}

export {getAllClasses, updateUserBookedClasses, getAllBookedClasses}

