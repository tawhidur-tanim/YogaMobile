import {Image, ImageBackground, StyleSheet, Text, View} from 'react-native';
import {StatusBar} from "expo-status-bar";
import {DYogaColors} from "../utils/DYogaColors";
import DYogaButton from "../components/DYogaButton";
import {useState} from "react";

// splash screen images
const SPLASH_1 = require('../../assets/splash_1.png');
const SPLASH_2 = require('../../assets/splash_2.png');

export default function GetStarted() {

    const [splashImage, setSplashImage] = useState(SPLASH_1)

    setInterval(() => {
        if (splashImage === SPLASH_1) {
            setSplashImage(SPLASH_2)
        } else {
            setSplashImage(SPLASH_1)
        }
    }, 5000)

    return (<View style={SplashScreenStyles.container}>
            <StatusBar translucent={true}/>
            <ImageBackground source={splashImage} resizeMode="cover"
                             style={SplashScreenStyles.image}>
                <View style={SplashScreenStyles.iconContainer}>
                    <Image source={require("../../assets/icon.png")}/>
                </View>
                <View style={SplashScreenStyles.buttonContainer}>
                    <DYogaButton title={'Get Started'}/>
                </View>
                <View style={SplashScreenStyles.footer}>
                    <Text style={SplashScreenStyles.footerText}>&copy; Copyright 2023</Text>
                    <Text style={SplashScreenStyles.footerText}>Product of Group II</Text>
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
