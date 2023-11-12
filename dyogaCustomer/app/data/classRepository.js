import {auth, db} from '../firebase'
import { collection, getDocs,doc, updateDoc } from 'firebase/firestore';


const collectionName = () => auth.currentUser.uid.substring(0,6)+"_classInstances";

const getAllClasses = (callback) => {

  const querySnapshot = getDocs(collection(db, collectionName()));
  querySnapshot
    .then((snapshot) => {

      if(typeof callback != "function") return;

      if (snapshot.empty) {
        
        callback([])

        return;
      }
      const result = [];
      snapshot.forEach((doc) => {
        result.push({...doc.data(), docId: doc.id});
      });

 

      callback(result);
  
    })
    .catch((error) => {
      console.error("Error getting documents: ", error);
    });
}


const updateClasses = ( updates, success, fail) => {
  updates.forEach(update => {

    const docRef = doc(db, collectionName(), update.docId); 
    updateDoc(docRef, update)
      .then(() => {

        if(typeof success == "function"){
          success()
        }

      })
      .catch(error => {
        
        if(typeof fail == "function"){
          fail(error)
        }


      });
  });
};


// 167909


export {getAllClasses, updateClasses}

