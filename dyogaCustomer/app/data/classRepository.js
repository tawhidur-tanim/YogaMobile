import {db} from '../firebase'
import {collection, getDocs, setDoc, doc, query, where, updateDoc} from 'firebase/firestore';

const getAllClasses = (callback) => {
    const querySnapshot = getDocs(collection(db, "classInstances"));
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
        console.error("Error getting documents [classInstances]: ", error);
        callback([])
    });
}

const updateUserBookedClasses = (currentUser, data) => {
    const subDocumentName = "data"
    const documentName = currentUser.uid.substring(0, 6) + "_bookedClassInstances"

    return setDoc(doc(db, documentName, subDocumentName), data);
}

const getAllBookedClasses = (currentUser, callback) => {
    if (!currentUser) {
        callback([])
        return
    }

    const documentName = currentUser.uid.substring(0, 6) + "_bookedClassInstances"
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
        console.error("Error getting documents [bookedClassInstances]: ", error);
        callback([])
    });
}

export {getAllClasses, updateUserBookedClasses, getAllBookedClasses}

