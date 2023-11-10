import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View } from 'react-native';
import EditText from '../components/EditText';
import HeaderTitle from '../components/HeaderTitle';

export default function AuthScreen() {
    return (
        <View style={styles.container}>

     <View style = {styles.row}>
         <HeaderTitle title={"Register | Login"}></HeaderTitle>
    </View>

    <View style = {styles.row}>
        <EditText placeholder={"example@example.com"}></EditText>
    </View>


    <View style = {styles.row}>
        <EditText placeholder={"password"}></EditText>
    </View>


        
        <StatusBar style="auto" />
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

    row: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        alignItems: 'center',
        height: 100,
      },
});


