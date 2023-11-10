import {Image, ImageBackground, StyleSheet, Text, View} from 'react-native';
import {StatusBar} from "expo-status-bar";
import {DYogaColors} from "../utils/DYogaColors";
import DYogaButton from "../components/DYogaButton";


export default function GetStarted() {

    return (<View style={SplashScreenStyles.container}>
            <StatusBar translucent={true}/>
            <ImageBackground source={require('../../assets/splash_1.png')} resizeMode="cover"
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
        fontFamily: "RobotoSlab",
        fontWeight: '500',
        fontSize: 11,
        marginBottom: 8,
        color: "rgba(0,0,0,0.51)"
    }
});
