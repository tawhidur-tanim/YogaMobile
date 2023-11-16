import {Dimensions, Image, ImageBackground, StyleSheet, Text, View} from 'react-native';
import {StatusBar} from "expo-status-bar";
import {DYogaColors} from "../utils/DYogaColors";
import DYogaButton from "../components/DYogaButton";
import {useEffect, useState} from "react";
import {constStrings} from "../utils/Constants";
import {ACCOUNT_AUTHENTICATED, auth} from "../firebase";
import * as Progress from 'react-native-progress';
import AsyncStorage from "@react-native-async-storage/async-storage";
import {useNetInfo} from "@react-native-community/netinfo";
import Toaster from "../components/Toaster";

// splash screen images
const SPLASH_1 = require('../../assets/splash_1.png');
const SPLASH_2 = require('../../assets/splash_2.png');

export default function GetStarted({navigation}) {

    const netInfo = useNetInfo();
    const [netInfoCounter, setNetInfoCounter] = useState(0)
    const currentUser = auth.currentUser
    const screenWidth = Dimensions.get('window').width;
    const [loading, setLoading] = useState(false)
    const [triggeredTimeout, setTriggeredTimeout] = useState(false)
    const [splashImageIndex, setSplashImageIndex] = useState(0)
    const [displayGetStarted, setDisplayGetStarted] = useState(false)

    const changeBackgroundImage = () => {
        setTimeout(() => {
            if (splashImageIndex === 0) {
                setSplashImageIndex(1)
            } else {
                setSplashImageIndex(0)
            }
        }, 5000)
    }

    const loadUserData = () => {
        if (displayGetStarted) return;

        if (currentUser && currentUser.uid) {
            if (!netInfo.isConnected) {
                if (netInfoCounter > 3)
                    Toaster.show({
                        type: 'error',
                        message: constStrings.noInternetConnection
                    })
                setNetInfoCounter(netInfoCounter + 1)
                return
            }

            // todo: load in the user booked data
            setTimeout(() => {
                navigation.replace("DashboardScreen")
            }, 3000)
        }
    }

    useEffect(() => {
        changeBackgroundImage()

        AsyncStorage.getItem(ACCOUNT_AUTHENTICATED).then(value => {
            if (value && value === 'true') {
                setLoading(true)
                setDisplayGetStarted(false)
            } else {
                setLoading(false)
                setDisplayGetStarted(true)
            }
        }).finally(() => {
            if(!triggeredTimeout) {
                setTimeout(() => {
                    if(!displayGetStarted) return

                    AsyncStorage.removeItem(ACCOUNT_AUTHENTICATED).then(() => {
                        setLoading(false)
                        setDisplayGetStarted(true)
                    })
                }, 10000)
                setTriggeredTimeout(true)
            }
        })

        loadUserData();
    }, [splashImageIndex])

    return (<View style={SplashScreenStyles.container}>
            <StatusBar translucent={true}/>
            <ImageBackground source={splashImageIndex === 0 ? SPLASH_1 : SPLASH_2} resizeMode="cover"
                             style={SplashScreenStyles.image}>
                <View style={SplashScreenStyles.iconContainer}>
                    <Image source={require("../../assets/icon.png")}/>
                </View>
                <View style={{...SplashScreenStyles.buttonContainer}}>
                    {loading && <View style={{justifyContent: 'center', width: screenWidth, alignItems: 'center'}}>
                        <Progress.Circle size={30} indeterminate={true} borderColor={DYogaColors.white}
                                         borderWidth={3}/>
                    </View>}
                    {!loading && displayGetStarted &&
                        <DYogaButton title={constStrings.getStarted} onPress={() => navigation.replace("AuthScreen")}/>}
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
