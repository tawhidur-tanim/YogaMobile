import { StyleSheet, View } from 'react-native';
import {StatusBar} from "expo-status-bar";

export default function SplashScreen() {
  return (
    <View style={styles.container}>
      <StatusBar translucent={true} />
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
});
