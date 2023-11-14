import {Image, ImageBackground, StyleSheet, Text, View} from 'react-native';
import {StatusBar} from "expo-status-bar";
import {DYogaColors} from "../utils/DYogaColors";
import DYogaButton from "../components/DYogaButton";
import {useEffect, useState} from "react";
import {constStrings} from "../utils/Constants";
import {auth} from "../firebase";

// splash screen images
const SPLASH_1 = require('../../assets/splash_1.png');
const SPLASH_2 = require('../../assets/splash_2.png');

export default function GetStarted({ navigation }) {

    const currentUser = auth.currentUser
    const [splashImageIndex, setSplashImageIndex] = useState(0)

    const changeBackgroundImage = () => {
        setTimeout(() => {
            if (splashImageIndex === 0) {
                setSplashImageIndex(1)
            } else {
                setSplashImageIndex(0)
            }
        }, 5000)
    }

    useEffect(() => {
        changeBackgroundImage()

        console.log(currentUser)
        if(currentUser && currentUser.id) {
            navigation.replace("DashboardScreen")
        }
    }, [splashImageIndex])

    return (<View style={SplashScreenStyles.container}>
            <StatusBar translucent={true}/>
            <ImageBackground source={splashImageIndex === 0 ? SPLASH_1 : SPLASH_2} resizeMode="cover"
                             style={SplashScreenStyles.image}>
                <View style={SplashScreenStyles.iconContainer}>
                    <Image source={require("../../assets/icon.png")}/>
                </View>
                <View style={SplashScreenStyles.buttonContainer}>
                    <DYogaButton title={constStrings.getStarted} onPress={() => navigation.replace("AuthScreen")}/>
                </View>
                <View style={SplashScreenStyles.footer}>
                    <Text style={SplashScreenStyles.footerText}>&copy; {constStrings.copyright}</Text>
                    <Text style={SplashScreenStyles.footerText}>{constStrings.productOfGroupII}</Text>
                </View>
            </ImageBackground>
        </View>
    );
}

const SplashScreenStyles = StyleSheet.create({
    container: {
        flex: 1,
        width: "100%",
        height: "100%",
        backgroundColor: DYogaColors.grey,
        alignItems: 'center',
        justifyContent: 'center',
    },
    image: {
        flex: 1,
        width: "100%",
        justifyContent: 'center',
        alignItems: "center"
    },
    buttonContainer: {
        paddingVertical: 20
    },
    iconContainer: {
        paddingTop: "47%",
        flexGrow: 5,
        justifyContent: 'center',
        alignItems: "center"
    },
    footer: {
        paddingVertical: 30,
        justifyContent: "flex-end"
    },
    footerText: {
        fontFamily: "Inter",
        fontWeight: '600',
        fontSize: 11,
        marginBottom: 8,
        color: "rgba(0,0,0,0.51)"
    }
});
