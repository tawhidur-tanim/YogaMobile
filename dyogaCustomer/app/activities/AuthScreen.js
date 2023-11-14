import {StatusBar} from 'expo-status-bar';
import {StyleSheet, View, Text, SafeAreaView} from 'react-native';
import EditText from '../components/EditText';
import HeaderTitle from '../components/HeaderTitle';
import DYogaButton from '../components/DYogaButton';
import {useRef, useState} from 'react';
import {ACCOUNT_AUTHENTICATED, auth, createUserWithEmailAndPassword, signInWithEmailAndPassword} from '../firebase'
import {constDimensions, constStrings} from '../utils/Constants'
import {DYogaColors} from "../utils/DYogaColors";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Toaster from "../components/Toaster";

// partly taken from https://github.com/mattfrances/FirebaseExpoAuthentication/blob/main/screens/LoginScreen.js

export default function AuthScreen({navigation}) {

    const passwordInputField = useRef()
    const [loading, setLoading] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const inputValidation = () => {
        if (!email) {
            Toaster.show({message: constStrings.emailIsRequired})
            return false;
        }

        if (!password) {
            Toaster.show({message: constStrings.passwordIsRequired})
            return false;
        }

        if (password.length < 6) {
            Toaster.show({message: constStrings.passwordIsRequirements})
            return false;
        }

        return true;
    }

    const onContinue = () => {
        if (!inputValidation()) {
            return;
        }

        setLoading(true)

        createUserWithEmailAndPassword(auth, email, password).then(authenticated).catch(error => {
            if (error.code !== constStrings.userExists) {
                console.log(error)
                return
            }

            // attempt user sign in
            signInWithEmailAndPassword(auth, email, password).then(authenticated).catch((error) => {
                console.log(error)
                setLoading(false)
                Toaster.show({message: constStrings.loginFailed})
            });
        })
    }

    const authenticated = (userCredentials) => {
        const user = userCredentials.user;
        console.log("User authenticated: ", user)
        AsyncStorage.setItem(ACCOUNT_AUTHENTICATED, "true").then(() => {
            navigation.replace("DashboardScreen")
        })
    }

    return (
        <SafeAreaView style={AuthScreenStyles.container}>
            <View style={{...AuthScreenStyles.row, marginTop: constDimensions.screenPaddingTop}}>
                <HeaderTitle title={constStrings.registerLogin}></HeaderTitle>
            </View>

            <View style={{...AuthScreenStyles.row, flexDirection: 'column', alignItems: 'flex-start'}}>
                <Text style={AuthScreenStyles.text}>{constStrings.newOrHaveAnAccount}</Text>
                <Text style={AuthScreenStyles.text}>{constStrings.enterUsernameAndPassword}</Text>
            </View>

            <View style={{...AuthScreenStyles.row, marginTop: 20}}>
                <View style={AuthScreenStyles.column}>
                    <Text style={AuthScreenStyles.label}>{constStrings.email}</Text>
                    <EditText value={email} onChangeText={text => setEmail(text)} autoFocus={true} returnKeyType="next"
                              onSubmitEditing={() => {
                                  passwordInputField.current.focus();
                              }}/>
                </View>
            </View>

            <View style={AuthScreenStyles.row}>
                <View style={AuthScreenStyles.column}>
                    <Text style={AuthScreenStyles.label}>Password</Text>
                    <EditText secureTextEntry={true} value={password} onChangeText={text => setPassword(text)}
                              innerRef={passwordInputField}/>
                </View>
            </View>

            <View style={{...AuthScreenStyles.row, justifyContent: 'center', marginTop: 40}}>
                <DYogaButton title={constStrings.continueText} onPress={onContinue} isLoading={loading}/>
            </View>

            <StatusBar style="auto"/>
        </SafeAreaView>
    );
}

const AuthScreenStyles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: DYogaColors.screenBackground,
        paddingLeft: constDimensions.screenPaddingHorizontal,
        paddingRight: constDimensions.screenPaddingHorizontal,
        width: '100%',
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'flex-start',
        alignItems: 'center',
        marginBottom: 10,
    },
    column: {
        flexDirection: 'column',
        width: '100%'
    },
    label: {
        fontFamily: "RobotoSlab",
        color: DYogaColors.grey,
        fontSize: 12,
        paddingBottom: 7
    },
    text: {
        fontFamily: "RobotoSlab",
        color: DYogaColors.grey
    }
});


