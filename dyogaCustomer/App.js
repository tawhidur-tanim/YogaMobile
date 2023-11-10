import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import GetStarted from "./app/activities/GetStarted";
import PlaygroundScreen from "./app/activities/PlaygroundScreen";
import AuthScreen from "./app/activities/AuthScreen";
import CheckoutScreen from "./app/activities/CheckoutScreen";
import DashboardScreen from "./app/activities/DashboardScreen";
import * as Font from "expo-font";
import {useEffect, useState} from "react";

const Stack = createNativeStackNavigator();

export default function App() {

    const [fontsLoaded, setFontLoaded] = useState(false)

    useEffect(() => {
        Font.loadAsync({
            "RobotoSlab": require("./assets/fonts/RobotoSlab.ttf"),
            "RobotoSlabBold": require("./assets/fonts/RobotoSlabBold.ttf")
        }).catch(() => console.log("Failed to load fonts")).finally(() => {
            console.log("finaly")
            setFontLoaded(true)
        })
    }, [])

    if(!fontsLoaded) {
        return null
    }

    return (<NavigationContainer>
            <Stack.Navigator screenOptions={{
                headerShown: false
            }}>
                <Stack.Screen name="PlaygroundScreen" component={PlaygroundScreen}/>
                <Stack.Screen name="GetStarted" component={GetStarted}/>
                <Stack.Screen name="AuthScreen" component={AuthScreen}/>
                <Stack.Screen name="DashboardScreen" component={DashboardScreen}/>
                <Stack.Screen name="CheckoutScreen" component={CheckoutScreen}/>
            </Stack.Navigator>
        </NavigationContainer>
    );
}