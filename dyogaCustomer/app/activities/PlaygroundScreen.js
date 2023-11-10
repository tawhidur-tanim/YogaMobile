import {StatusBar} from 'expo-status-bar';
import {Button, StyleSheet, View} from 'react-native';

export default function PlaygroundScreen({ navigation }) {

    function navigateTo(screenName) {
        console.log(`Navigating you to ${screenName}`)
        navigation.navigate(screenName)
    }

    return (
        <View style={styles.container}>
            <View style={styles.button}>
                <Button title={"GetStarted"} onPress={() => navigateTo("GetStarted")}>
                    Splash Screen
                </Button>
            </View>
            <View style={styles.button}>
                <Button title={"AuthScreen"} onPress={() => navigateTo("AuthScreen")}>
                    Auth Screen
                </Button>
            </View>
            <View style={styles.button}>
                <Button title={"DashboardScreen"} onPress={() => navigateTo("DashboardScreen")}>
                    Dashboard Screen
                </Button>
            </View>
            <View style={styles.button}>
                <Button title={"CheckoutScreen"} onPress={() => navigateTo("CheckoutScreen")}>
                    Checkout Screen
                </Button>
            </View>
            <StatusBar style="auto"/>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
    button: {
        marginBottom: 20
    }
});

