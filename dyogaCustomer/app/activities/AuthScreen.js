import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View, Text } from 'react-native';
import EditText from '../components/EditText';
import HeaderTitle from '../components/HeaderTitle';
import DYogaButton from '../components/DYogaButton';
import { useState,useEffect } from 'react';
import {auth,createUserWithEmailAndPassword,signInWithEmailAndPassword} from '../firebase'
import {constStrings} from '../utils/Constants'
import useAuthChange from '../hooks/useAuthChange';
import {getAllClasses, updateClasses, queryClasses} from '../data/classRepository'

// partly taken from https://github.com/mattfrances/FirebaseExpoAuthentication/blob/main/screens/LoginScreen.js

export default function AuthScreen({navigation}) {

    const [error, setError] =  useState(null);
    const [email, setEmail] =  useState('');
    const [password, setPassword] =  useState('');

    useAuthChanged(user => {
        if (user) {
            navigation.navigate("DashboardScreen")
        
            // updateClasses([{"comments": "updated fire", "courseId": 3, "createdAt": "2023-11-04 21:34:31", "date": "2023-11-06 21:34:11", "deleted": false, "docId": "167909", "id": 6, "instructor": "1111", "reference": "167909", "room": "1111", "synced": true, "updatedAt": "2023-11-04 21:34:31", "userId": "abOkMwtbLgbBYe2XbMCDCiWeKEw2"}, {"comments": "fire update", "courseId": 2, "createdAt": "2023-11-04 20:48:07", "date": "2023-12-04 20:50:40", "deleted": false, "docId": "e4da3b", "id": 5, "instructor": "tanim new", "reference": "e4da3b", "room": "101", "synced": true, "updatedAt": "2023-11-04 20:53:05", "userId": "abOkMwtbLgbBYe2XbMCDCiWeKEw2"}]
            // ,() => {getAllClasses((result) => console.log(result))});
            

            // queryClasses([{ field: "comments", value: "updated fire" }, { field: "courseId", value: "1" }], (result) => {

            //     console.log(result, "authnew");

            // }, ()=>{})

          }
    })

    const inputValidation = () =>{

        if(!email){
            setError('Email is required');
            return false;
        }

        if(!password){
            setError('Password is required');
            return false;
        }

        if(password.length < 6){
            setError('Password must be at least 6 characters');
            return false;
        }

        setError(null);

        return true;
    }

    const onContinue = () => {

    if(!inputValidation()){
        return;
    }

      
       createUserWithEmailAndPassword(auth, email, password)
        .then(userCredentials => {
          const user = userCredentials.user;
          console.log('Registered with:', user.email);
        })
        .catch(error => {
            
            if(error.code === constStrings.userExists){
                
                signInWithEmailAndPassword(auth, email, password)
                .then(() => navigation.navigate("DashboardScreen"))
                .catch((error) => {
                  
                    setError("Email or password is wrong");

                  });
            }
        })


  
    

    }

    return (
    <View style={styles.container}>

     <View style = {{...styles.row, marginTop: 60}}>
         <HeaderTitle title={"Register | Login"}></HeaderTitle>
    </View>

    <View style = {styles.row}>
        <Text>
        New or have an account?{"\n"}Enter your email and a password
        </Text>
    </View>

    <View style = {{...styles.row, marginTop:10}}>
        <View style={styles.column}>

            <Text style={styles.label}>Email</Text>
            <EditText placeholder={"example@example.com"} value={email} onChangeText={ text => setEmail(text)}></EditText>

        </View>
    </View>


    <View style = {styles.row}>
        
        <View style={styles.column}>
            <Text style={styles.label}>Password</Text>
            <EditText placeholder={"password"} secureTextEntry={true} value={password} onChangeText={ text => setPassword(text)}></EditText>
        </View>
    </View>

   {error && <Text style={{...styles.row,color: 'red'}}>{error}</Text>}

    <View style = {{...styles.row, justifyContent:'center'}}>
        <DYogaButton title='Continue' onPress={onContinue}/>
    </View>

    <StatusBar style="auto" />

    </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        paddingLeft: 30,
        paddingRight: 30,
        width:'100%',
    },

    row: {
        flexDirection: 'row',
        justifyContent: 'flex-start',
        alignItems: 'center',
        margin: 10,

      },

    column:{
        flexDirection: 'column'
    },
    label:{
        paddingBottom: 10
    }
});


