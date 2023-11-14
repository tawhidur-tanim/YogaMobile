import {auth, db} from '../firebase'
import { collection, getDocs,doc, updateDoc, query, where } from 'firebase/firestore';


const collectionName =  "classInstances";

const bookedCollectionName = () => auth.currentUser.uid.substring(0,6)+"_bookedClassIntances";

const getAllClasses = (callback) => {

  const querySnapshot = getDocs(collection(db, collectionName));
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




// 167909


export {getAllClasses}

