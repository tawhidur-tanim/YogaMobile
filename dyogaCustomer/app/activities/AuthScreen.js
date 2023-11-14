import {StatusBar} from 'expo-status-bar';
import {StyleSheet, View, Text, ToastAndroid} from 'react-native';
import EditText from '../components/EditText';
import HeaderTitle from '../components/HeaderTitle';
import DYogaButton from '../components/DYogaButton';
import {useState} from 'react';
import {auth, createUserWithEmailAndPassword, signInWithEmailAndPassword} from '../firebase'
import {constDimensions, constStrings} from '../utils/Constants'
import {DYogaColors} from "../utils/DYogaColors";

// partly taken from https://github.com/mattfrances/FirebaseExpoAuthentication/blob/main/screens/LoginScreen.js

export default function AuthScreen({navigation}) {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const inputValidation = () => {
        if (!email) {
            ToastAndroid.show(constStrings.emailIsRequired, ToastAndroid.LONG);
            return false;
        }

        if (!password) {
            ToastAndroid.show(constStrings.passwordIsRequired, ToastAndroid.LONG);
            return false;
        }

        if (password.length < 6) {
            ToastAndroid.show(constStrings.passwordIsRequirements, ToastAndroid.LONG);
            return false;
        }

        return true;
    }

    const onContinue = () => {
        if (!inputValidation()) {
            return;
        }

        createUserWithEmailAndPassword(auth, email, password).then(userCredentials => {
            const user = userCredentials.user;
            console.log('Registered with:', user.email);
            navigation.navigate("DashboardScreen")
        }).catch(error => {
            if (error.code !== constStrings.userExists) {
                console.log(error)
                return
            }

            // attempt user sign in
            signInWithEmailAndPassword(auth, email, password).then(userCredentials => {
                const user = userCredentials.user;
                console.log('Logged in with:', user.email);
                navigation.navigate("DashboardScreen")
            }).catch((error) => {
                console.log(error)
                ToastAndroid.show(constStrings.loginFailed, ToastAndroid.LONG);
            });
        })
    }

    return (
        <View style={AuthScreenStyles.container}>
            <View style={{...AuthScreenStyles.row, marginTop: constDimensions.screenPaddingTop}}>
                <HeaderTitle title={constStrings.registerLogin}></HeaderTitle>
            </View>

            <View style={{...AuthScreenStyles.row, flexDirection: 'column', alignItems: 'flex-start'}}>
                <Text style={AuthScreenStyles.text}>New or have an account?</Text>
                <Text style={AuthScreenStyles.text}>Enter your email and a password</Text>
            </View>

            <View style={{...AuthScreenStyles.row, marginTop: 20}}>
                <View style={AuthScreenStyles.column}>
                    <Text style={AuthScreenStyles.label}>{constStrings.email}</Text>
                    <EditText value={email} onChangeText={text => setEmail(text)} autoFocus={true}/>
                </View>
            </View>

            <View style={AuthScreenStyles.row}>
                <View style={AuthScreenStyles.column}>
                    <Text style={AuthScreenStyles.label}>Password</Text>
                    <EditText secureTextEntry={true} value={password} onChangeText={text => setPassword(text)}/>
                </View>
            </View>

            <View style={{...AuthScreenStyles.row, justifyContent: 'center', marginTop: 40}}>
                <DYogaButton title={constStrings.continueText} onPress={onContinue}/>
            </View>

            <StatusBar style="auto"/>
        </View>
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


