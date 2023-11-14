import {auth, db} from '../firebase'
import { collection, getDocs,doc, updateDoc, query, where } from 'firebase/firestore';


const collectionName =  "classInstances";

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


const updateClasses = ( updates, success, fail) => {
  updates.forEach(update => {

    const docRef = doc(db, collectionName, update.docId); 
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

const queryClasses = (conditions, success, fail) => {
  const results = new Map();

  let queriesCompleted = 0;

  conditions.forEach(condition => {
    const q = query(collection(db, collectionName), where(condition.field, "==", condition.value));
    getDocs(q)
      .then(querySnapshot => {

        querySnapshot.forEach(doc => {

          if (!results.has(doc.id)) {
            results.set(doc.id, doc.data());
          }

        });

        queriesCompleted++;
        if (queriesCompleted === conditions.length) {
         
          if (typeof success === 'function') {
            success(Array.from(results.values()));
          }
        }
      
      })
      .catch(error => {
        if (typeof fail === 'function') {
          fail(error);
        }
      });
  });






  
};


// 167909


export {getAllClasses, updateClasses, queryClasses}

