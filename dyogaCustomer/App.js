import { StyleSheet } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import SplashScreen from "./app/activities/SplashScreen";
import PlaygroundScreen from "./app/activities/PlaygroundScreen";
import AuthScreen from "./app/activities/AuthScreen";
import CheckoutScreen from "./app/activities/CheckoutScreen";
import DashboardScreen from "./app/activities/DashboardScreen";

const Stack = createNativeStackNavigator();

export default function App() {
  return (
      <NavigationContainer>
          <Stack.Navigator>
              <Stack.Screen name="PlaygroundScreen" component={PlaygroundScreen} />
              <Stack.Screen name="SplashScreen" component={SplashScreen} />
              <Stack.Screen name="AuthScreen" component={AuthScreen} />
              <Stack.Screen name="DashboardScreen" component={DashboardScreen} />
              <Stack.Screen name="CheckoutScreen" component={CheckoutScreen} />
          </Stack.Navigator>
      </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
